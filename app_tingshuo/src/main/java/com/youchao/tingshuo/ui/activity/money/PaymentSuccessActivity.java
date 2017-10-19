package com.youchao.tingshuo.ui.activity.money;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.activity.MainActivity;
import com.youchao.tingshuo.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/18.
 * 付款成功
 */

public class PaymentSuccessActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.success_img)
    ImageView mSuccessImg;
    @Bind(R.id.success_tv)
    TextView mSuccessTv;
    @Bind(R.id.success_tv_left)
    TextView mSuccessTvLeft;
    @Bind(R.id.success_tv_right)
    TextView mSuccessTvRight;

    @Override
    protected int initResource() {
        return R.layout.activity_payment_success;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("付款成功");

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

    @OnClick({R.id.iv_toptitle_back, R.id.success_tv_left, R.id.success_tv_right})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_toptitle_back://返回
                View view1 = this.getWindow().peekDecorView();
                if (view1 != null) {
                    InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                this.finish();
                break;
            case R.id.success_tv_left://发表评价
                intent = new Intent(this, PingJiaActivity.class);
                startActivity(intent);
                break;
            case R.id.success_tv_right://返回首页
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }


}
