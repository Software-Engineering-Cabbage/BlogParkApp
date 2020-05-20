package com.example.my_test6.blink_module;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.PostUserApi;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class DeliverLoginFragment extends Fragment implements View.OnClickListener{

    private Button deliver;
    private TextView edit_text;
    private CheckBox deliver_private;
    private static int DELIVER = 1;
    public DeliverLoginFragment() {
        // Required empty public constructor
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.blink_fragment_deliver_login, container, false);
        deliver = view.findViewById(R.id.deliver);
        edit_text = view.findViewById(R.id.edit_text);
        deliver_private = view.findViewById(R.id.deliver_private);
        deliver.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deliver:
                PostUserApi postUserApi = new PostUserApi();
                String url = "https://api.cnblogs.com/api/statuses ";
                RequestBody body = getDeliverBody();
                postUserApi.postMyApi(handler,url,body,DELIVER);
                Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                edit_text.setText("");
                break;
        }
    }

    private RequestBody getDeliverBody(){
        String isPrivate;
        if (deliver_private.isChecked()){
            isPrivate = "true";
        }else{
            isPrivate = "false";
        }
        RequestBody body = new FormBody.Builder()
                .add("Content",edit_text.getText().toString())
                .add("IsPrivate",isPrivate)
                .build();
        return body;
    }

}
