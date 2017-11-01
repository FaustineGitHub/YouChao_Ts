package com.youchao.tingshuo.ui.adapter.shouye;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youchao.tingshuo.R;
import com.youchao.tingshuo.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by dell on 2017/10/18.
 * 搜索Adapter
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.BaseViewHolder>{
    private List<String[]> mList;
    private Context mContext;
    //item中评论按钮的回调接口
    private OnDeleteClickListener onDeleteListener;
    //item的回调接口
    private OnTotalItemClickListener onTotalItemClickListener;

    public SearchAdapter(List<String[]> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_listview, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.tv_item_search.setText(mList.get(position)[1]);
        ImageLoader.getInstance().displayImage( mList.get(position)[2],holder.iv_item_searchicon, ImageLoaderUtil.getCircleBitmapOption(Color.WHITE,1));
        //设置item的删除事件
        holder.iv_item_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDeleteListener != null) {
                    // 获取最新item的位置
                    int pos = holder.getLayoutPosition();
                    onDeleteListener.onDeleteClick(view, pos);
                }
            }
        });
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

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public class BaseViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item_searchicon,iv_item_clear;
        TextView tv_item_search;
        public BaseViewHolder(View itemView) {
            super(itemView);
            iv_item_searchicon = itemView.findViewById(R.id.iv_item_searchicon);
            tv_item_search = itemView.findViewById(R.id.tv_item_search);
            iv_item_clear = itemView.findViewById(R.id.iv_item_clear);
        }
        //设置数据
        public void setData() {

        }
    }
    /**
     * 整个item的点击事件和长按事件回调接口
     */
    public interface OnTotalItemClickListener {
        void onItemClick(View v, int position);
    }

    /**
     * 删除的点击事件回调接口
     */
    public interface OnDeleteClickListener {
        void onDeleteClick(View v, int position);
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
     * 设置评论按钮点击事件的接口
     */
    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }
}
