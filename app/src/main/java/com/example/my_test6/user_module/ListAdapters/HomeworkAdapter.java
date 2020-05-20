package com.example.my_test6.user_module.ListAdapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_test6.R;
import com.example.my_test6.user_module.ItemBean.ItemHomework;

import java.util.ArrayList;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.innerHolder> {

    private final ArrayList<ItemHomework> mData;
    private OnItemClickListener clickListener;

    public HomeworkAdapter(ArrayList<ItemHomework> data){
        this.mData = data;
    }

    @NonNull
    @Override
    //创建条目View
    public innerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.user_item_homework,null);
        return new innerHolder(view);
    }

    @Override
    //用来绑定holder，设置数据
    public void onBindViewHolder(@NonNull innerHolder holder, int position) {
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

    public void setOnItemClickListener(OnItemClickListener listener) {
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
        private ImageView src;
        private ImageView head;
        private int mPosition;

        public innerHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_blogtitle);
            author = itemView.findViewById(R.id.item_author);
            Abstract = itemView.findViewById(R.id.item_abstract);
            comment = itemView.findViewById(R.id.item_comment);
            time = itemView.findViewById(R.id.item_time);
            src = itemView.findViewById(R.id.item_status);
            head = itemView.findViewById(R.id.item_Userhead);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.OnItemClick(mPosition);
                    }
                }
            });
        }
        public void setData(ItemHomework itembean,int position){
            this.mPosition = position;
            title.setText(itembean.title);
            comment.setText(itembean.comment);
            author.setText(itembean.author);
            Abstract.setText(itembean.Abstract);
            time.setText(itembean.time);
            Glide.with(head.getContext()).load(itembean.avatarUrl).into(head);
            if(itembean.src == 0)
                src.setImageResource(R.drawable.cross);
            else
                src.setImageResource(R.drawable.hook);
        }
    }
}
