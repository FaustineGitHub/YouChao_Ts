package com.youchao.tingshuo.ui.activity.shouye;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.adapter.CommonAdapter;
import com.youchao.tingshuo.ui.adapter.ViewHolder;
import com.youchao.tingshuo.ui.adapter.shouye.TuiJianAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.ImageLoaderUtil;
import com.youchao.tingshuo.view.ListViewDecoration1;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/17.
 * 推荐
 */

public class TuiJianActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.recycler_fans_list)
    RecyclerView mRecyclerFansList;
    private List<String[]> mList;
    private TuiJianAdapter mTuiJianAdapter;
    private String tuijian_type;

    @Override
    protected int initResource() {
        return R.layout.activity_fans_list;
    }

    @Override
    protected void initComponent() {
        tuijian_type = getIntent().getStringExtra("TUIJIANTYPE");
        if ("TONGCHNEG".equals(tuijian_type)){//同城推荐
            mTvToptitleTitle.setText("同城推荐");
        }else {
            mTvToptitleTitle.setText("耳朵推荐");
        }
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add(new String[]{"http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg","TOM","帅气有迷人的反派角色","198","693","关注"});
        mList.add(new String[]{"http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg","TOM","帅气有迷人的反派角色","198","693","关注"});
        mList.add(new String[]{"http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg","TOM","帅气有迷人的反派角色","198","693","关注"});
        mTuiJianAdapter = new TuiJianAdapter(mList,this);
        mRecyclerFansList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerFansList.addItemDecoration(new ListViewDecoration1(mContext));// 添加分割线。
        mRecyclerFansList.setAdapter(mTuiJianAdapter);


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
