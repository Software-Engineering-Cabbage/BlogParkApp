package com.example.my_test6.Pool;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.my_test6.Pool.netWork.GetUserToken;
import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.GetUserApi;
import com.example.my_test6.user_module.GsonBean.Users;
import com.google.gson.Gson;

import java.io.File;

public class login extends AppCompatActivity {
    private String Usertoken;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x2){
                Usertoken = (String) msg.obj;
                int p1 = Usertoken.indexOf("access_token",0);
                int p2 = Usertoken.indexOf(":",p1) + 2;
                int p3 = Usertoken.indexOf("\"",p2);
                Usertoken = Usertoken.substring(p2, p3);
                editor.putString("UserToken",Usertoken);
                editor.putBoolean("isLogin",true);
                editor.commit();
                TokenPool.getTokenPool().UserToken = Usertoken;
                TokenPool.getTokenPool().isLogin = true;
                System.out.println("UserToken " + Usertoken);
                final Handler handler1 = new Handler() {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 0x1) {//收到users信息
                            Gson gson = new Gson();
                            String json = (String) msg.obj;
                            Users users;
                            users = gson.fromJson(json, Users.class);
                            MinePool.getMinePool().users = users;
                            login.this.finish();
                        }
                    }
                };
                GetUserApi api = new GetUserApi();
                String url = "https://api.cnblogs.com/api/users";
                api.getMyApi(handler1, url, 1);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_log);
        getSupportActionBar().hide();

        WebView webview = findViewById(R.id.login_WebView);
        clearCache(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        webview.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webview.setWebViewClient(new MyWebViewClient());
        StringBuilder builder = new StringBuilder();
        builder.append("client_id=").append("8ab24367-91d6-4c19-9846-121909a0e01f").append("&")
                .append("scope=").append("openid profile CnBlogsApi").append("&")
                .append("response_type=").append("code id_token").append("&")
                .append("redirect_uri=").append("https://oauth.cnblogs.com/auth/callback").append("&")
                .append("state=").append("cnblogs.com").append("&")
                .append("nonce=").append("cnblogs.com");
        String postData = builder.toString();
        String url = "https://oauth.cnblogs.com/connect/authorize";
        webview.postUrl(url, postData.getBytes());
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if(request.getUrl().toString().substring(0,39).equals("https://oauth.cnblogs.com/auth/callback")){
                sharedpreferences = getApplication().getSharedPreferences("User", Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();
                int p = request.getUrl().toString().indexOf("&",45);
                //System.out.println("code " + request.getUrl().toString().substring(45,p));
                GetUserToken get = new GetUserToken(request.getUrl().toString().substring(45,p),handler);
                get.gettoken();
                return true;
            }
            return false;
        }
    }
    public static void clearCache(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 清除cookie
                CookieManager.getInstance().removeAllCookies(null);
            } else {
                CookieSyncManager.createInstance(context);
                CookieManager.getInstance().removeAllCookie();
                CookieSyncManager.getInstance().sync();
            }

            new WebView(context).clearCache(true);

            File cacheFile = new File(context.getCacheDir().getParent() + "/app_webview");
            clearCacheFolder(cacheFile, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int clearCacheFolder(File dir, long time) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, time);
                    }
                    if (child.lastModified() < time) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }
}