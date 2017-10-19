package com.youchao.tingshuo.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
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
 * Created by dell on 2017/10/10.
 * 粉丝列表
 */

public class FansActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.recycler_fans_list)
    RecyclerView mRecyclerFansList;
    private RecyclerViewUserListAdapter mAdapter;
    private List<CommonUserList> mList = new ArrayList<>();
    private List<CommonUserList> mList_guanzhu;
    private List<CommonUserList> mList_fans;
    private String TAG;

    @Override
    protected int initResource() {
        return R.layout.activity_fans_list;
    }

    @Override
    protected void initComponent() {
        mList_guanzhu = new ArrayList<>();
        mList_guanzhu.add(new CommonUserList("http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg", "RSGRHFH", "的封建快攻好的开发机构和对方尽快给回复","已关注"));
        mList_guanzhu.add(new CommonUserList("http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg", "GFHDFGHFGH", "的封建快攻好的开发机构和对方尽快给回复公司哦人骨松i故事肉欸故事日哦俄国","已关注"));
        mList_guanzhu.add(new CommonUserList("http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg", "HFGDHDFGH", "的封建快攻好的开发机构和对方尽快给回复","已关注"));
        mList_fans = new ArrayList<>();
        mList_fans.add(new CommonUserList("http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg", "RSGRHFH", "的封建快攻好的开发机构和对方尽快给回复","互相关注"));
        mList_fans.add(new CommonUserList("http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg", "GFHDFGHFGH", "的封建快攻好的开发机构和对方尽快给回复公司哦人骨松i故事肉欸故事日哦俄国","未关注"));
        mList_fans.add(new CommonUserList("http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg", "HFGDHDFGH", "的封建快攻好的开发机构和对方尽快给回复","未关注"));


        Intent intent = this.getIntent();        //获取已有的intent对象
        Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象
        TAG = bundle.getString("TAG");    //获取Bundle里面的字符串
        if (TAG.equals("GUANZHU")){
            mTvToptitleTitle.setText("关注");
            mList = mList_guanzhu;
        }else {
            mTvToptitleTitle.setText("粉丝");
            mList = mList_fans;
        }
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
