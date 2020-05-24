package com.example.my_test6.blink_module;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.my_test6.Pool.netWork.DeleteApi;
import com.example.my_test6.Pool.netWork.GetUserApi;
import com.example.my_test6.blink_module.adapter.blinkListAdapter;
import com.example.my_test6.blink_module.blinkBean.blinkInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlinkFatherFragment extends Fragment {

    //  handler.what标记
    private static final int BLINK_INIT = 1;
    private static final int BLINK_ADD = 2;
    private static final int BLINK_DELETE = 3;
    //  get请求的url的字段，用于get请求类型
    private String type;

    //  get请求的url的字段，表示页偏移
    private Integer pageIndex;

    //  get请求的url的字段，表示页闪存个数
    private Integer pageSize;

    //  get请求的url的字段，表示闪存tag
    private String tag;
    private ListView listView;

    //  onCreate时加载的layout
    @LayoutRes
    int layout;

    //  layout上的组件
    @IdRes
    int smartFresh;

    //  layout上的组件
    @IdRes
    int blinkList;

    //  闪存列
    private List<blinkInfo> blinkInfoList = new ArrayList<>();

    //  适配器
    private com.example.my_test6.blink_module.adapter.blinkListAdapter blinkListAdapter;

    //  所在的Activity
    private Activity mActivity;

    //  所在view
    private View rootView;

    private static String TAG = "BlinkFatherFragment";

    public BlinkFatherFragment() {
        // Required empty public constructor
    }

    public BlinkFatherFragment(String type, Integer pageIndex, Integer pageSize, String tag, @LayoutRes int layout, @IdRes int smartFresh, @IdRes int blinkList) {
        this.type = type;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.tag = tag;
        this.layout = layout;
        this.smartFresh = smartFresh;
        this.blinkList = blinkList;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //  将get到的请求转化为blinkInfoList的内容
            String s = (String) msg.obj;
            Log.d(TAG, "handleMessage: " + s);
            Type blinkListType = new TypeToken<ArrayList<blinkInfo>>() {
            }.getType();
            List<blinkInfo> blinkInfoList_temp;
            Gson gson = new Gson();
            blinkInfoList_temp = gson.fromJson(s, blinkListType);

            if (msg.what == BLINK_INIT) {
                Log.d("BlinkFatherFragment", "handleMessage: " + type + " " + mActivity);
                blinkInfoList.clear();
                blinkInfoList.addAll(blinkInfoList_temp);
                blinkListAdapter = new blinkListAdapter(mActivity, blinkInfoList);
                listView.setAdapter(blinkListAdapter);
            }
            if (msg.what == BLINK_ADD) {
                blinkInfoList.addAll(blinkInfoList_temp);
                blinkListAdapter.notifyDataSetChanged();
            }
            if (msg.what == BLINK_DELETE) {
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
        Log.d(TAG, "onCreate: " + type);
        super.onCreate(savedInstanceState);
        getBlink(handler, type, pageIndex.toString(), pageSize.toString(), tag, BLINK_INIT);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(layout, container, false);
        setUI();
        return rootView;
    }

    public void setUI() {
        listView = (ListView) rootView.findViewById(blinkList);
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 1, 0, "查看评论");//下拉菜单
                menu.add(0, 2, 0, "添加评论");//下拉菜单
                if (type == "my") {
                    menu.add(0, 3, 0, "删除闪存");//下拉菜单
                }

            }
        });


        RefreshLayout refreshLayout = (RefreshLayout) rootView.findViewById(smartFresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                blinkInfoList.clear();
                pageIndex = 1;
                getBlink(handler, type, pageIndex.toString(), pageSize.toString(), "2", BLINK_ADD);
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getBlink(handler, type, pageIndex.toString(), pageSize.toString(), "2", BLINK_ADD);
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ContextMenu.ContextMenuInfo info = item.getMenuInfo();
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) info;
//        获取选中行位置
        final int position = contextMenuInfo.position;
//        Log.d(TAG, "onContextItemSelected: "+position);
//        获取闪存内容
//        Log.d(TAG, "onContextItemSelected: "+item.getItemId());
        final blinkInfo blinkInfo = blinkInfoList.get(position);
        switch (item.getItemId()) {
            case 1:
                break;
            case 2:
                break;
            case 3: {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("确定删除?");
                builder.setTitle("提示");
                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        blinkInfoList.remove(position);
                        blinkListAdapter.notifyDataSetChanged();
                        deleteBlink(handler, blinkInfo.getId(), BLINK_DELETE);
                        Toast.makeText(mActivity, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.create().show();
            }
        }
        return true;
    }

    public void getBlink(final Handler handler, String type, String pageIndex, String pageSize, String tag, final int what) {
        String url = "https://api.cnblogs.com/api/statuses/";
        url = url + "@" + type + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize + "&tag=" + tag;
        GetUserApi getUserApi = new GetUserApi();
        getUserApi.getMyApi(handler, url, what);
    }

    public void deleteBlink(final Handler handler, String blinkId, final int what) {
        String url = "https://api.cnblogs.com/api/statuses/";
        url = url + blinkId;
        Log.d(TAG, "deleteBlink: "+url);
        DeleteApi deleteApi = new DeleteApi();
        deleteApi.Delete(handler, url, what);
    }

}
