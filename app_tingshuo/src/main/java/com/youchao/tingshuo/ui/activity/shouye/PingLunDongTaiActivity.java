package com.youchao.tingshuo.ui.activity.shouye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.CommonPingLun;
import com.youchao.tingshuo.ui.adapter.shouye.GalleryAdapter;
import com.youchao.tingshuo.ui.adapter.shouye.RecyclePingLunAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.MToast;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Bind(R.id.recyclerview_dianzan)
    RecyclerView mRecyclerviewDianzan;
    @Bind(R.id.edit_xiepinglun)
    TextView mEditXiepinglun;
    @Bind(R.id.pinglun_iv_dianzan)
    ImageView mPinglunIvDianzan;
    @Bind(R.id.pinglun_tv_dianzan)
    TextView mPinglunTvDianzan;
    @Bind(R.id.pinglun_ll_dianzan)
    RelativeLayout mPinglunLlDianzan;
    private RecyclePingLunAdapter mAdapter;
    private List<CommonPingLun> mList;
    private GalleryAdapter mGalleryAdapter;
    private List<Integer> mDatas;

    @Override
    protected int initResource() {
        return R.layout.activity_common_dongtai;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("评论");
    }

    @Override
    protected void initData() {
        initTopRecycler();
        mList = new ArrayList<>();
        mList.add(new CommonPingLun("", "", "", "", "", ""));
        mList.add(new CommonPingLun("", "", "", "", "", "5645"));
        mList.add(new CommonPingLun("", "", "", "", "", "56"));
        mList.add(new CommonPingLun("", "", "", "", "", ""));
        mList.add(new CommonPingLun("", "", "", "", "", ""));
        mList.add(new CommonPingLun("", "", "", "", "", "564456"));
        mList.add(new CommonPingLun("", "", "", "", "", ""));

        mAdapter = new RecyclePingLunAdapter(mList, this);
        mPinglunListInFragment.setLayoutManager(new LinearLayoutManager(this));
        mPinglunListInFragment.setAdapter(mAdapter);
        //设置适配器的监听
        setAdapterListener(mAdapter);


    }

    private void initTopRecycler() {
        mDatas = new ArrayList<>(Arrays.asList(R.drawable.user_icon,
                R.drawable.user_icon, R.drawable.user_icon, R.drawable.user_icon, R.drawable.user_icon));
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerviewDianzan.setLayoutManager(linearLayoutManager);

        //设置适配器
        mGalleryAdapter = new GalleryAdapter(this, mDatas);
        mRecyclerviewDianzan.setAdapter(mGalleryAdapter);
    }

    private void setAdapterListener(RecyclePingLunAdapter adapter) {
        adapter.setOnHuiFuClickListener(new RecyclePingLunAdapter.OnHuiFuClickListener() {
            @Override
            public void onHuiFuClick(View v, int position) {
                MToast.showToast(mContext, "回复");
            }
        });
        adapter.setOnDianZanClickListener(new RecyclePingLunAdapter.OnDianZanClickListener() {
            @Override
            public void onDianZanClick(View v, int position) {
                MToast.showToast(mContext, "点赞");

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_toptitle_back,R.id.edit_xiepinglun, R.id.pinglun_ll_dianzan})
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
            case R.id.edit_xiepinglun:
                Intent intent = new Intent(this,WritePingLunActivity.class);
                startActivity(intent);

                break;
            case R.id.pinglun_ll_dianzan:
                mPinglunIvDianzan.setImageResource(R.drawable.dianzan_select);
                break;

        }
    }



}
