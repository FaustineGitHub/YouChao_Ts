package com.youchao.tingshuo.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by dell on 2017/9/14.
 */

public class AnimUtil{
    public static void closeMenu(final RelativeLayout rl, int startOffse) {
        /*for (int i = 0; i < rl.getChildCount(); i++) {
            rl.getChildAt(i).setEnabled(false);
        }*/
        RotateAnimation animation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 1);

        animation.setDuration(100);
        animation.setFillAfter(true);
        animation.setStartOffset(startOffse);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl.post(new Runnable() {
                    @Override
                    public void run() {
                        rl.clearAnimation();
                        rl.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rl.startAnimation(animation);
    }

    /**
     * 展开菜单
     * @param rl 装菜单的最外层布局
     * @param startOffse /动画执行之前的等待时间
     */
    public static void showMenu(final RelativeLayout rl, int startOffse) {
        /*for (int i = 0; i < rl.getChildCount(); i++) {
            rl.getChildAt(i).setEnabled(true);
        }*/
        rl.setVisibility(View.VISIBLE);
        RotateAnimation animation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 1);

        animation.setDuration(100);
        animation.setFillAfter(true);
        animation.setStartOffset(startOffse);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rl.startAnimation(animation);
    }
}
