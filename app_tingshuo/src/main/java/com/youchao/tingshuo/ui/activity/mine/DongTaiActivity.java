package com.youchao.tingshuo.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.CommonNews;
import com.youchao.tingshuo.ui.activity.shouye.PingLunDongTaiActivity;
import com.youchao.tingshuo.ui.adapter.shouye.CircleMessageAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.ui.widget.dialog.ShareDialog;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.MToast;
import com.youchao.tingshuo.view.FullyLinearLayoutManager;
import com.youchao.tingshuo.view.ListViewDecoration30;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by dell on 2017/10/19.
 * 我的动态
 */

public class DongTaiActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.recycler_view_dongtai)
    RecyclerView mRecyclerViewDongtai;
    @Bind(R.id.swipe_layout_dongtai)
    SwipeRefreshLayout mSwipeLayoutDongtai;

    private String tag = "DongTaiActivity";

    private CircleMessageAdapter mCircleMessageAdapter;
    private List<CommonNews> list = new ArrayList<>();
    private int page = 0;

    private ArrayList<String> imgUrls1;  //一张图片数组
    private ArrayList<String> imgUrls2;  //无图片数组
    private ArrayList<String> imgUrls3;  //六张图片数组
    private ArrayList<String> imgUrls4;  //四张图片数组

    private ShareDialog shareDialog;

    @Override
    protected int initResource() {
        return R.layout.activity_dongtai;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("动态");

    }

    @Override
    protected void initData() {
        initSwipeMenuRecyclerView();
        mockData();
        processData();

    }

    private void initSwipeMenuRecyclerView() {
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
        manager.setOrientation(LinearLayout.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        mRecyclerViewDongtai.setLayoutManager(manager);

        mRecyclerViewDongtai.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRecyclerViewDongtai.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mRecyclerViewDongtai.addItemDecoration(new ListViewDecoration30(mContext));// 添加分割线。
        //RecyclerViewHeader必须在RecyclerView设置了LayoutManager之后调用
        //如果你打算在RecyclerView中使用setOnScrollListener(...)方法，确保在setOnScrollListener(...)的attachTo(...)方法之前使用。
        //mHeader.attachTo(mRecyclerViewShouye, true);

        // 添加滚动监听。
        mRecyclerViewDongtai.addOnScrollListener(mOnScrollListener);

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
        list.add(new CommonNews("春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls4, 0, "", "", "", "45", false,true));
        list.add(new CommonNews("春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls1, 0, "", "", "", "32", false,true));
        list.add(new CommonNews("春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4", imgUrls2, 1, "", "", "", "13", false,true));
        list.add(new CommonNews("春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls1, 0, "", "", "", "45", false,false));
        list.add(new CommonNews("春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls3, 1, "", "", "", "45", false,false));
        list.add(new CommonNews("春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4", imgUrls2, 1, "", "", "", "26", false,false));
        list.add(new CommonNews("春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls4, 1, "", "", "", "454", false,false));
        list.add(new CommonNews("春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls3, 1, "", "", "", "22", false,false));
        list.add(new CommonNews("春风十里", "vsdfsdfsadfsdaf", "2017_09_22", "", imgUrls2, 2, "", "", "", "245", false,false));

    }

    private void processData() {
        mCircleMessageAdapter = new CircleMessageAdapter(this, list,tag);
        mRecyclerViewDongtai.setAdapter(mCircleMessageAdapter);
        //设置适配器的监听
        setAdapterListener(mCircleMessageAdapter);
        getData_jiaoyi();
    }

    private void getData_jiaoyi() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCircleMessageAdapter.addAllData(list);
                        //mCircleMessageAdapter.setPullLoadMoreCompleted();
                    }
                });

            }
        }, 100);
    }

    private void setAdapterListener(final CircleMessageAdapter circleMessageAdapter) {
        //设置item的点击事件
        circleMessageAdapter.setOnTotalItemClickListener(new CircleMessageAdapter.OnTotalItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        circleMessageAdapter.setOnZhuanFaClickListener(new CircleMessageAdapter.OnZhuanFaClickListener() {
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
            }
        });
        circleMessageAdapter.setOnDianZanClickListener(new CircleMessageAdapter.OnDianZanClickListener() {
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
        circleMessageAdapter.setOnPingLunClickListener(new CircleMessageAdapter.OnPingLunClickListener() {

            @Override
            public void onPingLunClick(View v, int position) {
                Intent intent = new Intent(DongTaiActivity.this, PingLunDongTaiActivity.class);
                //intent.putExtra("url", mList.get(position).getUrl());
                //intent.putExtra("title", mList.get(position).getTitle());
                startActivity(intent);
            }
        });
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
    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
           /* PersonalIntroduceActivity activity = (PersonalIntroduceActivity) getActivity();
            activity.scrollBy(dy);
            Log.e("tag","滑动距离=" + dy);*/
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
