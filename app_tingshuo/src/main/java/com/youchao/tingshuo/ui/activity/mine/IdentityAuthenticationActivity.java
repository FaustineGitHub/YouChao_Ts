package com.youchao.tingshuo.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.activity.mine.result_shenhe.ShenHeFailureActivity;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.CommonUtlis;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.MToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/16.
 * 身份认证
 */

public class IdentityAuthenticationActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.identity_tv_name)
    EditText mIdentityTvName;
    @Bind(R.id.identity_tv_phone)
    EditText mIdentityTvPhone;
    @Bind(R.id.identity_tv_shenfenzheng)
    EditText mIdentityTvShenfenzheng;
    @Bind(R.id.identity_tv_zhengshu_num)
    EditText mIdentityTvZhengshuNum;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;

    @Override
    protected int initResource() {
        return R.layout.activity_identity_authentication;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("身份认证");
       // CommonUtlis.setTitleBar(this,true,"身份认证","");

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

    @OnClick({R.id.iv_toptitle_back, R.id.tv_commit})
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
            case R.id.tv_commit:
                int a = (int)(Math.random() * 2);
                L.e("TAG","随机数="+a);
                Intent intent = new Intent(this, ShenHeFailureActivity.class);
                Bundle bundle = new Bundle();                           //创建Bundle对象
                if (a == 0){
                    bundle.putString("TAG", "SUCCESS");     //装入数据
                }else if (a == 1){
                    bundle.putString("TAG", "FUILURE");     //装入数据
                }else {
                    bundle.putString("TAG", "LOADING");     //装入数据
                }
                intent.putExtras(bundle);                                //把Bundle塞入Intent里面
                startActivity(intent);
                MToast.showToast(this,"提交成功");
                break;
        }
    }

}
