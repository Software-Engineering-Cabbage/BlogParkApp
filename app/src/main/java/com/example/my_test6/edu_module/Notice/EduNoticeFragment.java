package com.example.my_test6.edu_module.Notice;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.my_test6.Pool.netWork.GetUserApi;
import com.example.my_test6.R;
import com.example.my_test6.edu_module.Manager.Pool;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class EduNoticeFragment extends Fragment {
    private int id;
    private int count;
    private NoticeList notice;
    private List<Bulletins> bulletins;
    private List<ClassNotice> classNoticeList;
    private int pagenumber;
    private String url;
    private TextView textView;
    private  View root;
    public EduNoticeFragment(int id,String url){
        this.id=id;
        this.url=url;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.edu_fragment_edu_notice, container, false);
        // Inflate the layout for this fragment
        /*classNoticeList=new ArrayList<>();
        @SuppressLint("HandlerLeak")final Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                String text=(String)msg.obj;
                textView=root.findViewById(R.id.emptyNotice);
                Gson gson=new Gson();
                NoticeList notice=gson.fromJson(text,NoticeList.class);
                if(notice!=null) {
                    count = notice.getTotalCount();
                    bulletins = notice.getBulletins();
                    int tempcount=100;
                    Pool.getMinePool().bulletinid=Integer.parseInt(bulletins.get(0).getBulletinId());
                    if(count<100){
                        tempcount=count;
                    }
                    for (int i = 0; i <tempcount; i++) {
                        String context=bulletins.get(i).getContent();
                        String date=bulletins.get(i).getDateAdded().split("T")[0];
                        ClassNotice classNotice=new ClassNotice(context,date);
                        classNoticeList.add(classNotice);
                    }
                    pagenumber=count/100;
                    final int extranumber=count%100;
                    final int[] temp = {0};
                    @SuppressLint("HandlerLeak")final Handler handler1=new Handler(){
                        @Override
                        public void handleMessage(@NonNull Message msg){
                            super.handleMessage(msg);
                            String text=(String)msg.obj;
                            Gson gson=new Gson();
                            NoticeList notice=gson.fromJson(text,NoticeList.class);
                            if(notice!=null){
                                if(temp[0]<pagenumber-1){
                                    for (int i = 0; i <100; i++) {
                                        String context=bulletins.get(i).getContent();
                                        String date=bulletins.get(i).getDateAdded().split("T")[0];
                                        ClassNotice classNotice=new ClassNotice(context,date);
                                        classNoticeList.add(classNotice);
                                    }
                                }
                                else{
                                    for (int i = 0; i <extranumber; i++) {
                                        String context=bulletins.get(i).getContent();
                                        String date=bulletins.get(i).getDateAdded().split("T")[0];
                                        ClassNotice classNotice=new ClassNotice(context,date);
                                        classNoticeList.add(classNotice);
                                    }
                                }
                            }
                            temp[0]++;
                            textView.setText(classNoticeList.get(0).getContext());
                        }
                    };
                    if(pagenumber>0){
                        for(int i=0;i<pagenumber;i++) {
                            getApi.getMyApi(handler1, "https://api.cnblogs.com/api/edu/schoolclass/bulletins/" + id + "/" +i+2+ "-100", 0);
                        }
                    }
                    else{
                        for(int i=0;i<classNoticeList.size();i++) {
                            System.out.println("createview:" + classNoticeList.get(i).getContext());
                        }
                        //textView.setText(classNoticeList.get(0).getContext());
                    }
                }
            }
        };
        getApi.getMyApi(handler,"https://api.cnblogs.com/api/edu/schoolclass/bulletins/"+id+"/1-100",0);*/
        return root;
    }
    public void onResume() {
        super.onResume();
        classNoticeList=new ArrayList<>();
        @SuppressLint("HandlerLeak")final Handler handler1=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                String text=(String)msg.obj;
                textView=root.findViewById(R.id.emptyNotice);
                if(text!=null){
                    textView.setText(text);
                }
                textView.setVisibility(View.VISIBLE);
            }
        };
        @SuppressLint("HandlerLeak")final Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                String text=(String)msg.obj;
                Gson gson=new Gson();
                NoticeList notice=gson.fromJson(text,NoticeList.class);
                if(notice!=null) {
                    bulletins = notice.getBulletins();
                    Pool.getMinePool().bulletinid=Integer.parseInt(bulletins.get(0).getBulletinId());
                }
            }
        };
        GetUserApi getUserApi=new GetUserApi();
        getUserApi.getMyApi(handler1,"https://api.cnblogs.com/api/edu/bulletin/current/"+id,0);
        GetUserApi getApi=new GetUserApi();
        getApi.getMyApi(handler,"https://api.cnblogs.com/api/edu/schoolclass/bulletins/"+id+"/1-100",0);
    }
}
