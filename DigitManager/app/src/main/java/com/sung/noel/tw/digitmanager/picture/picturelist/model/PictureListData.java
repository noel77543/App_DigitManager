package com.sung.noel.tw.digitmanager.picture.picturelist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 2018/2/19.
 */

public class PictureListData implements Parcelable {
    //圖片名稱
    private String name;
    //圖片info
    private String info;
    //圖片數據
    private byte[] data;
    //圖片路徑
    private String path;
    //圖片創建日期
    private String date;
    //資料夾名稱
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.info);
        dest.writeByteArray(this.data);
        dest.writeString(this.path);
        dest.writeString(this.date);
        dest.writeString(this.packageName);
    }

    public PictureListData() {
    }

    protected PictureListData(Parcel in) {
        this.name = in.readString();
        this.info = in.readString();
        this.data = in.createByteArray();
        this.path = in.readString();
        this.date = in.readString();
        this.packageName = in.readString();
    }

    public static final Creator<PictureListData> CREATOR = new Creator<PictureListData>() {
        @Override
        public PictureListData createFromParcel(Parcel source) {
            return new PictureListData(source);
        }

        @Override
        public PictureListData[] newArray(int size) {
            return new PictureListData[size];
        }
    };
}
