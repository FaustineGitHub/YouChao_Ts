package com.youchao.tingshuo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.activity.mine.DongTaiActivity;
import com.youchao.tingshuo.ui.activity.mine.FansActivity;
import com.youchao.tingshuo.ui.activity.mine.IdentityAuthenticationActivity;
import com.youchao.tingshuo.ui.activity.mine.MessageListActivity;
import com.youchao.tingshuo.ui.activity.mine.MyChatActivity;
import com.youchao.tingshuo.ui.activity.mine.PersonalIntroduceActivity;
import com.youchao.tingshuo.ui.activity.mine.SettingActivity;
import com.youchao.tingshuo.ui.activity.money.PaymentConfirmationActivity;
import com.youchao.tingshuo.ui.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/12.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = MyFragment.class.getSimpleName();
    @Bind(R.id.me_tv_username)
    TextView mMeTvUsername;
    @Bind(R.id.me_tv_userjianjie)
    TextView mMeTvUserjianjie;
    @Bind(R.id.tv_dongtai_num)
    TextView mTvDongtaiNum;
    @Bind(R.id.tv_guanzhu_num)
    TextView mTvGuanzhuNum;
    @Bind(R.id.tv_fans_num)
    TextView mTvFansNum;
    @Bind(R.id.my_qiandao)
    Button mMyQiandao;
    @Bind(R.id.my_chat)
    Button mMyChat;
    @Bind(R.id.my_wallet)
    Button mMyWallet;
    @Bind(R.id.my_tuijian_friends)
    Button mMyTuijianFriends;
    @Bind(R.id.my_yijian_fankui)
    Button mMyYijianFankui;
    @Bind(R.id.my_setting)
    Button mMySetting;
    @Bind(R.id.my_shenfen_renzheng)
    Button mMyShenfenRenzheng;
    @Bind(R.id.rl_user_ziliao)
    RelativeLayout mRlUserZiliao;
    @Bind(R.id.ll_dongtai)
    LinearLayout mLlDongtai;
    @Bind(R.id.ll_guanzhu)
    LinearLayout mLlGuanzhu;
    @Bind(R.id.ll_fans)
    LinearLayout mLlFans;
    @Bind(R.id.my_gird_layout)
    LinearLayout mMyGirdLayout;
    @Bind(R.id.iv_shouye_left)
    ImageView mIvShouyeLeft;
    @Bind(R.id.iv_shouye_right)
    ImageView mIvShouyeRight;
    @Bind(R.id.wode_title)
    RelativeLayout mWodeTitle;
    @Bind(R.id.me_iv_usericon)
    ImageView mMeIvUsericon;

    public static Fragment newInstance(int position) {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initData() {


    }

    @OnClick({R.id.iv_shouye_left, R.id.iv_shouye_right,R.id.rl_user_ziliao, R.id.ll_dongtai, R.id.ll_guanzhu, R.id.ll_fans, R.id.my_qiandao, R.id.my_chat, R.id.my_wallet, R.id.my_tuijian_friends, R.id.my_yijian_fankui, R.id.my_setting, R.id.my_shenfen_renzheng})
    public void onClick(View view) {
        Intent intent = null;
        Bundle bundle =null;
        switch (view.getId()) {
            case R.id.iv_shouye_left://消息
                intent = new Intent(getActivity(), MessageListActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_shouye_right:
                break;

            case R.id.my_qiandao:
                break;
            case R.id.my_chat:
                intent = new Intent(getActivity(), MyChatActivity.class);
                startActivity(intent);
                break;
            case R.id.my_wallet:
                intent = new Intent(getActivity(), PaymentConfirmationActivity.class);
                startActivity(intent);
                break;
            case R.id.my_tuijian_friends:
                break;
            case R.id.my_yijian_fankui:
                break;
            case R.id.my_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_shenfen_renzheng:
                intent = new Intent(getActivity(), IdentityAuthenticationActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_user_ziliao://个人资料
                intent = new Intent(getActivity(), PersonalIntroduceActivity.class);
                bundle = new Bundle();                    //创建Bundle对象
                bundle.putString("TAG","Mine");     //装入数据
                intent.putExtras(bundle);                                //把Bundle塞入Intent里面
                startActivity(intent);
                break;
            case R.id.ll_dongtai://动态
                intent = new Intent(getActivity(), DongTaiActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_guanzhu://关注
                intent = new Intent(getActivity(), FansActivity.class);
                bundle = new Bundle();                           //创建Bundle对象
                bundle.putString("TAG", "GUANZHU");     //装入数据
                intent.putExtras(bundle);                                //把Bundle塞入Intent里面
                startActivity(intent);
                break;
            case R.id.ll_fans://粉丝
                intent = new Intent(getActivity(), FansActivity.class);
                bundle = new Bundle();                           //创建Bundle对象
                bundle.putString("TAG", "FANS");     //装入数据
                intent.putExtras(bundle);                                //把Bundle塞入Intent里面
                startActivity(intent);
                break;
        }
    }

}
