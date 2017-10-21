package com.youchao.tingshuo.ui.adapter.shouye;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by dell on 2017/10/21.
 */

public class TuiJianAdapter extends RecyclerView.Adapter<TuiJianAdapter.ViewHolder>{
    private List<String[]> mList;
    private Context mContext;

    public TuiJianAdapter(List<String[]> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_tuijian,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_tuijian_list;
        RelativeLayout rl_tuijian_type;
        TextView tv_tuijian_name,tv_tuijian_jieshao,tv_tuijian_guanzhu,tv_tuijian_fans,tv_tuijian_type;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_tuijian_list = itemView.findViewById(R.id.iv_tuijian_list);
            rl_tuijian_type = itemView.findViewById(R.id.rl_tuijian_type);
            tv_tuijian_name = itemView.findViewById(R.id.tv_tuijian_name);
            tv_tuijian_jieshao = itemView.findViewById(R.id.tv_tuijian_jieshao);
            tv_tuijian_guanzhu = itemView.findViewById(R.id.tv_tuijian_guanzhu);
            tv_tuijian_fans = itemView.findViewById(R.id.tv_tuijian_fans);
            tv_tuijian_type = itemView.findViewById(R.id.tv_tuijian_type);

        }
        //设置数据
        public void setData(String[] str) {
            ImageLoader.getInstance().displayImage( str[0],iv_tuijian_list, ImageLoaderUtil.getCircleBitmapOption(Color.parseColor("#ffffff"),1));
            tv_tuijian_name.setText(str[1]);
            tv_tuijian_jieshao.setText(str[2]);
            tv_tuijian_guanzhu.setText("关注\t"+str[3]);
            tv_tuijian_fans.setText("粉丝\t"+str[4]);
            tv_tuijian_type.setText(str[5]);

        }


    }
}
