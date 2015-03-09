package com.nomad.instagramlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by baha on 06/03/15.
 */
public class InstaLogin {
    private String CLIENT_ID = "https://api.instagram.com/oauth/authorize";
    private String CLIENT_SECRET = "https://api.instagram.com/oauth/access_token";
    private String CALLBACKURL = "";
    private Activity context;

    public final static String USERNAME="user_name";
    public final static String FULLNAME="full_name";
    public final static String ACCESS_TOKEN="token";
    public final static String PROFILE_PIC="profile_pic";
    public final static String ID="id";
    public final static String BIO="bio";

    public InstaLogin(Activity context, String CLIENT_ID, String CLIENT_SECRET, String CALLBACKURL) {
        this.CLIENT_ID = CLIENT_ID;
        this.CLIENT_SECRET = CLIENT_SECRET;
        this.CALLBACKURL = CALLBACKURL;
        this.context = context;
    }

    public void login() {
        Intent intent = new Intent(context, Loginactivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Keys.CLIENT_ID_KEY, CLIENT_ID);
        bundle.putString(Keys.CLIENT_SECRET_KEY, CLIENT_SECRET);
        bundle.putString(Keys.CALLBACKURL_KEY, CALLBACKURL);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, Keys.LOGIN_REQ);
    }
}
