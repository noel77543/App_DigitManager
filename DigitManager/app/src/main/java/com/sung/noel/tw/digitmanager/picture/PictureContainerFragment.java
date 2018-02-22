package com.sung.noel.tw.digitmanager.picture;

import com.sung.noel.tw.digitmanager.basic.BasicContainerFragment;

/**
 * Created by User on 2018/2/22.
 */

public class PictureContainerFragment extends BasicContainerFragment {
    @Override
    public void init() {
        replaceFragment(new PictureFragment(), false);
    }
}
