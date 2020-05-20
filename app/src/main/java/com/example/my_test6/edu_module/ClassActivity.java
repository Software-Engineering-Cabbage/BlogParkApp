package com.example.my_test6.edu_module;

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

public class ClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edu_activity_class);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        setTitle(name);
        int id=Integer.parseInt(intent.getStringExtra("id"));
        String url=intent.getStringExtra("url");
        ViewPager viewPager=findViewById(R.id.EduviewPager);
        TabLayout tabLayout=findViewById(R.id.EdutabLayout);
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

    }
}
