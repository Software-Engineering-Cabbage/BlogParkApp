package com.example.my_test6.ui.edu;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.my_test6.Bean.BlogSummary;
import com.example.my_test6.R;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eduViewModel =
                ViewModelProviders.of(this).get(EduViewModel.class);
        View root = inflater.inflate(R.layout.user_fragment_edu, container, false);
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
                    default:
                        break;
                }
            }
        };
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                test_get(handler);
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
                    Log.d("TAG",result);
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
                .addHeader("Authorization","Bearer "+"eyJhbGciOiJSUzI1NiIsImtpZCI6IjlFMjcyMkFGM0IzRTFDNzU5RTI3NEFBRDI5NDFBNzg1MDlCMDc2RDAiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJuaWNpcnpzLUhIV2VKMHF0S1VHbmhRbXdkdEEifQ.eyJuYmYiOjE1ODcxMzEyNzYsImV4cCI6MTU4NzIxNzY3NiwiaXNzIjoiaHR0cDovL29wZW5hcGlfb2F1dGgtc2VydmVyIiwiYXVkIjpbImh0dHA6Ly9vcGVuYXBpX29hdXRoLXNlcnZlci9yZXNvdXJjZXMiLCJDbkJsb2dzQXBpIl0sImNsaWVudF9pZCI6IjhhYjI0MzY3LTkxZDYtNGMxOS05ODQ2LTEyMTkwOWEwZTAxZiIsInNjb3BlIjpbIkNuQmxvZ3NBcGkiXX0.LmJyWU-iwpE-Px0W8yrYbaOWuXq8nDEepKM0738CUNnmcJLsRIU3d9XkWA63mKo3OBD8AJadRQb6DD7fEkMuG6Ryx-T-9nOqzg915nkp0yBN0aE-6dazE6CR2vW8M6y_692ceFsGprQOb0ufPcJ0Io2fEvdW90EgMBwhEt62zTXHEzg04V1dLDuqcMQFA5pzNnPhmda4JmlhYHkUEZsDw2Ki8YBEaS2qyn6HXD_-q8zePj8yCGz83ErSJM5m-Nn3j7Pu8L4igcOk9KTiSBfDc_CbwYfgvi1Zf6cJSiMeD-m6vhyn_7gMpGPTtyXoMOkXPt3Dqix54aa7D7NiIKk0GQ")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
