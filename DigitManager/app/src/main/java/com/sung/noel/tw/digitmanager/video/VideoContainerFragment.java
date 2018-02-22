package com.sung.noel.tw.digitmanager.video;

import com.sung.noel.tw.digitmanager.basic.BasicContainerFragment;

/**
 * Created by User on 2018/2/22.
 */

public class VideoContainerFragment extends BasicContainerFragment{
    @Override
    public void init() {
        replaceFragment(new VideoFragment(),false);
    }
}
