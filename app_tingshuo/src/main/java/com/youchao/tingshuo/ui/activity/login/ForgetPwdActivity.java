package com.youchao.tingshuo.ui.activity.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.ui.widget.TimeButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/12.
 * 忘记密码
 */

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.iv_register_icon)
    ImageView mIvRegisterIcon;
    @Bind(R.id.register_et_account)
    EditText mRegisterEtAccount;
    @Bind(R.id.tv_getyzm)
    TimeButton mTvGetyzm;
    @Bind(R.id.et_yzm)
    EditText mEtYzm;
    @Bind(R.id.register_et_new_password)
    EditText mRegisterEtNewPassword;
    @Bind(R.id.tv_register)
    TextView mTvRegister;

    @Override
    protected int initResource() {
        return R.layout.activity_forgetpwd;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("忘记密码");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_toptitle_back, R.id.tv_getyzm, R.id.tv_register})
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
            case R.id.tv_getyzm:
                break;
            case R.id.tv_register:
                break;
        }
    }
}
