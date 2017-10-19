package com.youchao.tingshuo.ui.activity.mine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.base.BaseActivity;
import com.youchao.tingshuo.utils.MToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/27.
 * 获取二维码
 */

public class HuoQuErWeiMaActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.iv_toptitle_back)
    ImageView mIvToptitleBack;
    @Bind(R.id.tv_toptitle_title)
    TextView mTvToptitleTitle;
    @Bind(R.id.iv_toptitle_more_img)
    ImageView mIvToptitleMoreImg;
    @Bind(R.id.erweima_iv_usericon)
    ImageView mErweimaIvUsericon;
    @Bind(R.id.erweima_tv_name)
    TextView mErweimaTvName;
    @Bind(R.id.erweima_tv_id)
    TextView mErweimaTvId;
    @Bind(R.id.erweima_iv)
    ImageView mErweimaIv;

    @Override
    protected int initResource() {
        return R.layout.activity_huoqu_erweima;
    }

    @Override
    protected void initComponent() {
        mTvToptitleTitle.setText("我的二维码");

    }

    @Override
    protected void initData() {
        String myQRcode = "今天是个好日子";
        if (TextUtils.isEmpty(myQRcode)) {
            MToast.showToast(mContext, "返回二维码信息错误");
            return;
        }
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mErweimaIv.setImageBitmap(CodeUtils.createImage(myQRcode, 400, 400, null));
      //  CommonUtlis.setDisplayImage(mErweimaIv, myQRcode, 0, R.mipmap.ic_launcher);

    }
    @Override
    public void requestSuccess(int requestTag, JSONObject response, int showLoad) throws JSONException {
        super.requestSuccess(requestTag, response, showLoad);
        /*switch (requestTag) {
            case RequestAction.TAG_GETMYQRCODE:
                String myQRcode = response.getString("二维码地址");
                if (TextUtils.isEmpty(myQRcode)) {
                    MToast.showToast(mContext, "返回二维码信息错误");
                    return;
                }
                //        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//                ivEwm.setImageBitmap(CodeUtils.createImage(myQRcode, 400, 400, null));

                CommonUtlis.setDisplayImage(mErweimaIv, myQRcode, 0, R.mipmap.ic_launcher);
                break;
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_toptitle_back, R.id.iv_toptitle_more_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toptitle_back:
                View view1 = this.getWindow().peekDecorView();
                if (view1 != null) {
                    InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                this.finish();
                break;
            case R.id.iv_toptitle_more_img:
                break;
        }
    }


}
