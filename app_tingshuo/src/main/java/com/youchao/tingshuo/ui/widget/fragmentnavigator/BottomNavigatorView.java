package com.youchao.tingshuo.ui.widget.fragmentnavigator;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youchao.tingshuo.R;


/**
 * Created by aspsine on 16/3/31.
 */
public class BottomNavigatorView extends LinearLayoutCompat {

    OnBottomNavigatorViewItemClickListener mOnBottomNavigatorViewItemClickListener;

    public interface OnBottomNavigatorViewItemClickListener {
        void onBottomNavigatorViewItemClick(int position, View view);
    }

    public BottomNavigatorView(Context context) {
        this(context, null);
    }

    public BottomNavigatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);
        inflate(context, R.layout.layout_bottom_navigator, this);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            final int finalI = i;
            if (i != 1){
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnBottomNavigatorViewItemClickListener.onBottomNavigatorViewItemClick(finalI, v);
                    }
                });
            }
        }
    }
    //换颜色
    /*public void select(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == position) {
                selectChild(child, true);
            } else {
                selectChild(child, false);
            }
        }
    }*/
    //换图标
    public void select(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == position) {
                if (position ==0){
                    selectChild_1(child, true);
                }else if (position == 2){
                    selectChild_2(child, true);
                }
                //selectChild(child, true);
            } else {
                if (position ==0){
                    selectChild_1(child, false);
                }else if (position == 2){
                    selectChild_2(child, false);
                }
                //selectChild(child, false);
            }
        }
    }

    private void selectChild_1(View child, boolean select) {
        if (child instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) child;
            group.setSelected(select);
            for (int i = 0; i < group.getChildCount(); i++) {
                selectChild_1(group.getChildAt(i), select);
            }
        } else {
            child.setSelected(select);
            if (child instanceof ImageView) {
                ImageView iv = (ImageView) child;
                //Drawable drawable = iv.getDrawable().mutate();
                if (select) {
                    iv.setImageResource(R.drawable.quanzi_select);
                    //drawable.setColorFilter(getResources().getColor(R.color.colorTabSelected), PorterDuff.Mode.SRC_ATOP);
                } else {
                    iv.setImageResource(R.drawable.wode);
                    // drawable.setColorFilter(getResources().getColor(R.color.colorTabNormal), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
    }
    private void selectChild_2(View child, boolean select) {
        if (child instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) child;
            group.setSelected(select);
            for (int i = 0; i < group.getChildCount(); i++) {
                selectChild_2(group.getChildAt(i), select);
            }
        } else {
            child.setSelected(select);
            if (child instanceof ImageView) {
                ImageView iv = (ImageView) child;
                //Drawable drawable = iv.getDrawable().mutate();
                if (select) {
                    iv.setImageResource(R.drawable.wodeselect);
                    //drawable.setColorFilter(getResources().getColor(R.color.colorTabSelected), PorterDuff.Mode.SRC_ATOP);
                } else {
                    iv.setImageResource(R.drawable.quanzi);
                    // drawable.setColorFilter(getResources().getColor(R.color.colorTabNormal), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
    }

    private void selectChild(View child, boolean select) {
        if (child instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) child;
            group.setSelected(select);
            for (int i = 0; i < group.getChildCount(); i++) {
                selectChild(group.getChildAt(i), select);
            }
        } else {
            child.setSelected(select);
            if (child instanceof ImageView) {
                ImageView iv = (ImageView) child;
                Drawable drawable = iv.getDrawable().mutate();
                if (select) {
                    drawable.setColorFilter(getResources().getColor(R.color.colorTabSelected), PorterDuff.Mode.SRC_ATOP);
                } else {
                    drawable.setColorFilter(getResources().getColor(R.color.colorTabNormal), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
    }

    public void setOnBottomNavigatorViewItemClickListener(OnBottomNavigatorViewItemClickListener listener) {
        this.mOnBottomNavigatorViewItemClickListener = listener;
    }
}
