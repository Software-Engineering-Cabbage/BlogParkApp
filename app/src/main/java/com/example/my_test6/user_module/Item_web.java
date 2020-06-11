package com.example.my_test6.user_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.Pool.login;
import com.example.my_test6.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Item_web extends AppCompatActivity {
    private WebView web;
    private String postId = "";
    private String blogApp = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_item_web);
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        String s = getIntent().getStringExtra("url");
        FloatingActionButton actionbutton = findViewById(R.id.comment_fab);
        if(getIntent().getBooleanExtra("flag",false)){
            actionbutton.setVisibility(View.GONE);
        }
        else {
            blogApp = getIntent().getStringExtra("blogApp");
            postId = getIntent().getStringExtra("postId");
            actionbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto browseHistory
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.user_module.ResponseBlog");
                    intent.putExtra("postId",postId);
                    intent.putExtra("blogApp",blogApp);
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });
        }
        web = findViewById(R.id.item_webview);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        web.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        web.setWebViewClient(new MyWebViewClient());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(s, TokenPool.getTokenPool().Cookie);
        web.loadUrl(s);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(String.valueOf(request.getUrl()), TokenPool.getTokenPool().Cookie);
            return false;
        }
    }
}
