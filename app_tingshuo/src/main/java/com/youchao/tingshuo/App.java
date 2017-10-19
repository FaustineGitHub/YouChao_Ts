package com.youchao.tingshuo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.NoDoubleClickUtils;
import com.youchao.tingshuo.utils.SharedPreferencesUtils;
import com.youchao.tingshuo.utils.UrlUtlis;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.io.File;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;


/**
 * Created by JinzLin on 2016/8/9.
 */
public class App extends Application {

    private static Context context;//全局context
    private List<Activity> mList = new LinkedList<Activity>();
    private static App instance;

    // 用于存放倒计时时间
    public static Map<String, Long> timeMap;
    private static Socket mSocket;

    {
        try {
            mSocket = IO.socket(UrlUtlis.MESSAGE_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket() {
        return mSocket;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 日记调试打印
         * 测试 true，正式 false。
         */
        L.isDebug = true;

        context = getApplicationContext();
        SharedPreferencesUtils.init(this);
        AutoLayoutConifg.getInstance().useDeviceSize(); // 自动适配
        //ZXingLibrary.initDisplayOpinion(this);
        NoDoubleClickUtils.initLastClickTime();//初始化防止多次点击工具
        //initImageLoader(context);
        //CrashReport.initCrashReport(getApplicationContext(), "04e3a39997", false);//Bugly初始化（20170715），在测试阶段建议设置成true，发布时设置为false

        // 连接消息服务器
        /*LinkServer.init(this);
        SocketEvent.init(this);

        ChatEvent.init(this);*/
        //初始化ImageLoader配置
        initImageLoader(context);

    }

    public synchronized static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    /**
     * list集合中添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    /**
     * list集合中删除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        mList.remove(mList.size() - 1);
    }

    /**
     * 退出list集合中所有activity
     */
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 获取list集合中最后一个activity
     */
    public Activity getLastActivity() {
        return mList.get(mList.size() - 1);
    }

    /**
     * 缓存图片
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        //缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "kanjian/ImageLoader/Cache");
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.drawable.login_logo) //
                .showImageOnFail(R.drawable.login_logo) //
                .cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3) //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)  // 50 Mb sd卡(本地)缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 由原先的discCache -> diskCache
                .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        //全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

}
