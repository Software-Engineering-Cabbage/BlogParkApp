package com.example.my_test6.user_module.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.my_test6.R;
import com.example.my_test6.blink_module.WebViewActivity;
import com.example.my_test6.blink_module.blinkBean.blinkResponseInfo;
import com.example.my_test6.user_module.GsonBean.BlogResponse;

import java.util.List;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

public class BlogResponseListAdapter extends BaseAdapter {
    private Context context;
    private static String TAG = "GY blinkResponseListAdapter";
    private List<BlogResponse> blinkResponseInfoList;
    private LayoutInflater layoutInflater;

    public BlogResponseListAdapter(Context context , List<BlogResponse> blinkResponseInfoList) {
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
        final BlogResponse blinkResponseInfo = blinkResponseInfoList.get(position);
        final ImageView blinkResponseImage = view.findViewById(R.id.img_response_avatar);
        TextView blinkResponseName = view.findViewById(R.id.tv_response_author);
        TextView blinkResponseTime = view.findViewById(R.id.tv_response_time);
        TextView blinkResponseBlink = view.findViewById(R.id.tv_response_summary);

        blinkResponseName.setText(blinkResponseInfo.Author);
        blinkResponseTime.setText(blinkResponseInfo.DateAdded);
        blinkResponseBlink.setText(Html.fromHtml(blinkResponseInfo.Body,FROM_HTML_MODE_COMPACT));
        Glide.with(view)
                .load(blinkResponseInfo.FaceUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(blinkResponseImage);

        blinkResponseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url",blinkResponseInfo.AuthorUrl);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
