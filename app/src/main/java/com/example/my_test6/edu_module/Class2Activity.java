package com.example.my_test6.edu_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;
import com.example.my_test6.question_module.QuestionDetail_activity;

public class Class2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edu_activity_class2);
        Intent intent=getIntent();
        String url=intent.getStringExtra("web");
        int type=intent.getIntExtra("type",999);
        WebView webView=findViewById(R.id.webViewClass);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        switch (type){
            case 1:
                String homeworkurl="https://edu.cnblogs.com"+url;

                cookieManager.setCookie(homeworkurl, TokenPool.getTokenPool().Cookie);

                webView.loadUrl(homeworkurl);
                break;
            case 2:
                cookieManager.setCookie(url, TokenPool.getTokenPool().Cookie);
                webView.loadUrl(url);

                break;
            default:
                String memberurl="https:"+url;
                cookieManager.setCookie(memberurl, TokenPool.getTokenPool().Cookie);
                webView.loadUrl(memberurl);
                break;
        }

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
