package com.nomad.instagramlogin;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Loginactivity extends Activity {
    private static final String AUTHURL = "https://api.instagram.com/oauth/authorize/";
    private static final String TOKENURL = "https://api.instagram.com/oauth/access_token";

//    public static final String APIURL = "https://api.instagram.com/v1";

//    ViewGroup view;
    public  String CALLBACKURL = "";
    private String client_id ="";
    private String client_secret = "";
    private String tokenURLString;
    private String authURLString;
    private String request_token;
    WebView webView;
    private String username;
    private String profile_picture;
    private String bio;
    private String full_name;
    private String accessTokenString;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        view = new RelativeLayout(this);
        Bundle bundle = getIntent().getExtras();
        CALLBACKURL = bundle.getString(Keys.CALLBACKURL_KEY);
        client_id = bundle.getString(Keys.CLIENT_ID_KEY);
        client_secret = bundle.getString(Keys.CLIENT_SECRET_KEY);
        authURLString = AUTHURL + "?client_id=" + client_id + "&redirect_uri=" + CALLBACKURL + "&response_type=code&display=touch&scope=basic+likes+relationships";
        tokenURLString = TOKENURL + "?client_id=" + client_id + "&client_secret=" + client_secret + "&redirect_uri=" + CALLBACKURL + "&grant_type=authorization_code";
//        view = new RelativeLayout(Loginactivity.this);

        setContentView(R.layout.loginactivty);
        webView = (WebView) findViewById(R.id.web);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new AuthWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setLayoutParams(lp);
//        view.addView(webView);
        webView.getSettings().setAppCacheEnabled( false );
        webView.getSettings().setCacheMode( WebSettings.LOAD_NO_CACHE );
        webView.loadUrl(authURLString);
//        webView.loadUrl("http://www.google.com");
    }
    static  AlertDialog ad;
    public static AlertDialog getProgressDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View view = LayoutInflater.from(activity).inflate(
                R.layout.progress_dialog, null);
        View img1 = view.findViewById(R.id.pd_circle1);
        View img2 = view.findViewById(R.id.pd_circle2);
        View img3 = view.findViewById(R.id.pd_circle3);
        int ANIMATION_DURATION = 400;
        Animator anim1 = setRepeatableAnim(activity, img1, ANIMATION_DURATION, R.animator.growndisappear);
        Animator anim2 = setRepeatableAnim(activity, img2, ANIMATION_DURATION, R.animator.growndisappear);
        Animator anim3 = setRepeatableAnim(activity, img3, ANIMATION_DURATION, R.animator.growndisappear);
        setListeners(img1, anim1, anim2, ANIMATION_DURATION);
        setListeners(img2, anim2, anim3, ANIMATION_DURATION);
        setListeners(img3, anim3, anim1, ANIMATION_DURATION);
        anim1.start();
        builder.setView(view);
        ad = builder.create();
        ad.setCanceledOnTouchOutside(false);
        ad.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);

        ad.getWindow().setLayout(dpToPx(200, activity), dpToPx(125, activity));
        return ad;
    }
    public static void cancelDialog(){
        ad.dismiss();
    }
    public static int dpToPx(int i, Context mContext) {

        DisplayMetrics displayMetrics = mContext.getResources()
                .getDisplayMetrics();
        return (int) ((i * displayMetrics.density) + 0.5);

    }
    private static Animator setRepeatableAnim(Activity activity, View target, final int duration, int animRes) {
        final Animator anim = AnimatorInflater.loadAnimator(activity, animRes);
        anim.setDuration(duration);
        anim.setTarget(target);
        return anim;
    }
    private static void setListeners(final View target, Animator anim, final Animator animator, final int duration) {
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animat) {
                if (target.getVisibility() == View.INVISIBLE) {
                    target.setVisibility(View.VISIBLE);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animator.start();
                    }
                }, duration - 100);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    public class AuthWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
//            webView.loadUrl("https://instagram.com/accounts/login/");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("error")){
                Toast.makeText(Loginactivity.this,"User Cancelled",Toast.LENGTH_LONG).show();
                finish();
                return true;
            }
            if (url.contains("success")){
                Toast.makeText(Loginactivity.this,"Logged in as"+ username,Toast.LENGTH_LONG).show();
                finish();
                return true;
            }
            if (url.startsWith(CALLBACKURL)) {
                System.out.println(url);
                String parts[] = url.split("=");
                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    if (part.contains("code") && i < parts.length - 1) {
                        request_token = parts[i + 1];  //This is your request token.
                        break;
                    }
                }
                //InstagramLoginDialog.this.dismiss();
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        getProgressDialog(Loginactivity.this).show();
                    }
                    @Override
                    protected Void doInBackground(Void... params) {
                        try
                        {
                            URL url = new URL(tokenURLString);
                            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                            httpsURLConnection.setRequestMethod("POST");
                            httpsURLConnection.setDoInput(true);
                            httpsURLConnection.setDoOutput(true);
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpsURLConnection.getOutputStream());
                            outputStreamWriter.write("client_id="+client_id+
                                    "&client_secret="+ client_secret +
                                    "&grant_type=authorization_code" +
                                    "&redirect_uri="+CALLBACKURL+
                                    "&code=" + request_token);
//                                    "&code=" + token);
                            outputStreamWriter.flush();
                            String response = streamToString(httpsURLConnection.getInputStream());
                            JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
                            profile_picture = jsonObject.getJSONObject("user").getString("profile_picture");
                            bio = jsonObject.getJSONObject("user").getString("bio");
                            full_name = jsonObject.getJSONObject("user").getString("full_name");
                            accessTokenString = jsonObject.getString("access_token"); //Here is your ACCESS TOKEN
                            id = jsonObject.getJSONObject("user").getString("id");
                            username = jsonObject.getJSONObject("user").getString("username");
                            //This is how you can get the user info. You can explore the JSON sent by Instagram as well to know what info you got in a response

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        cancelDialog();
                        Intent returned = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(InstaLogin.USERNAME,username);
                        bundle.putString(InstaLogin.ID ,id);
                        bundle.putString(InstaLogin.ACCESS_TOKEN,accessTokenString);
                        bundle.putString(InstaLogin.FULLNAME,full_name);
                        bundle.putString(InstaLogin.BIO,bio);
                        bundle.putString(InstaLogin.PROFILE_PIC,profile_picture);
                        returned.putExtras(bundle);
                        setResult(Activity.RESULT_OK,returned);
                        finish();
                    }
                }.execute();
                return true;
            }
            return false;
        }
    }

    private String streamToString(InputStream inputStream) {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }

}
