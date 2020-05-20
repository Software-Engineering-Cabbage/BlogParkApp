package com.example.my_test6.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.example.my_test6.R;
import com.example.my_test6.ui.user.ItemBean.ItemBrowseHistory;
import com.example.my_test6.ui.user.ListAdapters.MyBrowseHistoryAdapter;

import java.util.ArrayList;

public class browse extends AppCompatActivity {
    private RecyclerView myBrowseHistoryList;
    private ArrayList<ItemBrowseHistory> data;
    private MyBrowseHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_browse);
        myBrowseHistoryList = findViewById(R.id.MyBrowseHistoryRecyclerView);
        setTitle("浏览记录");
        initData();
        initListener();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new MyBrowseHistoryAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //处理点击事件
                Intent intent = new Intent();
                ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.ui.user.Item_web");
                intent.setComponent(componentname);
                ItemBrowseHistory h = data.get(position);
                intent.putExtra("url",h.url);
                intent.putExtra("title",h.title);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        data = new ArrayList<>();

        for(int i = 0;i <= 15; i++){
            ItemBrowseHistory item = new ItemBrowseHistory();
            item.title = "Scrum Meeting " + i;
            item.Abstract = "这些都是模拟数据";
            item.author = "软工小白菜 ";
            item.comment = "评论：" + i;
            item.time = "2020年4月19日";
            if(i == 0)
                item.url = "https://www.cnblogs.com/team-pakchoi/p/12679258.html";
            else
                item.url = "https://www.cnblogs.com/team-pakchoi/p/12679181.html";
            data.add(item);
        }
        //设置RecyclerView样式(布局管理器)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myBrowseHistoryList.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new MyBrowseHistoryAdapter(data);
        //设置到RecyclerView中
        myBrowseHistoryList.setAdapter(adapter);
    }
}
