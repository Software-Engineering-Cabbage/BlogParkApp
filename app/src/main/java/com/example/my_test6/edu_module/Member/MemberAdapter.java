package com.example.my_test6.edu_module.Member;

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
import com.example.my_test6.edu_module.ItemTouchHelperAdapter;

import java.util.List;

public class MemberAdapter extends ArrayAdapter {
    private final int resource;
    public MemberAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Member member=(Member) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource, null);
        ImageView imageView=view.findViewById(R.id.MemberImage);
        TextView textView=view.findViewById(R.id.MemberName);
        textView.setText(member.getName());
        Glide.with(imageView.getContext()).load("https:"+member.getAvater()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
        return view;
    }


}
