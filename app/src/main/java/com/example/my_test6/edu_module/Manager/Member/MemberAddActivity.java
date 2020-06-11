package com.example.my_test6.edu_module.Manager.Member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.my_test6.Pool.netWork.PostUserApi;
import com.example.my_test6.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MemberAddActivity extends AppCompatActivity {
    private final MediaType mediaType
            = MediaType.parse("application/json; charset=utf-8");
    private final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edu_activity_member_add);
        setTitle("添加班级成员");
        Spinner spinner=findViewById(R.id.EduRoleSpinner);
        final int id=getIntent().getIntExtra("id",0);
        System.out.println("MemberAdd:"+id);
        ArrayList<String> rolelist=new ArrayList<>();
        rolelist.add("学生");
        rolelist.add("老师");
        rolelist.add("助教");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,rolelist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final int[] pos = {1};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos[0] =position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        @SuppressLint("HandlerLeak")final Handler handler=new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String text = (String) msg.obj;
                Gson gson=new Gson();
                ReturnMessage message=gson.fromJson(text,ReturnMessage.class);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("提示");
                if(message.getSuccess().equals("true")){
                    builder.setMessage("添加成功");
                }
                else{
                    builder.setMessage(message.getMessage());
                }
                builder.setPositiveButton("确定",null);
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        };
        final EditText displayname=findViewById(R.id.EduNameEdittext);
        final EditText realname=findViewById(R.id.EduRnameEdittext);
        final EditText sno=findViewById(R.id.EduNoEdittext);
        Button button=findViewById(R.id.EduCommitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostUserApi postUserApi=new PostUserApi();
                String bodyString="{schoolClassId:"+id+",displayName:\""+displayname.getText()+"\",realName:\""+realname.getText()+"\",role:"+pos[0]+",studentNo:\""+sno.getText()+"\"}";
                System.out.println(bodyString);
                RequestBody body=RequestBody.create(bodyString,mediaType);
                postUserApi.postMyApi(handler,"https://api.cnblogs.com/api/edu/member/register/displayName",body,0);
            }
        });
    }
}
