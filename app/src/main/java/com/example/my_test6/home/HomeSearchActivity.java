package com.example.my_test6.home;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_test6.R;

public class HomeSearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        getSupportActionBar().hide();
        SearchView searchView = findViewById(R.id.searchView_home);
      //  searchView.setSubmitButtonEnabled(true);
        ImageButton backButton = findViewById(R.id.imageButton_home_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
