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

import com.example.my_test6.Pool.netWork.PatchApi;
import com.example.my_test6.R;
import com.example.my_test6.edu_module.Manager.Member.ReturnMessage;
import com.example.my_test6.edu_module.Manager.Pool;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NoticeModifyActivity extends AppCompatActivity {
    private Context context=this;
    private final MediaType mediaType
            = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_modify);
        setTitle("修改公告");
        final int id=getIntent().getIntExtra("schoolid",0);
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
                    builder.setMessage("修改成功");
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
        final EditText content=findViewById(R.id.EduModifyNoticeeditText);
        Button button=findViewById(R.id.EduModifyNoticebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatchApi patchApi=new PatchApi();
                String bodyString="{schoolClassId:"+id+",content:\""+content.getText()+"\"}";
                System.out.println(bodyString);
                RequestBody body=RequestBody.create(bodyString,mediaType);
                patchApi.patch(handler,"https://api.cnblogs.com/api/edu/bulletin/modify/"+ Pool.getMinePool().bulletinid,body,0);
            }
        });
    }
}
