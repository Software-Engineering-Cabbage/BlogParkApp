package com.example.my_test6.home_module;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BlogActivity extends AppCompatActivity {
    private String postId;
    private String blogApp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_blog);
        getSupportActionBar().hide();
        WebView webView = findViewById(R.id.webView_home_blog);
        FloatingActionButton actionbutton = findViewById(R.id.comment_fab_home);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        postId = getIntent().getStringExtra("postId");
        blogApp = getIntent().getStringExtra("blogApp");
        actionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.user_module.ResponseBlog");
                intent.putExtra("postId",postId);
                intent.putExtra("blogApp",blogApp);
                intent.setComponent(componentname);
                startActivity(intent);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);//网页缩放功能
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new MyWebViewClient());
        String url1 = bundle.getString("blog_addr");
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url1, TokenPool.getTokenPool().Cookie);
      //  url1 = url1.replace("http","https");
        webView.loadUrl(url1);

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
