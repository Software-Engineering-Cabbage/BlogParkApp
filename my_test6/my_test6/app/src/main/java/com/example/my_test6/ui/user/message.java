package com.example.my_test6.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.my_test6.R;
import com.example.my_test6.ui.user.PagerAdapters.myFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class message extends AppCompatActivity {
    private ViewPager viewpager;
    private ArrayList viewlist;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_message);
        setTitle("消息中心");
        viewpager = findViewById(R.id.messagePager);
        tab = findViewById(R.id.messageTabs);
        viewlist = new ArrayList<Fragment>();
        viewlist.add(new SystemMessageFragment());
        viewlist.add(new InboxFragment());
        viewlist.add(new UnreadMessageFragment());
        FragmentManager fm=getSupportFragmentManager();
        myFragmentPagerAdapter mfpa=new myFragmentPagerAdapter(fm, viewlist);
        viewpager.setAdapter(mfpa);
        viewpager.setCurrentItem(0);
        tab.setupWithViewPager(viewpager);
        tab.getTabAt(0).setText("系统消息");
        tab.getTabAt(1).setText("收件箱");
        tab.getTabAt(2).setText("未读消息");
    }
}
