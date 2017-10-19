package com.youchao.tingshuo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.adapter.MainFragmentAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.ui.widget.fragmentnavigator.FragmentNavigator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/12.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int DEFAULT_POSITION = 0;
    @Bind(R.id.iv_shouye)
    ImageView mIvShouye;
    @Bind(R.id.tv_know)
    TextView mTvKnow;
    @Bind(R.id.rl_shouye)
    RelativeLayout mRlShouye;
    @Bind(R.id.iv_me)
    ImageView mIvMe;
    @Bind(R.id.tv_me)
    TextView mTvMe;
    @Bind(R.id.rl_me)
    RelativeLayout mRlMe;
    @Bind(R.id.ll_bottom_tab)
    LinearLayout mLlBottomTab;
    @Bind(R.id.iv_center)
    ImageView mIvCenter;
    @Bind(R.id.rl_main)
    RelativeLayout mRlMain;
    @Bind(R.id.main_frame_layout)
    FrameLayout mMainFrameLayout;

    private FragmentNavigator mNavigator;;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int initResource() {
        return R.layout.activity_main;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mNavigator = new FragmentNavigator(getSupportFragmentManager(), new MainFragmentAdapter(), R.id.main_frame_layout);
        mNavigator.setDefaultPosition(DEFAULT_POSITION); //0324修改，不在这里设默认位置，而在initData()中，根据从哪个页面进入的设置选中哪个位置。
        mNavigator.onCreate(savedInstanceState);
        setCurrentTab(mNavigator.getCurrentPosition());
        //mNavigator.showFragment(mNavigator.getCurrentPosition());
    }
    private void setCurrentTab(int currentPosition) {
        if (currentPosition == 0){
            //mNavigator.showFragment(currentPosition);
            // 设置图片文本的变化
            mIvShouye.setImageResource(R.drawable.quanzi_select);
            mTvKnow.setTextColor(getResources()
                    .getColor(R.color.colorTabSelected));
            mIvCenter.setImageResource(R.drawable.fanyi);
            mIvMe.setImageResource(R.drawable.wode);
            mTvMe.setTextColor(getResources().getColor(R.color.gray_999));
        }else if (currentPosition == 1){
            //mNavigator.showFragment(currentPosition);
            // 设置底部tab变化
            mIvShouye.setImageResource(R.drawable.quanzi);
            mTvKnow.setTextColor(getResources().getColor(R.color.gray_999));
            mIvCenter.setImageResource(R.drawable.fanyi);
            mIvMe.setImageResource(R.drawable.wode);
            mTvMe.setTextColor(getResources().getColor(R.color.gray_999));

        }else if (currentPosition == 2){
            //mNavigator.showFragment(currentPosition);
            // 设置底部tab变化
            mIvShouye.setImageResource(R.drawable.quanzi);
            mTvKnow.setTextColor(getResources().getColor(R.color.gray_999));
            mIvCenter.setImageResource(R.drawable.fanyi);
            mIvMe.setImageResource(R.drawable.wodeselect);
            mTvMe.setTextColor(getResources().getColor(R.color.colorTabSelected));

        }
        mNavigator.showFragment(currentPosition);

    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.rl_shouye, R.id.rl_me, R.id.iv_center})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_shouye:
                setCurrentTab(0);
                break;
            case R.id.iv_center:
                setCurrentTab(1);
                break;
            case R.id.rl_me:
                setCurrentTab(2);
                break;

        }
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
}
