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

import java.util.LinkedList;
import java.util.List;

public class lishi_fragment extends Fragment {

    private RecyclerView recyclerView;
    private recycler_adapter mAdapter;
    private LinearLayoutManager layoutManager;

    private static  final int GET_Question = 0x001;
    private static  final int REFRESH = 0x002;
    private static  final int LOADMORE = 0x003;

    //lqx
    private static LinkedList<list_item> histUrls = new LinkedList<>();



    public static void addHistUrls(list_item li) {
        histUrls.remove(li);
        histUrls.addFirst(li);
        if (histUrls.size() > 32) {
            histUrls.remove(31);
        }
    }

    private int pageindex = 1;

    private GetApi getApi;

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            mAdapter.setList_items(histUrls);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.question_view_lishi, container, false);

        final RefreshLayout refreshLayout = (RefreshLayout) root.findViewById(R.id.lishi_refreshlayout);
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
                String url = "https://api.cnblogs.com/api/questions/@unsolved?pageIndex="
                        +Integer.toString(pageindex)+"&pageSize=15";
                getApi.getMyApi(handler,url,LOADMORE);
                refreshLayout.finishLoadMore();
            }
        });

        recyclerView = (RecyclerView) root.findViewById(R.id.lishi_recyclerview);
        initdata();
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new recycler_adapter(getActivity(),histUrls);
        mAdapter.setOnItemClickListener(new recycler_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),QuestionDetail_activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("question_addr", histUrls.get(position).getQUrl());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    void initdata() {
        getApi = new GetApi();
        String url = "https://api.cnblogs.com/api/questions/@unsolved?pageIndex=1&pageSize=15";
        getApi.getMyApi(handler,url,GET_Question);
    }
}
