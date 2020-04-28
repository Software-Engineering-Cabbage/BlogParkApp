package com.example.my_test6.user_module;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;

import java.io.File;

public class logout extends AppCompatActivity {
    private Button confirm;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_logout);
        cancel = findViewById(R.id.logout_cancel);
        setTitle("退出登录");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goto login
                logout.this.finish();
            }
        });
        confirm = findViewById(R.id.logout_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goto login
                TokenPool.getTokenPool().UserToken = "";
                TokenPool.getTokenPool().isLogin = false;
                clearCache(logout.this);
                logout.this.finish();
            }
        });
    }

    public static void clearCache(Context context) {
        try {
            CookieManager.getInstance().removeAllCookies(null);
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
