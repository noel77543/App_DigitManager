package com.sung.noel.tw.digitmanager.favorite;

import com.sung.noel.tw.digitmanager.basic.BasicContainerFragment;

/**
 * Created by User on 2018/2/22.
 */

public class FavoriteContainerFragment extends BasicContainerFragment {
    @Override
    public void init() {
        replaceFragment(new FavoriteFragment(), false);
    }
}
