package com.example.my_test6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.Pool.netWork.GetToken;
import com.example.my_test6.user_module.GsonBean.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private static  final int Post_Blog_1 = 0x002;
    private GetToken tokenMy= new GetToken();
    private SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getApplication().getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean isLogin = sharedpreferences.getBoolean("isLogin",false);
        TokenPool.getTokenPool().isLogin = isLogin;
        System.out.println(isLogin);
        if(isLogin){
            TokenPool.getTokenPool().UserToken = sharedpreferences.getString("UserToken","");
            Gson gson = new Gson();
            String json = sharedpreferences.getString("Users","[]");
            System.out.println(json);
            MinePool.getMinePool().users = gson.fromJson(json,Users.class);
        }
        getToken();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

    //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_blink, R.id.navigation_question,R.id.navigation_edu,R.id.navigation_user)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }
    private void getToken(){
        tokenMy.getMyToken();
    }

}
