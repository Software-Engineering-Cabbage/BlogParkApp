package com.example.my_test6.edu_module.Blog;

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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class EduBlogFragment extends Fragment {
    private int id;
    private View root;
    private int count=0;
    private int pagenumber=0;
    private List<BlogPosts> blogPostsList;
    private Blogs blogs;
    private GetUserApi getApi=new GetUserApi();
    private List<ClassBlog> classBlogList=new ArrayList<>();
    public EduBlogFragment(int id){
        this.id=id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.edu_fragment_edu_blog, container, false);
        @SuppressLint("HandlerLeak")final Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                String text=(String)msg.obj;
                final ListView listView=root.findViewById(R.id.blogList);
                TextView textView=root.findViewById(R.id.emptyBlog);
                Gson gson=new Gson();
                blogs=gson.fromJson(text,Blogs.class);
                if(blogs!=null) {
                    count = blogs.getTotalCount();
                    blogPostsList = blogs.getBlogPosts();
                    textView.setVisibility(View.INVISIBLE);
                    int tempcount=100;
                    if(count<100){
                        tempcount=count;
                    }
                    for(int i=0;i<tempcount;i++){
                        String avater=blogPostsList.get(i).getAvatarUrl();
                        String name=blogPostsList.get(i).getAuthor();
                        String title=blogPostsList.get(i).getTitle();
                        String date=blogPostsList.get(i).getDateAdded();
                        ClassBlog classBlog=new ClassBlog(avater,name,title,date);
                        classBlogList.add(classBlog);
                    }
                    pagenumber=count/100;
                    final int extranumber=count%100;
                    final int[] temp = {0};
                    @SuppressLint("HandlerLeak")final Handler handler1=new Handler(){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            String text=(String)msg.obj;
                            Gson gson=new Gson();
                            blogs=gson.fromJson(text,Blogs.class);
                            if(blogs!=null){
                                if(temp[0]<pagenumber-1) {
                                    for (int i = 0; i < 100; i++) {
                                        String avater = blogPostsList.get(i).getAvatarUrl();
                                        String name = blogPostsList.get(i).getAuthor();
                                        String title = blogPostsList.get(i).getTitle();
                                        String date = blogPostsList.get(i).getDateAdded();
                                        ClassBlog classBlog = new ClassBlog(avater, name, title, date);
                                        classBlogList.add(classBlog);
                                    }
                                }
                                else{
                                    for (int i = 0; i < extranumber; i++) {
                                        String avater = blogPostsList.get(i).getAvatarUrl();
                                        String name = blogPostsList.get(i).getAuthor();
                                        String title = blogPostsList.get(i).getTitle();
                                        String date = blogPostsList.get(i).getDateAdded();
                                        ClassBlog classBlog = new ClassBlog(avater, name, title, date);
                                        classBlogList.add(classBlog);
                                    }
                                }
                            }
                            temp[0]++;
                            BlogAdapter blogAdapter=new BlogAdapter(getActivity(),R.layout.edu_class_blog,classBlogList);
                            listView.setAdapter(blogAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent=new Intent(getActivity(),new Class2Activity().getClass());
                                    intent.putExtra("web",blogPostsList.get(position).getUrl());
                                    intent.putExtra("type",2);
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    if(pagenumber>0) {
                        for(int i=0;i<pagenumber;i++){
                            getApi.getMyApi(handler1,"https://api.cnblogs.com/api/edu/schoolclass/posts/all/"+id+"/"+i+2+"-100",0);
                        }
                    }
                    else{
                        BlogAdapter blogAdapter=new BlogAdapter(getActivity(),R.layout.edu_class_blog,classBlogList);
                        listView.setAdapter(blogAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent=new Intent(getActivity(),new Class2Activity().getClass());
                                intent.putExtra("web",blogPostsList.get(position).getUrl());
                                intent.putExtra("type",2);
                                startActivity(intent);
                            }
                        });
                    }
                }

            }
        };
        getApi.getMyApi(handler,"https://api.cnblogs.com/api/edu/schoolclass/posts/all/"+id+"/1-100",0);
        return root;
    }
}
