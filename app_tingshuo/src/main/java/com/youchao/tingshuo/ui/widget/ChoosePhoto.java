package com.youchao.tingshuo.ui.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.adapter.ViewHolder;
import com.youchao.tingshuo.ui.widget.dialog.MyDialogBuilder;
import com.youchao.tingshuo.utils.AliOss;
import com.youchao.tingshuo.utils.BitmapUtils;
import com.youchao.tingshuo.utils.ChoosePhotoUtlis;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by dell on 2017/9/20.
 */

public abstract class ChoosePhoto {
    private Context mContext;
    private ChoosePhotoUtlis choosePhotoUtlis;
    private String title;
    private int mutiMax;
    private boolean muti;
    private int pics_upload_num, pics_upload_success_num = 0; //可上传的图片数量
    private List<Boolean> pics_upload_sf = new ArrayList<>(); //保存当前这张图片有没有上传成功
    public ChoosePhoto(Context mContext, String title) {
        this.mContext = mContext;
        this.title = title;
    }

    public ChoosePhoto setMuti(int mutiMax) {
        this.mutiMax = mutiMax;
        showActionSheet();
        return this;
    }

    /**
     * 选择单张不剪裁
     * @return
     */
    public ChoosePhoto setNoCropSingle(){
        muti = false;
        uploadSinglePic();
        showActionSheet();
        return this;
    }

    /**
     * 选择上传头像
     * @return
     */
    public ChoosePhoto setPortrait() {
        muti = false;
        uploadSinglePic();
        choosePhotoUtlis.setEnableCrop().setForceCrop();
        showActionSheet();
        return this;
    }

    private void uploadSinglePic(){
        choosePhotoUtlis = new ChoosePhotoUtlis(mContext, 6) {
            @Override
            public void onSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                final Bitmap image = BitmapFactory.decodeFile(resultList.get(0).getPhotoPath());

                final ProgressDialog progressDialog = new ProgressDialog(mContext);
                progressDialog.setTitle("图片上传中，请稍候...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new AliOss(mContext, AliOss.setPicName(AliOss.BucketName), BitmapUtils.compressImage(image)) {
                            @Override
                            protected void uploadProgress(long currentSize, long totalSize) {
                                progressDialog.setProgress((int) currentSize);
                                progressDialog.setMax((int) totalSize);
                                if (currentSize == totalSize) {
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void uploadSuccess(String myPicUrl) {
                                setPortraitonSuccess(myPicUrl);
                            }

                            @Override
                            protected void uploadFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                                setPortraitFailure();
                            }
                        }.start();
                    }
                }).start();
            }

            @Override
            public void onFailure(int requestCode, String errorMsg) {
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        };
    }


    /**
     * 选择上传单张图片,有本地缓存
     *
     * is_zhengfangxing 上传图片时是否有强制剪裁为正方形
     *
     * @return
     */
    public ChoosePhoto setPic(final boolean is_zhnegfangxing) {
        muti = false;
        choosePhotoUtlis = new ChoosePhotoUtlis(mContext) {
            @Override
            public void onSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                BitmapUtils.setBitmapDegree(resultList.get(0).getPhotoPath());
                final Bitmap image = BitmapUtils.compressBitmap(resultList.get(0).getPhotoPath(), 720, 1080);
                if (image == null) {
                    return;
                }
                final String imgSrc = "file://" + resultList.get(0).getPhotoPath();  //imgsrc 本地路径
                Log.e("单张图片本地路径", imgSrc);
                final ProgressDialog progressDialog = new ProgressDialog(mContext);
                progressDialog.setTitle("图片上传中，请稍候...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new AliOss(mContext, AliOss.setPicName(AliOss.BucketName, image.toString()), BitmapUtils.compressImage(image)) {
                            @Override
                            protected void uploadProgress(long currentSize, long totalSize) {
                                progressDialog.setProgress((int) currentSize);
                                progressDialog.setMax((int) totalSize);
                                if (currentSize == totalSize) {
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void uploadSuccess(String myPicUrl) {
                                progressDialog.dismiss();
                                setPicSuccess(imgSrc,myPicUrl);
                            }

                            @Override
                            protected void uploadFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                                progressDialog.dismiss();
                                setPortraitFailure();
                            }
                        }.start();
                    }
                }).start();
            }

            //            }
            @Override  //选择图片失败
            public void onFailure(int requestCode, String errorMsg) {
                Log.e("选择单张图片失败", "requestCode" + requestCode + "  errorMsg" + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        };
        if (is_zhnegfangxing) {//上传是否要求是正方形，强制裁剪成正方形
            choosePhotoUtlis.setEnableCrop().setForceCrop().setCropSquare(); //强制剪裁正方形
        } else {
            choosePhotoUtlis.setEnableCrop().setForceCrop();//强制剪裁自由图形
        }
        showActionSheet();
        return this;
    }

    /**
     * 选择上传多张图片，有本地缓存
     * is_zhnegfangxing 设置是否都是正方形
     * num 设置最大选择的图片数量
     */
    public ChoosePhoto setPics(int num) {
        final List<String> imgSrcList = new ArrayList<>();  //多张图片本地路径集合
        final List<String> imgUrlList = new ArrayList<>();  //多张图片网络路径集合
        muti = true; //多选
        this.mutiMax = num;
        choosePhotoUtlis = new ChoosePhotoUtlis(mContext) {
            @Override
            public void onSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                final List<Bitmap> images = new ArrayList<>();

                for (int i = 0; i < resultList.size(); i++) {
                    BitmapUtils.setBitmapDegree(resultList.get(i).getPhotoPath());
                    Bitmap image = BitmapUtils.compressBitmap(resultList.get(i).getPhotoPath(), 720, 1080);
                    images.add(image);
                    if (!TextUtils.isEmpty(resultList.get(i).getPhotoPath())) {
                        imgSrcList.add("file:/" + resultList.get(i).getPhotoPath());
                    }
                }

                Log.e("多张图片本地路径", imgSrcList.toString());
                final ProgressDialog progressDialog = new ProgressDialog(mContext);
                progressDialog.setTitle("图片上传中，请稍候...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();

                for (final Bitmap image : images) {
                    if (null == image) {
                        progressDialog.setProgress(0);
                        progressDialog.setMax(0);
                        progressDialog.dismiss();
                        setPicsFailure(imgSrcList, imgUrlList, pics_upload_sf);
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                new AliOss(mContext, AliOss.setPicName(AliOss.BucketName, image.toString()), BitmapUtils.compressImage(image)) {//这里设置上传的地址
                                    @Override
                                    protected void uploadProgress(long currentSize, long totalSize) {
                                        progressDialog.setProgress((int) currentSize);
                                        progressDialog.setMax((int) totalSize);
                                    }

                                    @Override
                                    public void uploadSuccess(String myPicUrl) {
                                        imgUrlList.add(myPicUrl);
                                        pics_upload_num++;
                                        pics_upload_success_num++;
                                        pics_upload_sf.add(true);
                                        Log.e("", myPicUrl);
                                        if (pics_upload_num == images.size()) {  //图片全部执行上传 不一定都成功
                                            progressDialog.dismiss();
                                            if (pics_upload_success_num == images.size()) {  //全部上传成功
                                                Log.e("多张图片全部添加成功 本地路径", imgSrcList.toString());
                                                Log.e("多张图片全部添加成功 网络路径", imgUrlList.toString());
                                                setPicsSuccess(imgSrcList, imgUrlList);
                                            } else {
                                                Log.e("多张图片添加部分失败 本地路径", imgSrcList.toString());
                                                Log.e("多张图片添加部分失败 网络路径", imgUrlList.toString());
                                                Log.e("上传成功失败标识", pics_upload_sf.toString());
                                                setPicsFailure(imgSrcList, imgUrlList, pics_upload_sf);
                                            }
                                        }
                                    }

                                    @Override
                                    protected void uploadFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {

                                        pics_upload_num++;
                                        pics_upload_sf.add(false);
                                        if (pics_upload_num == images.size()) {  //图片全部执行长传 不一定都失败
                                            progressDialog.dismiss();
                                            Log.e("多张图片添加部分失败 本地路径", imgSrcList.toString());
                                            Log.e("多张图片添加部分失败 网络路径", imgUrlList.toString());
                                            Log.e("上传成功失败标识", pics_upload_sf.toString());
                                            setPicsFailure(imgSrcList, imgUrlList, pics_upload_sf);
                                        }
                                    }
                                }.start();

                            }

                        }).start();
                    }
                }
            }

            @Override
            public void onFailure(int requestCode, String errorMsg) {
                Log.e("选择多张图片失败", "requestCode" + requestCode + "  errorMsg" + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        };
        showActionSheet();
        return this;
    }


    /**
     * 选择上传头像成功
     *
     * @param myPicUrl
     */
    protected abstract void setPortraitonSuccess(String myPicUrl);

    /**
     * 选择上传头像失败
     */
    protected abstract void setPortraitFailure();

    /**
     * 选择单张图片上传成功
     *
     * @param myImgSrc 本地地址
     * @param myPicUrl 网络地址
     */
    protected void setPicSuccess(String myImgSrc, String myPicUrl) {
    }

    /**
     * 选择多张图片上传失败
     *
     * @param picsSrc 本地地址集合
     * @param picsUrl 网络地址集合
     */
    protected void setPicsSuccess(List<String> picsSrc, List<String> picsUrl) {

    }
    /**
     * 选择多张图片部分上传失败
     *
     * @param picsSrc        本地地址集合
     * @param picsUrl        网络地址集合
     * @param pics_upload_sf 标识那些图片上传失败
     */
    protected void setPicsFailure(List<String> picsSrc, List<String> picsUrl, List<Boolean> pics_upload_sf) {

    }
    /**
     * 弹出选择框
     */
    private void showActionSheet() {
        String[] datas = {"拍照", "图库"};
        final MyDialogBuilder myDialogBuilder = MyDialogBuilder.getInstance(mContext);
        myDialogBuilder.withTitie(TextUtils.isEmpty(title) ? "选择图片" : title)
                .withEffects(MyDialogBuilder.SlideTop, MyDialogBuilder.SlideTopDismiss)
                .setBtn1Bg(R.drawable.bg_text)
                .setBtn1Text("取消")
                .setSingleChoice(mContext, datas, R.layout.item_textcenter, new MyDialogBuilder.SingleChoiceConvert() {
                    @Override
                    public void convert(ViewHolder holder, String s) {
                        holder.setText(R.id.tv_item_center, s);
                    }
                }, new MyDialogBuilder.SingleChoiceOnItemClick() {
                    @Override
                    public void onItemClick(String data) {
                        switch (data){
                            case "拍照":
                                choosePhotoUtlis.build();
                                choosePhotoUtlis.setOpenCamera();
                                break;
                            case "图库":
                                if (muti) {
                                    choosePhotoUtlis.setMutiSelectMax(mutiMax).build();
                                    choosePhotoUtlis.setMutiSelect();
                                } else {
                                    choosePhotoUtlis.build();
                                    choosePhotoUtlis.setSingleSelect();
                                }
                                break;
                        }
                    }
                })
                .setBtn1(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialogBuilder.dismiss();
                    }
                }).show();
    }
}
