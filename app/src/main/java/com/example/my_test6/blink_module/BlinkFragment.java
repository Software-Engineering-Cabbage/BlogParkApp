package com.example.my_test6.blink_module;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.my_test6.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class BlinkFragment extends Fragment {
    static final int NUM_ITEMS = 4;
    private String[] strings = new String[]{"推荐", "关注", "我的", "发布"};
    public static final String TAG = "BlinkFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.blink_fragment_home, container, false);
        ArrayList fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new RecommendFragment());
        fragmentList.add(new AttentionFragment());
        fragmentList.add(new MineFragment());
        fragmentList.add(new DeliverFragment());
        TabLayout tab_layout = root.findViewById(R.id.tab_layout);
        ViewPager viewPager = root.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(7);
        MyAdapter fragmentAdater = new MyAdapter(getChildFragmentManager(),fragmentList); //    注意使用getChildFragmentManager()
        viewPager.setAdapter(fragmentAdater);
        tab_layout.setupWithViewPager(viewPager);
        Log.d(TAG, "onCreateView: ");
        return root;
    }


    public class MyAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> viewLists;
        public MyAdapter(FragmentManager fm,ArrayList<Fragment> list) {
            super(fm);
            viewLists = list;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return viewLists.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }

    }

}

