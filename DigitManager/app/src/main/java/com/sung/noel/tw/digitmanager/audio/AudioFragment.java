package com.sung.noel.tw.digitmanager.audio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sung.noel.tw.digitmanager.R;
import com.sung.noel.tw.digitmanager.basic.BasicFragment;

import butterknife.ButterKnife;

/**
 * Created by User on 2018/2/20.
 */

public class AudioFragment extends BasicFragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_audio, container, false);
            ButterKnife.bind(this, view);
            init();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }
    // ----------------------------

    /**
     * 起始點
     */
    private void init() {

    }
}
