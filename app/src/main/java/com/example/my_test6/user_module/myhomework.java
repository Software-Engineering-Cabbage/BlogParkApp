package com.example.my_test6.user_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.my_test6.R;
import com.example.my_test6.user_module.PagerAdapters.myFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class myhomework extends AppCompatActivity {
    private ViewPager viewpager;
    private ArrayList viewlist;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_myhomework);
        getSupportActionBar().hide();
        //setTitle("我的作业");
        viewpager = findViewById(R.id.testPager);
        tab = findViewById(R.id.homeworkTabs);
        viewlist = new ArrayList<Fragment>();
        viewlist.add(new NotFinishHomeworkFragment());
        viewlist.add(new FinishHomeworkFragment());
        FragmentManager fm=getSupportFragmentManager();
        myFragmentPagerAdapter mfpa=new myFragmentPagerAdapter(fm, viewlist);
        viewpager.setAdapter(mfpa);
        viewpager.setCurrentItem(0);
        tab.setupWithViewPager(viewpager);
        tab.getTabAt(0).setText("未截止");
        tab.getTabAt(1).setText("已截止");
    }
}
