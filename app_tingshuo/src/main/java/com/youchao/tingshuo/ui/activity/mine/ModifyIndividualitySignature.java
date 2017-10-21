package com.youchao.tingshuo.ui.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.MToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/19.
 * 修改个性签名
 */

public class ModifyIndividualitySignature extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.tv_toptitle_right)
    TextView mTvToptitleRight;
    @Bind(R.id.et_new)
    EditText mEtNew;
    @Bind(R.id.iv_delete)
    ImageView mIvDelete;
    @Bind(R.id.rl_edit)
    RelativeLayout mRlEdit;
    @Bind(R.id.tv_new_number)
    TextView mTvNewNumber;
    @Bind(R.id.et_new_nickname)
    EditText mEtNewNickname;
    @Bind(R.id.ll_nickname)
    LinearLayout mLlNickname;

    @Override
    protected int initResource() {
        return R.layout.activity_modify_individualitysignature;
    }

    @Override
    protected void initComponent() {



    }

    @Override
    protected void initData() {
        String tag = getIntent().getStringExtra("TAG");
        mTvToptitleRight.setText("确定");
        mTvNewNumber.setText("0/100");
        setTextWatcher();
        //mEtNew.addTextChangedListener(new MaxLengthWatcher(100, mEtNew));//签名最多100位
        if ("UpadateIndividualitySignature".equals(tag)) {//修改个性签名
            mRlEdit.setVisibility(View.VISIBLE);
            mTvNewNumber.setVisibility(View.VISIBLE);
            mTvToptitleTitle.setText("简介");
            mEtNew.setHint("请填写个人简介");

        } else if ("UpadateNickName".equals(tag)) {//修改昵称
            mLlNickname.setVisibility(View.VISIBLE);
            mTvToptitleTitle.setText("昵称");
        }


    }

    //监控输入框长度
    private void setTextWatcher() {
        mEtNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEtNew.getText().toString().trim().length() >= 0 && s.length() > 0) {
                    mTvNewNumber.setText((mEtNew.getText().toString().length()-1) + "/100");
                }

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_toptitle_back, R.id.iv_delete, R.id.tv_toptitle_right})
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
            case R.id.tv_toptitle_right:
                MToast.showToast(this, "保存成功");
                break;
            case R.id.iv_delete:
                mEtNew.setText("");
                mTvNewNumber.setText("0/100");
                MToast.showToast(this, "删除成功");
                break;
        }
    }

}
