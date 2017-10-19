package com.youchao.tingshuo.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.ui.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by dell on 2017/9/11.
 * 引导页
 */

public class GuideActivity extends BaseActivity implements View.OnClickListener{
    private static final int[] mImageIds = new int[]{R.mipmap.yindao1, R.mipmap.yindao2};
    private ArrayList<ImageView> mImageViewList;
    private ViewPager vp_guide;
    private TextView tv_guide_letsgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置无标题
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initResource() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initComponent() {
        /*//修改字体为汉仪细中圆简字体
        Typeface face = Typeface.createFromAsset(this.getAssets(), "fonts/youchao_ziti.ttf");
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        tv_guide_letsgo = (TextView) findViewById(R.id.tv_guide_letsgo);
        tv_guide_letsgo.setTypeface(face);*/

    }

    @Override
    protected void initData() {
        mImageViewList = new ArrayList<>();
        //初始化引导页的两个页面
        for (int i = 0;i<mImageIds.length;i++){
            ImageView image = new ImageView(this);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setAdjustViewBounds(true);//保持原图的长宽比
            image.setBackgroundResource(mImageIds[i]);//设置引导页背景
            mImageViewList.add(image);
        }
        vp_guide.setAdapter(new GuideAdapter());
        vp_guide.setOnPageChangeListener(new GuidePageListener());
        tv_guide_letsgo.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_guide_letsgo:
                //TODO SharedPreferencesUtils.setBoolean(mContext, ConstantUtlis.SP_ISGUIDEPAGESHOWED,true);
                startActivity(new Intent(this,LoginActivity.class));
                break;
            default:
                break;
        }

    }
    //ViewPager适配器
    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    //ViewPager滑动事件监听
    class GuidePageListener implements ViewPager.OnPageChangeListener{
        // 滑动事件
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        //某个页面被选中
        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length - 1){//最后一个页面
                tv_guide_letsgo.setVisibility(View.VISIBLE);// 显示开始体验的按钮
            }else {
                tv_guide_letsgo.setVisibility(View.GONE);
            }
        }
        // 滑动状态发生变化
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
