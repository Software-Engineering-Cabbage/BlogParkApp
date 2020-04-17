package com.example.my_test6.ui.edu;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.my_test6.Bean.BlogSummary;
import com.example.my_test6.Bean.Token;
import com.example.my_test6.R;
import com.example.my_test6.home.HomeSearchActivity;
import com.example.my_test6.netWork.GetApi;
import com.example.my_test6.netWork.GetToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EduFragment extends Fragment {
    private EduViewModel eduViewModel;
    private OkHttpClient client = new OkHttpClient();
    private static  final int GET_MESSAGE_1 = 0x01;
    private static  final int Post_Blog_1 = 0x002;
    private String token;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eduViewModel =
                ViewModelProviders.of(this).get(EduViewModel.class);
        View root = inflater.inflate(R.layout.fragment_edu, container, false);
        final Button button1 = root.findViewById(R.id.button);
        final TextView textView1 = root.findViewById(R.id.textView);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch(msg.what){
                    case GET_MESSAGE_1:
                        String text = (String)msg.obj;
                       Gson gson = new Gson();
                        List<BlogSummary> blogSummaryList1 = gson.fromJson(text, new TypeToken<List<BlogSummary>>(){}.getType());
                       textView1.setText(blogSummaryList1.get(0).getDescription());
                        break;
                    case Post_Blog_1:
                        String text2 = (String)msg.obj;
                        Gson gson1 = new Gson();
                        Token token1= gson1.fromJson(text2, Token.class);
                        token = token1.getAccess_token();

                     //   Log.d("Tag",text2);
                        break;
                    default:
                        break;
                }
            }
        };
      //  GetToken getToken = new GetToken();
      //  getToken.getMyToken(handler);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GetApi getApi = new GetApi();
                String url = "https://api.cnblogs.com/api/blogposts/@picked?pageIndex=2&pageSize=10";
               getApi.getMyApi(handler,url,token);
            }
        });
        return root;
    }

    private  void test_get(final Handler handler){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String result = getUrl("https://api.cnblogs.com/api/blogposts/@picked?pageIndex=2&pageSize=10");
                //    Log.d("TAG",result);
                    Message message1 = Message.obtain();
                    message1.what=GET_MESSAGE_1;
                    message1.obj = result;
                    handler.sendMessage(message1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


    String getUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+"eyJhbGciOiJSUzI1NiIsImtpZCI6IjlFMjcyMkFGM0IzRTFDNzU5RTI3NEFBRDI5NDFBNzg1MDlCMDc2RDAiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJuaWNpcnpzLUhIV2VKMHF0S1VHbmhRbXdkdEEifQ.eyJuYmYiOjE1ODcwMjk3NTYsImV4cCI6MTU4NzExNjE1NiwiaXNzIjoiaHR0cDovL29wZW5hcGlfb2F1dGgtc2VydmVyIiwiYXVkIjpbImh0dHA6Ly9vcGVuYXBpX29hdXRoLXNlcnZlci9yZXNvdXJjZXMiLCJDbkJsb2dzQXBpIl0sImNsaWVudF9pZCI6IjhhYjI0MzY3LTkxZDYtNGMxOS05ODQ2LTEyMTkwOWEwZTAxZiIsInNjb3BlIjpbIkNuQmxvZ3NBcGkiXX0.aK_OwsmT2VjNNmFxlR1xyEmRfIr3CsWWIoVLsveKnaljO3rcWdN3vgf-w4gL6Bw9MOkVr3M8KsLKTChul07PQNTp6nQe3ogsjTjKjAuBt8uvvg4jhYhu5a8CdIAlfyKKJuVKv9tzfEzXIP5hikZESAOuQd12zhzLd42rInS9N6PZ5MquWBkXYyC4eEQvTEFRhM4qVbV_aZ75zyRADIr7LzaZir0pIH0LUy3wQK2Didwm75e9XjCjZTg4W-q5WRD2fWRa8Cvp9DS9_A0Cfs7qvD1aF-Iuv7Eee8gtdGWevco3tY9oyebxmi2KlK6asbaSbegIYa6V5hVFxfIG0aEmNg")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
