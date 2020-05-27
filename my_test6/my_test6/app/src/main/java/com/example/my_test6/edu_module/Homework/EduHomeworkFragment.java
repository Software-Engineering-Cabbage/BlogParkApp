package com.example.my_test6.edu_module.Homework;

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
import com.example.my_test6.edu_module.Notice.ClassNotice;
import com.example.my_test6.edu_module.Notice.NoticeAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class EduHomeworkFragment extends Fragment {
    private int id;
    private View root;
    private ClassHomework homework;
    private int count;
    private int pagenumber;
    private List<Homeworks> homeworksList;
    private List<ClassNotice> classHomeworkList;
    final GetUserApi getApi=new GetUserApi();
    public EduHomeworkFragment(int id){
        this.id=id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.edu_fragment_edu_homework, container, false);
        classHomeworkList=new ArrayList<>();
        @SuppressLint("HandlerLeak")final Handler handler=new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String text=(String)msg.obj;
                TextView textView=root.findViewById(R.id.emptyHomework);
                Gson gson=new Gson();
                homework=gson.fromJson(text,ClassHomework.class);
                if(homework!=null){
                    textView.setVisibility(View.INVISIBLE);
                    final ListView listView=root.findViewById(R.id.homework);
                    count=homework.getTotalCount();
                    homeworksList=homework.getHomeworks();
                    int tempcount=100;
                    if(count<100){
                        tempcount=count;
                    }
                    for(int i=0;i<tempcount;i++){
                        String title=homeworksList.get(i).getTitle();
                        String date=homeworksList.get(i).getDisplayTime().split("T")[0];
                        ClassNotice classHomework=new ClassNotice(title,date);
                        classHomeworkList.add(classHomework);
                    }
                    pagenumber=count/100;
                    final int extranumber=count%100;
                    final int[] temp = {0};
                    @SuppressLint("HandlerLeak")final Handler handler1=new Handler() {
                        @Override
                        public void handleMessage(@NonNull Message msg){
                            super.handleMessage(msg);
                            String text=(String)msg.obj;
                            Gson gson=new Gson();
                            homework=gson.fromJson(text,ClassHomework.class);
                            if(homework!=null){
                                if(temp[0]<pagenumber-1){
                                    for(int i=0;i<100;i++){
                                        String title=homeworksList.get(i).getTitle();
                                        String date=homeworksList.get(i).getDisplayTime().split("T")[0];
                                        ClassNotice classHomework=new ClassNotice(title,date);
                                        classHomeworkList.add(classHomework);
                                    }
                                }
                                else{
                                    for(int i=0;i<extranumber;i++){
                                        String title=homeworksList.get(i).getTitle();
                                        String date=homeworksList.get(i).getDisplayTime().split("T")[0];
                                        ClassNotice classHomework=new ClassNotice(title,date);
                                        classHomeworkList.add(classHomework);
                                    }
                                }

                            }
                            temp[0]++;
                            NoticeAdapter homeworkAdapter=new NoticeAdapter(getActivity(), R.layout.edu_class_notice, classHomeworkList);
                            listView.setAdapter(homeworkAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent=new Intent(getActivity(),new Class2Activity().getClass());
                                    intent.putExtra("web",homeworksList.get(position).getUrl());
                                    intent.putExtra("type",1);
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    if(pagenumber>0){
                        for(int i=0;i<pagenumber;i++){
                            getApi.getMyApi(handler1,"https://api.cnblogs.com/api/edu/schoolclass/homeworks/true/"+id+"/"+i+2+"-100",0);
                        }
                    }
                    else{
                        NoticeAdapter homeworkAdapter=new NoticeAdapter(getActivity(), R.layout.edu_class_notice, classHomeworkList);
                        listView.setAdapter(homeworkAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent=new Intent(getActivity(),new Class2Activity().getClass());
                                intent.putExtra("web",homeworksList.get(position).getUrl());
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        };
        getApi.getMyApi(handler,"https://api.cnblogs.com/api/edu/schoolclass/homeworks/true/"+id+"/1-100",0);
        return root;
    }
}
