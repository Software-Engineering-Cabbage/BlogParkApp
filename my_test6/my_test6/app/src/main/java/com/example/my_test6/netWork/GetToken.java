package com.example.my_test6.netWork;


import android.os.Handler;

import java.io.IOException;
import android.os.Message;

import com.example.my_test6.Bean.Token;
import com.example.my_test6.Pool.TokenPool;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetToken {
    private String token;
    private OkHttpClient client;
    private static  final int GET_Blog_1 = 0x001;
    private static  final int Post_Blog_1 = 0x002;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    public GetToken() {
    }

    public String getToken() {
        return token;
    }
    public void getMyToken(){
        String myToken;
        client = new OkHttpClient();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String result = getPost("https://api.cnblogs.com/token","");
                    //    Log.d("TAG",result);
                    Gson gson1 = new Gson();
                    Token token1= gson1.fromJson(result, Token.class);
                    TokenPool.getTokenPool().setMyToken(token1.getAccess_token());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
    private String getPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        RequestBody requestBody = new FormBody.Builder()
                .add("client_id","8ab24367-91d6-4c19-9846-121909a0e01f")
                .add("client_secret","nD63VpKAHFeE8ObrKiPYOZD0yPwoT1pxfgDhZG_E1SpgDyZogA2n0Z_0-3qXOq92z8avekcEzxDmy4Qp")
                .add("grant_type","client_credentials")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
