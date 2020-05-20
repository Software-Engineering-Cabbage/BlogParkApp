package com.example.my_test6.user_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.my_test6.R;
import com.example.my_test6.blink_module.BlinkFragment;
import com.example.my_test6.blink_module.MineFragment;

public class flash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_flash);
        setTitle("我的动态");
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new MineFragment();
        fm.beginTransaction().replace(R.id.user_Mypyq,fragment).commit();
    }
}
