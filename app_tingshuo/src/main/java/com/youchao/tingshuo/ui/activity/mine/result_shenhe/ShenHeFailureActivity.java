package com.youchao.tingshuo.ui.activity.mine.result_shenhe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.activity.mine.IdentityAuthenticationActivity;
import com.youchao.tingshuo.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by dell on 2017/10/16.
 * 审核结果
 */

public class ShenHeFailureActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.shenhe_bg)
    ImageView mShenheBg;
    @Bind(R.id.shenhe_icon)
    ImageView mShenheIcon;
    @Bind(R.id.tv_shenhe)
    TextView mTvShenhe;
    @Bind(R.id.tv_result)
    TextView mTvResult;
    private String SUCCESS = "SUCCESS";
    private String FUILURE = "FUILURE";
    private String LOADING = "LOADING";
    private String tag;

    @Override
    protected int initResource() {
        return R.layout.activity_shenhe_failure;
    }

    @Override
    protected void initComponent() {
        Intent intent = this.getIntent();        //获取已有的intent对象
        Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象
        tag = bundle.getString("TAG");    //获取Bundle里面的字符串
        if (SUCCESS.equals(tag)){//审核成功
            mShenheBg.setImageResource(R.drawable.shenhechenggong_bg);
            mShenheIcon.setImageResource(R.drawable.shenhechenggong_icon);
            mTvShenhe.setText("恭喜,您的身份认证成功");
            mTvResult.setText("查看");
        }else if (FUILURE.equals(tag)){//审核失败
            mShenheBg.setImageResource(R.drawable.shenhe_failure_bg);
            mShenheIcon.setImageResource(R.drawable.shenhe_failure_icon);
            mTvShenhe.setText("您的身份认证审核失败\n请重新认证");
            mTvResult.setText("重新认证");
        }else if (LOADING.equals(tag)){//审核中
            mShenheBg.setImageResource(R.drawable.shenhezhong_bg);
            mShenheIcon.setImageResource(R.drawable.shenhezhong_icon);
            mTvShenhe.setText("您的身份认证正在审核中\n请耐心等待");
            mTvResult.setVisibility(View.GONE);
        }


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

    @OnClick(R.id.tv_result)
    public void onClick(View view) {
        Intent intent = null;
        if (SUCCESS.equals(tag)){//审核成功
            this.finish();
        }else if (FUILURE.equals(tag)){//审核失败
            intent = new Intent(this, IdentityAuthenticationActivity.class);
            startActivity(intent);
        }

    }

}
