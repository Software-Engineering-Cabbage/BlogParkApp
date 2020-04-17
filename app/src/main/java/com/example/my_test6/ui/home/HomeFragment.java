package com.example.my_test6.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_test6.Bean.BlogSummary;
import com.example.my_test6.Bean.Token;
import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;
import com.example.my_test6.home.HomeSearchActivity;
import com.example.my_test6.netWork.GetApi;
import com.example.my_test6.netWork.GetToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText editTextSearch;
    private RecyclerView recyclerView;
    private View root;
    private AdapterHomeList adapter_home;
    private List<BlogSummary> mData;
    private OkHttpClient client = new OkHttpClient();
    private static  final int GET_Blog_1 = 0x001;
    private static  final int Post_Blog_1 = 0x002;
    private GetApi getApi;
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case GET_Blog_1:
                    String text = (String)msg.obj;
                    Gson gson = new Gson();
                    List<BlogSummary> blogSummaryList1 = gson.fromJson(text, new TypeToken<List<BlogSummary>>(){}.getType());
                    mData = blogSummaryList1;
                    adapter_home.setmData(mData);
                    break;
                case Post_Blog_1:
                    String text2 = (String)msg.obj;
                    Gson gson1 = new Gson();
                    Token token1= gson1.fromJson(text2, Token.class);
                    TokenPool.myToken = token1.getAccess_token();
                    break;
                default:
                    break;
            }
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return root;

    }


    private void initView() {
        editTextSearch= root.findViewById(R.id.editText_search);
        editTextSearch.setFocusable(false);
        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeSearchActivity.class);
                startActivity(intent);
            }
        });

        recyclerView =  root.findViewById(R.id.recyclerView_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        initData();
        adapter_home = new AdapterHomeList();
        recyclerView.setAdapter(adapter_home);
    }

    private void initData() {
        getApi = new GetApi();
        String url = "https://api.cnblogs.com/api/blogposts/@sitehome?pageIndex=1&pageSize=10";
        getApi.getMyApi(handler,url,TokenPool.myToken);
    }
}
