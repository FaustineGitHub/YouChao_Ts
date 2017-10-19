package com.youchao.tingshuo.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.githang.statusbar.StatusBarCompat;
import com.loopj.android.http.RequestHandle;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.App;
import com.youchao.tingshuo.network.OnDataListener;
import com.youchao.tingshuo.network.RequestAction;
import com.youchao.tingshuo.ui.activity.login.LoginActivity;
import com.youchao.tingshuo.ui.widget.dialog.LoadDialog;
import com.youchao.tingshuo.ui.widget.dialog.MyNewDialogBuilder;
import com.youchao.tingshuo.ui.widget.dialog.MyDialogBuilder;
import com.youchao.tingshuo.utils.ConstantUtlis;
import com.youchao.tingshuo.utils.EasyPermissions;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.MToast;
import com.youchao.tingshuo.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/9/11.
 * 基础的Fragment
 */

public class BaseFragment extends Fragment implements OnDataListener, EasyPermissions.PermissionCallbacks{
    private static final String TAG = "BaseFragment";
    protected Context mContext;
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;
    protected RequestAction requestAction;
    protected ArrayList<RequestHandle> requestHandleArrayList = new ArrayList<>();
    private MyNewDialogBuilder myDialogBuilder;

    public void setRequestErrorCallback(RequestErrorCallback requestErrorCallback) {
        this.requestErrorCallback = requestErrorCallback;
    }

    protected RequestErrorCallback requestErrorCallback;

    // 请求状态！=成功回调
    public interface RequestErrorCallback {
        void requestErrorcallback(int requestTag, JSONObject response) throws Exception;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //preferences = SharedPreferencesUtils.getInstance().getSharedPreferences();
        //editor = preferences.edit();
        mContext = getActivity();
        //修改字体
        //TypefaceUtil.replaceFont(getActivity(), "fonts/youchao_ziti.ttf");
        requestAction = new RequestAction();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.colorTabSelected));
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelRequestHandle();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 请求开始
     *
     * @param requestTag 请求标志
     */
    @Override
    public void onStart(int requestTag, int showLoad) {
        L.d(TAG, "onStart: " + requestTag);
        if (showLoad == 0 || showLoad == 1) {
            LoadDialog.show(mContext, requestHandleArrayList);
        }
    }

    /**
     * 请求成功(过滤 状态=成功)
     *
     * @param requestTag 请求标志
     * @param response   请求返回
     * @throws JSONException
     */
    @Override
    public void requestSuccess(int requestTag, JSONObject response, int showLoad) throws JSONException {

    }

    /**
     * 请求成功
     *
     * @param requestTag 请求标志
     * @param response   请求返回
     */
    @Override
    public void onSuccess(int requestTag, JSONObject response, int showLoad) {
        L.d(TAG, "requestTag: " + requestTag + " onSuccess: " + response);
        L.e(TAG, "onSuccess: " + response);
        if (showLoad == 0 || showLoad == 2) {
            LoadDialog.dismiss(mContext);
        }
        try {
            if (response.getString("状态").equals("成功")) {
                requestSuccess(requestTag, response, showLoad);
            } else if (response.getString("状态").equals("随机码不正确")) {
                myDialogBuilder = MyNewDialogBuilder.getInstance(mContext);
                myDialogBuilder.setCancelable(false);//设置返回键不可点击
                myDialogBuilder
                        .withTitie("提示")
                        .withCenterContent("该账号在其他设备登录\n请您重新登录")
                        .withEffects(MyDialogBuilder.SlideTop, MyDialogBuilder.SlideTopDismiss)
                        .setOneBtn("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialogBuilder.dismissNoAnimator();
                                startActivity(new Intent(mContext, LoginActivity.class));
                                App.getInstance().exit();//这里不要简单的finish，而应该退出整个应用
                            }
                        })
                        .show();

            } else if (response.getString("状态").contains("未获取到账号信息")) {
                myDialogBuilder = MyNewDialogBuilder.getInstance(mContext);
                myDialogBuilder.setCancelable(false);//设置返回键不可点击
                myDialogBuilder
                        .withTitie("提示")
                        .withCenterContent("账号状态异常\n请您重新登录")
                        .withEffects(MyDialogBuilder.SlideTop, MyDialogBuilder.SlideTopDismiss)
                        .setOneBtn("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialogBuilder.dismissNoAnimator();
                                SharedPreferencesUtils.setString(mContext, ConstantUtlis.SP_LOGINSTATE, "失败");//设置登录状态
                                startActivity(new Intent(mContext, LoginActivity.class));
                                App.getInstance().exit();//这里不要简单的finish，而应该退出整个应用
                            }
                        })
                        .show();
            } else {
                if (requestErrorCallback != null) {
                    requestErrorCallback.requestErrorcallback(requestTag, response);
                } else {
                    MToast.showToast(mContext, response.getString("状态"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
//            DialogUtils.oneBtnNormal(mContext, "数据异常-1\n请稍后再试");
        } catch (Exception e) {
            e.printStackTrace();
//            DialogUtils.oneBtnNormal(mContext, "数据异常-2\n请稍后再试");
        }
    }

    /**
     * 请求失败
     *
     * @param requestTag    请求标志
     * @param errorResponse 错误请求返回
     */
    @Override
    public void onFailure(int requestTag, JSONObject errorResponse, int showLoad) {
        L.e(TAG, "onFailure: " + requestTag + " errorResponse: " + errorResponse);
        MToast.showToast(mContext, "请求超时,请检查你的网络!");
        LoadDialog.dismiss(mContext);
    }

    @Override
    public void onCancel(int requestTag, int showLoad) {
        L.e(TAG, "onCancel: " + requestTag);
    }

    /**
     * 取消网络请求
     */
    public void cancelRequestHandle() {
        if (requestHandleArrayList.size() != 0) {
            for (int i = 0; i < requestHandleArrayList.size(); i++) {
                requestHandleArrayList.get(i).cancel(true);
            }
        }
    }

    //判断是否登录
    public boolean check_login() {
        if (preferences.getString(ConstantUtlis.SP_LOGINSTATE, "").equals("成功")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * 权限回调接口
     */
    private BaseActivity.CheckPermListener mListener;

    public interface CheckPermListener {
        //权限通过后的回调方法
        void superPermission();
    }

    public void checkPermission(BaseActivity.CheckPermListener listener, int resString, String... mPerms) {
        mListener = listener;
        if (EasyPermissions.hasPermissions(getActivity(), mPerms)) {
            if (mListener != null)
                mListener.superPermission();
        } else {
            EasyPermissions.requestPermissions(this, getString(resString), 123, mPerms);
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EasyPermissions.SETTINGS_REQ_CODE) {
            //设置返回
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //同意了某些权限可能不是全部
    }

    @Override
    public void onPermissionsAllGranted() {
        if (mListener != null)
            mListener.superPermission();//同意了全部权限的回调
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.perm_tip),
                R.string.setting, R.string.cancel, null, perms);
    }
}
