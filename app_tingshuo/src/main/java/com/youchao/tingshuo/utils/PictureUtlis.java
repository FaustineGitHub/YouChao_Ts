package com.youchao.tingshuo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.youchao.tingshuo.view.CircleTransform;

/**
 * Created by dell on 2017/9/19.
 */

public class PictureUtlis {
    /**
     * 指定大小加载图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param width      宽
     * @param height     高
     * @param mImageView 控件
     */
    public static void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        Picasso.with(mContext).load(path).resize(width, height).centerCrop().into(mImageView);
    }


    /**
     * 加载有默认图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param resId      默认图片资源
     * @param mImageView 控件
     */
    public static void loadImageViewHolder(Context mContext, String path, int resId, ImageView mImageView) {
        try {
            Picasso.with(mContext).load(path).fit().placeholder(resId).into(mImageView);
        } catch (Exception e) {
            Log.e("loadCircular", "loadCircularImageViewHolder: " + e);
        }
    }

    /**
     * 加载无默认图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param mImageView 控件
     */
    public static void loadImageViewHolder(Context mContext, String path, ImageView mImageView) {
        try {
            Picasso.with(mContext).load(path).fit().into(mImageView);
        } catch (Exception e) {
            Log.e("loadCircular", "loadCircularImageViewHolder: " + e);
        }
    }

    /**
     * 加载无默认图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param mImageView 控件
     * @param callback   加载完成回调
     */
    public static void loadImageViewHolder(Context mContext, String path, ImageView mImageView, Callback callback) {
        try {
            Picasso.with(mContext).load(path).fit().into(mImageView, callback);
        } catch (Exception e) {
            Log.e("loadCircular", "loadCircularImageViewHolder: " + e);
        }
    }

    /**
     * 加载有默认图片(圆形)
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param resId      默认图片资源
     * @param mImageView 控件
     */
    public static void loadCircularImageViewHolder(Context mContext, String path, int resId, ImageView mImageView) {
        try {
            Picasso.with(mContext).load(path).centerCrop().transform(new CircleTransform()).fit().placeholder(resId).into(mImageView);
        } catch (Exception e) {
            Log.e("loadCircular", "loadCircularImageViewHolder: " + e);
        }
    }

    /**
     * 加载本地资源文件中的图片（圆形）
     *
     * @param mContext   上下文
     * @param resId      图片的ID
     * @param mImageView 控件
     */
    public static void loadLoaclImageViewHolder(Context mContext, int resId, ImageView mImageView) {
        try {
            Picasso.with(mContext).load(resId).centerCrop().transform(new CircleTransform()).fit().into(mImageView);
        } catch (Exception e) {
            Log.e("loadCircular", "loadCircularImageViewHolder: " + e);
        }
    }

    /**
     * 裁剪图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param mImageView 控件
     */
    public static void loadImageViewCrop(Context mContext, String path, ImageView mImageView) {
        Picasso.with(mContext).load(path).transform(new CropImageView()).into(mImageView);
    }

    /**
     * 自定义图片裁剪
     */
    public static class CropImageView implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap newBitmap = Bitmap.createBitmap(source, x, y, size, size);

            if (newBitmap != null) {
                //内存回收
                source.recycle();
            }
            return newBitmap;
        }

        @Override
        public String key() {

            return "lgl";
        }
    }
}
