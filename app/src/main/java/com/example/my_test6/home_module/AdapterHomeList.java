package com.example.my_test6.home_module;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.my_test6.Bean.BlogSummary;
import com.example.my_test6.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterHomeList extends RecyclerView.Adapter<AdapterHomeList.InnerHolder> {
    private List<BlogSummary> mData = new ArrayList<>();
    private OnItemClickListener listener;

    public  void setmData(List<BlogSummary> data){
      this.mData = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_adaper_home,parent,false);
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

    public AdapterHomeList() { }
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
            Glide.with(touXiang.getContext())
                    .load(blogSummary.getAvatar())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(touXiang);
        }
    }

}
