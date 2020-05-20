package com.example.my_test6.edu_module;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.R;
import com.example.my_test6.edu_module.Blog.EduBlogFragment;
import com.example.my_test6.edu_module.Homework.EduHomeworkFragment;
import com.example.my_test6.edu_module.Member.ClassMember;
import com.example.my_test6.edu_module.Member.EduMemberFragment;
import com.example.my_test6.edu_module.Notice.EduNoticeFragment;
import com.example.my_test6.netWork.GetApi;
import com.example.my_test6.netWork.GetUserApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {
    private List<ClassMember> classMemberList;
    private FloatingActionButton floatingActionButton;
    private EduMemberInfo memberInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edu_activity_class);
        final Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        setTitle(name);
        final int id=Integer.parseInt(intent.getStringExtra("id"));
        String url=intent.getStringExtra("url");
        ViewPager viewPager=findViewById(R.id.EduviewPager);
        TabLayout tabLayout=findViewById(R.id.EdutabLayout);
        floatingActionButton=findViewById(R.id.EdufloatingActionButton);
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String text = (String) msg.obj;
                Gson gson=new Gson();
                memberInfo=gson.fromJson(text,EduMemberInfo.class);
                if(memberInfo.getMembership().equals("1")){
                    floatingActionButton.setVisibility(View.INVISIBLE);
                    floatingActionButton.setEnabled(false);
                }
                else{
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("id",id);
                            ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.edu_module.Manager.ClassManagerActivity");
                            intent.setComponent(componentname);
                            System.out.println("id是"+id);
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        final List<Fragment> fragments=new ArrayList<>();
        final String[] title=new String[]{"公告","作业","博文","成员"};
        fragments.add(new EduNoticeFragment(id,url));
        fragments.add(new EduHomeworkFragment(id));
        fragments.add(new EduBlogFragment(id));
        fragments.add(new EduMemberFragment(id));
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
            @Override
            public CharSequence getPageTitle(int position){
                return title[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        GetUserApi getUserApi=new GetUserApi();
        getUserApi.getMyApi(handler,"https://api.cnblogs.com/api/edu/member/"+MinePool.getMinePool().users.BlogId+"/"+id,0);
    }
}
