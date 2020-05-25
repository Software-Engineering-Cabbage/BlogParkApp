package com.example.my_test6.user_module;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.GetUserApi;
import com.example.my_test6.user_module.GsonBean.MyBlogs;
import com.example.my_test6.user_module.GsonBean.Users;
import com.google.gson.Gson;

import static com.example.my_test6.Pool.login.clearCache;

public class UserFragment extends Fragment {
    private UserViewModel userViewModel;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private View root;
    private Button message;
    private Button browse;
    private Button collect;
    private Button blog;
    private Button homework;
    private Button about;
    private Button login;
    private TextView attentionNum;
    private TextView attention;
    private TextView ageNum;
    private TextView age;
    private TextView name;
    private ImageView head;
    private String Usertoken;
    private boolean isLogin;
    private Users users;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel =
                ViewModelProviders.of(this).get(UserViewModel.class);
        root = inflater.inflate(R.layout.user_fragment_user, container, false);
        message = root.findViewById(R.id.SignmessageCenter);
        browse = root.findViewById(R.id.SignbrowseHistory);
        collect = root.findViewById(R.id.Signcollect);
        blog = root.findViewById(R.id.Signblog);
        homework = root.findViewById(R.id.Signhomework);
        about = root.findViewById(R.id.Signabout);
        login = root.findViewById(R.id.log);
        attentionNum = root.findViewById(R.id.UserattentionNum);
        attention = root.findViewById(R.id.Userattention);
        ageNum = root.findViewById(R.id.UserageNum);
        age = root.findViewById(R.id.Userage);
        name = root.findViewById(R.id.Username);
        head = root.findViewById(R.id.Userhead);
        sp = getActivity().getSharedPreferences("User",Context.MODE_PRIVATE);
        editor = sp.edit();
        setUI();
        /*final TextView textView = root.findViewById(R.id.text_user);
        userViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }

    public void onResume() {
        //System.out.println("开始setUI:"+ TokenPool.getTokenPool().UserToken);
        setUI();
        super.onResume();
    }

    private void setUI(){
        //users = MinePool.getMinePool().users;
        isLogin = TokenPool.getTokenPool().isLogin;
        Usertoken = TokenPool.getTokenPool().UserToken;
        if(isLogin) {
            @SuppressLint("HandlerLeak")
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 0x1) {//收到users信息
                        Gson gson = new Gson();
                        String json = (String) msg.obj;
                        Users users;
                        users = gson.fromJson(json, Users.class);
                        MinePool.getMinePool().users = users;
                        //设置未完成UI
                        ageNum.setText(users.Seniority);
                        name.setText(users.DisplayName);
                        Glide.with(head.getContext()).load(users.Face).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(head);
                        System.out.println("blogApp: " + users.BlogApp);
                        //博客信息
                        @SuppressLint("HandlerLeak")
                         final Handler handler1 = new Handler(){
                            @Override
                            public void handleMessage(@NonNull Message msg) {
                                super.handleMessage(msg);
                                if(msg.what == 0x1){//设置用户信息
                                    Gson gson = new Gson();
                                    String json = (String) msg.obj;
                                    MyBlogs myBlogs = gson.fromJson(json,MyBlogs.class);
                                    MinePool.getMinePool().myblogs = myBlogs;
                                    String s = "" + myBlogs.postCount;
                                    attentionNum.setText(s);
                                }
                            }
                        };
                        GetUserApi api = new GetUserApi();
                        String url = "https://api.cnblogs.com/api/blogs/" + users.BlogApp;
                        api.getMyApi(handler1,url,1);
                    }
                }
            };
            //调用户博客信息
            GetUserApi api = new GetUserApi();
            String url = "https://api.cnblogs.com/api/users";
            api.getMyApi(handler, url, 1);
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto message
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.user_module.flash");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });

            browse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto browseHistory
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.user_module.browse");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });

            collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto collection
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.user_module.collection");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });

            blog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto blog
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.user_module.myblog");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });

            homework.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto homework
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.user_module.myhomework");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });

            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto about
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.user_module.about");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });
            login.setText("退出登录");
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto login
                    showCoverDialog();
                }
            });
            attention.setText("我的博客");
            age.setText("我的园龄");
        }

        else {

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto message
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.Pool.login");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });

            browse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto browseHistory
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.Pool.login");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });

            collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto collection
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.Pool.login");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });

            blog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto blog
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.Pool.login");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });

            homework.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto homework
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.Pool.login");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });

            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto about
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.Pool.login");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });
            login.setText("登录");
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goto login
                    Intent intent = new Intent();
                    ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.Pool.login");
                    intent.setComponent(componentname);
                    startActivity(intent);
                }
            });
            name.setText("登录");
            age.setText("");
            ageNum.setText("");
            attention.setText("");
            attentionNum.setText("");
            head.setImageResource(R.drawable.head);
        }
    }
    private void showCoverDialog(){
        final Context context = this.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("确定退出登录吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TokenPool.getTokenPool().UserToken = "";
                TokenPool.getTokenPool().isLogin = false;
                editor.putBoolean("isLogin",false);
                editor.commit();
                setUI();//这里不用清理登录网页缓存，因为在每次进入登录的模块前清理了
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
