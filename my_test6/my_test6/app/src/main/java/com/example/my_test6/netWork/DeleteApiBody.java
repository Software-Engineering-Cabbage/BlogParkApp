package com.example.my_test6.netWork;

import android.os.Handler;
import android.os.Message;

import com.example.my_test6.Pool.TokenPool;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeleteApiBody {
    private OkHttpClient client;
    public void Delete(final Handler handler, final String url, final RequestBody body, final int what){
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
                    message1.what=what;
                    message1.obj = result;
                    handler.sendMessage(message1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
    String getUrl(String url,RequestBody body,String token) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token)
                .delete(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
