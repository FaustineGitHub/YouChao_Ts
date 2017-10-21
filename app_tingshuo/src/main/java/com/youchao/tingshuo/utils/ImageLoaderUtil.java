package com.youchao.tingshuo.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.youchao.tingshuo.R;

/**
 * Created by Tao Yimin on 2016/9/11.
 * 第三方图片加载框架ImageLoader工具类,记得下载框架后使用
 */
public class ImageLoaderUtil {

    /**
     * 获取一个默认的配置
     *
     * @return
     */
    public static DisplayImageOptions getDefaultOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // TODO 网络图片下载完成之前的预加载图片
                .showImageOnLoading(R.drawable.zhanwei)
                // TODO 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.zhanwei)
                // TODO 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.zhanwei)
                // 设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                // 设置是否保留Exif信息
                // .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                // .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片下载前的延迟
                // .delayBeforeLoading(100)
                // 设置图片在下载前是否重置，复位
                // .resetViewBeforeLoading(true)
                .build();
        return options;
    }

    /**
     * 获取一个圆形的配置
     *
     * @param strokeColor 圆形边框的颜色
     * @param strokeWidth 圆形边框的宽度
     * @return
     */
    public static DisplayImageOptions getCircleBitmapOption(Integer strokeColor, float strokeWidth) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.zhanwei)
                .showImageForEmptyUri(R.drawable.zhanwei)
                .showImageOnFail(R.drawable.zhanwei)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer(strokeColor, strokeWidth))
                .build();
        return options;
    }

    /**
     * 获取一个圆角的配置
     * 不推荐使用!它会创建新的ARGB_8888格式的Bitmap对象
     *
     * @param cornerRadiusPixels 圆角的弧度值
     * @return
     */
    public static DisplayImageOptions getRoundBitmapOption(int cornerRadiusPixels) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.zhanwei)
                .showImageForEmptyUri(R.drawable.zhanwei)
                .showImageOnFail(R.drawable.zhanwei)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels)).build();
        return options;
    }

    /**
     * 获取一个渐显图片的配置
     *
     * @param durationMillis 图片渐显的时间
     * @return
     */
    public static DisplayImageOptions getFadeInBitmapOption(int durationMillis) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.zhanwei)
                .showImageForEmptyUri(R.drawable.zhanwei)
                .showImageOnFail(R.drawable.zhanwei)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(durationMillis)).build();
        return options;
    }
}

