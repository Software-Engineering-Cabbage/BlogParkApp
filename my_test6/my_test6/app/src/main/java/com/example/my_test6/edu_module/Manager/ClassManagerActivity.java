package com.example.my_test6.edu_module.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.my_test6.R;
import com.example.my_test6.edu_module.Blog.EduBlogFragment;
import com.example.my_test6.edu_module.Homework.EduHomeworkFragment;
import com.example.my_test6.edu_module.Member.EduMemberFragment;
import com.example.my_test6.edu_module.Notice.EduNoticeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ClassManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_manager);
        String title1="班级管理";
        setTitle(title1);
        final int id=getIntent().getIntExtra("id",0);
        ViewPager viewPager=findViewById(R.id.EduManagerviewPager);
        TabLayout tabLayout=findViewById(R.id.EduManagertabLayout);
        final List<Fragment> fragments=new ArrayList<>();
        final String[] title=new String[]{"公告","成员"};
        fragments.add(new NoticeManagerFragment(id));
        fragments.add(new MemberManagerFragment(id));
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
    }
}
