package com.youchao.tingshuo.ui.activity.shouye;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.CommonUtlis;
import com.youchao.tingshuo.utils.MToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/28.
 * 写评论
 */

public class WritePingLunActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.edit_pinglun)
    EditText mEditPinglun;

    @Override
    protected int initResource() {
        return R.layout.activity_write_pinglun;
    }

    @Override
    protected void initComponent() {
        CommonUtlis.setTitleBar(this, true, "发评论", "发表");

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

    @OnClick(R.id.tv_toptitle_right)
    public void onClick(View view) {
        MToast.showToast(this,"发表评论成功");
        finish();
    }


}
