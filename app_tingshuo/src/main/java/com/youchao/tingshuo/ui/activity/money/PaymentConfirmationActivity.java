package com.youchao.tingshuo.ui.activity.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.MToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/18.
 * 确认付款
 */

public class PaymentConfirmationActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.payment_tv_title)
    TextView mPaymentTvTitle;
    @Bind(R.id.payment_iv_user)
    ImageView mPaymentIvUser;
    @Bind(R.id.payment_tv_name)
    TextView mPaymentTvName;
    @Bind(R.id.payment_tv_info)
    TextView mPaymentTvInfo;
    @Bind(R.id.payment_tv_time)
    TextView mPaymentTvTime;
    @Bind(R.id.payment_tv_money)
    TextView mPaymentTvMoney;
    @Bind(R.id.payment_tv_money_right)
    TextView mPaymentTvMoneyRight;
    @Bind(R.id.iv_yue_type)
    ImageView mIvYueType;
    @Bind(R.id.rl_chongzhi_yue)
    RelativeLayout mRlChongzhiYue;
    @Bind(R.id.iv_zhifubao_type)
    ImageView mIvZhifubaoType;
    @Bind(R.id.rl_chongzhi_zhifubao)
    RelativeLayout mRlChongzhiZhifubao;
    @Bind(R.id.iv_weixin_type)
    ImageView mIvWeixinType;
    @Bind(R.id.rl_chongzhi_weixin)
    RelativeLayout mRlChongzhiWeixin;
    @Bind(R.id.payment_tv)
    TextView mPaymentTv;

    //默认情况下选中余额
    private boolean isYueSelect = true;
    private boolean isZhifubaoSelect = false;
    private boolean isWeixinSelect = false;

    @Override
    protected int initResource() {
        return R.layout.activity_payment_confirmation;
    }

    @Override
    protected void initComponent() {

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

    @OnClick({R.id.rl_chongzhi_yue, R.id.rl_chongzhi_zhifubao, R.id.rl_chongzhi_weixin,R.id.payment_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_chongzhi_yue://余额支付
                isYueSelect = true;
                isZhifubaoSelect = false;
                isWeixinSelect = false;
                if (isYueSelect == true && isZhifubaoSelect == false && isWeixinSelect == false) {
                    mIvYueType.setImageResource(R.drawable.zhifu_select);
                    mIvZhifubaoType.setImageResource(R.drawable.zhifu_unselect);
                    mIvWeixinType.setImageResource(R.drawable.zhifu_unselect);
                }
                break;
            case R.id.rl_chongzhi_zhifubao://支付宝支付
                isYueSelect = false;
                isZhifubaoSelect = true;
                isWeixinSelect = false;
                if (isYueSelect == false && isZhifubaoSelect == true && isWeixinSelect == false) {
                    mIvYueType.setImageResource(R.drawable.zhifu_unselect);
                    mIvZhifubaoType.setImageResource(R.drawable.zhifu_select);
                    mIvWeixinType.setImageResource(R.drawable.zhifu_unselect);
                }
                break;
            case R.id.rl_chongzhi_weixin://微信支付
                isYueSelect = false;
                isZhifubaoSelect = false;
                isWeixinSelect = true;
                if (isYueSelect == false && isZhifubaoSelect == false && isWeixinSelect == true) {
                    mIvYueType.setImageResource(R.drawable.zhifu_unselect);
                    mIvZhifubaoType.setImageResource(R.drawable.zhifu_unselect);
                    mIvWeixinType.setImageResource(R.drawable.zhifu_select);
                }
                break;
            case R.id.payment_tv://确认支付
                if (isYueSelect == true){
                    MToast.showToast(this,"余额支付");
                }else if (isZhifubaoSelect == true){
                    MToast.showToast(this,"支付宝支付");
                }else if (isWeixinSelect == true){
                    MToast.showToast(this,"微信支付");
                }
                Intent intent = new Intent(this, PaymentSuccessActivity.class);
                startActivity(intent);

                break;
        }
    }


}
