package com.example.my_test6.user_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.example.my_test6.R;
import com.example.my_test6.question_module.QuestionFragment;
import com.example.my_test6.question_module.wode_fragment;
import com.example.my_test6.user_module.ItemBean.ItemBrowseHistory;
import com.example.my_test6.user_module.ListAdapters.MyBrowseHistoryAdapter;

import java.util.ArrayList;

public class browse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_browse);
        setTitle("我的博问");
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new wode_fragment();
        fm.beginTransaction().replace(R.id.user_Myquestion,fragment).commit();
    }
}
