package com.example.my_test6.user_module.ListAdapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_test6.R;
import com.example.my_test6.user_module.ItemBean.ItemCollection;


import java.util.ArrayList;

public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.innerHolder> {
    private final ArrayList<ItemCollection> mData;
    private MyCollectionAdapter.OnItemClickListener clickListener;

    public MyCollectionAdapter(ArrayList<ItemCollection> data){
        this.mData = data;
    }

    @NonNull
    @Override
    //创建条目View
    public MyCollectionAdapter.innerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.user_item_collection,null);
        return new MyCollectionAdapter.innerHolder(view);
    }

    @Override
    //用来绑定holder，设置数据
    public void onBindViewHolder(@NonNull MyCollectionAdapter.innerHolder holder, int position) {
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

    public void setOnItemClickListener(MyCollectionAdapter.OnItemClickListener listener) {
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
            title = itemView.findViewById(R.id.item_Collection_blogtitle);
            Abstract = itemView.findViewById(R.id.item_Collection_abstract);
            time = itemView.findViewById(R.id.item_Collection_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.OnItemClick(mPosition);
                    }
                }
            });
        }
        public void setData(ItemCollection itembean,int position){
            this.mPosition = position;
            title.setText(itembean.title);
            Abstract.setText(itembean.Abstract);
            time.setText(itembean.time);
        }
    }
}
