package com.example.my_test6.edu_module.Manager.Notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.my_test6.R;
import com.example.my_test6.edu_module.Manager.Member.ReturnMessage;
import com.example.my_test6.edu_module.Manager.Pool;
import com.example.my_test6.netWork.PatchApi;
import com.example.my_test6.netWork.PostUserApi;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NoticeAddActivity extends AppCompatActivity {
    private final MediaType mediaType
            = MediaType.parse("application/json; charset=utf-8");
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_add);
        final int id=getIntent().getIntExtra("schoolid",0);
        setTitle("发布公告");
        @SuppressLint("HandlerLeak")final Handler handler=new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String text = (String) msg.obj;
                System.out.println(text);
                Gson gson=new Gson();
                ReturnMessage message=gson.fromJson(text,ReturnMessage.class);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("提示");
                if(message.getSuccess().equals("true")){
                    builder.setMessage("发布成功");
                }
                else{
                    builder.setMessage(message.getMessage());
                }
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        };
        final EditText content=findViewById(R.id.EduNoticeeditText);
        Button button=findViewById(R.id.EduNoticebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostUserApi postUserApi=new PostUserApi();
                String bodyString="{schoolClassId:"+id+",content:\""+content.getText()+"\"}";
                System.out.println(bodyString);
                RequestBody body=RequestBody.create(bodyString,mediaType);
                postUserApi.postMyApi(handler,"https://api.cnblogs.com/api/edu/bulletin/publish",body,0);
            }
        });
    }
}
