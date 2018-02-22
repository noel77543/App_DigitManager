package com.sung.noel.tw.digitmanager.picture.picturelist.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sung.noel.tw.digitmanager.R;
import com.sung.noel.tw.digitmanager.picture.picturelist.model.PictureListData;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2018/2/19.
 */

public class PictureListAdapter extends RecyclerView.Adapter<PictureListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PictureListData> pictureListDatas;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public PictureListAdapter(Context context) {
        this.context = context;
        pictureListDatas = new ArrayList<>();
    }

    //--------------------
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        PictureListData pictureListData = pictureListDatas.get(position);
        holder.tvName.setText(pictureListData.getName());
        Glide.with(context)
                .load(getUriFromSDCard(pictureListData.getPath()))
                .error(R.drawable.img_default)
                .into(holder.ivPicture);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onRecyclerViewItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pictureListDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_picture)
        ImageView ivPicture;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;

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
     * @param pictureListDatas
     */
    public void setData(ArrayList<PictureListData> pictureListDatas) {
        this.pictureListDatas = pictureListDatas;
        notifyDataSetChanged();
    }

    //---------

    /***
     *  讀取絕對路徑中的圖片
     * @param filePath
     * @return
     */
    private Uri getUriFromSDCard(String filePath) {
        File file = new File(filePath);
        return Uri.fromFile(file);
    }
}
