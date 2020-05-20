package com.example.my_test6.edu_module.Manager.Member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.my_test6.R;
import com.example.my_test6.netWork.PostUserApi;
import com.example.my_test6.ui.user.ListAdapters.MyBlogAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MemberAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add);
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
                System.out.println("进入！！！");
                System.out.println(text);
            }
        };
        final EditText displayname=findViewById(R.id.EduNameEdittext);
        final EditText realname=findViewById(R.id.EduRnameEdittext);
        final EditText sno=findViewById(R.id.EduNoEdittext);
        Button button=findViewById(R.id.EduCommitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" id:"+id+" displayName:"+displayname.getText().toString()+" realName:"+realname.getText().toString()+" role:"+Integer.toString(pos[0])+" studentNo:"+sno.getText().toString());
                PostUserApi postUserApi=new PostUserApi();
                RequestBody body=new FormBody.Builder()
                                .add("schoolClassId", String.valueOf(id))
                                .add("displayName",displayname.getText().toString())
                                .add("realName",realname.getText().toString())
                                .add("role", String.valueOf(pos[0]))
                                .add("studentNo",sno.getText().toString())
                                .build();
                postUserApi.postMyApi(handler,"https://api.cnblogs.com/api/edu/member/register/displayName",body,0);
            }
        });
    }
}
