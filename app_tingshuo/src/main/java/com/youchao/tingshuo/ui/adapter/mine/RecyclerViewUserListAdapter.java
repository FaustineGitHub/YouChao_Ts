package com.youchao.tingshuo.ui.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.bean.CommonUserList;
import com.youchao.tingshuo.utils.ImageLoaderUtil;
import com.youchao.tingshuo.utils.ViewUtil;

import java.util.List;


/**
 * Created by dell on 2017/10/10.
 * 交易记录页面的adapter
 */

public class RecyclerViewUserListAdapter extends RecyclerView.Adapter<RecyclerViewUserListAdapter.ViewHolder>{
    private List<CommonUserList> mList;
    private CommonUserList commonNews;
    private Context mContext;



    public RecyclerViewUserListAdapter(List<CommonUserList> list, Context context) {
        mList = list;
        mContext = context;
    }
    public void addAllData(List<CommonUserList> dataList) {
        this.mList.addAll(dataList);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_userlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setData(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return  mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_fans_type;
        ImageView iv_fans_list,iv_fans_type;
        TextView tv_fans_name,tv_fans_jieshao,tv_fans_type;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_fans_type = itemView.findViewById(R.id.rl_fans_type);
            iv_fans_type = itemView.findViewById(R.id.iv_fans_type);
            iv_fans_list = itemView.findViewById(R.id.iv_fans_list);
            tv_fans_name = itemView.findViewById(R.id.tv_fans_name);
            tv_fans_jieshao = itemView.findViewById(R.id.tv_fans_jieshao);
            tv_fans_type = itemView.findViewById(R.id.tv_fans_type);
        }
        //设置数据
        public void setData(CommonUserList commonNews) {
            if ("已关注".equals(commonNews.getType())){
                tv_fans_type.setText("已关注");
                tv_fans_type.setTextColor(Color.parseColor("#ff999999"));
                rl_fans_type.setBackgroundResource(R.drawable.text_bg_stroke_gray);
            }else if ("互相关注".equals(commonNews.getType())){
                iv_fans_type.setVisibility(View.VISIBLE);
                tv_fans_type.setText("关注");
                tv_fans_type.setTextColor(Color.parseColor("#ff999999"));
                rl_fans_type.setBackgroundResource(R.drawable.text_bg_stroke_gray);
            }else if ("未关注".equals(commonNews.getType())){
                tv_fans_type.setText("+关注");
                tv_fans_type.setTextColor(Color.parseColor("#ff00D1CA"));
                rl_fans_type.setBackgroundResource(R.drawable.text_bg_stroke_blue);
            }else {//我的聊天页面
                tv_fans_type.setText(commonNews.getType());
                tv_fans_type.setTextColor(Color.parseColor("#ffCDCDCD"));
                tv_fans_type.setTextSize(6);
                tv_fans_name.setTextColor(Color.parseColor("#ff222222"));
            }
            tv_fans_name.setText(commonNews.getName());
            tv_fans_jieshao.setText(commonNews.getJieshao());
            ImageLoader.getInstance().displayImage( commonNews.getImg(),iv_fans_list, ImageLoaderUtil.getDefaultOption());
        }


    }
}
