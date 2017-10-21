package com.youchao.tingshuo.ui.adapter;

/**
 * Created by dell on 2017/10/21.
 */

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView的 基本适配器
 */
public abstract class MyBaseAdapter extends RecyclerView.Adapter{

    /**
     * 头
     */
    public View headerView;
    /**
     * 头的类型
     */
    private final int HEADER_TYPE = 10000;

    /**
     * 添加头
     * @param header
     */
    public void addHeaderView(View header){
        this.headerView = header;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE){
            return new HeaderViewHolder(headerView);
        }else{
            return onCreateViewHolder_my(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == HEADER_TYPE){
            return;
        }
        if(headerView == null){
            onBindViewHolder_my(holder,position);
        }else{
            onBindViewHolder_my(holder,position - 1);
        }

    }

    @Override
    public int getItemCount() {
        if(headerView == null){
            return getData().size();
        }
        return getData().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(headerView == null){
            return getItemViewType_my(position);
        }else {
            //有头
            if(position == 0){
                return HEADER_TYPE;
            }else{
                return getItemViewType_my(position - 1);//除去头
            }
        }
    }

    /**
     * 头
     */
    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 针对GridLayoutManager,解决添加的头不会单独占据一行
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
                @Override
                public int getSpanSize(int position)
                {
                    int viewType = getItemViewType(position);
                    if (viewType == HEADER_TYPE) {
                        return gridLayoutManager.getSpanCount();//跨距整个列数
                    }
                    return 1;//正常情况为1列
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    public abstract List getData();

    /**
     * 头类型  使用的10000 不同就行
     * @param position  不用管包不包括头
     * @return
     */
    public abstract int getItemViewType_my(int position);

    public abstract RecyclerView.ViewHolder onCreateViewHolder_my(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder_my(RecyclerView.ViewHolder holder, int position);


}
