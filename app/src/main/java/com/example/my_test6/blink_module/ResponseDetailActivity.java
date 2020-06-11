package com.example.my_test6.blink_module;


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

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.Pool.login;
import com.example.my_test6.Pool.netWork.GetApi;
import com.example.my_test6.Pool.netWork.PostUserApi;
import com.example.my_test6.R;
import com.example.my_test6.blink_module.adapter.blinkResponseListAdapter;
import com.example.my_test6.blink_module.blinkBean.blinkResponseInfo;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ResponseDetailActivity extends AppCompatActivity  {

    private static final int RESPONSE_INIT = 1;
    private static final int SEND_RESPONSE = 2;
    private String blinkId;
    private String userId;
    private ListView listView;
    private FloatingActionButton fab;
    private PopupWindow mPopWindow;
    private EditText response_content;
    private Button send_confirm;
    private TextView noResponse;
    private RefreshLayout refreshLayout;
    private List<blinkResponseInfo> blinkResponseInfoList = new ArrayList<>();
    private blinkResponseListAdapter blinkResponseListAdapter;
    private static String TAG = "ResponseDetailActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == RESPONSE_INIT) {
                String s = (String) msg.obj;
                android.util.Log.d(TAG, "ResponseDetailActivityhandleMessage: " + s);
                Type blinkResponseListType = new TypeToken<ArrayList<blinkResponseInfo>>() {
                }.getType();
                List<blinkResponseInfo> blinkResponseInfoList_temp;
                Gson gson = new Gson();
                blinkResponseInfoList_temp = gson.fromJson(s, blinkResponseListType);
                try {
                    blinkResponseInfoList.clear();
                    blinkResponseInfoList.addAll(blinkResponseInfoList_temp);
                    blinkResponseListAdapter = new blinkResponseListAdapter(ResponseDetailActivity.this, blinkResponseInfoList);
                    listView.setAdapter(blinkResponseListAdapter);
                    if (!blinkResponseInfoList.isEmpty()) {
                        noResponse.setVisibility(View.INVISIBLE);
                    } else {
                        noResponse.setText("莫得评论.....");
                        noResponse.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Toast.makeText(ResponseDetailActivity.this, "网络好像出了些问题，再刷新一下试一试", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == SEND_RESPONSE) {
                getResponse(handler, blinkId, RESPONSE_INIT);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        blinkId = intent.getStringExtra("blinkId");
        userId = intent.getStringExtra("userId");
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
                if(isNetworkConnected(ResponseDetailActivity.this)){
                    if (!TokenPool.getTokenPool().isLogin) {
                        Intent intent = new Intent(ResponseDetailActivity.this, login.class);
                        startActivity(intent);
                    } else {
                        showPopupWindow("");
                    }
                }else{
                    Toast.makeText(ResponseDetailActivity.this, "网络好像出了点问题", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if(isNetworkConnected(ResponseDetailActivity.this)){
                    blinkResponseInfoList.clear();
                    getResponse(handler, blinkId, RESPONSE_INIT);
                    refreshLayout.finishRefresh();
                }else{
                    refreshLayout.finishRefresh();
                    Toast.makeText(ResponseDetailActivity.this, "网络好像出了点问题", Toast.LENGTH_SHORT).show();
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
                blinkResponseInfo blinkResponseInfo = (blinkResponseInfo) listView.getAdapter().getItem(position);
                if(isNetworkConnected(ResponseDetailActivity.this)){
                    if (!TokenPool.getTokenPool().isLogin) {
                        Intent intent = new Intent(ResponseDetailActivity.this, login.class);
                        startActivity(intent);
                    } else {
                        showPopupWindow(blinkResponseInfo.getUserDisplayName());
                    }
                }else{
                    Toast.makeText(ResponseDetailActivity.this, "网络好像出了点问题", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getResponse(handler, blinkId, RESPONSE_INIT);
    }

    //  评论弹出框
    private void showPopupWindow(final String userName) {
        //设置contentView
        View contentView = LayoutInflater.from(ResponseDetailActivity.this).inflate(R.layout.blink_responsepop, null);
        mPopWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        response_content = contentView.findViewById(R.id.et_edit_comment_body);
        send_confirm = contentView.findViewById(R.id.btn_send_comment);
        send_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected(ResponseDetailActivity.this)){
                    if(response_content.getText().toString().equals("")){
                        Toast.makeText(ResponseDetailActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    }else{
                        if(userName.equals("")){
                            postResponse();
                        }else{
                            postResponseToUser(userName);
                        }
                        Toast.makeText(ResponseDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                        response_content.setText("");
                        mPopWindow.dismiss();
                    }
                }else{
                    Toast.makeText(ResponseDetailActivity.this, "网络好像出了点问题", Toast.LENGTH_SHORT).show();
                    mPopWindow.dismiss();
                }
            }
        });
        if(!userName.equals("")){
            //  回复某人 @某人提示
            response_content.setHint("@"+userName);
        }
        //显示PopupWindow
        View rootview = LayoutInflater.from(ResponseDetailActivity.this).inflate(R.layout.blink_activity_response_detail, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    public void getResponse(final Handler handler, String blinkId, final int what) {
        String url = "https://api.cnblogs.com/api/statuses/";
        url = url + blinkId + "/comments";
        GetApi getApi = new GetApi();
        getApi.getMyApi(handler, url, what);
    }

    public void postResponse() {
        PostUserApi postUserApi = new PostUserApi();
        String url = "https://api.cnblogs.com/api/statuses/" + blinkId + "/comments";
        RequestBody body = new FormBody.Builder()
                .add("Content", response_content.getText().toString())
                .add("ReplyToUserId", userId)
                .add("lngId", blinkId)
                .add("ParentCommentId", "0")
                .build();
        postUserApi.postMyApi(handler, url, body, SEND_RESPONSE);
    }

    public void postResponseToUser(String userName) {
        PostUserApi postUserApi = new PostUserApi();
        String url = "https://api.cnblogs.com/api/statuses/" + blinkId + "/comments";
        RequestBody body = new FormBody.Builder()
                .add("Content", "@"+userName +" "+ response_content.getText().toString())
                .add("ReplyToUserId", "")
                .add("lngId", blinkId)
                .add("ParentCommentId", "0")
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
