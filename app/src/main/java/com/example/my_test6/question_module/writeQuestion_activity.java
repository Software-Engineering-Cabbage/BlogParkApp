package com.example.my_test6.question_module;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.PostUserApi;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class writeQuestion_activity extends Activity {

    private ImageButton returnButton;
    private Button fabu_button;
    private EditText edit_title;
    private EditText edit_content;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_write_question);
        //找到返回按钮和发布按钮
        returnButton = findViewById(R.id.writeQuestion_return);
        fabu_button = findViewById(R.id.fabuQuestion_button);
        edit_title = findViewById(R.id.QuestionTitle_edit);
        edit_content = findViewById(R.id.QuestionContent_edit);
        //设置监听器
        returnButton.setOnClickListener(l);
        fabu_button.setOnClickListener(l);
    }


    //设置点击事件监听器l来监控该activity上的点击事件
    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.writeQuestion_return:
                    finish();
                    break;
                case R.id.fabuQuestion_button:
                    fabu();
                    break;
                default:
                    break;
            }
        }
    };

    private void fabu() {
        String title = edit_title.getText().toString();
        String content = edit_content.getText().toString();
        if (title.length() < 6 || content.length() < 20) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("输入内容过少！请保证标题6字以上，内容20字以上！");
            builder.setPositiveButton("确认",null);
            builder.show();
        }
        else {
            PostUserApi postUserApi = new PostUserApi();
            String url = "https://api.cnblogs.com/api/questions";
            RequestBody body = getDeliverBody();
            postUserApi.postMyApi(handler,url,body,1);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("发布成功！");
            builder.setPositiveButton("确认",null);
            builder.show();
            edit_title.setText("");
            edit_content.setText("");
        }

    }

    private RequestBody getDeliverBody() {
        RequestBody body = new FormBody.Builder()
                .add("Title",edit_title.getText().toString())
                .add("Content",edit_content.getText().toString())
                .add("Tags","")
                .add("Flags","1")
                .add("UserID",MinePool.getMinePool().users.UserId)
                .build();
        return  body;
    }
}
