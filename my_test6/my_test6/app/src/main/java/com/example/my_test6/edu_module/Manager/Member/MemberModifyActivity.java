package com.example.my_test6.edu_module.Manager.Member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.my_test6.R;
import com.example.my_test6.netWork.PatchApi;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MemberModifyActivity extends AppCompatActivity {
    private Context context=this;
    private final MediaType mediaType
            = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_modify);
        setTitle("修改成员信息");
        Intent intent=getIntent();
        final int schoolclassid=intent.getIntExtra("schoolclassid",0);
        final String memberid=intent.getStringExtra("memberid");
        final String displayname=intent.getStringExtra("displayname");
        final String studentno=intent.getStringExtra("studentno");
        String realnameString=intent.getStringExtra("realname");
        TextView textView=findViewById(R.id.EduNameModifyTextview2);
        textView.setText(displayname);
        final TextView realname=findViewById(R.id.EduRnameModifyEdittext);
        realname.setText(realnameString);
        Spinner spinner=findViewById(R.id.EduRoleModifySpinner);
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
        Button button=findViewById(R.id.EduModifyCommitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatchApi patchApi=new PatchApi();
                String bodyString="{schoolClassId:"+schoolclassid+",realName:\""+realname.getText()+"\",role:"+pos[0]+",studentNo:\""+studentno+"\"}";
                System.out.println(bodyString+"memberid:"+memberid);
                RequestBody body=RequestBody.create(bodyString,mediaType);
                patchApi.patch(handler,"https://api.cnblogs.com/api/edu/member/modify/"+memberid,body,0);
            }
        });
    }
}
