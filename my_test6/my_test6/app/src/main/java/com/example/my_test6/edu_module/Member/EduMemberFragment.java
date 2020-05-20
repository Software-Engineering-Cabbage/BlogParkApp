package com.example.my_test6.edu_module.Member;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.my_test6.R;
import com.example.my_test6.netWork.GetUserApi;
import com.example.my_test6.edu_module.Class2Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EduMemberFragment extends Fragment {
    private int id;
    private View root;
    private List<ClassMember> classMemberList;
    public EduMemberFragment(int id){
        this.id=id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.edu_fragment_edu_member, container, false);
        @SuppressLint("HandlerLeak")final Handler handler=new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String text=(String)msg.obj;
                TextView textView=root.findViewById(R.id.emptyMember);
                Gson gson=new Gson();
                classMemberList=gson.fromJson(text, new TypeToken<List<ClassMember>>(){}.getType());
                if(classMemberList!=null){
                    textView.setVisibility(View.INVISIBLE);
                    List<Member> memberList=new ArrayList<>();
                    ListView listView=root.findViewById(R.id.memberList);
                    for(int i=0;i<classMemberList.size();i++){
                        String avater=classMemberList.get(i).getAvatarUrl();
                        String name=classMemberList.get(i).getDisplayName()+"("+classMemberList.get(i).getRealName()+")";
                        Member member=new Member(avater,name);
                        memberList.add(member);
                    }
                    MemberAdapter memberAdapter=new MemberAdapter(getActivity(),R.layout.edu_class_member,memberList);
                    listView.setAdapter(memberAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent(getActivity(),new Class2Activity().getClass());
                            intent.putExtra("web",classMemberList.get(position).getBlogUrl());
                            intent.putExtra("type",3);
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        GetUserApi getApi=new GetUserApi();
        getApi.getMyApi(handler,"https://api.cnblogs.com/api/edu/schoolclass/members/"+id,0);
        return root;
    }
}
