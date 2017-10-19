package com.youchao.tingshuo.ui.activity.money;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.MToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/18.
 * 评价页面
 */

public class PingJiaActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.pingjia_iv_user)
    ImageView mPingjiaIvUser;
    @Bind(R.id.pingjia_tv_name)
    TextView mPingjiaTvName;
    @Bind(R.id.pingjia_tv_info)
    TextView mPingjiaTvInfo;
    @Bind(R.id.pingjia_tv_date)
    TextView mPingjiaTvDate;
    @Bind(R.id.pingjia_tv_duration)
    TextView mPingjiaTvDuration;
    @Bind(R.id.pingjia_tv_duration_center)
    TextView mPingjiaTvDurationCenter;
    @Bind(R.id.pingjia_ratingBar)
    RatingBar mPingjiaRatingBar;
    @Bind(R.id.pingjia_ratingBar_tv)
    TextView mPingjiaRatingBarTv;
    @Bind(R.id.pingjia_tv_fabiao)
    TextView mPingjiaTvFabiao;

    @Override
    protected int initResource() {
        return R.layout.activity_pingjia;
    }

    @Override
    protected void initComponent() {
        mIvToptitleBack.setImageResource(R.drawable.delete_icon_gray);
        mTvToptitleTitle.setText("评价");
    }

    @Override
    protected void initData() {
        mPingjiaRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mPingjiaRatingBar.setRating(v);
                L.e("TAG","星星个数 = "+v);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_toptitle_back, R.id.pingjia_tv_fabiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toptitle_back://退出
                View view1 = this.getWindow().peekDecorView();
                if (view1 != null) {
                    InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                this.finish();
                break;
            case R.id.pingjia_tv_fabiao://发表
                MToast.showToast(this,"发表成功");
                this.finish();
                break;
        }
    }


}
