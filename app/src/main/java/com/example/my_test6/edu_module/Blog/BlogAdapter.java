package com.example.my_test6.edu_module.Blog;

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

public class BlogAdapter extends ArrayAdapter {
    private final int resource;
    public BlogAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClassBlog classBlog=(ClassBlog)getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource, null);
        ImageView avater=view.findViewById(R.id.item_Homework_Userhead);
        TextView title=view.findViewById(R.id.item_Homework_blogtitle);
        TextView name=view.findViewById(R.id.item_Homework_author);
        TextView date=view.findViewById(R.id.item_Homework_time);
        TextView detail = view.findViewById(R.id.item_Homework_abstract);
        TextView read = view.findViewById(R.id.item_Homework_read);
        TextView comment = view.findViewById(R.id.item_Homework_comment);
        title.setText(classBlog.getTitle());
        name.setText(classBlog.getName());
        date.setText(classBlog.getDate().split("T")[0]);
        Glide.with(avater.getContext()).load(classBlog.getAvater()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avater);
        detail.setText(classBlog.detail);
        read.setText("浏览：" + classBlog.read);
        comment.setText("评论：" + classBlog.comment);
        return view;
    }
}
