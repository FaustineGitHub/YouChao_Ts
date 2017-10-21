package com.youchao.tingshuo.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.trycatch.mysnackbar.ScreenUtil;
import com.trycatch.mysnackbar.TSnackbar;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.ui.widget.TimeButton;
import com.youchao.tingshuo.ui.widget.dialog.MyDialogBuilder;
import com.youchao.tingshuo.utils.MD5Util;
import com.youchao.tingshuo.utils.MToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/12.
 * 注册页面
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.iv_register_back)
    ImageView mIvRegisterBack;
    @Bind(R.id.iv_register_icon)
    ImageView mIvRegisterIcon;
    @Bind(R.id.register_et_account)
    EditText mRegisterEtAccount;
    @Bind(R.id.register_et_password)
    EditText mRegisterEtPassword;
    @Bind(R.id.tv_getyzm)
    TimeButton mTvGetyzm;
    @Bind(R.id.et_yzm)
    EditText mEtYzm;
    @Bind(R.id.tv_register)
    TextView mTvRegister;

    private Context context;
    private String mAccount, mPassword,mZxing;
    private TSnackbar snackBar;
    private int APP_DOWn = TSnackbar.APPEAR_FROM_TOP_TO_DOWN;
    @Override
    protected int initResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        context = this;
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_register_back, R.id.tv_getyzm, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
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
                final MyDialogBuilder myDialogBuilder = MyDialogBuilder.getInstance(mContext);
                //myDialogBuilder.
                if (login_verification()) {
                    mAccount = mRegisterEtAccount.getText().toString().trim();
                    mPassword = MD5Util.Md5(mRegisterEtPassword.getText().toString().trim());
                    mZxing = mEtYzm.getText().toString().trim();
                    //setSnackBar_green("注册成功");
                    Intent intent = new Intent(this, LoginActivity.class);
                    //intent.putExtra("pwdType", "login");
                    startActivity(intent);
                    //requestHandleArrayList.add(requestAction.shopLogin(LoginActivity.this, mAccount, mPassword));
                    //MToast.showToast(this, "注册成功");

                }

                break;
        }
    }
    //页面数据验证
    private boolean login_verification() {

        if (TextUtils.isEmpty(mRegisterEtAccount.getText().toString())) {
            setSnackBar_red("手机号有误");
            return false;
        } else if (TextUtils.isEmpty(mRegisterEtPassword.getText().toString())) {
            setSnackBar_red("密码有误");
            return false;
        } else if (mRegisterEtPassword.getText().length() < 6) {
            setSnackBar_red("密码有误");
            return false;
        }else if (TextUtils.isEmpty(mEtYzm.getText().toString())) {
            setSnackBar_red("验证码有误");
            return false;
        }
        else {
            return true;
        }
    }
    //设置顶部提示框
    private void setSnackBar_red(String content) {
        final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.snackbar_container).getRootView();
        int height = ScreenUtil.getStatusHeight(mContext);//状态栏的高度
        snackBar = TSnackbar.make(viewGroup, content, TSnackbar.LENGTH_SHORT, APP_DOWn);
        snackBar.addIcon(R.drawable.shibai,100,100);
        /*snackBar.setAction("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        //设置背景颜色
        View mView = snackBar.getView();
        mView.setBackgroundColor(Color.parseColor("#FF4354"));
        //设置Snackbar的高度---不设置高度时，默认为导航栏+height
        //snackBar.setMinHeight(0,height);
        snackBar.show();
    }
    //设置顶部提示框
    private void setSnackBar_green(String content) {
        final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.snackbar_container).getRootView();
        int height = ScreenUtil.getStatusHeight(mContext);//状态栏的高度
        snackBar = TSnackbar.make(viewGroup, content, TSnackbar.LENGTH_SHORT, APP_DOWn);
        snackBar.addIcon(R.drawable.chenggong,100,100);
        /*snackBar.setAction("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        //设置背景颜色
        View mView = snackBar.getView();
        mView.setBackgroundColor(Color.parseColor("#71D43C"));
        //设置Snackbar的高度
        snackBar.setMinHeight(0,height);
        snackBar.show();
    }
}
