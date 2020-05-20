package com.example.my_test6.question_module;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.GetApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class gaofen_fragment extends Fragment {

    private RecyclerView recyclerView;
    private recycler_adapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<list_item> mdata;

    private static  final int GET_Question = 0x001;
    private static  final int REFRESH = 0x002;
    private static  final int LOADMORE = 0x003;

    private int pageindex = 1;

    private GetApi getApi;

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_Question:
                    String text = (String)msg.obj;
                    //Log.d("TAG",text);
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

        View root = inflater.inflate(R.layout.question_view_gaofen, container, false);

        final RefreshLayout refreshLayout = (RefreshLayout) root.findViewById(R.id.gaofen_refreshlayout);
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initdata();
                pageindex = 1;
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageindex++;
                String url = "https://api.cnblogs.com/api/questions/@highscore?pageIndex="
                        +Integer.toString(pageindex)+"&pageSize=15";
                getApi.getMyApi(handler,url,LOADMORE);
                refreshLayout.finishLoadMore();
            }
        });

        recyclerView = (RecyclerView) root.findViewById(R.id.gaofen_recyclerview);
        initdata();
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new recycler_adapter(getActivity(),mdata);
        mAdapter.setOnItemClickListener(new recycler_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),QuestionDetail_activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("question_addr", mdata.get(position).getQUrl());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    void initdata() {
        getApi = new GetApi();
        String url = "https://api.cnblogs.com/api/questions/@highscore?pageIndex=1&pageSize=15";
        getApi.getMyApi(handler,url,GET_Question);
    }
}
