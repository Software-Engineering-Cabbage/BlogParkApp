package com.example.my_test6.question_module;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_test6.R;

import java.util.ArrayList;
import java.util.List;

//lqx
import java.util.LinkedList;

public class recycler_adapter extends RecyclerView.Adapter<recycler_adapter.Myviewholder> {

    private List<list_item> list_items = new ArrayList<>();
    private LinkedList<String> urlItems = new LinkedList<>();
    private Context context;
    private OnItemClickListener mlistener;

    public recycler_adapter(Context c, List<list_item> li) {
        this.context = c;
        this.list_items = li;
    }

    //lqx
    public recycler_adapter(Context c, LinkedList<String> ui) {
        this.context = c;
        this.urlItems = ui;
    }

    public void setList_items(List<list_item> data) {
        list_items = data;
        notifyDataSetChanged();
    }

    public void addList_items(List<list_item> data) {
        list_items.addAll(data);
        notifyDataSetChanged();
    }

    //lqx
    public void setUrlItems(LinkedList<String> urls) {
        urlItems = urls;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = View.inflate(parent.getContext(), R.layout.questionitem_view, null);
        return new Myviewholder(itemview,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        holder.setdata(list_items.get(position));
    }

    @Override
    public int getItemCount() {
        if (list_items != null) return list_items.size();
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mlistener = l;
    }

    class Myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView img;
        private TextView username;
        private TextView answerCount;
        private TextView questionTime;
        private OnItemClickListener mlistener;

        public Myviewholder(View itemView, OnItemClickListener listener) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_img);
            title = (TextView) itemView.findViewById(R.id.item_title);
            username = (TextView) itemView.findViewById(R.id.user_name);
            answerCount = (TextView) itemView.findViewById(R.id.answer_count);
            questionTime = (TextView) itemView.findViewById(R.id.question_time);

            mlistener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
            mlistener.onItemClick(v, getPosition());
        }

        public void setdata(list_item data) {
            //img.setImageResource(R.drawable.ic_launcher_foreground);
            title.setText(data.getTitle());
            username.setText(data.getQuestionUserInfo().getUserName());
            questionTime.setText(data.getDateAdded().substring(0,16));
            answerCount.setText(Integer.toString(data.getAnswerCount()));
            Glide.with(img.getContext())
                    .load(data.getQuestionUserInfo().getFace())
                    //.apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(img);
        }
    }

    //自定义一个监听器接口，在view holder中实现
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}
