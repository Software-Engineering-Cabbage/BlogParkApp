package com.example.my_test6.home_module;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_test6.Bean.BlogFromSearch;
import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.GetApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.List;

import okhttp3.OkHttpClient;

public class SearchBlogActivity extends AppCompatActivity {
    private HomeViewModel homeViewModel;
    private  SearchView searchView;
    private RecyclerView recyclerView;
    private AdapterHomeSearchBlog adapter_search_blog;
    private List<BlogFromSearch> mData;
    private OkHttpClient client = new OkHttpClient();
    private final int GET_Search_Blog = 0x0010;
    private final int REFRESH_Search_blog = 0x0011;
    private final int LOADMORE_Search_blog = 0x0012;
    private RefreshLayout refreshLayout;
    private GetApi getApi = new GetApi();
    private int pageIndex = 1;
    private String searchType = "Blog";
    private String searchKeyWord = "";
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case GET_Search_Blog:
                    String text = (String)msg.obj;
                    Gson gson = new Gson();
                    mData = gson.fromJson(text, new TypeToken<List<BlogFromSearch>>(){}.getType());
                    adapter_search_blog.setmData(mData);
                    break;
                case LOADMORE_Search_blog:
                    String text3 = (String)msg.obj;
                    Gson gson3 = new Gson();
                    mData.addAll((List<BlogFromSearch>)gson3.fromJson(text3, new TypeToken<List<BlogFromSearch>>(){}.getType()));
                    adapter_search_blog.setmData(mData);
                    refreshLayout.finishLoadMore(true);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.home_activity_home_search);
        SearchView searchView = findViewById(R.id.searchView_home);
          searchView.setSubmitButtonEnabled(true);
        searchView = findViewById(R.id.searchView_home);
        searchView.setSubmitButtonEnabled(true);
        final SearchView finalSearchView = searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query==""||query==null){
                    Toast toast=Toast.makeText(SearchBlogActivity.this,"搜索内容不能为空",Toast.LENGTH_SHORT    );
                    toast.show();
                    finalSearchView.clearFocus();
                    return true;
                }
                else{
                    searchKeyWord = query;
                    pageIndex = 1;
                    String url = "https://api.cnblogs.com/api/ZzkDocuments/"+searchType+
                            "?keyWords="+searchKeyWord+"&pageIndex="+pageIndex;
                    getApi.getMyApi(handler,url,GET_Search_Blog);
                    finalSearchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        initView();
    }

    public void initView() {
        setLoadMore();
        recyclerView = findViewById(R.id.recyclerView_search_blog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();
        adapter_search_blog = new AdapterHomeSearchBlog();
        adapter_search_blog.setOnItemClickListener(new AdapterHomeSearchBlog.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent(SearchBlogActivity.this, BlogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("blog_addr", mData.get(position).getUri());
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
        recyclerView.setAdapter(adapter_search_blog);

    }

    public void setLoadMore() {
        refreshLayout = findViewById(R.id.refreshLayout_search_blog);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));

        if(searchKeyWord=="")  refreshLayout.finishLoadMore(true);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex = pageIndex+1;
                String url = "https://api.cnblogs.com/api/ZzkDocuments/"+searchType+
                        "?keyWords="+searchKeyWord+"&pageIndex="+pageIndex;
                getApi.getMyApi(handler,url,LOADMORE_Search_blog);
            }
        });

    }
    public void initData(){

    }
}
