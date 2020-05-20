package com.example.my_test6.ui.user.ListAdapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_test6.R;
import com.example.my_test6.ui.user.ItemBean.ItemMessage;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.innerHolder> {
    private final ArrayList<ItemMessage> mData;
    private MessageAdapter.OnItemClickListener clickListener;

    public MessageAdapter(ArrayList<ItemMessage> data){
        this.mData = data;
    }

    @NonNull
    @Override
    //创建条目View
    public MessageAdapter.innerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.user_item_message,null);
        return new MessageAdapter.innerHolder(view);
    }

    @Override
    //用来绑定holder，设置数据
    public void onBindViewHolder(@NonNull MessageAdapter.innerHolder holder, int position) {
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

    public void setOnItemClickListener(MessageAdapter.OnItemClickListener listener) {
        //设置一个Item的监听器
        clickListener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public class innerHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView Abstract;
        private TextView time;
        private int mPosition;

        public innerHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_Message_blogtitle);
            Abstract = itemView.findViewById(R.id.item_Message_abstract);
            time = itemView.findViewById(R.id.item_Message_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.OnItemClick(mPosition);
                    }
                }
            });
        }
        public void setData(ItemMessage itembean,int position){
            this.mPosition = position;
            title.setText(itembean.title);
            Abstract.setText(itembean.Abstract);
            time.setText(itembean.time);
        }
    }
}
