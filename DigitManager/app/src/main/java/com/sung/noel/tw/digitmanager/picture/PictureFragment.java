package com.sung.noel.tw.digitmanager.picture;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sung.noel.tw.digitmanager.R;
import com.sung.noel.tw.digitmanager.basic.BasicFragment;
import com.sung.noel.tw.digitmanager.key.BundleInfo;
import com.sung.noel.tw.digitmanager.picture.adapter.PictureAdapter;
import com.sung.noel.tw.digitmanager.picture.model.PictureData;
import com.sung.noel.tw.digitmanager.picture.picturelist.PictureListFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * Created by User on 2018/2/20.
 */

@RuntimePermissions
public class PictureFragment extends BasicFragment implements PictureAdapter.OnRecyclerViewItemClickListener {
    private View view;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<PictureData> pictureDatas;
    private PictureAdapter pictureAdapter;
    //用來裝 所有資料夾
    private Set<String> imgDirectories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_photo, container, false);
            ButterKnife.bind(this, view);
            //要求權限
            PictureFragmentPermissionsDispatcher.getExternalStorageDataWithCheck(this);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    //----------

    /***
     * 當取得權限
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void getExternalStorageData() {
        init();
    }
    //---

    /***
     *  init
     */
    private void init() {
        initRecyclerView();
        getImages();
        pictureAdapter.setData(imgDirectories);
    }
    //----------

    /***
     *  初始  RecyclerView
     */
    private void initRecyclerView() {
        pictureAdapter = new PictureAdapter(activity);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(pictureAdapter);
        pictureAdapter.setOnRecyclerViewItemClickListener(this);
    }

    //----------

    /***
     * 取得所有圖片
     */
    private void getImages() {
        imgDirectories = new HashSet<>();
        pictureDatas = new ArrayList<>();
        PictureData pictureData;
        //圖片名稱
        String name;
        //info
        String info;
        //路徑
        String path;
        //日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.TAIWAN);
        //檔案
        File file;
        //創建日期
        String date;
        //圖片數據
        byte[] data;
        //package 名稱
        String packageName;
        //sd卡路徑
        String sdPath = Environment.getExternalStorageDirectory().getPath() + "/";

        //SD卡中所有圖片,得到游標
        Cursor cursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            pictureData = new PictureData();
            data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            info = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            file = new File(path);
            date = dateFormat.format(new Date(file.lastModified()));

            packageName = path.replace(sdPath, "");
            packageName = packageName.substring(0, packageName.indexOf("/"));
            imgDirectories.add(packageName);

            pictureData.setName(name);
            pictureData.setInfo(info);
            pictureData.setPath(path);
            pictureData.setPackageName(packageName);
            pictureData.setDate(date);
            pictureData.setData(data);
            pictureDatas.add(pictureData);
        }
        cursor.close();
    }
    //----------

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PictureFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
    //----------

    /***
     * 當第二次開啟APP要求權限
     * @param request
     */
    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onShowRationale(final PermissionRequest request) {
        request.proceed();
    }
    //----------

    /****
     * 當拒絕
     */
    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onExternalStorageDenied() {
        Toast.makeText(activity, getString(R.string.dialog_message), Toast.LENGTH_SHORT).show();
    }
    //----------

    /***
     * 當選擇不在提問
     */
    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onNeverAskExternalStorage() {
    }


    //-----------------------

    /***
     *  當點擊項目
     * @param position
     */
    @Override
    public void onRecyclerViewItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BundleInfo.PICTURE_DATA, getSelectedDirectoryPhotos(position));
        replaceFragment2(new PictureListFragment(), true, bundle);
    }
    //-----------------------

    /***
     * 取得所選資料夾中的所有圖片
     */
    private ArrayList<PictureData> getSelectedDirectoryPhotos(int position) {
        String selectedDirectory = imgDirectories.toArray(new String[0])[position];
        ArrayList<PictureData> selectedData = new ArrayList<>();
        for (int i = 0; i < pictureDatas.size(); i++) {
            if (pictureDatas.get(i).getPackageName().equals(selectedDirectory)) {
                selectedData.add(pictureDatas.get(i));
            }
        }
        return selectedData;
    }
}
