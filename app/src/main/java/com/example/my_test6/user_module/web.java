package com.example.my_test6.user_module;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.my_test6.R;

public class web extends AppCompatActivity {
    private WebView team;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_web);
        getSupportActionBar().hide();
        team = findViewById(R.id.team_webview);
        team.getSettings().setJavaScriptEnabled(true);
        team.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        team.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        team.setWebViewClient(new MyWebViewClient());
        team.loadUrl("https://www.cnblogs.com/team-pakchoi/p/12544117.html");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    }
}
