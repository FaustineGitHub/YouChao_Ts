package com.youchao.tingshuo.ui.adapter.shouye;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.CommonNews;
import com.youchao.tingshuo.ui.activity.shouye.ImagePagerActivity;
import com.youchao.tingshuo.ui.adapter.MyBaseAdapter;
import com.youchao.tingshuo.utils.ImageLoaderUtil;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.view.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by dell on 2017/9/20.
 * 圈子页面的adapter
 */

public class CircleMessage1Adapter extends MyBaseAdapter {
    private List<CommonNews> mList;
    private CommonNews commonNews;
    private Context mContext;
    //item的回调接口
    private OnTotalItemClickListener onTotalItemClickListener;
    //item中转发按钮的回调接口
    private OnZhuanFaClickListener onZhuanFaClickListener;
    //item中评论按钮的回调接口
    private OnPingLunClickListener onPingLunClickListener;
    //item中点赞按钮的回调接口
    private OnDianZanClickListener onDianZanClickListener;
    //item中头像按钮的回调接口
    private OnUserIconClickListener onUserIconClickListener;
    //标志
    private String tag;


    public CircleMessage1Adapter(Context mContext, List<CommonNews> list, String tag) {
        this.mContext = mContext;
        this.mList = list;
        this.tag = tag;
    }
    public void addAllData(List<CommonNews> dataList) {
        this.mList.addAll(dataList);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder_my(ViewGroup parent, int viewType) {
        View itemView = null;
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                //实例化一个视频新闻的ViewHolder
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_circle_message_video, parent, false);
                viewHolder = new VideoViewHolder(itemView);
                break;
            case 10:
                //实例化一个纯文本的ViewHolder
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_circle_message_nopicture, parent, false);
                viewHolder = new NoImageViewHolder(itemView);
                break;
            case 1:
                //实例化一张图片的ViewHolder
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_circle_message_onepicture, parent, false);
                viewHolder = new OneImageViewHolder(itemView);
                break;
            case 4:
                //实例化四张图片的ViewHolder
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_circle_message_fourpicture, parent, false);
                viewHolder = new FourImageViewHolder(itemView);

                break;
            default:
                //其他情况下,实例化多张图片
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_circle_message_manypicture, parent, false);
                viewHolder = new ManyImageViewHolder(itemView);

                break;
        }


        return viewHolder;
    }



    @Override
    public void onBindViewHolder_my(RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder viewHolder = (BaseViewHolder) holder;
        viewHolder.tv_guanzhu.setVisibility(View.GONE);
        /*L.e("TAG",tag);
        if ("HotFragment".equals(tag)){
            holder.tv_guanzhu.setVisibility(View.VISIBLE);
        }else {

        }*/
        commonNews = mList.get(position);
        int type = -1;
        type = getItemViewType_my(position);
        //根据不同的数据类型,采用相应的填充数据方法    这四套布局  都有点赞吧？是的

        switch (type) {
            case 0://视频数据填充
                fillDataVideo(viewHolder, commonNews, position);
                break;
            case 10://纯文本填充
                fillDataNoImage(viewHolder, commonNews,position);
                break;
            case 1://一张图片填充
                fillDataSingleImage(viewHolder, commonNews,position);
                break;
            case 4://四张图片填充
                fillDataFourImage(viewHolder, commonNews,position);
                break;
            default://多张图片填充
                fillDataManyImage(viewHolder, commonNews,position);
                break;
        }

        //ToDo 点赞
        if(mList.get(position).isZan()){
            //已经点了
            viewHolder.ivDianzan.setImageResource(R.drawable.dianzan_select);
            viewHolder.tv_dianzan.setText((Integer.parseInt(mList.get(position).getDianzan())+1)+"");//点赞数
            viewHolder.tv_dianzan.setTextColor(Color.parseColor("#00D1CA"));
        }else{
            viewHolder.ivDianzan.setImageResource(R.drawable.dianzan);
            viewHolder.tv_dianzan.setText(mList.get(position).getDianzan());
            viewHolder.tv_dianzan.setTextColor(Color.parseColor("#ff999999"));
        }
        //TODO 关注
        if(mList.get(position).isGuanZhu()){
            //已经点了---灰色
            viewHolder.tv_guanzhu.setBackgroundResource(R.drawable.text_bg_stroke_gray);
            viewHolder.tv_guanzhu.setTextColor(Color.parseColor("#ff999999"));
            viewHolder.tv_guanzhu.setText("已关注");
        }else{
            viewHolder.tv_guanzhu.setBackgroundResource(R.drawable.text_bg_stroke_blue);
            viewHolder.tv_guanzhu.setTextColor(Color.parseColor("#00D1CA"));
            viewHolder.tv_guanzhu.setText("+关注");
        }

        viewHolder.setData(mList.get(position).getImgUrls());
        //通过接口回调设置点击事件
        initListener(viewHolder);
    }

    private void initListener(final BaseViewHolder holder) {
        //设置item的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTotalItemClickListener != null) {
                    // 获取最新item的位置
                    int pos = holder.getLayoutPosition();
                    onTotalItemClickListener.onItemClick(v, pos);
                }
            }
        });
        //设置item的长按事件
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onTotalItemClickListener != null) {
                    // 获取最新item的位置
                    int pos = holder.getLayoutPosition();
                    onTotalItemClickListener.onItemLongClick(v, pos);
                }
                // 消费事件
                return true;
            }
        });
        //设置item的点赞事件
        holder.ll_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDianZanClickListener != null) {
                    // 获取最新item的位置

                   int pos = holder.getLayoutPosition();
                    onDianZanClickListener.onDianZanClick(view, pos);
                }
            }
        });


        holder.ll_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPingLunClickListener != null) {
                    // 获取最新item的位置
                    int pos = holder.getLayoutPosition();
                    onPingLunClickListener.onPingLunClick(view, pos);
                }
            }
        });
        holder.ll_zhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onZhuanFaClickListener != null) {
                    // 获取最新item的位置
                    int pos = holder.getLayoutPosition();
                    onZhuanFaClickListener.onZhuanFaClick(view, pos);
                }
            }
        });
        /**
         * 头像的点击事件
         */
        holder.iv_circle_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onUserIconClickListener != null) {
                    // 获取最新item的位置
                    int pos = holder.getLayoutPosition();
                    onUserIconClickListener.onUserIconClick(view, pos);
                }
            }
        });
    }

    /**
     * 四张图片界面的填充
     * @param holder
     * @param commonNews
     * @param
     */
    private void fillDataFourImage(BaseViewHolder holder, CommonNews commonNews, final int position1) {
        FourImageViewHolder viewHolder = (FourImageViewHolder) holder;
        holder.setData(mList.get(position1).getImgUrls());
        viewHolder.gird_four_circle_img.setAdapter(new ImageGridAdapter(mContext, commonNews.getImgUrls()));
        // 点击回帖九宫格。查看大图
        viewHolder.gird_four_circle_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position2, long l) {
                imageBrower(position2, mList.get(position1).getImgUrls());
            }
        });
    }

    /**
     * 多张图片界面的填充(除四张外)
     *
     * @param holder
     * @param commonNews
     */
    private void fillDataManyImage(BaseViewHolder holder, CommonNews commonNews,final int position1) {
        ManyImageViewHolder viewHolder = (ManyImageViewHolder) holder;
        holder.setData(mList.get(position1).getImgUrls());
        viewHolder.gird_circle_img.setAdapter(new ImageGridAdapter(mContext, commonNews.getImgUrls()));
        // 点击回帖九宫格。查看大图
        viewHolder.gird_circle_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position2, long l) {
                imageBrower(position2, mList.get(position1).getImgUrls());
            }
        });
    }
    /**
     * 单张图片界面的填充
     *
     * @param holder
     * @param commonNews
     */
    private void fillDataSingleImage(BaseViewHolder holder, CommonNews commonNews,final int position1) {
        OneImageViewHolder viewHolder = (OneImageViewHolder) holder;
        List imgs = commonNews.getImgUrls();
        ImageLoader.getInstance().displayImage( mList.get(position1).getImgUrls().get(0),viewHolder.iv_circle_img, ImageLoaderUtil.getDefaultOption());
        //PictureUtlis.loadImageViewHolder(mContext, mList.get(position1).getImgUrls().get(0), R.drawable.default_image, viewHolder.iv_circle_img);
        // 点击回帖九宫格。查看大图
        viewHolder.iv_circle_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageBrower(0, mList.get(position1).getImgUrls());
            }
        });
    }
    /**
     * 纯文本界面的填充
     *
     * @param holder
     * @param commonNews
     */
    private void fillDataNoImage(BaseViewHolder holder, CommonNews commonNews,int position) {
        NoImageViewHolder viewHolder = (NoImageViewHolder) holder;
    }
    /**
     * 视频新闻界面的填充
     * @param holder
     * @param commonNews
     */
    private void fillDataVideo(final BaseViewHolder holder, CommonNews commonNews, int position) {
        final VideoViewHolder viewHolder = (VideoViewHolder) holder;
        holder.setData(mList.get(position).getImgUrls());
        //PictureUtlis.loadImageViewHolder(mContext, commonNews.getUrl(), R.drawable.default_image, viewHolder.iv);
        L.e("TAG","视频地址="+commonNews.getUrl());
        if (viewHolder.player_list_video != null) {
            viewHolder.player_list_video.release();
        }
        boolean setUp = viewHolder.player_list_video.setUp(commonNews.getUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
        if (setUp) {
            Glide.with(mContext).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(viewHolder.player_list_video.thumbImageView);
        }
        //ImageLoader.getInstance().displayImage(commonNews.getUrl(),viewHolder.iv,ImageLoaderUtil.getDefaultOption());
        /* viewHolder.iv_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onVideoViewClickListener != null) {
                    // 获取最新item的位置
                    int pos = viewHolder.getLayoutPosition();
                    onVideoViewClickListener.onVideoViewClick(view, pos);
                }

            }
        });*/
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    @Override
    public int getItemViewType_my(int position) {
        //避免空指针,解析的json对象为新闻对象时,图片数组为空
        if (mList.get(position).getImgUrls() == null || mList.get(position).getImgUrls().size() == 0) {//这里是判断有没有图片
            //0代表视频新闻或者纯文本新闻
            if (mList.get(position).getUrl() == null||mList.get(position).getUrl().equals("")){//这里才是判断是否有视频地址
                //代表纯文本新闻
                return 10;
            }else {
                return 0;
            }

        }else if (mList.get(position).getImgUrls().size() == 1){
            //1代表一张图片
            return 1;
        }
        //图片数组的长度代表布局类型
        return mList.get(position).getImgUrls().size();
    }
    @Override
    public List getData() {
        return mList;
    }




    /**
     * 四张图片显示的viewholder
     */
    class FourImageViewHolder extends BaseViewHolder{
        public NoScrollGridView gird_four_circle_img;
        public FourImageViewHolder(View itemView) {
            super(itemView);
            gird_four_circle_img = itemView.findViewById(R.id.gird_four_circle_img);

        }

    }
    /**
     * 多张图片显示的viewholder
     */
    class ManyImageViewHolder extends BaseViewHolder{
        public NoScrollGridView gird_circle_img;
        public ManyImageViewHolder(View itemView) {
            super(itemView);
            gird_circle_img = itemView.findViewById(R.id.gird_circle_img);
        }
    }
    /**
     * 视频新闻显示的ViewHolder
     */
    class VideoViewHolder extends BaseViewHolder{
      /*  private ImageView iv;
        private ViewGroup iv_group;*/
      private JCVideoPlayerStandard player_list_video;

        public VideoViewHolder(View itemView) {
            super(itemView);
            player_list_video = itemView.findViewById(R.id.player_list_video);
           /* iv= itemView.findViewById(R.id.item_iv);
            iv_group=  itemView.findViewById(R.id.iv_group);*/
        }
    }
    /**
     * 一张图片显示的ViewHolder
     */
    class OneImageViewHolder extends BaseViewHolder{
        private ImageView iv_circle_img;
        public OneImageViewHolder(View itemView) {
            super(itemView);
            iv_circle_img = itemView.findViewById(R.id.iv_circle_img);
        }//赞 子啊
    }
    /**
     * 纯文本显示的ViewHolder
     */
    class NoImageViewHolder extends BaseViewHolder{
        public NoImageViewHolder(View itemView) {
            super(itemView);
        }
    }
    /**
     * 所有ViewHolder的基类
     */
    class BaseViewHolder extends RecyclerView.ViewHolder{
        LinearLayout ll_zhuanfa,ll_pinglun,ll_dianzan;//点击事件
        ImageView iv_circle_icon,ivDianzan;
        TextView tv_circle_name,tv_circle_date,tv_circle_info,tv_pinglun,tv_dianzan,tv_guanzhu;

        public BaseViewHolder(View itemView) {
            super(itemView);
            ll_zhuanfa = itemView.findViewById(R.id.ll_zhuanfa);
            ll_pinglun = itemView.findViewById(R.id.ll_pinglun);
            ll_dianzan = itemView.findViewById(R.id.ll_dianzan);
            ivDianzan = itemView.findViewById(R.id.iv_dianzan);
            iv_circle_icon = itemView.findViewById(R.id.iv_circle_icon);
            tv_circle_name = itemView.findViewById(R.id.tv_circle_name);
            tv_circle_date = itemView.findViewById(R.id.tv_circle_date);
            tv_circle_info = itemView.findViewById(R.id.tv_circle_info);
            tv_pinglun = itemView.findViewById(R.id.tv_pinglun);

            tv_dianzan = itemView.findViewById(R.id.tv_dianzan);
            tv_guanzhu = itemView.findViewById(R.id.tv_guanzhu);


        }
        //设置数据
        public void setData(ArrayList<String> imgUrls) {
        }
    }
    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    protected void imageBrower(int position, ArrayList<String> urls2) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }
    //以下是对外公开的方法

    /**
     * 整个item的点击事件和长按事件回调接口
     */
    public interface OnTotalItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }

    /**
     * 转发按钮的点击事件回调接口
     */
    public interface OnZhuanFaClickListener {
        void onZhuanFaClick(View v, int position);
    }
    /**
     * 评论按钮的点击事件回调接口
     */
    public interface OnPingLunClickListener {
        void onPingLunClick(View v, int position);
    }
    /**
     * 点赞按钮的点击事件回调接口
     */
    public interface OnDianZanClickListener {
        void onDianZanClick(View v, int position);
    }


    /**
     * 头像按钮的点击事件回调接口
     */
    public interface OnUserIconClickListener{
        void onUserIconClick(View v, int position);
    }
    public void setOnUserIconClickListener(OnUserIconClickListener onUserIconClickListener){
        this.onUserIconClickListener = onUserIconClickListener;
    }

    /**
     * 设置item点击事件的接口
     *
     * @param
     */
    public void setOnTotalItemClickListener(OnTotalItemClickListener onTotalItemClickListener) {
        this.onTotalItemClickListener = onTotalItemClickListener;
    }

    /**
     * 设置转发按钮点击事件的接口
     */
    public void setOnZhuanFaClickListener(OnZhuanFaClickListener onZhuanFaClickListener) {
        this.onZhuanFaClickListener = onZhuanFaClickListener;
    }
    /**
     * 设置评论按钮点击事件的接口
     */
    public void setOnPingLunClickListener(OnPingLunClickListener onPingLunClickListener) {
        this.onPingLunClickListener = onPingLunClickListener;
    }
    /**
     * 设置点赞按钮点击事件的接口
     */
    public void setOnDianZanClickListener(OnDianZanClickListener onDianZanClickListener) {
        this.onDianZanClickListener = onDianZanClickListener;
    }



}
