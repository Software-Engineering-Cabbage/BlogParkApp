package com.example.my_test6.blink_module.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.my_test6.R;
import com.example.my_test6.blink_module.ResponseDetailActivity;
import com.example.my_test6.blink_module.WebViewActivity;
import com.example.my_test6.blink_module.blinkBean.blinkInfo;

import java.util.List;

public class blinkListAdapter extends  BaseAdapter {
    private Context context;
    private static String TAG = "GY blinkListAdapter";
    private  List<blinkInfo> blinkInfoList;
    private  LayoutInflater layoutInflater;

    public blinkListAdapter(Context context , List<blinkInfo> blinkInfoList) {
        Log.d(TAG, "blinkListAdapter: context"+context);
        this.context = context;
        this.blinkInfoList = blinkInfoList;
         layoutInflater = LayoutInflater.from(context);
        Log.d(TAG, "construct:"+this.blinkInfoList.size());
    }

    @Override
    public int getCount() {
//        Log.d(TAG, "getCount: "+blinkInfoList.size());
        return blinkInfoList.size();
    }

    @Override
    public Object getItem(int position) {
//        Log.d(TAG, "getItem: "+blinkInfoList.get(position));
        return blinkInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
//        Log.d(TAG, "getItemId: "+position);
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
//        Log.d(TAG, "getView: "+position);
        View view = layoutInflater.inflate(R.layout.blink_item,null);
        final blinkInfo blinkInfo = blinkInfoList.get(position);
        final ImageView blinkImage = view.findViewById(R.id.img_blog_avatar);
        TextView blinkName = view.findViewById(R.id.tv_blog_author);
        TextView blinkTime = view.findViewById(R.id.tv_blog_time);
        TextView blinkBlink = view.findViewById(R.id.tv_blog_summary);
        TextView blinkResponseAll = view.findViewById(R.id.tv_blog_reponse);
        blinkName.setText(blinkInfo.getUserDisplayName());
        blinkTime.setText(blinkInfo.getDateAdded());
        blinkBlink.setText(blinkInfo.getContent());
        if(Integer.parseInt(blinkInfo.getCommentCount()) != 0 ){
            Log.d(TAG, "getView: "+blinkInfo.getCommentCount());
            blinkResponseAll.setText("查看"+blinkInfo.getCommentCount()+"条回复");
        }else{
            blinkResponseAll.setVisibility(View.GONE);
        }
        Glide.with(view).load(blinkInfo.getUserIconUrl()).into(blinkImage);

        blinkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context,WebViewActivity.class);
               intent.putExtra("url","https://home.cnblogs.com/u/"+blinkInfo.getUserAlias());
               context.startActivity(intent);
            }
        });

        blinkResponseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ResponseDetailActivity.class);
                intent.putExtra("blinkId",blinkInfo.getId());
                context.startActivity(intent);
            }
        });


        return view;
    }

}
