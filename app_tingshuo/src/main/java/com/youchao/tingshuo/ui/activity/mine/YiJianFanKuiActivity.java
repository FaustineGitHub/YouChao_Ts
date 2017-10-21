package com.youchao.tingshuo.ui.activity.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.DebugUtil;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.adapter.shouye.SendTrendsAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.ui.widget.FullyGridLayoutManager;
import com.youchao.tingshuo.utils.MToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by dell on 2017/10/10.
 * 意见反馈页面
 */

public class YiJianFanKuiActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.et_fankui_content)
    EditText mEtFankuiContent;
    @Bind(R.id.recycler_fanhui_image)
    RecyclerView mRecyclerFanhuiImage;
    @Bind(R.id.fankui_et_qq)
    EditText mFankuiEtQq;
    @Bind(R.id.fankui_et_youxiang)
    EditText mFankuiEtYouxiang;
    @Bind(R.id.tv_tijiao_fankui)
    TextView mTvTijiaoFankui;
    private final static String TAG = YiJianFanKuiActivity.class.getSimpleName();
    private List<LocalMedia> selectList = new ArrayList<>();
    private SendTrendsAdapter adapter;
    private int maxSelectNum = 9;
    private int chooseMode = PictureMimeType.ofAll();
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
    private int themeId;

    @Override
    protected int initResource() {
        return R.layout.activity_yijian_fankui;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("意见反馈");

    }

    @Override
    protected void initData() {
        themeId = R.style.picture_default_style;
        FullyGridLayoutManager manager = new FullyGridLayoutManager(YiJianFanKuiActivity.this, 4, GridLayoutManager.VERTICAL, false);
        mRecyclerFanhuiImage.setLayoutManager(manager);
        adapter = new SendTrendsAdapter(YiJianFanKuiActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        mRecyclerFanhuiImage.setAdapter(adapter);
        adapter.setOnItemClickListener(new SendTrendsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(YiJianFanKuiActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(YiJianFanKuiActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(YiJianFanKuiActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(YiJianFanKuiActivity.this);
                } else {
                    Toast.makeText(YiJianFanKuiActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

    }
    // 响应startActivityForResult，获取图片路径
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    DebugUtil.i(TAG, "onActivityResult:" + selectList.size());
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_toptitle_back, R.id.tv_tijiao_fankui})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toptitle_back:
                View view1 = this.getWindow().peekDecorView();
                if (view1 != null) {
                    InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                this.finish();
                break;
            case R.id.tv_tijiao_fankui:
                MToast.showToast(this,"提交成功");
                this.finish();
                break;
        }
    }
    private SendTrendsAdapter.onAddPicClickListener onAddPicClickListener = new SendTrendsAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = true;
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(YiJianFanKuiActivity.this)
                        .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .enablePreviewAudio(true) // 是否可播放音频
                        .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .compressMode(compressMode)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(true)// 是否显示gif图片
                        //.freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
                        //.circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                        //.showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        //.showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        //.openClickSound(cb_voice.isChecked())// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                        //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                        //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
                        //.recordVideoSecond()//录制视频秒数 默认60s
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            } else {
                // 单独拍照
                PictureSelector.create(YiJianFanKuiActivity.this)
                        .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .theme(themeId)// 主题样式设置 具体参考 values/styles
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .enablePreviewAudio(true) // 是否可播放音频
                        .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                        .isCamera(true)// 是否显示拍照按钮
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .compressMode(compressMode)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(true)// 是否显示gif图片
                        //.freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
                        //.circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                        //.showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        //.showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        //.openClickSound(cb_voice.isChecked())// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                        //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                        //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()////显示多少秒以内的视频or音频也可适用
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        }

    };
}
