package com.youchao.tingshuo.ui.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.MessageCircle;
import com.youchao.tingshuo.ui.adapter.mine.MessageListAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.MToast;
import com.youchao.tingshuo.view.ListViewDecoration1;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/17.
 * 消息页面(d多布局)
 */

public class MessageListActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.tv_toptitle_right)
    TextView mTvToptitleRight;
    @Bind(R.id.message_recycler_view)
    SwipeMenuRecyclerView mMessageRecyclerView;
    @Bind(R.id.message_swipe_layout)
    SwipeRefreshLayout mMessageSwipeLayout;
    @Bind(R.id.tv_listview_no)
    TextView mTvListviewNo;

    private MessageListAdapter mMessageListAdapter;
    private List<MessageCircle> list = new ArrayList<>();
    private int page = 0;
    private int width;

    @Override
    protected int initResource() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("消息");
        mTvToptitleRight.setText("清空");
        mTvToptitleRight.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        //设置TextView两端对齐
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        width = dm.widthPixels;

    }

    @Override
    protected void initData() {
        mockData();
        processData();

    }

    private void mockData() {
        list.add(new MessageCircle("","","","",null,0,"","","","","gsfdg","fdgdsg","dfgdg"));
        list.add(new MessageCircle("","","","",null,0,"","","","","","fdgdsg","dfgdg"));
        list.add(new MessageCircle("","","","",null,0,"","","","","","","dfgdg"));

        list.add(new MessageCircle("","","","",null,1,"","","","","gsfdg","fdgdsg","dfgdg"));
        list.add(new MessageCircle("","","","",null,1,"","","","","","fdgdsg","dfgdg"));
        list.add(new MessageCircle("","","","",null,1,"","","","","","","dfgdg"));

        list.add(new MessageCircle("","","","",null,2,"","","","","gsfdg","fdgdsg","dfgdg"));
        list.add(new MessageCircle("","","","",null,2,"","","","","","fdgdsg","dfgdg"));
        list.add(new MessageCircle("","","","",null,2,"","","","","","","dfgdg"));

        list.add(new MessageCircle("","","","",null,3,"","","","","gsfdg","fdgdsg","dfgdg"));
        list.add(new MessageCircle("","","","",null,3,"","","","","","fdgdsg","dfgdg"));
        list.add(new MessageCircle("","","","",null,3,"","","","","","","dfgdg"));
    }
    private void processData() {
        mMessageListAdapter = new MessageListAdapter(list,this);
        //需要设置LayoutManager。否则RecyclerView不显示数据
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecyclerView.setAdapter(mMessageListAdapter);
        //设置分割线
        mMessageRecyclerView.addItemDecoration(new ListViewDecoration1(mContext));
        //设置适配器的监听
        setAdapterListener(mMessageListAdapter);
        getData_message();
    }
    private void setAdapterListener(MessageListAdapter messageListAdapter) {
        //设置item的点击事件
        mMessageListAdapter.setOnItemClickListener(new MessageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                MToast.showToast(mContext,"跳转到详情页面");
            }

            @Override
            public void onItemLongClick(View v, int position) {
                MToast.showToast(mContext,"删除该评论");

            }
        });
    }

    private void getData_message() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMessageListAdapter.addAllData(list);
                        L.e("TAG","获取数据 "+list.get(2).getType());
                        //mCircleMessageAdapter.setPullLoadMoreCompleted();
                    }
                });
            }
        }, 100);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    @OnClick({R.id.iv_toptitle_back, R.id.tv_toptitle_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toptitle_back://返回
                View view1 = this.getWindow().peekDecorView();
                if (view1 != null) {
                    InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                this.finish();
                break;
            case R.id.tv_toptitle_right://消息

                break;
        }
    }
}
