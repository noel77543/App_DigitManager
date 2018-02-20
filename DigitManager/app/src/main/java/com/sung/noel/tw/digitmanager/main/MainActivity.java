package com.sung.noel.tw.digitmanager.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.sung.noel.tw.digitmanager.R;
import com.sung.noel.tw.digitmanager.facebook.FacebookHandler;
import com.sung.noel.tw.digitmanager.implement.OnSuccessLoginFacebookListener;
import com.sung.noel.tw.digitmanager.main.adapter.PictureAdapter;
import com.sung.noel.tw.digitmanager.main.model.PictureData;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends FragmentActivity implements PictureAdapter.OnRecyclerViewItemClickListener, OnSuccessLoginFacebookListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<PictureData> pictureDatas;
    private PictureAdapter pictureAdapter;
    private FacebookHandler facebookHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //要求權限
        MainActivityPermissionsDispatcher.getExternalStorageDataWithCheck(this);
    }
    //----------

    /***
     * 當取得權限
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void getExternalStorageData() {
        init();
    }
    //----------

    /***
     *  init...
     */
    private void init() {
        initRecyclerView();
        getImages();
        pictureAdapter.setData(pictureDatas);
        facebookHandler = new FacebookHandler(this);
        facebookHandler.setOnSuccessLoginFacebookListener(this);
        facebookHandler.login();
    }


    //----------

    /***
     *  初始  RecyclerView
     */
    private void initRecyclerView() {
        pictureAdapter = new PictureAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(pictureAdapter);
        pictureAdapter.setOnRecyclerViewItemClickListener(this);
    }
    //----------

    /***
     * 取得所有圖片
     */
    private void getImages() {
        pictureDatas = new ArrayList<>();
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

        //SD卡中所有圖片,得到游標
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            PictureData pictureData = new PictureData();
            name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            info = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            file = new File(path);
            date = dateFormat.format(new Date(file.lastModified()));
            data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            pictureData.setName(name);
            pictureData.setInfo(info);
            pictureData.setPath(path);
            pictureData.setDate(date);
            pictureData.setData(data);
            pictureDatas.add(pictureData);
        }
        cursor.close();
    }


    //----------

    /***
     * 當成功登入臉書
     */
    @Override
    public void OnSuccessLoginFacebook() {
//        Log.e("path", pictureDatas.get(0).getPath());
        facebookHandler.sharePhoto(pictureDatas.get(0).getPath());
    }
    //----------

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
        Toast.makeText(this, getString(R.string.dialog_message), Toast.LENGTH_SHORT).show();
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
        facebookHandler.sharePhoto(pictureDatas.get(position).getPath());

    }

    //-----------------------

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        facebookHandler.callbackManager.onActivityResult(requestCode, responseCode, data);
    }
}
