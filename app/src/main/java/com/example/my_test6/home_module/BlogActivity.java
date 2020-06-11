package com.example.my_test6.home_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;

public class BlogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_blog);
        getSupportActionBar().hide();
        WebView webView = findViewById(R.id.webView_home_blog);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
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
