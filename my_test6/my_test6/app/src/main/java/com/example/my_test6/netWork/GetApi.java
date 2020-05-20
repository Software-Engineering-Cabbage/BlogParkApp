package com.example.my_test6.netWork;


import android.os.Handler;

import java.io.IOException;
import android.os.Message;

import com.example.my_test6.Pool.TokenPool;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class GetApi {
    private OkHttpClient client;
    private static  final int GET_Blog_1 = 0x001;
    private static  final int Post_Blog_1 = 0x002;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");


    public void getMyApi(final Handler handler, final String url,final int what){
        final String token = TokenPool.getTokenPool().getMyToken();
        client = new OkHttpClient();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String result = getUrl(url,token);
                    //    Log.d("TAG",result);
                    Message message1 = Message.obtain();
                    message1.what=what;
                    message1.obj = result;
                    handler.sendMessage(message1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
    String getUrl(String url,String token) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
