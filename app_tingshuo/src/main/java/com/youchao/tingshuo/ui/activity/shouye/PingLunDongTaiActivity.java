package com.youchao.tingshuo.ui.activity.shouye;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.CommonPingLun;
import com.youchao.tingshuo.ui.adapter.shouye.RecyclePingLunAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.MToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/28.
 * 动态评论页面
 */

public class PingLunDongTaiActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.tv_pinglun_number)
    TextView mTvPinglunNumber;
    @Bind(R.id.pinglun_list_in_fragment)
    RecyclerView mPinglunListInFragment;
    private RecyclePingLunAdapter mAdapter;
    private List<CommonPingLun> mList;

    @Override
    protected int initResource() {
        return R.layout.activity_common_dongtai;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("评论列表");
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add(new CommonPingLun("","","","","",""));
        mList.add(new CommonPingLun("","","","","","5645"));
        mList.add(new CommonPingLun("","","","","","56"));
        mList.add(new CommonPingLun("","","","","",""));
        mList.add(new CommonPingLun("","","","","",""));
        mList.add(new CommonPingLun("","","","","","564456"));
        mList.add(new CommonPingLun("","","","","",""));

        mAdapter = new RecyclePingLunAdapter(mList, this);
        mPinglunListInFragment.setLayoutManager(new LinearLayoutManager(this));
        mPinglunListInFragment.setAdapter(mAdapter);
        //设置适配器的监听
        setAdapterListener(mAdapter);


    }

    private void setAdapterListener(RecyclePingLunAdapter adapter) {
        adapter.setOnHuiFuClickListener(new RecyclePingLunAdapter.OnHuiFuClickListener() {
            @Override
            public void onHuiFuClick(View v, int position) {
                MToast.showToast(mContext,"回复");
            }
        });
        adapter.setOnDianZanClickListener(new RecyclePingLunAdapter.OnDianZanClickListener() {
            @Override
            public void onDianZanClick(View v, int position) {
                MToast.showToast(mContext,"点赞");

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_toptitle_back})
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

        }
    }


}
