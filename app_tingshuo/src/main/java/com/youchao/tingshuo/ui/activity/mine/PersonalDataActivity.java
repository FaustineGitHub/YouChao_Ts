package com.youchao.tingshuo.ui.activity.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.abcaaa.photopicker.PhotoPicker;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.adapter.ViewHolder;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.ui.widget.ChoosePhoto;
import com.youchao.tingshuo.ui.widget.dialog.MyDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/19.
 * 个人资料详情页面
 */

public class PersonalDataActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_userIcon)
    ImageView mIvUserIcon;
    @Bind(R.id.rl_userIcon)
    RelativeLayout mRlUserIcon;
    @Bind(R.id.tv_nickname)
    TextView mTvNickname;
    @Bind(R.id.rl_nickname)
    RelativeLayout mRlNickname;
    @Bind(R.id.tv_sex)
    TextView mTvSex;
    @Bind(R.id.rl_sex)
    RelativeLayout mRlSex;
    @Bind(R.id.iv_zxing)
    ImageView mIvZxing;
    @Bind(R.id.rl_zxing)
    RelativeLayout mRlZxing;
    @Bind(R.id.tv_birthday)
    TextView mTvBirthday;
    @Bind(R.id.rl_birthday)
    RelativeLayout mRlBirthday;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.rl_address)
    RelativeLayout mRlAddress;
    @Bind(R.id.tv_Individuality_signature)
    TextView mTvIndividualitySignature;
    @Bind(R.id.rl_Individuality_signature)
    RelativeLayout mRlIndividualitySignature;

    public static final int REQUEST_CODE_TOUXIANG = 1;
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;

    private TimePickerView datePicker;
    private OptionsPickerView addressPicker;
    private String birthday;
    private String default_url_tx, shoujihao, myPic;
    private String contents = "";

    @Override
    protected int initResource() {
        return R.layout.activity_personaldata;
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData() {
        mTvToptitleTitle.setText("个人资料");
        // CommonUtlis.setTitleBar(this,true,"编辑资料","");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_toptitle_back, R.id.rl_userIcon, R.id.rl_nickname, R.id.rl_sex, R.id.rl_zxing, R.id.rl_birthday, R.id.rl_address, R.id.rl_Individuality_signature})
    public void onClick(View view) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_toptitle_back:
                View view1 = this.getWindow().peekDecorView();
                if (view1 != null) {
                    InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                this.finish();
                break;
            case R.id.rl_userIcon://更换头像
                checkPermission(new CheckPermListener() {
                    @Override
                    public void superPermission() {
                        checkPermission(new CheckPermListener() {
                            @Override
                            public void superPermission() {
                                new ChoosePhoto(mContext, "头像选择") {
                                    @Override
                                    protected void setPortraitonSuccess(final String myPicUrl) {
                                        PersonalDataActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                myPic = myPicUrl;
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("头像", myPicUrl);
                                                updateMyData(mIvUserIcon,  myPicUrl, hashMap);
                                            }
                                        });
                                    }

                                    @Override
                                    protected void setPortraitFailure() {
                                        Log.e("设置头像失败", "设置头像失败");
                                    }
                                }.setPortrait();
                            }
                        }, R.string.perm_readstorage, Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }, R.string.perm_camera, Manifest.permission.CAMERA);
                /*checkPermission(new CheckPermListener() {
                    @Override
                    public void superPermission() {
                        selectPic();
                    }
                }, R.string.camera_updatepics, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);*/
                break;
            case R.id.rl_nickname:
                intent = new Intent(this, ModifyIndividualitySignature.class);
                intent.putExtra("TAG", "UpadateNickName");
                startActivity(intent);
                break;
            case R.id.rl_sex:
                String[] datas = {"男", "女"};
                final MyDialogBuilder myDialogBuilder = MyDialogBuilder.getInstance(mContext);
                myDialogBuilder.withTitie("性别")
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
                                    case "男":
                                        mTvSex.setText("男");
                                        myDialogBuilder.dismiss();
                                        break;
                                    case "女":
                                        mTvSex.setText("女");
                                        myDialogBuilder.dismiss();
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
                break;
            case R.id.rl_zxing://获取二维码
                /*intent = new Intent(this, HuoQuErWeiMaActivity.class);
                intent.putExtra("TAG", "UpadateNickName");
                startActivity(intent);*/

                break;
            case R.id.rl_birthday:
                //时间选择器 ，自定义布局
               datePicker = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        birthday = getTime(date);
                    }
                })
                .isDialog(true)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datePicker.returnData();
                                datePicker.dismiss();
                                mTvBirthday.setText(birthday);
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datePicker.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.WHITE)
                .build();
                datePicker.show();
                break;
            case R.id.rl_address:
                //条件选择器
                /*addressPicker = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText()
                                + options2Items.get(options1).get(option2)
                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        mTvAddress.setText(tx);
                    }
                }).build();
                addressPicker.setPicker(options1Items, options2Items, options3Items);
                addressPicker.show();*/

                break;
            case R.id.rl_Individuality_signature:
                intent = new Intent(this, ModifyIndividualitySignature.class);
                intent.putExtra("TAG", "UpadateIndividualitySignature");
                startActivity(intent);
                break;
        }
    }

    //头像照片
    private void selectPic() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(false)
                .setPreviewEnabled(true)
                .start(this, REQUEST_CODE_TOUXIANG);
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    /**
     * 修改资料
     */
    private void updateMyData(final ImageView imageview, final String content, HashMap<String, Object> hashMap) {
        //requestHandleArrayList.add(requestAction.UpdateMyData(ZhiLiaoActivity.this, hashMap));
        contents = content;
        mIvUserIcon = imageview;
    }


}
