package com.example.my_test6.user_module;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class myItemTouchHelperCallBack extends ItemTouchHelper.Callback{
    private ItemTouchHelperAdapter itemTouchHelperAdapter;
    private Context context;
    private String title;

    public myItemTouchHelperCallBack(ItemTouchHelperAdapter itemTouchHelperAdapter, Context context, String title) {
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
        this.context = context;
        this.title = title;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //允许上下拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //允许从右向左滑动
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //onItemDelete接口里的方法
        showCoverDialog(viewHolder);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        //该方法返回值为true时，表示支持长按ItemView拖动
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        //该方法返回true时，表示如果用户触摸并且左滑了view，那么可以执行滑动删除操作，就是可以调用onSwiped()方法
        return true;
    }

    private void showCoverDialog(final RecyclerView.ViewHolder viewHolder){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemTouchHelperAdapter.onItemDelete(viewHolder.getAdapterPosition());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemTouchHelperAdapter.onItemRefresh(viewHolder.getAdapterPosition());
            }
        });
        builder.show();
    }
}
