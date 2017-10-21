package com.youchao.tingshuo.ui.activity.fanyi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.adapter.CommonAdapter;
import com.youchao.tingshuo.ui.adapter.fanyi.GameAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/21.
 * 小游戏页面
 */

public class GameActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.iv_game_head)
    ImageView mIvGameHead;
    @Bind(R.id.game_recycler)
    RecyclerView mGameRecycler;
    private List<String[]> mList;
    private GameAdapter mGameAdapter;

    @Override
    protected int initResource() {
        return R.layout.activity_game;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("小游戏");

    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add(new String[]{"http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg","开心消消乐","4","是上做好玩的策略游戏"});
        mList.add(new String[]{"http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg","俄罗斯方块","5","是上做好玩的策略游戏"});
        mList.add(new String[]{"http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg","斗地主","3","是上做好玩的策略游戏","开心消消乐","4","是上做好玩的策略游戏"});

        mGameAdapter = new GameAdapter(mList,this);
        mGameRecycler.setLayoutManager(new LinearLayoutManager(this));
        mGameRecycler.setAdapter(mGameAdapter);

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
