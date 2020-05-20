package com.example.my_test6.edu_module.Notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
        TextView context=view.findViewById(R.id.noticeContent);
        TextView date=view.findViewById(R.id.noticeDate);
        context.setText(classNotice.getContext());
        date.setText(classNotice.getDate());
        return view;
    }
}
