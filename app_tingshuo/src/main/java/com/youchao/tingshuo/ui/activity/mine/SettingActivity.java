package com.youchao.tingshuo.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trycatch.mysnackbar.ScreenUtil;
import com.trycatch.mysnackbar.TSnackbar;
import com.youchao.tingshuo.App;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.activity.MainActivity_Bottom;
import com.youchao.tingshuo.ui.activity.login.LoginActivity;
import com.youchao.tingshuo.ui.activity.mine.update.UpdateManager;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.ui.widget.SlideSwitch;
import com.youchao.tingshuo.ui.widget.dialog.MyDialogBuilder;
import com.youchao.tingshuo.utils.CleanMessageUtil;
import com.youchao.tingshuo.utils.ConstantUtlis;
import com.youchao.tingshuo.utils.SharedPreferencesUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/10/9.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.slideSwitch_miandarao)
    SlideSwitch mSlideSwitchMiandarao;
    @Bind(R.id.slideSwitch_liaotian)
    SlideSwitch mSlideSwitchLiaotian;
    @Bind(R.id.setting_rl_heimingdan)
    RelativeLayout mSettingRlHeimingdan;
    @Bind(R.id.setting_rl_clear)
    RelativeLayout mSettingRlClear;
    @Bind(R.id.setting_tv_heimingdan)
    TextView mSettingTvHeimingdan;
    @Bind(R.id.setting_rl_new_banben)
    RelativeLayout mSettingRlNewBanben;
    @Bind(R.id.setting_rl_lianxi)
    RelativeLayout mSettingRlLianxi;
    @Bind(R.id.setting_rl_yijian)
    RelativeLayout mSettingRlYijian;
    @Bind(R.id.setting_tv_tuichu)
    TextView mSettingTvTuichu;
    @Bind(R.id.setting_rl_account_manager)
    RelativeLayout mSettingRlAccountManager;

    private TSnackbar snackBar;
    private int APP_DOWn = TSnackbar.APPEAR_FROM_TOP_TO_DOWN;
    private MyDialogBuilder mMyDialogBuilder;

    @Override
    protected int initResource() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("设置");
        mSettingTvHeimingdan.setText(getVersion());

    }

    @Override
    protected void initData() {

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.setting_rl_account_manager,R.id.iv_toptitle_back,  R.id.slideSwitch_miandarao, R.id.slideSwitch_liaotian, R.id.setting_rl_heimingdan, R.id.setting_rl_clear, R.id.setting_rl_new_banben, R.id.setting_rl_lianxi, R.id.setting_rl_yijian, R.id.setting_tv_tuichu})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {

            case R.id.iv_toptitle_back:
                View view1 = this.getWindow().peekDecorView();
                if (view1 != null) {
                    InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                this.finish();
                break;
            case R.id.setting_rl_account_manager://账号管理
                break;
            case R.id.slideSwitch_miandarao:
                break;
            case R.id.slideSwitch_liaotian:
                break;
            case R.id.setting_rl_heimingdan:
                break;
            case R.id.setting_rl_clear:
                CleanMessageUtil.clearAllCache(getApplicationContext());
                setSnackBar("清除缓存成功");
                break;
            case R.id.setting_rl_new_banben:
                UpdateManager manager = new UpdateManager(SettingActivity.this);
                // 检查软件更新
                if (manager.checkUpdate() == false){
                    setSnackBar("已经是最新版本");
                }else {
                    manager.checkUpdate();
                }
                break;
            case R.id.setting_rl_lianxi://关于我们
//                intent = new Intent(this,LianXiKeFuActivity.class);
//                startActivity(intent);

                break;
            case R.id.setting_rl_yijian:
                intent = new Intent(this,RealNameAuthenticationActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_tv_tuichu:
                mMyDialogBuilder = MyDialogBuilder.getInstance(mContext);
                mMyDialogBuilder
                        .withContene("您确定要退出登录吗")
                        .withEffects(MyDialogBuilder.SlideTop, MyDialogBuilder.SlideTopDismiss)
                        .setBtnClick("确定", MyDialogBuilder.BtnNormal, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMyDialogBuilder.dismissNoAnimator();
                                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                                SharedPreferencesUtils.setString(mContext, ConstantUtlis.SP_LOGINSTATE, "失败");//登录状态
                                //取消通知
                                /*if (mBuilder != null) {
                                    mNotifyMgr.cancel(001);
                                }*/
                                MainActivity_Bottom.mainActivity.finish();//因为MainActivity没有加入到这个集合中，需手动finish.
                                App.getInstance().exit();//这里不要简单的finish，而应该退出整个应用。0323修改，注意一定要先finish MainActivity，在调用这个方法。

                            }
                        })
                        .setBtnClick("取消", MyDialogBuilder.BtnCancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMyDialogBuilder.dismiss();
                            }
                        }).show();
                break;
        }
    }

    //设置顶部提示框
    private void setSnackBar(String content) {
        final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.snackbar_container).getRootView();
        int height = ScreenUtil.getStatusHeight(mContext);//状态栏的高度
        //snackBar = TSnackbar.make(viewGroup, content, TSnackbar.LENGTH_INDEFINITE, APP_DOWn);//不会自动消失
        snackBar = TSnackbar.make(viewGroup, content, TSnackbar.LENGTH_LONG, APP_DOWn);//会自动消失
        snackBar.addIcon(R.drawable.chenggong, 100, 100);
        snackBar.setTextColor(Color.parseColor("#ff222222"));
        /*snackBar.setAction("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        //设置背景颜色
        View mView = snackBar.getView();
        mView.setBackgroundColor(Color.parseColor("#00D1CA"));
        //设置Snackbar的高度--不包括zhuang
        //snackBar.setMinHeight(0, height + 100);
        snackBar.show();
    }
    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "当前版本" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "未获取到当前版本号";
        }
    }



}
