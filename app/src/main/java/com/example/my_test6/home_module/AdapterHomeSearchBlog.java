package com.example.my_test6.home_module;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_test6.Bean.BlogFromSearch;
import com.example.my_test6.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterHomeSearchBlog extends RecyclerView.Adapter<AdapterHomeSearchBlog.InnerHolder> {
    private List<BlogFromSearch> mData = new ArrayList<>();
    private OnItemClickListener listener;

    public  void setmData(List<BlogFromSearch> data){
        this.mData = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_adapter_search_blog,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        View itemView = holder.itemView;
        holder.setData(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnItemClick(position);
                }
            }
        });

    }

    public AdapterHomeSearchBlog() { }
    @Override
    public int getItemCount() {
        if(mData!=null){
            return mData.size();
        }
        return 0;
    }

    public  void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    public  interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public class InnerHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView user;
        private  TextView text;
        private TextView msg;
        private ImageView touXiang;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title_search_blog);
            user = itemView.findViewById(R.id.textView_user_search_blog);
            text = itemView.findViewById(R.id.textView_text_search_blog);
            msg = itemView.findViewById(R.id.textView_msg_search_blog);
            touXiang = itemView.findViewById(R.id.circleImageView);
        }

        public void setData(BlogFromSearch blogFromSearch){
            title.setText(Html.fromHtml(blogFromSearch.getTitle()));
            user.setText(blogFromSearch.getUserName()+" | "+blogFromSearch.getPublishTime().substring(0,10));
            text.setText(Html.fromHtml(blogFromSearch.getContent()));
            msg.setText(blogFromSearch.getViewTimes()+" 浏览   "+blogFromSearch.getCommentTimes()+" 评论   "+blogFromSearch.getVoteTimes()+" 点赞");
        }
    }

}
