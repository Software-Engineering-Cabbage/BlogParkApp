package com.example.my_test6.question_module;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.GetUserApi;
import com.example.my_test6.user_module.myItemTouchHelperCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class wode_fragment extends Fragment {

    private RecyclerView recyclerView;
    private recycler_adapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<list_item> mdata;
    private View root;
    private Context context;
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
                    ItemTouchHelper.Callback callback = new myItemTouchHelperCallBack(mAdapter,context,"确定删除此这条博问吗？");
                    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                    touchHelper.attachToRecyclerView(recyclerView);
                    break;
                case LOADMORE:
                    String moretext = (String)msg.obj;
                    //Log.d("TAG",text);
                    Gson moregson = new Gson();
                    List<list_item> morequestionList =moregson.fromJson(moretext, new TypeToken<List<list_item>>(){}.getType());
                    mAdapter.addList_items(morequestionList);
                    mAdapter.setList_items(mdata);
                    callback = new myItemTouchHelperCallBack(mAdapter,context,"确定删除此这条博问吗？");
                    touchHelper = new ItemTouchHelper(callback);
                    touchHelper.attachToRecyclerView(recyclerView);
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
        context = getContext();
        setUI();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUI();
    }

    private void setUI() {

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
                    lishi_fragment.addHistUrls(mdata.get(position));
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(mAdapter);
    }

    void initdata() {
        getuserApi = new GetUserApi();
        String url = "https://api.cnblogs.com/api/questions/@myquestion?pageIndex=1&pageSize=35&spaceUserId="
                + MinePool.getMinePool().users.SpaceUserId;
        System.out.println("SpaceUserId " + MinePool.getMinePool().users.SpaceUserId);
        getuserApi.getMyApi(handler,url,GET_Question);
    }
}
