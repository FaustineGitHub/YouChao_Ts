package com.youchao.tingshuo.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.adapter.shouye.ViewPagerAdapter;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.ui.fragment.mine.DongTaiFragment;
import com.youchao.tingshuo.ui.fragment.mine.TabFragmentZiLiao;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.MToast;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.youchao.tingshuo.utils.BitmapUtils.dip2px;


/**
 * Created by dell on 2017/9/26.
 * 个人介绍的详情页（自己查看，和别人查看时页面数据不同）
 */

public class PersonalIntroduceActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.introduce_iv_back)
    ImageView mIntroduceIvBack;
    @Bind(R.id.introduce_iv_zxing)
    ImageView mIntroduceIvZxing;
    @Bind(R.id.introduce_iv_user_icon)
    ImageView mIntroduceIvUserIcon;
    @Bind(R.id.introduce_tv_nickname)
    TextView mIntroduceTvNickname;
    @Bind(R.id.introduce_tv_info)
    TextView mIntroduceTvInfo;
    @Bind(R.id.introduce_iv_bianji)
    ImageView mIntroduceIvBianji;
    @Bind(R.id.introduce_view_1)
    View mIntroduceView1;
    @Bind(R.id.ll_center)
    LinearLayout mLlCenter;
    @Bind(R.id.introduce_ll_header)
    RelativeLayout mIntroduceLlHeader;
    @Bind(R.id.introduce_tabs)
    TabLayout mIntroduceTabs;
    @Bind(R.id.introduce_viewpager)
    ViewPager mIntroduceViewpager;
    @Bind(R.id.main_collapsing)
    CollapsingToolbarLayout mMainCollapsing;
    @Bind(R.id.main_appbar)
    AppBarLayout mMainAppbar;
    @Bind(R.id.rl_guanzhu_person)
    RelativeLayout mRlGuanzhuPerson;
    @Bind(R.id.rl_chat_person)
    RelativeLayout mRlChatPerson;
    @Bind(R.id.ll_friends)
    LinearLayout mLlFriends;

    private List<Integer> mDatas;
    private String tag;


    @Override
    protected int initResource() {
        return R.layout.activity_personal_introduce;
    }

    @Override
    protected void initComponent() {

        Intent intent = this.getIntent();        //获取已有的intent对象
        Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象
        tag = bundle.getString("TAG");    //获取Bundle里面的字符串
        if ("Friends".equals(tag)) {//他人动态
            mLlFriends.setVisibility(View.VISIBLE);
            mIntroduceIvBianji.setVisibility(View.GONE);
        } else if ("Mine".equals(tag)){//自己动态
            mLlFriends.setVisibility(View.GONE);
            mIntroduceIvBianji.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initData() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabFragmentZiLiao(), "资料");
        adapter.addFragment(new DongTaiFragment(), "动态");
        //添加完所有tab后调用！！
        reflex(mIntroduceTabs);
        mIntroduceViewpager.setAdapter(adapter);
        mIntroduceTabs.setupWithViewPager(mIntroduceViewpager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //自定义TabLayout下划线的长度
    public void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 40);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @OnClick({R.id.introduce_iv_back, R.id.introduce_iv_zxing, R.id.introduce_iv_bianji, R.id.rl_guanzhu_person, R.id.rl_chat_person})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.introduce_iv_back:
                View view1 = this.getWindow().peekDecorView();
                if (view1 != null) {
                    InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                this.finish();
                break;
            case R.id.introduce_iv_zxing://资料  三点
                break;
            case R.id.introduce_iv_bianji:
                intent = new Intent(this, PersonalDataActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_guanzhu_person:
                MToast.showToast(this,"关注待开发");
                break;
            case R.id.rl_chat_person:
                MToast.showToast(this,"聊天待开发");
                break;
        }
    }


}
