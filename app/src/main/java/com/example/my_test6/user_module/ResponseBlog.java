package com.example.my_test6.user_module;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.Pool.login;
import com.example.my_test6.Pool.netWork.GetApi;
import com.example.my_test6.Pool.netWork.PostUserApi;
import com.example.my_test6.R;
import com.example.my_test6.user_module.GsonBean.BlogResponse;
import com.example.my_test6.user_module.ListAdapters.BlogResponseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

@SuppressLint("Registered")
public class ResponseBlog extends AppCompatActivity {

    private static final int RESPONSE_INIT = 1;
    private static final int SEND_RESPONSE = 2;
    private String blogApp;
    private String postId;
    private ListView listView;
    private FloatingActionButton fab;
    private PopupWindow mPopWindow;
    private EditText response_content;
    private Button send_confirm;
    private TextView noResponse;
    private RefreshLayout refreshLayout;
    private List<BlogResponse> blogResponseList = new ArrayList<>();
    private BlogResponseListAdapter blogResponseListAdapter;
    private static String TAG = "ResponseBlog";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == RESPONSE_INIT) {
                String s = (String) msg.obj;
                android.util.Log.d(TAG, "ResponseDetailActivityhandleMessage: " + s);
                List<BlogResponse> blogResponseList_temp;
                Gson gson = new Gson();
                blogResponseList_temp = gson.fromJson(s, new TypeToken<ArrayList<BlogResponse>>() {
                }.getType());
                try {
                    blogResponseList.clear();
                    blogResponseList.addAll(blogResponseList_temp);
                    blogResponseListAdapter = new BlogResponseListAdapter(ResponseBlog.this, blogResponseList);
                    listView.setAdapter(blogResponseListAdapter);
                    if (!blogResponseList.isEmpty()) {
                        noResponse.setVisibility(View.INVISIBLE);
                    } else {
                        noResponse.setText("无评论");
                        noResponse.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Toast.makeText(ResponseBlog.this, "网络好像出了些问题，再刷新一下试一试", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == SEND_RESPONSE) {
                getResponse(handler, blogApp, postId, RESPONSE_INIT);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        blogApp = intent.getStringExtra("blogApp");
        postId = intent.getStringExtra("postId");
        setContentView(R.layout.blink_activity_response_detail);
        setUI();
    }

    private void setUI() {
        listView = findViewById(R.id.response_list);
        fab = findViewById(R.id.response_fab);
        refreshLayout = findViewById(R.id.response_refreshLayout);
        noResponse = findViewById(R.id.no_response);
        noResponse.setText("刷新中~~");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected(ResponseBlog.this)){
                    if (!TokenPool.getTokenPool().isLogin) {
                        Intent intent = new Intent(ResponseBlog.this, login.class);
                        startActivity(intent);
                    } else {
                        showPopupWindow("");
                    }
                }else{
                    Toast.makeText(ResponseBlog.this, "网络好像出了点问题", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if(isNetworkConnected(ResponseBlog.this)){
                    blogResponseList.clear();
                    getResponse(handler, blogApp, postId, RESPONSE_INIT);
                    refreshLayout.finishRefresh();
                }else{
                    refreshLayout.finishRefresh();
                    Toast.makeText(ResponseBlog.this, "网络好像出了点问题", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BlogResponse blogResponse = (BlogResponse) listView.getAdapter().getItem(position);
                if(isNetworkConnected(ResponseBlog.this)){
                    if (!TokenPool.getTokenPool().isLogin) {
                        Intent intent = new Intent(ResponseBlog.this, login.class);
                        startActivity(intent);
                    } else {
                        showPopupWindow(blogResponse.Author);
                    }
                }else{
                    Toast.makeText(ResponseBlog.this, "网络好像出了点问题", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getResponse(handler, blogApp, postId, RESPONSE_INIT);
    }

    //  评论弹出框
    private void showPopupWindow(final String userName) {
        //设置contentView
        View contentView = LayoutInflater.from(ResponseBlog.this).inflate(R.layout.blink_responsepop, null);
        mPopWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        response_content = contentView.findViewById(R.id.et_edit_comment_body);
        send_confirm = contentView.findViewById(R.id.btn_send_comment);
        send_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected(ResponseBlog.this)){
                    if(response_content.getText().toString().equals("")){
                        Toast.makeText(ResponseBlog.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    }else{
                        if(userName.equals("")){
                            postResponse();
                        }else{
                            postResponseToUser(userName);
                        }
                        Toast.makeText(ResponseBlog.this, "发送成功", Toast.LENGTH_SHORT).show();
                        response_content.setText("");
                        mPopWindow.dismiss();
                    }
                }else{
                    Toast.makeText(ResponseBlog.this, "网络好像出了点问题", Toast.LENGTH_SHORT).show();
                    mPopWindow.dismiss();
                }
            }
        });
        if(!userName.equals("")){
            //  回复某人 @某人提示
            response_content.setHint("@"+userName);
        }
        //显示PopupWindow
        View rootview = LayoutInflater.from(ResponseBlog.this).inflate(R.layout.blink_activity_response_detail, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    public void getResponse(final Handler handler, String blogApp, String postId, final int what) {
        String url = "https://api.cnblogs.com/api/blogs/"+blogApp+"/posts/"+postId+"/comments?pageIndex=1&pageSize=100";
        GetApi getApi = new GetApi();
        getApi.getMyApi(handler, url, what);
    }

    public void postResponse() {
        PostUserApi postUserApi = new PostUserApi();
        String url = "https://api.cnblogs.com/api/blogs/" + blogApp + "/posts/" + postId + "/comments";
        RequestBody body = new FormBody.Builder()
                .add("body", response_content.getText().toString())
                .build();
        postUserApi.postMyApi(handler, url, body, SEND_RESPONSE);
    }

    public void postResponseToUser(String userName) {
        PostUserApi postUserApi = new PostUserApi();
        String url = "https://api.cnblogs.com/api/blogs/" + blogApp + "/posts/" + postId + "/comments";
        RequestBody body = new FormBody.Builder()
                .add("body", "@"+userName +"\n"+ response_content.getText().toString())
                .build();
        postUserApi.postMyApi(handler, url, body, SEND_RESPONSE);
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                //这种方法也可以
                //return mNetworkInfo .getState()== NetworkInfo.State.CONNECTED
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}