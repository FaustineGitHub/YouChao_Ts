package com.youchao.tingshuo.ui.widget.dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.utils.ViewUtil;

/**
 * Created by dell on 2017/10/14.
 * 转发页面
 */

public class ShareDialog extends Dialog implements View.OnClickListener{
    private static final String TAG = "ShareDialog";
    private Context mContext;
    private RelativeLayout sharedialog_main;
    private Button btn_weixin,btn_weixin_circle,btn_sina,btn_qq,btn_qq_kongjian,btn_cancle;
    private ShareMyDialogListener listener;
    private static ShareDialog mShareDialog;
    private int wHeight, width; // 屏幕高度宽度
    public interface ShareMyDialogListener{
        public void onClick(View view);
    }
    public ShareDialog(Context context) {
        super(context);
        mContext = context;

        this.getContext().setTheme(android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        setContentView(R.layout.dialog_share);
        // 必须放在加载布局后
        setparams();
    }

    public ShareDialog(Context context, int themeResId,ShareMyDialogListener listener) {
        super(context, themeResId);
        wHeight = getScreenHeight();
        width = getScreenWidth();
        this.mContext = context;
        this.listener = listener;
        this.getContext().setTheme(android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        setContentView(R.layout.dialog_share);


        sharedialog_main = findViewById(R.id.sharedialog_main);
        btn_weixin = findViewById(R.id.btn_weixin);
        btn_weixin_circle = findViewById(R.id.btn_weixin_circle);
        btn_sina = findViewById(R.id.btn_sina);
        btn_qq = findViewById(R.id.btn_qq);
        btn_qq_kongjian = findViewById(R.id.btn_qq_kongjian);
        btn_cancle = findViewById(R.id.btn_cancle);

        btn_weixin.setOnClickListener(this);
        btn_weixin_circle.setOnClickListener(this);
        btn_sina.setOnClickListener(this);
        btn_qq.setOnClickListener(this);
        btn_qq_kongjian.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);


        setparams();
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_share);

    }*/

    @Override
    public void onClick(View view) {
        listener.onClick(view);

    }
    private void setparams() {
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);

        try {
            window.getDecorView().getBackground().setAlpha(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 获得屏幕高度
    public int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    // 获得屏幕宽度
    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
