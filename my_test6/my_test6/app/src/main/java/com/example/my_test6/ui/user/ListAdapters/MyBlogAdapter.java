package com.example.my_test6.ui.user.ListAdapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_test6.R;
import com.example.my_test6.ui.user.ItemBean.ItemMyBlog;

import java.util.ArrayList;

public class MyBlogAdapter extends RecyclerView.Adapter<MyBlogAdapter.innerHolder> {
    private final ArrayList<ItemMyBlog> mData;
    private MyBlogAdapter.OnItemClickListener clickListener;

    public MyBlogAdapter(ArrayList<ItemMyBlog> data){
        this.mData = data;
    }

    @NonNull
    @Override
    //创建条目View
    public MyBlogAdapter.innerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.user_item_blog,null);
        return new MyBlogAdapter.innerHolder(view);
    }

    @Override
    //用来绑定holder，设置数据
    public void onBindViewHolder(@NonNull MyBlogAdapter.innerHolder holder, int position) {
        holder.setData(mData.get(position),position);
    }

    @Override
    //返回条目的个数
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(MyBlogAdapter.OnItemClickListener listener) {
        //设置一个Item的监听器
        clickListener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public class innerHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private TextView Abstract;
        private TextView comment;
        private TextView time;
        private ImageView head;
        private int mPosition;

        public innerHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_Homework_blogtitle);
            author = itemView.findViewById(R.id.item_Homework_author);
            Abstract = itemView.findViewById(R.id.item_Homework_abstract);
            comment = itemView.findViewById(R.id.item_Homework_comment);
            time = itemView.findViewById(R.id.item_Homework_time);
            head = itemView.findViewById(R.id.item_Homework_Userhead);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.OnItemClick(mPosition);
                    }
                }
            });
        }
        public void setData(ItemMyBlog itembean,int position){
            this.mPosition = position;
            title.setText(itembean.title);
            comment.setText(itembean.comment);
            author.setText(itembean.author);
            Abstract.setText(itembean.Abstract);
            time.setText(itembean.time);
            Glide.with(head.getContext()).load(itembean.head).into(head);
    }
    }
}
