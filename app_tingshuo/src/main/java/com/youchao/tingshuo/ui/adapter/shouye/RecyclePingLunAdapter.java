package com.youchao.tingshuo.ui.adapter.shouye;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.CommonPingLun;

import java.util.List;


/**
 * Created by dell on 2017/9/28.
 */

public class RecyclePingLunAdapter extends RecyclerView.Adapter<RecyclePingLunAdapter.BaseViewHolder>{
    private List<CommonPingLun> mList;
    private CommonPingLun commonNews;
    private Context mContext;
    //item中评论按钮的回调接口
    private OnHuiFuClickListener onHuiFuClickListener;
    //item中点赞按钮的回调接口
    private OnDianZanClickListener onDianZanClickListener;

    public RecyclePingLunAdapter(List<CommonPingLun> list, Context context) {
        mList = list;
        mContext = context;
    }
    public void addAllData(List<CommonPingLun> dataList) {
        this.mList.addAll(dataList);
        notifyDataSetChanged();
    }


    @Override
    public RecyclePingLunAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pinglun_2, parent, false);
        return new BaseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclePingLunAdapter.BaseViewHolder holder, int position) {

        if (TextUtils.isEmpty(mList.get(position).getDuixiang())){//为空时
            holder.pinglun_tv_content_normal.setVisibility(View.VISIBLE);
            //holder.pinglun_tv_content.setVisibility(View.GONE);
        }else {
            holder.pinglun_tv_content_normal.setVisibility(View.VISIBLE);
            //holder.pinglun_tv_content.setVisibility(View.VISIBLE);
            mode1(holder);
        }
        //设置item的点赞事件
        holder.pinglun_ll_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDianZanClickListener != null) {
                    // 获取最新item的位置

                    int pos = holder.getLayoutPosition();
                    onDianZanClickListener.onDianZanClick(view, pos);
                }
            }
        });
        holder.pinglun_ll_huifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onHuiFuClickListener != null) {
                    // 获取最新item的位置
                    int pos = holder.getLayoutPosition();
                    onHuiFuClickListener.onHuiFuClick(view, pos);
                }
            }
        });
    }
    /**
     * 使用SpannableString设置样式——字体颜色
     */
    private void mode1(RecyclePingLunAdapter.BaseViewHolder holder) {
        String username = "大乌龟";
        SpannableString spannableString = new SpannableString("回复"+username+":大乌龟dfsgsdgsdgdgergwggsergi社会公示oh个i送额好贵i哦是如果i哦记得更加生动苹果键盘是佛的几个破的设计风格 个破欸如果拍摄日恶搞色额是如果和人格人格");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#12cec8"));
        spannableString.setSpan(colorSpan, 2, username.length()+3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.pinglun_tv_content_normal.setText(spannableString);
    }

    @Override
    public int getItemCount() {
        return  mList.size();
    }
    public class BaseViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout pinglun_ll_huifu,pinglun_ll_dianzan;
        ImageView fujing_iv_usericon;
        TextView pinglun_tv_name,pinglun_tv_time,pinglun_tv_content_normal,pinglun_tv_huifu,pinglun_tv_dianzan;
        public BaseViewHolder(View itemView) {
            super(itemView);
            pinglun_ll_huifu = itemView.findViewById(R.id.pinglun_ll_huifu);
            pinglun_ll_dianzan = itemView.findViewById(R.id.pinglun_ll_dianzan);
            fujing_iv_usericon = itemView.findViewById(R.id.fujing_iv_usericon);
            pinglun_tv_name = itemView.findViewById(R.id.pinglun_tv_name);
            pinglun_tv_time = itemView.findViewById(R.id.pinglun_tv_time);
            pinglun_tv_huifu = itemView.findViewById(R.id.pinglun_tv_huifu);
            pinglun_tv_dianzan = itemView.findViewById(R.id.pinglun_tv_dianzan);
            pinglun_tv_content_normal = itemView.findViewById(R.id.pinglun_tv_content_normal);
        }
        //设置数据
        public void setData(CommonPingLun commonNews) {
        }
    }
    /**
     * 评论按钮的点击事件回调接口
     */
    public interface OnHuiFuClickListener {
        void onHuiFuClick(View v, int position);
    }
    /**
     * 点赞按钮的点击事件回调接口
     */
    public interface OnDianZanClickListener {
        void onDianZanClick(View v, int position);
    }
    /**
     * 设置评论按钮点击事件的接口
     */
    public void setOnHuiFuClickListener(OnHuiFuClickListener onHuiFuClickListener) {
        this.onHuiFuClickListener = onHuiFuClickListener;
    }
    /**
     * 设置点赞按钮点击事件的接口
     */
    public void setOnDianZanClickListener(OnDianZanClickListener onDianZanClickListener) {
        this.onDianZanClickListener = onDianZanClickListener;
    }

}
