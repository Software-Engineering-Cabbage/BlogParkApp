package com.example.my_test6.edu_module.Manager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.my_test6.R;
import com.example.my_test6.edu_module.Manager.Member.MemberAddActivity;
import com.example.my_test6.edu_module.Manager.Member.MemberDeleteActivity;

import java.util.ArrayList;

public class MemberManagerFragment extends Fragment {
    private int schoolid;
    private View root;
    MemberManagerFragment(int id){
        this.schoolid=id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.edu_fragment_member_manager, container, false);
        ListView listView=root.findViewById(R.id.EduManagerMemberListview);
        ArrayList<String> ctype=new ArrayList<>();
        ctype.add("添加班级成员");
        ctype.add("修改/删除成员信息");
        ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,ctype);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent=new Intent(getActivity(),new MemberAddActivity().getClass());
                    intent.putExtra("id",schoolid);
                    startActivity(intent);
                }
                if(position==1){
                    Intent intent=new Intent(getActivity(),new MemberDeleteActivity().getClass());
                    intent.putExtra("id",schoolid);
                    startActivity(intent);
                }
            }
        });
        return root;
    }
    public void onResume() {
        super.onResume();
        ListView listView=root.findViewById(R.id.EduManagerMemberListview);
        ArrayList<String> ctype=new ArrayList<>();
        ctype.add("添加班级成员");
        ctype.add("修改/删除成员信息");
        ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,ctype);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent=new Intent(getActivity(),new MemberAddActivity().getClass());
                    intent.putExtra("id",schoolid);
                    startActivity(intent);
                }
                if(position==1){
                    Intent intent=new Intent(getActivity(),new MemberDeleteActivity().getClass());
                    intent.putExtra("id",schoolid);
                    startActivity(intent);
                }
            }
        });
    }
}
