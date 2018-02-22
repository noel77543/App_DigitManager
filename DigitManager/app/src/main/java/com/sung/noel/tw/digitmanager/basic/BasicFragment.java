package com.sung.noel.tw.digitmanager.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.sung.noel.tw.digitmanager.MainActivity;
import com.sung.noel.tw.digitmanager.R;


/**
 * Created by noel on 2017/11/14.
 */

public class BasicFragment extends Fragment {
    public MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            activity = (MainActivity) context;
        }
    }
    //-----------------------

    /**
     * 第一層 箱子 container用
     */
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(android.R.id.tabcontent, fragment);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }
    //-----------------------

    public void replaceFragment(Fragment fragment, boolean addToBackStack, Bundle bundle) {

        fragment.setArguments(bundle);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(android.R.id.tabcontent, fragment);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }
    //-----------------------

    /***
     * 第二層候用
     * @param fragment
     * @param addToBackStack
     * @param bundle
     */
    public void replaceFragment2(Fragment fragment, boolean addToBackStack, Bundle bundle) {
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragment().getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(android.R.id.tabcontent, fragment);
        transaction.commit();
        getParentFragment().getChildFragmentManager().executePendingTransactions();
    }
    //-----------------------
    /***
     * 第二層候用 沒bundle
     * @param fragment
     * @param addToBackStack
     */
    public void replaceFragment2(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(android.R.id.tabcontent, fragment);
        transaction.commit();
        getFragmentManager().executePendingTransactions();
    }


    public boolean popFragment() {
        boolean isPop = false;
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }

    public void onBack() {
        activity.onBackPressed();
    }

    //------------------------------
    // 替換inner的container為外層的用  有bundle
    public void replaceInnerFragment(int layoutId, Fragment fragment, boolean addToBackStack, Bundle bundle) {
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getParentFragment().getParentFragment().getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(layoutId, fragment);
        transaction.commit();
        getParentFragment().getParentFragment().getChildFragmentManager().executePendingTransactions();
    }
}
