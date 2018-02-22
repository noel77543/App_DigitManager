package com.sung.noel.tw.digitmanager;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sung.noel.tw.digitmanager.audio.AudioContainerFragment;
import com.sung.noel.tw.digitmanager.basic.BasicFragment;
import com.sung.noel.tw.digitmanager.favorite.FavoriteContainerFragment;
import com.sung.noel.tw.digitmanager.picture.PictureContainerFragment;
import com.sung.noel.tw.digitmanager.video.VideoContainerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;
    private String[] tabNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    //----------

    /***
     *  init...
     */
    private void init() {
        tabNames = getResources().getStringArray(R.array.main_tab_array);
        Class[] classes = new Class[]{PictureContainerFragment.class, AudioContainerFragment.class, VideoContainerFragment.class, FavoriteContainerFragment.class};
        tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        for (int i = 0; i < tabNames.length; i++) {
            tabhost.addTab(tabhost.newTabSpec(tabNames[i]).setIndicator(getTabView(tabNames[i])), classes[i], null);
        }
    }

    //----------------------

    //設定tab的背景圖
    private View getTabView(String title) {
        View view = LayoutInflater.from(this).inflate(R.layout.module_tab, null);
        TextView tvTitle = ButterKnife.findById(view,R.id.tv_title) ;
        tvTitle.setText(title);
        return view;
    }


    //----------------------

    /**
     * 檢查tag
     */
    private boolean isTabTag(String tabName) {
        for (int i = 0; i < tabNames.length; i++) {
            if (tabName.equals(tabNames[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        backToBeforePage();
    }

    private void backToBeforePage() {
        boolean isPopFragment = false;
        String currentTabTag = tabhost.getCurrentTabTag();
        if (isTabTag(currentTabTag)) {
            isPopFragment = ((BasicFragment) getSupportFragmentManager()
                    .findFragmentByTag(currentTabTag)).popFragment();
        }
        if (!isPopFragment) {
            this.finish(); // 關閉
        }
    }
}
