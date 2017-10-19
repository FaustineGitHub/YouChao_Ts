package com.youchao.tingshuo.ui.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.MessageCircle;
import com.youchao.tingshuo.utils.L;

import java.util.List;


/**
 * Created by dell on 2017/9/21.
 * 消息列表多布局的Adapter
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>{
    private List<MessageCircle> mList;
    private MessageCircle mMessageCircle;
    private Context mContext;
    //item的回调接口
    private OnItemClickListener onItemClickListener;

    public MessageListAdapter(List<MessageCircle> list, Context context) {
        mList = list;
        mContext = context;
    }
    public void addAllData(List<MessageCircle> dataList) {
        this.mList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        MessageViewHolder viewHolder = null;
        switch (viewType) {
            //用户评论操作的分为带图片、带视频或者纯文字的
            case 0:
                //实例化点赞的ViewHolder
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_message_list_dianzan, parent, false);
                viewHolder = new MessageListAdapter.DianZanViewHolder(itemView);
                break;
            case 1:
                //实例化评论的ViewHolder
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_message_list_pinglun, parent, false);
                viewHolder = new MessageListAdapter.PingLunViewHolder(itemView);
                break;
            case 2:
                //实例化转发的ViewHolder
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_message_list_zhuanfa, parent, false);
                viewHolder = new MessageListAdapter.ZhuanFaViewHolder(itemView);
                break;
            case 3:
                //实例化删除评论的ViewHolder
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_message_list_deletepinglun, parent, false);
                viewHolder = new MessageListAdapter.DeletePingLunViewHolder(itemView);

                break;
            default:
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_message_list_dianzan, parent, false);
                viewHolder = new MessageListAdapter.DianZanViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        mMessageCircle = mList.get(position);
        String video = mMessageCircle.getRightVideo();
        String image = mMessageCircle.getRightImage();
        String text = mMessageCircle.getRightText();



        int type = -1;
        type = getItemViewType(position);
        //根据不同的数据类型,采用相应的填充数据方法
        switch (type) {
            case 0://点赞数据填充
                fillDataDianzan(holder, mMessageCircle, position);
                break;
            case 1://评论填充
                fillDataPingLun(holder, mMessageCircle,position);
                break;
            case 2://转发填充
                fillDataZhuanFa(holder, mMessageCircle,position);
                break;
            case 3://删除评论填充
                fillDataDelete(holder, mMessageCircle,position);
                break;
            default://点赞数据填充
                fillDataDianzan(holder, mMessageCircle,position);
                break;
        }
        //通过接口回调设置点击事件
        initListener(holder);
        L.e("TAG","视频地址="+video+" 图片地址="+image+" 文字="+text);
        //判断右侧展示文件类型
        setRightTag(holder,video,image,text);


    }
    //设置右侧类型
    private void setRightTag(MessageViewHolder holder,String video, String image, String text) {
        if (!TextUtils.isEmpty(video)){//当视频文件不为空时，右侧展示视频图像
            holder.iv_customer_video.setVisibility(View.VISIBLE);
            holder.iv_customer_tupian.setVisibility(View.GONE);
            holder.iv_customer_text.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(image)&&TextUtils.isEmpty(video)) {//当视频文件为空且图片文件不为空时，右侧展示图片图像
            holder.iv_customer_video.setVisibility(View.GONE);
            holder.iv_customer_tupian.setVisibility(View.VISIBLE);
            holder.iv_customer_text.setVisibility(View.GONE);
        }else if (!TextUtils.isEmpty(text)&&TextUtils.isEmpty(image)&&TextUtils.isEmpty(video)){//当视频文件为空且图片文件为空时，右侧展示文字图像
            holder.iv_customer_video.setVisibility(View.GONE);
            holder.iv_customer_tupian.setVisibility(View.GONE);
            holder.iv_customer_text.setVisibility(View.VISIBLE);
        }

    }

    private void initListener(final MessageViewHolder holder) {
        //设置item的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    // 获取最新item的位置
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(v, pos);
                }
            }
        });
        //设置item的长按事件
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    // 获取最新item的位置
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(v, pos);
                }
                // 消费事件
                return true;
            }
        });
    }

    private void fillDataDelete(MessageViewHolder holder, MessageCircle messageCircle, int position) {
        DeletePingLunViewHolder delete = (DeletePingLunViewHolder) holder;
    }
    private void fillDataZhuanFa(MessageViewHolder holder, MessageCircle messageCircle, int position) {
        ZhuanFaViewHolder zhuanfa = (ZhuanFaViewHolder) holder;
    }

    private void fillDataPingLun(MessageViewHolder holder, MessageCircle messageCircle, int position) {
        PingLunViewHolder pinglun = (PingLunViewHolder) holder;

    }

    private void fillDataDianzan(MessageViewHolder holder, MessageCircle messageCircle, int position) {
        DianZanViewHolder dianzan = (DianZanViewHolder) holder;
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //避免空指针,解析的json对象为消息对象时,type为空
        if (mList.get(position).getType() == 0) {//这里是判断点赞
            return 0;
        }else if (mList.get(position).getType() == 1){//这里是判断评论
            return 1;
        }else if (mList.get(position).getType() == 2){//这里是判断转发
            return 2;
        }else if (mList.get(position).getType() == 3){//这里是判断删除评论
            return 3;
        }
        //type的值代表不同的消息类型
        return mList.get(position).getType();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout iv_customer_video;
        ImageView iv_customer_touxiang,iv_customer_tupian,item_iv;
        TextView tv_customer_name,tv_customer_time,iv_customer_text;
        public MessageViewHolder(View itemView) {
            super(itemView);
            iv_customer_touxiang = itemView.findViewById(R.id.iv_customer_touxiang);
            tv_customer_name = itemView.findViewById(R.id.tv_customer_name);
            tv_customer_time = itemView.findViewById(R.id.tv_customer_time);

            iv_customer_tupian = itemView.findViewById(R.id.iv_customer_tupian);
            iv_customer_text = itemView.findViewById(R.id.iv_customer_text);
            iv_customer_video = itemView.findViewById(R.id.iv_customer_video);
            item_iv = itemView.findViewById(R.id.item_iv);
        }
        //设置数据
        public void setData(List<MessageCircle> mList) {
        }
    }

    public class DianZanViewHolder extends MessageViewHolder {
        private ImageView iv_dianzan;
        public DianZanViewHolder(View itemView) {
            super(itemView);
            iv_dianzan = itemView.findViewById(R.id.iv_customer_dianzan);
        }
    }

    public class DeletePingLunViewHolder extends MessageViewHolder {
        private TextView tv_delete;
        public DeletePingLunViewHolder(View itemView) {
            super(itemView);
            tv_delete = itemView.findViewById(R.id.iv_customer_delete);
        }
    }

    public class ZhuanFaViewHolder extends MessageViewHolder {
        private LinearLayout tv_zhuanfa;
        public ZhuanFaViewHolder(View itemView) {
            super(itemView);
            tv_zhuanfa = itemView.findViewById(R.id.ll_customer_zhuanfa);
        }
    }

    public class PingLunViewHolder extends MessageViewHolder {
        private TextView tv_pinglun;
        public PingLunViewHolder(View itemView) {
            super(itemView);
            tv_pinglun = itemView.findViewById(R.id.iv_customer_pinlun);
        }
    }
    /**
     * 整个item的点击事件和长按事件回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }
    /**
     * 设置item点击事件的接口
     *
     * @param
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
