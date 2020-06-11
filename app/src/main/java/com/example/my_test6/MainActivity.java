package com.example.my_test6;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.Pool.login;
import com.example.my_test6.Pool.netWork.GetToken;
import com.example.my_test6.Pool.netWork.GetUserApi;
import com.example.my_test6.user_module.GsonBean.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
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
        getToken();
        sharedpreferences = getApplication().getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean isLogin = sharedpreferences.getBoolean("isLogin",false);
        TokenPool.getTokenPool().UserToken = sharedpreferences.getString("UserToken","");
        //boolean isLogin = false;
        System.out.println(isLogin);
        if(isLogin){
            GetUserApi api = new GetUserApi();
            String url = "https://api.cnblogs.com/api/users";
            @SuppressLint("HandlerLeak")
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 0x1) {//收到users信息
                        String Json = (String) msg.obj;
                        if(Json.equals("")){
                            TokenPool.getTokenPool().isLogin = false;
                        }
                        else{
                            TokenPool.getTokenPool().isLogin = true;
                            Gson gson = new Gson();
                            String json = sharedpreferences.getString("Users","[]");
                            String cookie = sharedpreferences.getString("Cookie","");
                            System.out.println(json);
                            MinePool.getMinePool().users = gson.fromJson(json,Users.class);
                            TokenPool.getTokenPool().Cookie = cookie;
                        }
                    }
                }
            };
            api.getMyApi(handler, url, 1);
        }
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
