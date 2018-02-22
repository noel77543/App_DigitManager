package com.sung.noel.tw.digitmanager.picture.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sung.noel.tw.digitmanager.R;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2018/2/19.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private Context context;
    private String[] packageNames;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public PictureAdapter(Context context) {
        this.context = context;
        packageNames = new String[0];
    }

    //--------------------
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvPackageName.setText(packageNames[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onRecyclerViewItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packageNames.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_package_name)
        TextView tvPackageName;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    //--------------------

    /***
     * 接口 ItemClickListener
     */
    public interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClick(int position);
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
    //--------------------

    /***
     *  更新資料
     * @param packageNames
     */
    public void setData(Set<String> packageNames) {
        this.packageNames = packageNames.toArray(new String[0]);
        for (int i = 0; i < this.packageNames.length; i++) {
            Log.e("" + i, this.packageNames[i]);
        }
        notifyDataSetChanged();
    }
}