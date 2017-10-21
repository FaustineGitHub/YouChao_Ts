package com.youchao.tingshuo.ui.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.activity.MainActivity;
import com.youchao.tingshuo.ui.activity.MainActivity_Bottom;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.CommonInputUtils;
import com.youchao.tingshuo.utils.MD5Util;
import com.youchao.tingshuo.utils.MToast;
import com.youchao.tingshuo.utils.MaxLengthWatcher;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/11.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.login_et_account)
    EditText mLoginEtAccount;
    @Bind(R.id.login_et_password)
    EditText mLoginEtPassword;
    @Bind(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    @Bind(R.id.tv_login)
    TextView mTvLogin;
    @Bind(R.id.tv_register)
    TextView mTvRegister;
    @Bind(R.id.iv_wx_login)
    ImageView mIvWxLogin;
    @Bind(R.id.iv_qq_login)
    ImageView mIvQqLogin;
    @Bind(R.id.iv_sina_login)
    ImageView mIvSinaLogin;

    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.iv_login_icon)
    ImageView mIvLoginIcon;

    private Context mContext;
    private String mAccount, mPassword;
    private boolean isShowPwd = false;
    public static Activity loginActivity;

    //private MyNewDialogBuilder mMyNewDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        mContext = this;
    }

    @Override
    protected int initResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponent() {
    }

    @Override
    protected void initData() {
        loginActivity = this;
        /*if (SharedPreferencesUtils.getString(mContext, ConstantUtlis.SP_ISLOGINPWDRESET, "").equals("true")) {
            MToast.showToast(mContext, "您的密码已重置，请重新登录");
            SharedPreferencesUtils.setString(mContext, ConstantUtlis.SP_ISLOGINPWDRESET, "false");
        }*/
        //setTextWatcher();
        CommonInputUtils.filterBlank(mLoginEtPassword);//过滤空格
        CommonInputUtils.filterBlank(mLoginEtAccount);
        mLoginEtPassword.addTextChangedListener(new MaxLengthWatcher(16, mLoginEtPassword));//密码最多16位


    }

    //监控输入框长度
    private void setTextWatcher() {
        mLoginEtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mLoginEtPassword.getText().toString().trim().length() >= 6 && s.length() > 6) {
                    mTvLogin.setEnabled(true);
                    mTvLogin.setBackgroundResource(R.drawable.bg_text);
                    mTvLogin.setTextColor(getResources().getColor(R.color.white));
                    mTvLogin.setOnClickListener(LoginActivity.this);
                } else {
                    mTvLogin.setEnabled(false);
                    mTvLogin.setBackgroundResource(R.drawable.btn_disabled);
                    mTvLogin.setTextColor(getResources().getColor(R.color.white));
                }

            }
        });


        mLoginEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mLoginEtAccount.getText().toString().trim().length() > 6 && s.length() >= 6) {
                    mTvLogin.setEnabled(true);
                    mTvLogin.setBackgroundResource(R.drawable.bg_text);
                    mTvLogin.setTextColor(getResources().getColor(R.color.white));
                    mTvLogin.setOnClickListener(LoginActivity.this);
                } else {
                    mTvLogin.setEnabled(false);
                    mTvLogin.setBackgroundResource(R.drawable.btn_disabled);
                    mTvLogin.setTextColor(getResources().getColor(R.color.white));
                }

            }
        });
    }


    @OnClick({R.id.tv_forget_password, R.id.tv_login, R.id.tv_register, R.id.iv_wx_login, R.id.iv_qq_login, R.id.iv_sina_login})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_forget_password://忘记密码
                intent = new Intent(this, ForgetPwdActivity.class);
                intent.putExtra("pwdType", "login");
                startActivity(intent);
                break;
            case R.id.tv_login://登陆
                intent = new Intent(this, MainActivity_Bottom.class);
                //intent.putExtra("pwdType", "login");
                startActivity(intent);
                if (login_verification()) {
                    mAccount = mLoginEtAccount.getText().toString().trim();
                    mPassword = MD5Util.Md5(mLoginEtPassword.getText().toString().trim());

                    //requestHandleArrayList.add(requestAction.shopLogin(LoginActivity.this, mAccount, mPassword));
                }
                break;
            case R.id.tv_register://立即注册
                intent = new Intent(this, RegisterActivity.class);
                //intent.putExtra("pwdType", "login");
                startActivity(intent);
                break;
            case R.id.iv_wx_login://微信登陆
                break;
            case R.id.iv_qq_login://QQ登陆
                break;
            case R.id.iv_sina_login://新浪微博登录
                break;

        }

    }

    //页面数据验证
    private boolean login_verification() {
        if (TextUtils.isEmpty(mLoginEtAccount.getText().toString())) {
            MToast.showToast(mContext, "账号不能为空，请输入");
            return false;
        } else if (TextUtils.isEmpty(mLoginEtPassword.getText().toString())) {
            MToast.showToast(mContext, "密码不能为空，请输入");
            return false;
        } else if (mLoginEtPassword.getText().length() < 6) {
            MToast.showToast(mContext, "您输入的密码有误");
            return false;
        } else {
            return true;
        }
    }


}
