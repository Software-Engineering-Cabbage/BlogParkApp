package com.example.my_test6.question_module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_test6.Pool.netWork.DeleteApi;
import com.example.my_test6.R;
import com.example.my_test6.user_module.ItemBean.ItemCollection;
import com.example.my_test6.user_module.ItemTouchHelper.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.List;

public class recycler_adapter extends RecyclerView.Adapter<recycler_adapter.Myviewholder> implements ItemTouchHelperAdapter {

    private List<list_item> list_items = new ArrayList<>();
    private Context context;
    private OnItemClickListener mlistener;

    public recycler_adapter(Context c, List<list_item> li) {
        this.context = c;
        this.list_items = li;
    }

    public void setList_items(List<list_item> data) {
        list_items = data;
        notifyDataSetChanged();
    }

    public void addList_items(List<list_item> data) {
        list_items.addAll(data);
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

    @Override
    public void onItemDelete(int position) {
        list_item del = list_items.get(position);
        String url = "https://api.cnblogs.com/api/questions/" + del.Qid;
        System.out.println("Id为： " + del.Qid);
        DeleteApi delapi = new DeleteApi();
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 1){
                    //删除成功
                    String json = (String)msg.obj;
                }
            }
        };
        delapi.Delete(handler, url,1);
        list_items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemRefresh(int position) {
        notifyItemChanged(position);
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
