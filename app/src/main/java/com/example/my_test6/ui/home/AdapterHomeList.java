package com.example.my_test6.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_test6.Bean.BlogSummary;
import com.example.my_test6.MyWidgets.CircleImageView;
import com.example.my_test6.R;

import java.util.List;

public class AdapterHomeList extends RecyclerView.Adapter<AdapterHomeList.InnerHolder> {
    private List<BlogSummary> mData;
    public  void setmData(List<BlogSummary> mData){
        this.mData =  mData;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaper_home,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        holder.setData(mData.get(position));
    }

    public AdapterHomeList() { }
    @Override
    public int getItemCount() {
        if(mData!=null){
            return mData.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView user;
        private  TextView text;
        private TextView msg;
        private CircleImageView touXiang;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            user = itemView.findViewById(R.id.textView_user);
            text = itemView.findViewById(R.id.textView_text);
            msg = itemView.findViewById(R.id.textView_msg);
            touXiang = itemView.findViewById(R.id.circleImageView);
        }
        public void setData(BlogSummary blogSummary){
            title.setText(blogSummary.getTitle());
            user.setText(blogSummary.getAuthor());
            text.setText(blogSummary.getDescription());
            msg.setText(blogSummary.getViewCount()+" 浏览   "+blogSummary.getCommentCount()+" 评论   "+blogSummary.getDiggCount()+" 点赞");

        }
    }

}
