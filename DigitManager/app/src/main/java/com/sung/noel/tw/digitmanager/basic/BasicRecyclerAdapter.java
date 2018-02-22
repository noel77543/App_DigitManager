package com.sung.noel.tw.digitmanager.basic;

import android.support.v7.widget.RecyclerView;

/**
 * Created by User on 2018/2/16.
 */

public abstract class BasicRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public interface OnRecyclerViewItemClickListener {
        void OnRecyclerViewItemClick();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
