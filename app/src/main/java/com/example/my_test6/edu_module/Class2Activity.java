package com.example.my_test6.edu_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;
import com.example.my_test6.question_module.QuestionDetail_activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Class2Activity extends AppCompatActivity {
    private String postId;
    private String blogApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edu_activity_class2);
        Intent intent=getIntent();
        String url=intent.getStringExtra("web");
        int type=intent.getIntExtra("type",999);
        postId = getIntent().getStringExtra("postId");
        blogApp = getIntent().getStringExtra("blogApp");
        FloatingActionButton actionbutton = findViewById(R.id.comment_fab_edu);
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
