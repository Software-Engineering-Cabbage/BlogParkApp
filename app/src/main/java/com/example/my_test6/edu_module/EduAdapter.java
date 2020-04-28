package com.example.my_test6.edu_module;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.my_test6.R;

import java.util.List;

public class EduAdapter extends ArrayAdapter {
    private final int resource;
    public EduAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EduClass eduClass=(EduClass) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource, null);
        ImageView imageView=view.findViewById(R.id.MemberImage);
        TextView textView=view.findViewById(R.id.MemberName);
        textView.setText(eduClass.getName());
        Glide.with(imageView.getContext()).load(eduClass.getAvater()).into(imageView);
        Log.d("Test",eduClass.getAvater());
        return view;
    }
}
