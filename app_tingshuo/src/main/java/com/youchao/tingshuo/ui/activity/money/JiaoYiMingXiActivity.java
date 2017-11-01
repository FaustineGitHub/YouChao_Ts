package com.youchao.tingshuo.ui.activity.money;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.adapter.CommonAdapter;
import com.youchao.tingshuo.ui.adapter.ViewHolder;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.view.ListViewDecoration1;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/26.
 * 交易明细
 */

public class JiaoYiMingXiActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.recycler_fans_list)
    RecyclerView mRecyclerFansList;
    private CommonAdapter mCommonAdapter;
    private List<String []> mList;

    @Override
    protected int initResource() {
        return R.layout.activity_fans_list;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("交易明细");
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add(new String[]{"在线支付","23.00","2017-10-27","-100.00"});
        mList.add(new String[]{"在线充值","123.00","2017-10-27","+100.00"});
        mList.add(new String[]{"在线提现","73.00","2017-10-27","-200.00"});
        mList.add(new String[]{"收款","273.00","2017-10-27","+250.00"});
        mRecyclerFansList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerFansList.addItemDecoration(new ListViewDecoration1(mContext));// 添加分割线。




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_toptitle_back)
    public void onClick(View view) {
        View view1 = this.getWindow().peekDecorView();
        if (view1 != null) {
            InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        this.finish();
    }
}
