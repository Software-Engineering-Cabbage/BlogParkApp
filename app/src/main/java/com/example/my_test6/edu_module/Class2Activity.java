package com.example.my_test6.edu_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import com.example.my_test6.R;

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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        switch (type){
            case 1:
                String homeworkurl="https://edu.cnblogs.com"+url;
                webView.loadUrl(homeworkurl);
                break;
            case 2:
                webView.loadUrl(url);
                break;
            default:
                String memberurl="https:"+url;
                webView.loadUrl(memberurl);
                break;
        }

    }
}
