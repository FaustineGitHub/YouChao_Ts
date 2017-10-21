package com.youchao.tingshuo.ui.adapter.fanyi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by dell on 2017/10/21.
 */

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>{
    private List<String[]> mList;
    private Context mContext;

    public GameAdapter(List<String[]> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_game,
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
        ImageView iv_game_icon;
        RatingBar ratting_bar_game;
        TextView tv_game_name,tv_game_info;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_game_icon = itemView.findViewById(R.id.iv_game_icon);
            ratting_bar_game = itemView.findViewById(R.id.ratting_bar_game);
            tv_game_name = itemView.findViewById(R.id.tv_game_name);
            tv_game_info = itemView.findViewById(R.id.tv_game_info);

        }
        //设置数据
        public void setData(String[] str) {
            ImageLoader.getInstance().displayImage( str[0],iv_game_icon, ImageLoaderUtil.getDefaultOption());
            tv_game_name.setText(str[1]);
            ratting_bar_game.setRating(Float.parseFloat(str[2]));
            tv_game_info.setText(str[3]);

        }


    }
}
