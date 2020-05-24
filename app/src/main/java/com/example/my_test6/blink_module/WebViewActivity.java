package com.example.my_test6.blink_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.my_test6.R;

public class WebViewActivity extends AppCompatActivity {
    private String url;
    private WebView web_display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blink_activity_web_view);
        Intent intent  = getIntent();
        url = intent.getStringExtra("url");
        web_display = findViewById(R.id.web_display);
        web_display.canGoBack();
        web_display.canGoForward();
        WebSettings webSettings = web_display.getSettings();
        webSettings.setBuiltInZoomControls(true);     
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);    
        webSettings.setUseWideViewPort(true);     
        webSettings.setLoadWithOverviewMode(true);   
        webSettings.setSavePassword(true);    
        webSettings.setSaveFormData(true);     
        webSettings.setJavaScriptEnabled(true);     
        web_display.loadUrl(url);
        web_display.setWebViewClient(new WebViewClient());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)&&web_display.canGoBack()){
            web_display.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
