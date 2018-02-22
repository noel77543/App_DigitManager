package com.sung.noel.tw.digitmanager.basic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sung.noel.tw.digitmanager.R;


/**
 * Created by noel on 2017/11/14.
 */

public abstract class BasicContainerFragment extends BasicFragment {
    // ---------------------------------------------------
    private boolean isViewCreated;

    // ---------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TabhostLayout 裡面的那層
        return inflater.inflate(R.layout.module_container, null);
    }

    // ---------------------------------------------------
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!isViewCreated) {
            Log.e("isViewCreated","isViewCreated");
            isViewCreated = true;
            init();
        }
    }

    // ---------------------------------------------------
    public abstract void init();
}
