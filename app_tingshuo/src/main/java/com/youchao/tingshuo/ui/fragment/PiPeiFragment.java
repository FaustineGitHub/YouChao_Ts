package com.youchao.tingshuo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;
import com.squareup.picasso.Picasso;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.SwipeCardBean;
import com.youchao.tingshuo.ui.base.BaseFragment;
import com.youchao.tingshuo.ui.widget.swipecard.TanTanCallback;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.MToast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/12.
 * 匹配翻译页面
 */

public class PiPeiFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = PiPeiFragment.class.getSimpleName();
    @Bind(R.id.tv_pipei_title)
    TextView mTvPipeiTitle;
    @Bind(R.id.rv_pipei)
    RecyclerView mRvPipei;
    @Bind(R.id.activity_swipe_card)
    RelativeLayout mActivitySwipeCard;

    CommonAdapter<SwipeCardBean> mAdapter;
    List<SwipeCardBean> mDatas;
    @Bind(R.id.iv_love)
    ImageView mIvLove;
    @Bind(R.id.iv_del)
    ImageView mIvDel;

    public static Fragment newInstance(int position) {
        PiPeiFragment fragment = new PiPeiFragment();
        return fragment;
    }

    public PiPeiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pipei, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        mRvPipei.setLayoutManager(new OverLayCardLayoutManager());
        mRvPipei.setAdapter(mAdapter = new CommonAdapter<SwipeCardBean>(getActivity(), mDatas = SwipeCardBean.initDatas(), R.layout.item_swipe_card) {
            public static final String TAG = "zxt/Adapter";

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Log.d(TAG, "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");
                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
                super.onBindViewHolder(holder, position);
            }

            @Override
            public void convert(ViewHolder viewHolder, SwipeCardBean swipeCardBean) {
                Log.d(TAG, "convert() called with: viewHolder = [" + viewHolder + "], swipeCardBean = [" + swipeCardBean + "]");
                viewHolder.setText(R.id.tv_pipei_name, swipeCardBean.getName());
                viewHolder.setText(R.id.tv_pipei_constellation, swipeCardBean.getConstellation());
                viewHolder.setText(R.id.tv_pipei_age, swipeCardBean.getPostition()+"岁");
                viewHolder.setText(R.id.tv_pipei_time, swipeCardBean.getTime());
                viewHolder.setText(R.id.tv_pipei_percent, swipeCardBean.getPipei_percent());
                Picasso.with(getActivity()).load(swipeCardBean.getUrl()).into((ImageView) viewHolder.getView(R.id.iv_pipei_image));
            }
        });

        CardConfig.initConfig(getActivity());

        final TanTanCallback callback = new TanTanCallback(mRvPipei, mAdapter, mDatas);

        //测试竖直滑动是否已经不会被移除屏幕
        //callback.setHorizontalDeviation(Integer.MAX_VALUE);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRvPipei);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_love, R.id.iv_del})
    public void onClick(View view) {
        OverLayCardLayoutManager ma = (OverLayCardLayoutManager) mRvPipei.getLayoutManager();
        switch (view.getId()) {
            case R.id.iv_love://删除
               //★实现循环的要点
                if (ma.getItemCount()>0){
                    Object remove = mDatas.remove(ma.getItemCount()-1);
                    mDatas.add(0, (SwipeCardBean) remove);
                    L.e("TAG","remove = ");
                    mAdapter.notifyDataSetChanged();
                }else {
                    MToast.showToast(getActivity(),"已无数据加载");
                }

                Log.e("tag","看看是不是这个索引" + ma.getItemCount());

                break;
            case R.id.iv_del:

                break;
        }
    }
}
