package com.youchao.tingshuo.ui.fragment.shouye;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.CommonNews;
import com.youchao.tingshuo.ui.activity.mine.PersonalIntroduceActivity;
import com.youchao.tingshuo.ui.activity.shouye.PingLunDongTaiActivity;
import com.youchao.tingshuo.ui.adapter.shouye.CircleMessage1Adapter;
import com.youchao.tingshuo.ui.adapter.shouye.GalleryAdapter;
import com.youchao.tingshuo.ui.base.BaseFragment;
import com.youchao.tingshuo.ui.widget.dialog.ShareDialog;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.MToast;
import com.youchao.tingshuo.view.ListViewDecoration30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/12.
 * 关注
 */

public class GuanZhuFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = GuanZhuFragment.class.getSimpleName();
    @Bind(R.id.id_recyclerview_tuijian)
    RecyclerView mIdRecyclerviewTuijian;
    @Bind(R.id.iv_tuijian)
    ImageView mIvTuijian;
    @Bind(R.id.rl_shouye_tuijian)
    RelativeLayout mRlShouyeTuijian;
    @Bind(R.id.recycler_view_shouye)
    RecyclerView mRecyclerViewShouye;
    @Bind(R.id.header)
    RecyclerViewHeader mHeader;
    /*@Bind(R.id.recycler_view_shouye)
    SwipeMenuRecyclerView mRecyclerViewShouye;*/
    @Bind(R.id.swipe_layout_shouye)
    SwipeRefreshLayout mSwipeLayoutShouye;
    @Bind(R.id.tv_tuijian_type)
    TextView mTvTuijianType;

    private GalleryAdapter mAdapter;
    private List<Integer> mDatas;
    private ShareDialog shareDialog;

    private CircleMessage1Adapter mCircleMessageAdapter;
    private List<CommonNews> list = new ArrayList<>();
    private int page = 0;
    private String tag = "HotFragment";

    private ArrayList<String> imgUrls1;  //一张图片数组
    private ArrayList<String> imgUrls2;  //无图片数组
    private ArrayList<String> imgUrls3;  //六张图片数组
    private ArrayList<String> imgUrls4;  //四张图片数组

    public static Fragment newInstance(int position) {
        GuanZhuFragment fragment = new GuanZhuFragment();
        return fragment;
    }

    public GuanZhuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guanzhu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initIcon();
        initData();
    }

    private void initIcon() {
        mTvTuijianType.setText("同城推荐");
        mDatas = new ArrayList<>(Arrays.asList(R.drawable.user_icon,
                R.drawable.user_icon, R.drawable.user_icon));
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mIdRecyclerviewTuijian.setLayoutManager(linearLayoutManager);

        //设置适配器
        mAdapter = new GalleryAdapter(getActivity(), mDatas);
        mIdRecyclerviewTuijian.setAdapter(mAdapter);

    }

    private void initData() {
        initSwipeMenuRecyclerView();
        mockData();
        processData();
    }

    private void initSwipeMenuRecyclerView() {

        mRecyclerViewShouye.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器。
        mRecyclerViewShouye.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRecyclerViewShouye.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mRecyclerViewShouye.addItemDecoration(new ListViewDecoration30(mContext));// 添加分割线。
        //RecyclerViewHeader必须在RecyclerView设置了LayoutManager之后调用
        //如果你打算在RecyclerView中使用setOnScrollListener(...)方法，确保在setOnScrollListener(...)的attachTo(...)方法之前使用。
        mHeader.attachTo(mRecyclerViewShouye, true);

        // 添加滚动监听。
        //mRecyclerViewShouye.addOnScrollListener(mOnScrollListener);
    }

    private void mockData() {
        imgUrls1 = new ArrayList<>();
        imgUrls2 = new ArrayList<>();
        imgUrls3 = new ArrayList<>();
        imgUrls4 = new ArrayList<>();
        imgUrls1.add("http://img.deyi.net/forum/month_1002/20100210_d1e176261300d8bbb79cfWX91vBOOKiP.jpg");
        imgUrls3.add("http://m10.app111.org/forum/201109/25/232310amuc0t0qi99kkubt.jpg");
        imgUrls3.add("http://p0.so.qhimgs1.com/t01dc74deab434cbb10.jpg");
        imgUrls3.add("http://sony.it168.com/data/attachment/forum/201407/22/121421qbffxkr4okposkkf.jpg");
        imgUrls3.add("http://images.missyuan.com/attachments/day_080414/20080414_1e539b8c93472d7df169BfAmAENshpdG.jpg");
        imgUrls3.add("http://fj.heze.cc/attachments/forum/201112/15/154515m9mn69i33mkidce9.jpg");
        imgUrls3.add("http://img3.redocn.com/20120313/Redocn_2012031309094696.jpg");

        imgUrls4.add("http://cn.club.vmall.com/data/attachment/forum/201208/22/071725awmda433jh9jjddq.jpg");
        imgUrls4.add("http://p1.so.qhimgs1.com/t0189b12bc2dd0a84ad.jpg");
        imgUrls4.add("http://attachments.gfan.com/forum/201504/07/095447evh9cigggne1celv.jpg");
        imgUrls4.add("http://img.sucai.redocn.com/attachments/images/201207/20120707/Redocn_2012070709495130.jpg");
        //String title, String source, String time, String url, String[] imgs, String cover, String userIcon, String zhuanfa, String pinglun, String dianzan//
        list.add(new CommonNews(0,"春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls3, 1, "", "", "", "45", false, true));
        list.add(new CommonNews(1,"春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4", imgUrls2, 1, "", "", "", "26", false, false));
        list.add(new CommonNews(2,"春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls4, 1, "", "", "", "454", false, true));
        list.add(new CommonNews(3,"春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls3, 1, "", "", "", "22", false, true));
        list.add(new CommonNews(4,"春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls2, 2, "", "", "", "245", false, false));
        list.add(new CommonNews(5,"春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls4, 0, "", "", "", "45", false, false));
        list.add(new CommonNews(6,"春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls1, 0, "", "", "", "32", false, false));
        list.add(new CommonNews(7,"春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4", imgUrls2, 1, "", "", "", "13", false, true));
        list.add(new CommonNews(8,"春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls1, 0, "", "", "", "45", false, false));
    }

    private void processData() {
        mCircleMessageAdapter = new CircleMessage1Adapter(getActivity(), list, tag);
        mRecyclerViewShouye.setAdapter(mCircleMessageAdapter);
        //设置适配器的监听
        setAdapterListener(mCircleMessageAdapter);
        getData_jiaoyi();
    }

    private void getData_jiaoyi() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCircleMessageAdapter.addAllData(list);
                        //mCircleMessageAdapter.setPullLoadMoreCompleted();
                    }
                });

            }
        }, 100);
    }

    private void setAdapterListener(final CircleMessage1Adapter circleMessageAdapter) {
        //设置item的点击事件
        circleMessageAdapter.setOnTotalItemClickListener(new CircleMessage1Adapter.OnTotalItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        circleMessageAdapter.setOnZhuanFaClickListener(new CircleMessage1Adapter.OnZhuanFaClickListener() {
            @Override
            public void onZhuanFaClick(View v, int position) {
                shareDialog = new ShareDialog(mContext, R.style.dialog_share, new ShareDialog.ShareMyDialogListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.btn_weixin:
                                MToast.showToast(mContext, "微信");
                                break;
                            case R.id.btn_weixin_circle:
                                break;
                            case R.id.btn_qq:
                                break;
                            case R.id.btn_qq_kongjian:
                                break;
                            case R.id.btn_sina:
                                break;
                            case R.id.btn_cancle:

                                break;
                            default:
                                break;
                        }
                        shareDialog.dismiss();
                    }
                });
                shareDialog.show();
                //MToast.showToast(getActivity(), "转发");
            }
        });
        circleMessageAdapter.setOnUserIconClickListener(new CircleMessage1Adapter.OnUserIconClickListener() {
            @Override
            public void onUserIconClick(View v, int position) {
                Intent intent = new Intent(getActivity(), PersonalIntroduceActivity.class);
                Bundle bundle = new Bundle();                           //创建Bundle对象
                bundle.putString("TAG", "Friends");     //装入数据
                intent.putExtras(bundle);                                //把Bundle塞入Intent里面
                startActivity(intent);

            }
        });
        circleMessageAdapter.setOnDianZanClickListener(new CircleMessage1Adapter.OnDianZanClickListener() {
            @Override
            public void onDianZanClick(View v, int position) {
                //执行请求
                if (list.get(position).isZan() == false) {//未点赞
                    list.get(position).setZan(true);
                } else {
                    list.get(position).setZan(false);
                }
                circleMessageAdapter.notifyDataSetChanged();

            }
        });
        circleMessageAdapter.setOnPingLunClickListener(new CircleMessage1Adapter.OnPingLunClickListener() {

            @Override
            public void onPingLunClick(View v, int position) {
                Intent intent = new Intent(getActivity(), PingLunDongTaiActivity.class);
                //intent.putExtra("url", mList.get(position).getUrl());
                //intent.putExtra("title", mList.get(position).getTitle());
                startActivity(intent);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.rl_shouye_tuijian)
    public void onClick(View view) {
    }

    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。

                if (list.size() != 0) {
                    if (page > 0) {
                        //request(2, page + 1, 3, 0);
                    } else {
                        if (list.size() > 10) {
                            //MToast.showToast(mContext, "已无数据加载");
                        }
                        L.e("加载更多执行", "page==0");
                    }
                }

            }
        }
    };
}
