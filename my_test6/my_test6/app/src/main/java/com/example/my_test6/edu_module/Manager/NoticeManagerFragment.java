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

import java.util.ArrayList;

public class NoticeManagerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

            }
        });
        return root;
    }
}
