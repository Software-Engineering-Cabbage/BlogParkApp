package com.example.my_test6.blink_module;

import android.os.Handler;

import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.GetApi;



public class RecommendFragment extends BlinkFatherFragment{

    public  RecommendFragment () {
        super("all", 1, 50, "1", R.layout.blink_fragment_recommend, R.id.recommend_list);
    }


    public void getBlink(final Handler handler, String type, String pageIndex, String pageSize, String tag, final int what) {
        String url = "https://api.cnblogs.com/api/statuses/";
        url = url + "@" + type + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize + "&tag=" + tag;
        GetApi getApi = new GetApi();
        getApi.getMyApi(handler, url, what);
    }
}
