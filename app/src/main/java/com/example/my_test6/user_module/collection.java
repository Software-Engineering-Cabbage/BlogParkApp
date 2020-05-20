package com.example.my_test6.user_module;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.GetUserApi;
import com.example.my_test6.user_module.GsonBean.MyCollection;
import com.example.my_test6.user_module.ItemBean.ItemCollection;
import com.example.my_test6.user_module.ListAdapters.MyCollectionAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class collection extends AppCompatActivity {
    private RecyclerView myCollectionList;
    private ArrayList<ItemCollection> data = new ArrayList<>();
    private MyCollectionAdapter adapter;
    private List<MyCollection> datalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_collection);
        myCollectionList = findViewById(R.id.CollectionRecyclerView);
        setTitle("我的收藏");
        initData();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new MyCollectionAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //处理点击事件
                Intent intent = new Intent();
                ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.user_module.Item_web");
                intent.setComponent(componentname);
                ItemCollection h = data.get(position);
                intent.putExtra("url",h.url);
                intent.putExtra("title",h.title);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 1){
                    Gson gson = new Gson();
                    String json1 = (String) msg.obj;
                    String json = "";
                    int j = 0;
                    int len = json1.length();
                    for(int i = 0;i < len;i++){
                        if(json1.charAt(i) == '['){
                            if(i == 0){
                                continue;
                            }
                            json = json + json1.substring(j,i) + "\"\"";
                        }
                        else if(json1.charAt(i) == ']'){
                            if(i == len - 1){
                                json = json + json1.substring(j,i + 1);
                            }
                            j = i+1;
                        }
                    }
                    System.out.println("json: " + json);
                    datalist = gson.fromJson(json, new TypeToken<List<MyCollection>>() {
                    }.getType());
                    for (int i = 0; i < datalist.size(); i++) {
                        MyCollection collection = datalist.get(i);
                        ItemCollection icollection = new ItemCollection();
                        icollection.title = collection.Title;
                        icollection.Abstract = collection.Summary;
                        icollection.time = collection.DateAdded;
                        icollection.time = icollection.time.replaceAll("T"," ");
                        icollection.time = icollection.time.substring(0,icollection.time.lastIndexOf(":"));
                        icollection.url = collection.LinkUrl;
                        data.add(icollection);
                    }
                    //后续要设定下拉刷新，否则可能导致超过100个收藏就无法显示
                    adapter = new MyCollectionAdapter(data);
                    //设置到RecyclerView中
                    myCollectionList.setAdapter(adapter);
                    initListener();
                }
            }
        };
        GetUserApi api = new GetUserApi();
        String url = "https://api.cnblogs.com/api/Bookmarks?pageIndex=" + 1 + "&pageSize=" + 100;
        api.getMyApi(handler, url, 1);
        //设置RecyclerView样式(布局管理器)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myCollectionList.setLayoutManager(layoutManager);
    }
}
