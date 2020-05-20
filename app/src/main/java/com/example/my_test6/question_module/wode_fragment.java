package com.example.my_test6.question_module;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.GetUserApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class wode_fragment extends Fragment {

    private RecyclerView recyclerView;
    private recycler_adapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<list_item> mdata;
    private View root;
    private int pageindex = 1;

    private static  final int GET_Question = 0x001;
    private static  final int Post_Question = 0x002;
    private static  final int LOADMORE = 0x003;
    private GetUserApi getuserApi;

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_Question:
                    String text = (String)msg.obj;
                    Gson gson = new Gson();
                    List<list_item> questionList =gson.fromJson(text, new TypeToken<List<list_item>>(){}.getType());
                    mdata = questionList;
                    mAdapter.setList_items(mdata);
                    break;
                case LOADMORE:
                    String moretext = (String)msg.obj;
                    //Log.d("TAG",text);
                    Gson moregson = new Gson();
                    List<list_item> morequestionList =moregson.fromJson(moretext, new TypeToken<List<list_item>>(){}.getType());
                    mAdapter.addList_items(morequestionList);
                    mAdapter.setList_items(mdata);
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.question_view_wode, container, false);
        setUI();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUI();
    }

    private void setUI() {
        if (!TokenPool.getTokenPool().isLogin) {
            //未登录状态
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            wode_weidenglu_fragment f = new wode_weidenglu_fragment();
            ft.replace(R.id.wode_fragment,f);
            ft.commit();
            Log.d("TAG","发现还未登录，切换到登录按钮界面");
        }else {
            //登陆状态


            Log.d("TAG","进入到登陆后的我的博问，可以加载我的博问列表");
            recyclerView = (RecyclerView) root.findViewById(R.id.wode_recyclerview);
            initdata();
            layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new recycler_adapter(getActivity(), mdata);
            mAdapter.setOnItemClickListener(new recycler_adapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), QuestionDetail_activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("question_addr", mdata.get(position).getQUrl());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(mAdapter);
        }
    }

    void initdata() {
        getuserApi = new GetUserApi();
        String url = "https://api.cnblogs.com/api/questions/@myquestion?pageIndex=1&pageSize=35&spaceUserId="
                + MinePool.getMinePool().users.SpaceUserId;
        getuserApi.getMyApi(handler,url,GET_Question);
    }
}
