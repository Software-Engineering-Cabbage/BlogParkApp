package com.example.my_test6.ui.user;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.my_test6.R;
import com.example.my_test6.ui.user.ItemBean.ItemMessage;
import com.example.my_test6.ui.user.ListAdapters.MessageAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SystemMessageFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ItemMessage> mdata;
    private MessageAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.user_fragment_system_message, container, false);
        recyclerView = root.findViewById(R.id.messageFragementRecyclerview1);
        initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MessageAdapter(mdata);
        recyclerView.setAdapter(mAdapter);
        initListener();
        return root;
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //处理点击事件
                Intent intent = new Intent();
                ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.ui.user.Item_web");
                intent.setComponent(componentname);
                ItemMessage h = mdata.get(position);
                intent.putExtra("url",h.url);
                intent.putExtra("title",h.title);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        mdata = new ArrayList<>();

        for (int i = 0; i <= 1; i++) {
            ItemMessage item = new ItemMessage();
            item.title = "系统通知";
            item.time = "2020年3月20日";
            if (i == 0) {
                item.Abstract = "RE：您的博客申请已批准";
                item.url = "https://msg.cnblogs.com/item/2764718";
            } else {
                item.Abstract = "RE：博客园博客申请通知";
                item.url = "https://msg.cnblogs.com/item/2764685";
            }
            mdata.add(item);
        }
    }
}
