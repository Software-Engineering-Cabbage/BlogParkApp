package com.example.my_test6.netWork;

import android.os.Handler;
import android.os.Message;

import com.example.my_test6.Pool.TokenPool;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostUserApi {
    private OkHttpClient client;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");


    public void postMyApi(final Handler handler, final String url, final RequestBody body, final int what){
        final String token = TokenPool.getTokenPool().UserToken;
        client = new OkHttpClient();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String result = getUrl(url,body,token);
                    //    Log.d("TAG",result);
                    Message message1 = Message.obtain();
                    message1.what= what;
                    message1.obj = result;
                    handler.sendMessage(message1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    String getUrl(String url,RequestBody body, String token) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
