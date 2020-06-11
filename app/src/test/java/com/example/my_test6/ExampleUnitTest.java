package com.example.my_test6;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.my_test6.Bean.BlogSummary;
import com.example.my_test6.Pool.netWork.PatchApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testPost(){
        PatchApi patchApi = new PatchApi();
        final MediaType JSON
                = MediaType.get("application/json; charset=utf-8");
        String noticeId="1824";
        String schoolClassId = "2142";

        final int number = 122;
        String url = "https://api.cnblogs.com/api/edu/bulletin/modify/"+noticeId;
        RequestBody body = RequestBody.create("", JSON);
        RequestBody requestBody = new FormBody.Builder()
                .add("bulletinId",noticeId)
                .add("schoolClassId",schoolClassId)
                .add("content","这是在测试修改公告")
                .build();
        Handler handler = new Handler(){
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case number:
                        String text = (String) msg.obj;
                        Log.d("tag", text);
                        break;
                    default:
                        break;
                }
            }
        };
        patchApi.patch(handler,url,body,number);
        assertEquals(4, 2 + 2);
    }
}