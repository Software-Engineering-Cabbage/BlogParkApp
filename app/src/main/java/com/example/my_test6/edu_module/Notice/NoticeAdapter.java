package com.example.my_test6.edu_module.Notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.my_test6.R;

import java.util.List;

public class NoticeAdapter extends ArrayAdapter {
    private final int resource;
    public NoticeAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClassNotice classNotice=(ClassNotice)getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource, null);
        TextView context=view.findViewById(R.id.item_blogtitle);
        TextView date=view.findViewById(R.id.item_time);
        TextView detail = view.findViewById(R.id.item_abstract);
        TextView author = view.findViewById(R.id.item_author);
        ImageView head = view.findViewById(R.id.item_Userhead);
        TextView submit = view.findViewById(R.id.item_comment);
        ImageView src = view.findViewById(R.id.item_status);
        author.setText(classNotice.author);
        context.setText(classNotice.getContext());
        date.setText(classNotice.getDate());
        detail.setText(classNotice.detail);
        Glide.with(head.getContext()).load(classNotice.head).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(head);
        submit.setText("提交：" + classNotice.submit);
        if(classNotice.src == 1){
            src.setImageResource(R.drawable.cross);
        }
        else{
            src.setImageResource(R.drawable.hook);
        }
        return view;
    }
}
