package com.example.my_test6.edu_module.Manager.Member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.my_test6.R;
import com.example.my_test6.edu_module.Class2Activity;
import com.example.my_test6.edu_module.Member.ClassMember;
import com.example.my_test6.edu_module.Member.Member;
import com.example.my_test6.edu_module.myItemTouchHelperCallBack;
import com.example.my_test6.netWork.GetUserApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MemberDeleteActivity extends AppCompatActivity {
    private List<ClassMember> classMemberList;
    private RecyclerView recyclerView;
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_delete);
        setTitle("修改/删除班级成员");
        final int id=getIntent().getIntExtra("id",0);
        @SuppressLint("HandlerLeak")final Handler handler=new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String text = (String) msg.obj;
                Gson gson=new Gson();
                classMemberList=gson.fromJson(text, new TypeToken<List<ClassMember>>(){}.getType());
                if(classMemberList!=null){
                    List<Member> memberList=new ArrayList<>();
                    for(int i=0;i<classMemberList.size();i++){
                        String avater=classMemberList.get(i).getAvatarUrl();
                        String name=classMemberList.get(i).getDisplayName()+"("+classMemberList.get(i).getRealName()+")";
                        Member member=new Member(avater,name);
                        memberList.add(member);
                    }
                    MemberAdapter adapter=new MemberAdapter(memberList,id,classMemberList);
                    recyclerView.setAdapter(adapter);
                    ItemTouchHelper.Callback callback=new myItemTouchHelperCallBack(adapter,context,"确定删除该成员?");
                    ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                    adapter.setOnItemClickListener(new MemberAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {
                            Intent intent=new Intent();
                            ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.edu_module.Manager.Member.MemberModifyActivity");
                            intent.setComponent(componentname);
                            intent.putExtra("memberid",classMemberList.get(position).getMemberId());
                            intent.putExtra("schoolclassid",id);
                            intent.putExtra("displayname",classMemberList.get(position).getDisplayName());
                            intent.putExtra("studentno",classMemberList.get(position).getStudentNo());
                            intent.putExtra("realname",classMemberList.get(position).getRealName());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        recyclerView=findViewById(R.id.EduMemberRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GetUserApi getApi=new GetUserApi();
        getApi.getMyApi(handler,"https://api.cnblogs.com/api/edu/schoolclass/members/"+id,0);
    }
    public void onResume() {
        super.onResume();
        setTitle("修改/删除班级成员");
        final int id=getIntent().getIntExtra("id",0);
        @SuppressLint("HandlerLeak")final Handler handler=new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String text = (String) msg.obj;
                Gson gson=new Gson();
                classMemberList=gson.fromJson(text, new TypeToken<List<ClassMember>>(){}.getType());
                if(classMemberList!=null){
                    List<Member> memberList=new ArrayList<>();
                    for(int i=0;i<classMemberList.size();i++){
                        String avater=classMemberList.get(i).getAvatarUrl();
                        String name=classMemberList.get(i).getDisplayName()+"("+classMemberList.get(i).getRealName()+")";
                        Member member=new Member(avater,name);
                        memberList.add(member);
                    }
                    MemberAdapter adapter=new MemberAdapter(memberList,id,classMemberList);
                    recyclerView.setAdapter(adapter);
                    ItemTouchHelper.Callback callback=new myItemTouchHelperCallBack(adapter,context,"确定删除该成员?");
                    ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                    adapter.setOnItemClickListener(new MemberAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {
                            Intent intent=new Intent();
                            ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.edu_module.Manager.Member.MemberModifyActivity");
                            intent.setComponent(componentname);
                            intent.putExtra("memberid",classMemberList.get(position).getMemberId());
                            intent.putExtra("schoolclassid",id);
                            intent.putExtra("displayname",classMemberList.get(position).getDisplayName());
                            intent.putExtra("studentno",classMemberList.get(position).getStudentNo());
                            intent.putExtra("realname",classMemberList.get(position).getRealName());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        recyclerView=findViewById(R.id.EduMemberRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GetUserApi getApi=new GetUserApi();
        getApi.getMyApi(handler,"https://api.cnblogs.com/api/edu/schoolclass/members/"+id,0);
    }
}
