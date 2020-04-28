package com.example.my_test6.blink_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        web_display.loadUrl(url);
        web_display.setWebViewClient(new WebViewClient());
    }
}
