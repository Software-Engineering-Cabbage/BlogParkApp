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
import com.example.my_test6.blink_module.WebViewActivity;
import com.example.my_test6.blink_module.blinkBean.blinkResponseInfo;

import java.util.List;

public class blinkResponseListAdapter extends BaseAdapter  {
    private Context context;
    private static String TAG = "GY blinkResponseListAdapter";
    private List<blinkResponseInfo> blinkResponseInfoList;
    private LayoutInflater layoutInflater;

    public blinkResponseListAdapter(Context context , List<blinkResponseInfo> blinkResponseInfoList) {
        Log.d(TAG, "blinkListAdapter: context"+context);
        this.context = context;
        this.blinkResponseInfoList = blinkResponseInfoList;
        layoutInflater = LayoutInflater.from(context);
        Log.d(TAG, "construct:"+this.blinkResponseInfoList.size());
    }
    @Override
    public int getCount() {
        return blinkResponseInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return blinkResponseInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.blink_response_item,null);
        final blinkResponseInfo blinkResponseInfo = blinkResponseInfoList.get(position);
        final ImageView blinkResponseImage = view.findViewById(R.id.img_response_avatar);
        TextView blinkResponseName = view.findViewById(R.id.tv_response_author);
        TextView blinkResponseTime = view.findViewById(R.id.tv_response_time);
        TextView blinkResponseBlink = view.findViewById(R.id.tv_response_summary);

        blinkResponseName.setText(blinkResponseInfo.getUserDisplayName());
        blinkResponseTime.setText(blinkResponseInfo.getDateAdded());
        blinkResponseBlink.setText(blinkResponseInfo.getContent());
        Glide.with(view).load(blinkResponseInfo.getUserIconUrl()).into(blinkResponseImage);

        blinkResponseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url","https://home.cnblogs.com/u/"+blinkResponseInfo.getUserAlias());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
