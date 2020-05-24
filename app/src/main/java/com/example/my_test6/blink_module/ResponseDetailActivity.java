package com.example.my_test6.blink_module;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_test6.Pool.netWork.GetApi;
import com.example.my_test6.R;
import com.example.my_test6.blink_module.adapter.blinkResponseListAdapter;
import com.example.my_test6.blink_module.blinkBean.blinkResponseInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResponseDetailActivity extends AppCompatActivity {

    private static final int RESPONSE_INIT = 1;
    private String blinkId;
    private ListView listView;
    private List<blinkResponseInfo> blinkResponseInfoList = new ArrayList<>();
    private static String TAG = "ResponseDetailActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            android.util.Log.d(TAG, "ResponseDetailActivityhandleMessage: "+s);
            Type blinkResponseListType = new TypeToken<ArrayList<blinkResponseInfo>>() {
            }.getType();
            List<blinkResponseInfo> blinkResponseInfoList_temp;
            Gson gson = new Gson();
            blinkResponseInfoList_temp = gson.fromJson(s, blinkResponseListType);

            if(msg.what == RESPONSE_INIT){
                blinkResponseInfoList.clear();
                blinkResponseInfoList.addAll(blinkResponseInfoList_temp);
                blinkResponseListAdapter blinkResponseListAdapter = new blinkResponseListAdapter(ResponseDetailActivity.this,blinkResponseInfoList);
                listView.setAdapter(blinkResponseListAdapter);
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent  = getIntent();
        blinkId = intent.getStringExtra("blinkId");
        setContentView(R.layout.blink_activity_response_detail);
        getResponse(handler,blinkId,RESPONSE_INIT);
        setUI();
    }

    private void setUI() {
        listView = findViewById(R.id.response_list);
        RefreshLayout refreshLayout = findViewById(R.id.response_refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                blinkResponseInfoList.clear();
                getResponse(handler,blinkId,RESPONSE_INIT);
                refreshLayout.finishRefresh();
            }
        });
    }

    public void getResponse(final Handler handler,String blinkId, final int what) {
        String url = "https://api.cnblogs.com/api/statuses/";
        url = url +  blinkId + "/comments";
        GetApi getApi = new GetApi();
        getApi.getMyApi(handler, url, what);
    }

}
