package com.sung.noel.tw.digitmanager.main.adapter;

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
import com.sung.noel.tw.digitmanager.main.model.PictureData;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2018/2/19.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PictureData> pictureDatas;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public PictureAdapter(Context context) {
        this.context = context;
        pictureDatas = new ArrayList<>();
    }

    //--------------------
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        PictureData pictureData = pictureDatas.get(position);
        holder.tvName.setText(pictureData.getName());
        Glide.with(context)
                .load(getUriFromSDCard(pictureData.getPath()))
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
        return pictureDatas.size();
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
     * @param pictureDatas
     */
    public void setData(ArrayList<PictureData> pictureDatas) {
        this.pictureDatas = pictureDatas;
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
