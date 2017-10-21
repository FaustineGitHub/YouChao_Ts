package com.youchao.tingshuo.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.activity.AutoLayoutActivity;
import com.youchao.tingshuo.ui.activity.MainActivity;
import com.youchao.tingshuo.ui.activity.MainActivity_Bottom;
import com.youchao.tingshuo.utils.ConstantUtlis;
import com.youchao.tingshuo.utils.SharedPreferencesUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/9/11.
 * APP进入首页判断
 */

public class SplashActivity extends AutoLayoutActivity {
    @Bind(R.id.tv_appname)
    TextView mTvAppname;
    @Bind(R.id.tv_copyright)
    TextView mTvCopyright;
    private String phoneId, phoneName, phoneType;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置无标题
        // 设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mContext = this;
        initComponent();

        //如果程序在运行，点home键后，点击程序图标，防止再次启动该activity
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        getPhoneInfo();
        //无数据时，直接进入主页
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                String type = SharedPreferencesUtils.getString(mContext, ConstantUtlis.SP_LOGINSTATE, "");//登录状态
                String authenticationState = SharedPreferencesUtils.getString(mContext, ConstantUtlis.SP_AUTHENTIFICATIONSTATE, "");//认证状态
//                boolean isGuideshowed = SharedPreferencesUtils.getBoolean(mContext, ConstantUtlis.SP_ISGUIDEPAGESHOWED, false);

                boolean isGuideshowed = true;//第一个版本没时间做引导页了。20170717.

                if (isGuideshowed) {
                    if (type.equals("成功")) {
//                        if (authenticationState.equals("未上传资料")) {
//                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                            SplashActivity.this.finish();
//                        } else if (authenticationState.equals("未审核")) {
//                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                            SplashActivity.this.finish();
//                        } else if (authenticationState.equals("审核未通过")) {
//                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                            SplashActivity.this.finish();
//                        } else if (authenticationState.equals("审核通过")) {
//                            String paypwdState = SharedPreferencesUtlis.getString(mContext, ConstantUtlis.SP_PAYPWDSTATE, "");
//                            if (paypwdState.equals("未设置")){
//                                startActivity(new Intent(SplashActivity.this, SetPaypwdActivity.class));
//                                SplashActivity.this.finish();
//                            }else {
//                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                SplashActivity.this.finish();
//                            }
//
//                        }.


                        Intent intent = new Intent(SplashActivity.this, MainActivity_Bottom.class);
                        startActivity(intent);
                        SharedPreferencesUtils.setBoolean(mContext, ConstantUtlis.SP_ISFROMLOGINTOMAIN, true);//从登录页进入主页
                        SplashActivity.this.finish();


                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    SplashActivity.this.finish();
                }
            }
        }, 2000);

    }

    private void initComponent() {
        /*//修改字体为汉仪细中圆简字体
        Typeface face = Typeface.createFromAsset(this.getAssets(), "fonts/youchao_ziti.ttf");
        mTvAppname.setTypeface(face);
        mTvCopyright.setTypeface(face);*/
    }

    private void getPhoneInfo() {
        if (SharedPreferencesUtils.getString(mContext, ConstantUtlis.SP_PHENEID, "").isEmpty()) {
//            phoneId = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
            phoneId = "";
            phoneName = "Android " + Build.MODEL;
            phoneType = "Android";

            //TODO SharedPreferencesUtils.setInt(mContext, ConstantUtlis.SP_PHONEHEIGHT, CommonUtlis.getScreenHeight(this));
            //TODO SharedPreferencesUtils.setString(mContext, ConstantUtlis.SP_PHENEID, phoneId);
            //TODO SharedPreferencesUtils.setString(mContext, ConstantUtlis.SP_PHONEINFO_KV, "\n手机ID=" + phoneId + "\n手机名称=" + phoneName + "\n手机型号=" + phoneType);

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("手机ID", phoneId);
            hashMap.put("手机名称", phoneName);
            hashMap.put("手机型号", phoneType);
//            CommonUtlis.saveMap(ConstantUtlis.SP_PHONEINFO_JSON, hashMap);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
