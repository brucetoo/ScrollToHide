package com.bruce.scrolltohide;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by n1007 on 2015/3/9.
 */
public abstract class ScrollHideListener extends RecyclerView.OnScrollListener {
    private static final int HIDE_DISTANCE = 20; //隐藏显示的距离控制

    private int mScrolledDistance = 0;   //滚动的长度
    private boolean mControlsVisible = true; //show ?

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        //recyclerview 只能根据LinearLayoutManager来获取第一个显示的item
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        //当滚动到的位置恰好保证了第一个item在最上方，则显示
        if (firstVisibleItem == 0) {
            if(!mControlsVisible) {
                onShow();
                mControlsVisible = true;
            }
        } else {
            if (mScrolledDistance > HIDE_DISTANCE && mControlsVisible) {
                onHide();
                mControlsVisible = false;
                mScrolledDistance = 0;
            } else if (mScrolledDistance < -HIDE_DISTANCE && !mControlsVisible) {
                onShow();
                mControlsVisible = true;
                mScrolledDistance = 0;
            }
        }
        
        //mControlsVisible && dy>0 向下移动  !mControlsVisible && dy<0向上移动
        if((mControlsVisible && dy>0) || (!mControlsVisible && dy<0)) {
            mScrolledDistance += dy;
        }
    }


    public abstract void onHide();
    public abstract void onShow();
}
