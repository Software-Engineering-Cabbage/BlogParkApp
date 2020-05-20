package com.example.my_test6.ui.user;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.my_test6.R;
import com.example.my_test6.netWork.GetApi;
import com.example.my_test6.netWork.GetUserApi;
import com.example.my_test6.ui.user.GsonBean.MyClassList;
import com.example.my_test6.ui.user.GsonBean.MyHomeworkAll;
import com.example.my_test6.ui.user.GsonBean.MyHomeworks;
import com.example.my_test6.ui.user.ItemBean.ItemHomework;
import com.example.my_test6.ui.user.ListAdapters.HomeworkAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class NotFinishHomeworkFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ItemHomework> mdata = new ArrayList<>();
    private HomeworkAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.user_fragment_notfinishhomework, container, false);
        recyclerView = root.findViewById(R.id.FragementRecyclerview2);
        initData();
        return root;
    }

    private void initData() {
        mdata = new ArrayList<>();
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 1){
                    Gson gson = new Gson();
                    String json = (String) msg.obj;
                    List<MyClassList> datalist = gson.fromJson(json, new TypeToken<List<MyClassList>>(){}.getType());
                    int len = datalist.size();
                    for(int i = 0;i<len;i++){
                        MyClassList myclass = datalist.get(i);
                        final Handler handler1 = new Handler(){
                            @Override
                            public void handleMessage(@NonNull Message msg) {//没有考虑如果超过了100个作业的情况，beta阶段需要完善
                                if(msg.what == 1){
                                    Gson gson = new Gson();
                                    String json = (String) msg.obj;
                                    MyHomeworkAll homeworkAll = gson.fromJson(json,MyHomeworkAll.class);
                                    List<MyHomeworks> homeworklist = homeworkAll.homeworks;
                                    int len = homeworklist.size();
                                    for(int i = 0;i<len;i++){
                                        MyHomeworks homework = homeworklist.get(i);
                                        if(homework.isFinished) {
                                            continue;
                                        }
                                        ItemHomework itemHomework = new ItemHomework();
                                        itemHomework.title = homework.title;
                                        itemHomework.url = "https://edu.cnblogs.com" + homework.url;
                                        itemHomework.Abstract = homework.description;
                                        itemHomework.author = homework.displayName;
                                        itemHomework.avatarUrl = "https:" + homework.avatarUrl;
                                        itemHomework.time = homework.startTime.substring(0,homework.startTime.length()-3)
                                                + "~" + homework.deadline.substring(0,homework.deadline.length()-3);
                                        itemHomework.time = itemHomework.time.replaceAll("T"," ");
                                        itemHomework.comment = "提交：" + homework.answerCount;
                                        itemHomework.src = 0;
                                        mdata.add(itemHomework);
                                    }
                                    mAdapter = new HomeworkAdapter(mdata);
                                    //设置到RecyclerView中
                                    recyclerView.setAdapter(mAdapter);
                                    initListener();
                                }
                            }
                        };
                        GetUserApi api = new GetUserApi();
                        String url = "https://api.cnblogs.com/api/edu/schoolclass/homeworks/false/" + myclass.schoolClassId + "/1-100";
                        api.getMyApi(handler1, url, 1);
                    }
                }
            }
        };
        GetUserApi api = new GetUserApi();
        String url = "https://api.cnblogs.com/api/edu/member/schoolclasses";
        api.getMyApi(handler, url, 1);
        //设置RecyclerView样式(布局管理器)
        //创建适配器
        //设置到RecyclerView中
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new HomeworkAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //处理点击事件
                Intent intent = new Intent();
                ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.ui.user.Item_web");
                intent.setComponent(componentname);
                ItemHomework h = mdata.get(position);
                intent.putExtra("url",h.url);
                intent.putExtra("title",h.title);
                startActivity(intent);
            }
        });
    }
}
