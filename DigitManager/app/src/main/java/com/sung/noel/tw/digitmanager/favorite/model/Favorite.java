package com.sung.noel.tw.digitmanager.favorite.model;

import android.support.annotation.AnimatorRes;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by User on 2018/2/21.
 */

public class Favorite {

    public static final String FAVORITE_PHOTO = "PHOTO";
    public static final String FAVORITE_AUDIO = "AUDIO";
    public static final String FAVORITE_VIDEO = "VIDEO";

    @StringDef({FAVORITE_PHOTO, FAVORITE_AUDIO, FAVORITE_VIDEO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FavoriteType {

    }
    private String name;
    private String type;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
