package com.sung.noel.tw.digitmanager.audio;

import com.sung.noel.tw.digitmanager.basic.BasicContainerFragment;

/**
 * Created by User on 2018/2/22.
 */

public class AudioContainerFragment extends BasicContainerFragment {
    @Override
    public void init() {
        replaceFragment(new AudioFragment(), false);
    }
}
