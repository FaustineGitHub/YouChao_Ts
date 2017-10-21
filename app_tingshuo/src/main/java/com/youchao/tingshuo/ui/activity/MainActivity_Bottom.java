package com.youchao.tingshuo.ui.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.activity.fanyi.GameActivity;
import com.youchao.tingshuo.ui.adapter.MainFragmentAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.ui.widget.fragmentnavigator.BottomNavigatorView;
import com.youchao.tingshuo.ui.widget.fragmentnavigator.FragmentNavigator;
import com.youchao.tingshuo.utils.AnimUtil;
import com.youchao.tingshuo.view.ChildClickableLinearLayout;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/12.
 * 底部导航栏中心为扇形
 */

public class MainActivity_Bottom extends BaseActivity implements View.OnClickListener, BottomNavigatorView.OnBottomNavigatorViewItemClickListener {
    private static final int DEFAULT_POSITION = 0;
    @Bind(R.id.container)
    FrameLayout mContainer;
    @Bind(R.id.bg_zhibo)
    ChildClickableLinearLayout mBgZhibo;
    @Bind(R.id.iv_solive)
    ImageView mIvSolive;
    @Bind(R.id.ll_spolive)
    LinearLayout mLlSpolive;
    @Bind(R.id.iv_dynamic)
    ImageView mIvDynamic;
    @Bind(R.id.ll_dynamic)
    LinearLayout mLlDynamic;
    @Bind(R.id.rl_menu)
    RelativeLayout mRlMenu;
    @Bind(R.id.ll_bottomshouye)
    BottomNavigatorView mLlBottomshouye;
    @Bind(R.id.iv_center)
    ImageView mIvCenter;


    private FragmentNavigator mNavigator;
    public static Activity mainActivity;

    private SimpleDateFormat formatter;

    public static NotificationCompat.Builder mBuilder;
    public static NotificationManager mNotifyMgr;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int initResource() {
        return R.layout.activity_main_bottom_na;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mNavigator = new FragmentNavigator(getSupportFragmentManager(), new MainFragmentAdapter(), R.id.container);
        mNavigator.setDefaultPosition(DEFAULT_POSITION); //0324修改，不在这里设默认位置，而在initData()中，根据从哪个页面进入的设置选中哪个位置。
        mNavigator.onCreate(savedInstanceState);
        if (mLlBottomshouye != null) {
            mLlBottomshouye.setOnBottomNavigatorViewItemClickListener(this);
        }
        setCurrentTab(mNavigator.getCurrentPosition());
        //mNavigator.showFragment(mNavigator.getCurrentPosition());
    }

    private void setCurrentTab(int currentPosition) {

        mNavigator.showFragment(currentPosition);
        mLlBottomshouye.select(currentPosition);
        //参数为true则所有子控件可以点击，false则不可点击。
        mBgZhibo.setChildClickable(true);
        if (mRlMenu.getVisibility() == View.VISIBLE) {
            AnimUtil.closeMenu(mRlMenu, 0);
        } else {
            mRlMenu.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData() {
        mainActivity = this;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBottomNavigatorViewItemClick(int position, View view) {
        setCurrentTab(position);
    }

    @OnClick({R.id.iv_solive, R.id.iv_dynamic, R.id.iv_center})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_solive://
                AnimUtil.closeMenu(mRlMenu, 0);//关闭弹框
                break;
            case R.id.iv_dynamic://小游戏
                intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                AnimUtil.closeMenu(mRlMenu, 0);//关闭弹框
                break;
            case R.id.iv_center:
                //点击中间按钮，出现直播和动态弹框
                if (mRlMenu.getVisibility() == View.VISIBLE) {
                    //参数为true则所有子控件可以点击，false则不可点击。
                    mBgZhibo.setChildClickable(true);
                    AnimUtil.closeMenu(mRlMenu, 0);
                } else {
                    AnimUtil.showMenu(mRlMenu, 0);
                    //参数为true则所有子控件可以点击，false则不可点击。
                    mBgZhibo.setChildClickable(false);
                }
                break;
        }
    }
}
