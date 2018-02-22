package com.sung.noel.tw.digitmanager.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sung.noel.tw.digitmanager.favorite.model.Favorite;

import java.util.ArrayList;

/**
 * Created by User on 2018/2/20.
 */

public class SharedpreferenceUtil {

    private final static String _NAME = "DigitManager";
    //我的最愛
    private final static String _FAVORITE = "Favorite";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedpreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    /**
     * 設定我的最愛的資料
     *
     * @param favorites
     */
    public void setFavoriteData(ArrayList<Favorite> favorites) {
        editor.putString(_FAVORITE, gson.toJson(favorites)).commit();
    }

    // -------------------------------------------------------------------

    /**
     * 取得我的最愛的資料
     *
     * @return
     */
    public ArrayList<Favorite> getFavoriteData() {
        return gson.fromJson(sharedPreferences.getString(_FAVORITE, "[]"), new TypeToken<ArrayList<Favorite>>() {
        }.getType());
    }
}
