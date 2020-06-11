package com.example.my_test6.home_module;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import okhttp3.OkHttpClient;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText editTextSearch;
    private RecyclerView recyclerView;
    private View root;
    private AdapterHomeList adapter_home;
    private List<BlogSummary> mData;
    private OkHttpClient client = new OkHttpClient();
    private   final int GET_Blog_1 = 0x001;
    private   final int Post_Blog_1 = 0x002;
    private  final int  REFRESH_1 = 0x003;
    private final int REFRESH_2 = 0x004;
    private  RefreshLayout refreshLayout;
    private GetApi getApi = new GetApi();
    private int blog_page = 1;
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case GET_Blog_1:
                    String text = (String)msg.obj;
                    Gson gson = new Gson();
                    mData = gson.fromJson(text, new TypeToken<List<BlogSummary>>(){}.getType());
                    adapter_home.setmData(mData);
                    break;
                case REFRESH_1:
                    String text2 = (String)msg.obj;
                    Gson gson2 = new Gson();
                    mData = gson2.fromJson(text2, new TypeToken<List<BlogSummary>>(){}.getType());
                    adapter_home.setmData(mData);
                    refreshLayout.finishRefresh(true);
                    break;
                case REFRESH_2:
                    String text3 = (String)msg.obj;
                    Gson gson3 = new Gson();
                    mData.addAll((List<BlogSummary>)gson3.fromJson(text3, new TypeToken<List<BlogSummary>>(){}.getType()));
                    adapter_home.setmData(mData);
                    refreshLayout.finishLoadMore(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (null != root) {
            ViewGroup parent = (ViewGroup) root.getParent();
            if (null != parent) {
                parent.removeView(root);
            }
        } else {
            root = inflater.inflate(R.layout.home_fragment_home, container, false);
            initView();// 控件初始化
        }
        return root;
    }

    private void initView() {
        editTextSearch= root.findViewById(R.id.editText_search);
        editTextSearch.setFocusable(false);
        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SearchBlogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("searchType","Blog");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setRefresh();
        recyclerView =  root.findViewById(R.id.recyclerView_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        initData();
        adapter_home = new AdapterHomeList();
        adapter_home.setOnItemClickListener(new AdapterHomeList.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent(getActivity(), BlogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("blog_addr",mData.get(position).getUrl());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 0;
                outRect.top = 5;
            }
        });
        recyclerView.setAdapter(adapter_home);
    }

    private void initData() {
        String url = "https://api.cnblogs.com/api/blogposts/@sitehome?pageIndex=1&pageSize=10";
        getApi.getMyApi(handler,url,GET_Blog_1);
        blog_page = 1;
    }
    private void setRefresh(){
        refreshLayout = (RefreshLayout) root.findViewById(R.id.refreshLayout_home);

      //  refreshLayout.setEnableLoadMore(true);
//设置 Header 为 贝塞尔雷达 样式

        refreshLayout.setRefreshHeader(new BezierRadarHeader(this.getActivity()).setEnableHorizontalDrag(true));
//设置 Footer 为 球脉冲 样式

        refreshLayout.setRefreshFooter(new BallPulseFooter(this.getActivity()).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(new OnRefreshListener(){

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                String url = "https://api.cnblogs.com/api/blogposts/@sitehome?pageIndex=1&pageSize=10";
                getApi.getMyApi(handler,url,REFRESH_1);
                blog_page = 1;
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                blog_page = blog_page+1;
                String url = "https://api.cnblogs.com/api/blogposts/@sitehome?pageIndex="+blog_page+"&pageSize=10";
                getApi.getMyApi(handler,url,REFRESH_2);
            }
        });
    }
}
