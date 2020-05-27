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
import com.example.my_test6.edu_module.ClassActivity;
import com.example.my_test6.edu_module.Manager.Notice.NoticeAddActivity;
import com.example.my_test6.edu_module.Manager.Notice.NoticeModifyActivity;

import java.util.ArrayList;

public class NoticeManagerFragment extends Fragment {
    private int schoolid;
    NoticeManagerFragment(int id){
        this.schoolid=id;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.edu_fragment_notice_manager, container, false);
        ListView listView=root.findViewById(R.id.EduManagerNoticeListview);
        ArrayList<String> ctype=new ArrayList<>();
        ctype.add("发布公告");
        ctype.add("修改公告");
        ctype.add("删除公告");
        ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,ctype);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent=new Intent(getActivity(),new NoticeAddActivity().getClass());
                    intent.putExtra("schoolid",schoolid);
                    startActivity(intent);
                }
                if(position==1){
                    Intent intent=new Intent(getActivity(),new NoticeModifyActivity().getClass());
                    intent.putExtra("schoolid",id);
                    startActivity(intent);
                }
            }
        });
        return root;
    }
}
