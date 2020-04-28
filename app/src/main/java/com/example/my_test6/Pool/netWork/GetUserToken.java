package com.example.my_test6.Pool.netWork;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GetUserToken {
    private String code;
    final private int GET_USER_TOKEN = 0x2;
    final private Handler handler;

    public GetUserToken(String Code,final Handler handler){
        this.handler = handler;
        code = Code;
    }

    public void gettoken(){
        String url = "https://oauth.cnblogs.com/connect/token ";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("client_id","8ab24367-91d6-4c19-9846-121909a0e01f")
                .add("client_secret","nD63VpKAHFeE8ObrKiPYOZD0yPwoT1pxfgDhZG_E1SpgDyZogA2n0Z_0-3qXOq92z8avekcEzxDmy4Qp")
                .add("grant_type","authorization_code")
                .add("code",code)
                .add("redirect_uri","https://oauth.cnblogs.com/auth/callback")
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(body)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    String s = response.body().string();
                    Message message = new Message();
                    message.what = GET_USER_TOKEN;
                    message.obj = s;
                    handler.sendMessage(message);
                    Log.d(TAG, "run: sendMessage"+s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
