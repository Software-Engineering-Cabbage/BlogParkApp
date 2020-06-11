package com.example.my_test6.user_module.test;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchStatus {
    boolean onItemRemove(int position);
    void onSaveItemStatus(RecyclerView.ViewHolder viewHolder);
}
