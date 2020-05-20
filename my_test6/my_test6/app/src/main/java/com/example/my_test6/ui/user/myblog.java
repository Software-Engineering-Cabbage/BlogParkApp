package com.example.my_test6.ui.user;

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

import com.bumptech.glide.Glide;
import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.R;
import com.example.my_test6.netWork.GetUserApi;
import com.example.my_test6.ui.user.GsonBean.MyBlogList;
import com.example.my_test6.ui.user.GsonBean.MyBlogs;
import com.example.my_test6.ui.user.GsonBean.Users;
import com.example.my_test6.ui.user.ItemBean.ItemMyBlog;
import com.example.my_test6.ui.user.ListAdapters.MyBlogAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class myblog extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyBlogAdapter mAdapter;
    private ArrayList<ItemMyBlog> mdata = new ArrayList<>();
    private MyBlogs myBlogs;
    private Users users;
    private List<MyBlogList> datalist;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x1){//设置列表
                String json = (String) msg.obj;
                if(!json.equals("[]")) {
                    Gson gson = new Gson();
                    datalist = gson.fromJson(json, new TypeToken<List<MyBlogList>>() {
                    }.getType());
                    for (int i = 0; i < datalist.size(); i++) {
                        MyBlogList myBlogList = datalist.get(i);
                        ItemMyBlog iblog = new ItemMyBlog();
                        iblog.title = myBlogList.Title;
                        iblog.time = myBlogList.PostDate;
                        iblog.time = iblog.time.replaceAll("T"," ");
                        iblog.time = iblog.time.substring(0,iblog.time.length()-3);
                        iblog.url = myBlogList.Url;
                        iblog.author = myBlogList.Author;
                        iblog.Abstract = myBlogList.Description;
                        iblog.comment = "评论：" + myBlogList.CommentCount;
                        iblog.head = myBlogList.Avatar;
                        mdata.add(iblog);
                    }
                    mAdapter = new MyBlogAdapter(mdata);
                    recyclerView.setAdapter(mAdapter);
                    initListener();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_myblog);
        setTitle("我的博客");
        users = MinePool.getMinePool().users;
        myBlogs = MinePool.getMinePool().myblogs;
        recyclerView = findViewById(R.id.MyBlogRecyclerView);
        initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new MyBlogAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //处理点击事件
                Intent intent = new Intent();
                ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.ui.user.Item_web");
                intent.setComponent(componentname);
                ItemMyBlog h = mdata.get(position);
                intent.putExtra("url",h.url);
                intent.putExtra("title",h.title);
                startActivity(intent);
            }
        });
    }

    private void initData(){
        int pindex = myBlogs.postCount/myBlogs.pageSize + 1;
        for(int i= 1; i<=pindex; i++) {
            GetUserApi api = new GetUserApi();
            String url = "https://api.cnblogs.com/api/blogs/" + users.BlogApp + "/posts?pageIndex=" + i;
            api.getMyApi(handler, url, 1);
        }
    }
}
