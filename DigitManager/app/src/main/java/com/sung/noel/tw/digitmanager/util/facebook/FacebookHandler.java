package com.sung.noel.tw.digitmanager.util.facebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.sung.noel.tw.digitmanager.R;
import com.sung.noel.tw.digitmanager.util.implement.OnSuccessLoginFacebookListener;
import com.sung.noel.tw.digitmanager.MainActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 2018/2/19.
 */

public class FacebookHandler {
    public CallbackManager callbackManager;
    private LoginManager loginManager;
    private List<String> permissionNeeds;
    private MainActivity mainActivity;
    private OnSuccessLoginFacebookListener onSuccessLoginFacebookListener;

    public FacebookHandler(Context context) {
        FacebookSdk.sdkInitialize(context);
        if (context instanceof MainActivity) {
            this.mainActivity = (MainActivity) context;
        }
    }
    //---------

    /***
     * 登入
     */
    public void login() {

        callbackManager = CallbackManager.Factory.create();
        permissionNeeds = Arrays.asList("publish_actions");
        loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(mainActivity, permissionNeeds);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                onSuccessLoginFacebookListener.OnSuccessLoginFacebook();
            }

            @Override
            public void onCancel() {
//                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
//                System.out.println("onError");
            }
        });
    }

    //---------

    /***
     * 分享圖片
     */
    public void sharePhoto(String filePath) {
        if (isFaccebookLogin()) {
            Bitmap image = BitmapFactory.decodeFile(filePath);
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    .build();

            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            ShareApi.share(content, null);
        } else {
            Toast.makeText(mainActivity, mainActivity.getString(R.string.facebook_unlogin), Toast.LENGTH_SHORT).show();
        }
    }
    //----------

    /***
     *  是否已登入
     * @return
     */
    public boolean isFaccebookLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
    //----------

    public void setOnSuccessLoginFacebookListener(OnSuccessLoginFacebookListener onSuccessLoginFacebookListener) {
        this.onSuccessLoginFacebookListener = onSuccessLoginFacebookListener;
    }
}
