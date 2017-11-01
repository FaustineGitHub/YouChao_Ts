package com.youchao.tingshuo.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.activity.shouye.SendTrendsActivity;
import com.youchao.tingshuo.ui.activity.shouye.SousuoActivity;
import com.youchao.tingshuo.ui.base.BaseFragment;
import com.youchao.tingshuo.ui.fragment.shouye.GuanZhuFragment;
import com.youchao.tingshuo.ui.fragment.shouye.HotFragment;
import com.youchao.tingshuo.ui.widget.dialog.MyDialogBuilder;
import com.youchao.tingshuo.utils.MToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/12.
 */

public class CircleFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = CircleFragment.class.getSimpleName();
    @Bind(R.id.iv_shouye_left)
    ImageView mIvShouyeLeft;
    @Bind(R.id.iv_shouye_right)
    ImageView mIvShouyeRight;
    @Bind(R.id.shouye_viewpager)
    ViewPager mShouyeViewpager;
    @Bind(R.id.shouye_title)
    RelativeLayout mShouyeTitle;
    @Bind(R.id.shouye_tab)
    FixedIndicatorView mShouyeTab;
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    private int index;
    private String tabName;


    public static Fragment newInstance(int position) {
        CircleFragment fragment = new CircleFragment();
        return fragment;
    }

    public CircleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, view);

        return view;
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

    }

    private void initData() {
        mShouyeTab.setScrollBar(new ColorBar(getActivity(), Color.WHITE, 5));
        float unSelectSize = 16;
        float selectSize = unSelectSize;

        int selectColor = getResources().getColor(R.color.white);
        int unSelectColor = getResources().getColor(R.color.gray_999);
        mShouyeTab.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        mShouyeViewpager.setOffscreenPageLimit(1);
        indicatorViewPager = new IndicatorViewPager(mShouyeTab, mShouyeViewpager);
        inflate = LayoutInflater.from(getActivity());

        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面
        // 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        indicatorViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.iv_shouye_left, R.id.iv_shouye_right})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_shouye_left://编辑
                intent = new Intent(getActivity(), SendTrendsActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_shouye_right://搜索
                intent = new Intent(getActivity(), SousuoActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            if (position == 0){
                textView.setText("关注");
            }else {
                textView.setText("热门");
            }
            //textView.setText(tabName + " " + position);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            Fragment mainFragment = null;
            if (position == 0){
                mainFragment = new GuanZhuFragment();//无关注
            }else {
                mainFragment = new HotFragment();
            }
            return mainFragment;
        }
    }

}
