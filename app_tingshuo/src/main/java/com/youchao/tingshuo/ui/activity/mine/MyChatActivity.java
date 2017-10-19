package com.youchao.tingshuo.ui.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.CommonUserList;
import com.youchao.tingshuo.ui.adapter.mine.RecyclerViewUserListAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.view.ListViewDecoration1;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/19.
 * 我的聊天页面---recylerview+title
 */

public class MyChatActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.recycler_fans_list)
    RecyclerView mRecyclerFansList;
    private RecyclerViewUserListAdapter mAdapter;
    private List<CommonUserList> mList;

    @Override
    protected int initResource() {
        return R.layout.activity_fans_list;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("聊天");
        mList = new ArrayList<>();
        mList.add(new CommonUserList("http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg", "好看的皮囊", "明天见","上午10：09"));
        mList.add(new CommonUserList("http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg", "好看的皮囊", "明天见","昨天"));
        mList.add(new CommonUserList("http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg", "好看的皮囊", "明天见","1/10/2017"));

    }

    @Override
    protected void initData() {
        mAdapter = new RecyclerViewUserListAdapter(mList, this);
        mRecyclerFansList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerFansList.addItemDecoration(new ListViewDecoration1(this));
        mRecyclerFansList.setAdapter(mAdapter);

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
