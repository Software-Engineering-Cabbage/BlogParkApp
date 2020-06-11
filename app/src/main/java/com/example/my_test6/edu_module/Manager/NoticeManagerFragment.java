package com.example.my_test6.edu_module.Manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.Pool.netWork.DeleteApiBody;
import com.example.my_test6.R;
import com.example.my_test6.edu_module.ClassActivity;
import com.example.my_test6.edu_module.Manager.Member.ReturnMessage;
import com.example.my_test6.edu_module.Manager.Notice.NoticeAddActivity;
import com.example.my_test6.edu_module.Manager.Notice.NoticeModifyActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NoticeManagerFragment extends Fragment {
    private int schoolid;
    private Context context=getContext();
    private final MediaType mediaType
            = MediaType.parse("application/json; charset=utf-8");
    NoticeManagerFragment(int id){
        this.schoolid=id;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.edu_fragment_notice_manager, container, false);
        ListView listView=root.findViewById(R.id.EduManagerNoticeListview);
        ArrayList<String> ctype=new ArrayList<>();
        ctype.add("发布公告");
        ctype.add("修改当前公告");
        ctype.add("删除当前公告");
        ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,ctype);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent=new Intent(getActivity(),new NoticeAddActivity().getClass());
                    intent.putExtra("schoolid",schoolid);
                    startActivity(intent);
                }
                if(position==1){
                    Intent intent=new Intent(getActivity(),new NoticeModifyActivity().getClass());
                    intent.putExtra("schoolid",schoolid);
                    startActivity(intent);
                }
                if(position==2){
                    @SuppressLint("HandlerLeak")final Handler handler=new Handler() {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            String text = (String) msg.obj;
                            System.out.println(text);
                            Gson gson=new Gson();
                            ReturnMessage message=gson.fromJson(text,ReturnMessage.class);
                            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                            builder.setTitle("提示");
                            if(message.getSuccess().equals("true")){
                                builder.setMessage("删除成功");
                            }
                            else{
                                builder.setMessage(message.getMessage());
                            }
                            builder.setPositiveButton("确定", null );
                            AlertDialog alertDialog=builder.create();
                            alertDialog.show();
                        }
                    };
                    DeleteApiBody deleteApiBody=new DeleteApiBody();
                    String bodyString="{blogId:"+ MinePool.getMinePool().users.BlogId+"}";
                    RequestBody body=RequestBody.create(bodyString,mediaType);
                    deleteApiBody.Delete(handler,"https://api.cnblogs.com/api/edu/bulletin/remove/"+schoolid+"/"+Pool.getMinePool().bulletinid,body,0);
                }
            }
        });
        return root;
    }
}
