package com.example.my_test6.blink_module;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.Pool.netWork.GetUserApi;
import com.example.my_test6.blink_module.adapter.blinkListAdapter;
import com.example.my_test6.blink_module.blinkBean.blinkInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlinkFatherFragment extends Fragment {

    private static final int BLINK_INIT = 1;
    private static final int BLINK_ADD = 2;
    private String type ;
    private Integer pageIndex ;
    private Integer pageSize ;
    private String tag ;
    private PullToRefreshListView refreshListView;
    @LayoutRes
    int layout;
    @IdRes
    int blinkList;
    private List<blinkInfo> blinkInfoList = new ArrayList<>();
    private com.example.my_test6.blink_module.adapter.blinkListAdapter blinkListAdapter;
    private Activity mActivity;
    private static String TAG = "BlinkFatherFragment";

    public BlinkFatherFragment() {
        // Required empty public constructor
    }
    public BlinkFatherFragment(String type, Integer pageIndex, Integer pageSize, String tag, @LayoutRes int layout, @IdRes int blinkList) {
        this.type = type;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.tag = tag;
        this.layout = layout;
        this.blinkList = blinkList;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            Type blinkListType = new TypeToken<ArrayList<blinkInfo>>() {
            }.getType();
            List<blinkInfo> blinkInfoList_temp;
            Gson gson = new Gson();
            blinkInfoList_temp = gson.fromJson(s, blinkListType);
            blinkInfoList.addAll(blinkInfoList_temp);
            if (msg.what == BLINK_INIT) {
                Log.d("BlinkFatherFragment", "handleMessage: "+type+" "+mActivity);
                blinkListAdapter = new blinkListAdapter(mActivity, blinkInfoList);
                refreshListView.setAdapter(blinkListAdapter);
            }
            if (msg.what == BLINK_ADD) {
                blinkListAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        getBlink(handler, type, pageIndex.toString(), pageSize.toString(), tag, BLINK_INIT);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layout, container, false);
        refreshListView = (PullToRefreshListView) view.findViewById(blinkList);
        //设置可上拉刷新和下拉刷新
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新时显示的文本
        ILoadingLayout startLayout = refreshListView.getLoadingLayoutProxy(true, false);
        startLayout.setPullLabel("正在下拉刷新...");
        startLayout.setRefreshingLabel("正在玩命加载中...");
        startLayout.setReleaseLabel("放开以刷新");

        ILoadingLayout endLayout = refreshListView.getLoadingLayoutProxy(false, true);
        endLayout.setPullLabel("正在上拉刷新...");
        endLayout.setRefreshingLabel("正在玩命加载中...");
        endLayout.setReleaseLabel("放开以刷新");
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    protected void onPostExecute(Void result) {
                        blinkInfoList.clear();
                        pageIndex = 1;
                        getBlink(handler, type, pageIndex.toString(), pageSize.toString(), "2", BLINK_ADD);
                        refreshView.onRefreshComplete();
                    }
                }.execute();
            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    protected void onPostExecute(Void result) {
                        pageIndex ++;
                        getBlink(handler, type, pageIndex.toString(), pageSize.toString(), "2", BLINK_ADD);
                        refreshView.onRefreshComplete();
                    }

                    ;
                }.execute();
            }
        });
        return view;
    }

    public void getBlink(final Handler handler, String type, String pageIndex, String pageSize, String tag, final int what) {
        final String token = TokenPool.getTokenPool().getMyToken();
        String url = "https://api.cnblogs.com/api/statuses/";
        url = url + "@" + type + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize + "&tag=" + tag;
        GetUserApi getUserApi = new GetUserApi();
        getUserApi.getMyApi(handler, url, what);
    }
}
