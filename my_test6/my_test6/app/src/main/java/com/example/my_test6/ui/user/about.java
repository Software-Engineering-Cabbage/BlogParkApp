package com.example.my_test6.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;

import com.example.my_test6.R;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_about);
        setTitle("关于");
        Button about_team = findViewById(R.id.about_team);
        about_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentname = new ComponentName("com.example.my_test6","com.example.my_test6.ui.user.web");
                intent.setComponent(componentname);
                startActivity(intent);
            }
        });
    }
}
