package com.bruce.scrolltohide;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by n1007 on 2015/3/9.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItemList;
    //recyler的item type
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;

    public RecyclerAdapter(List<String> itemList) {
        this.mItemList = itemList;
    }

    /**
     * ItemViewHolder  (TYPE_ITEM)
     */
    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public ItemViewHolder(View itemView, TextView textView) {
            super(itemView);
            this.mTextView = textView;
        }

        /**
         * 对外抛出的实例化方法
         *
         * @param itemView
         * @return
         */
        public static ItemViewHolder newInstance(View itemView) {
            TextView textView = (TextView) itemView.findViewById(R.id.itemTextView);
            return new ItemViewHolder(itemView, textView);
        }

        /**
         * 抛出方法设置text
         * @param text
         */
        public void setTextView(CharSequence text) {
            mTextView.setText(text);
        }
    }

    /**
     * HeadViewHolder （TYPE_HEADER）*
     */
    public static final class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return ItemViewHolder.newInstance(view); //创建item viewHolder
        }else if(viewType == TYPE_HEADER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_header, parent, false);
            return new HeaderViewHolder(view); //创建head viewHolder
        }
        //item 没有匹配抛出异常
        throw new RuntimeException(viewType+" is not exist");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(!isHeaderView(position)) {  //只处理非headview
            ItemViewHolder hold = (ItemViewHolder) holder; //绑定viewHolder
            hold.setTextView(mItemList.get(position-1)); //减1 是因为顶部有一个headerView
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0+1 : mItemList.size() + 1;  //加 1 是因为手动加上了一个head view
    }


    /**
     * 有不同type时 需要重写
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(isHeaderView(position)){
            return TYPE_HEADER;
        }else{
            return TYPE_ITEM;
        }
    }

    /**
     * 
     * 判断是不是Header @param position
     * @return
     */
    public boolean isHeaderView(int position){
       return position == 0;
    }
}
