package com.example.my_test6.edu_module.Manager.Member;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.my_test6.Pool.MinePool;
import com.example.my_test6.Pool.netWork.DeleteApiBody;
import com.example.my_test6.R;
import com.example.my_test6.edu_module.ItemTouchHelperAdapter;
import com.example.my_test6.edu_module.Member.ClassMember;
import com.example.my_test6.edu_module.Member.Member;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.innerHolder> implements ItemTouchHelperAdapter {
    private  List<Member> MemberList;
    private MemberAdapter.OnItemClickListener clickListener;
    private int schoolclassid;
    private List<ClassMember> classMemberList;
    private MediaType mediaType
            = MediaType.parse("application/json; charset=utf-8");
    private @SuppressLint("HandlerLeak")final Handler handler=new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String text = (String) msg.obj;
            System.out.println(text);
        }
    };
    public MemberAdapter(List<Member> MemberList,int id,List<ClassMember> classMemberList){
        this.MemberList=MemberList;
        this.schoolclassid=id;
        this.classMemberList=classMemberList;
    }
    @NonNull
    @Override
    public MemberAdapter.innerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.edu_class_manager_member,null);
        return new MemberAdapter.innerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.innerHolder holder, int position) {
        holder.setData(MemberList.get(position),position);
    }

    @Override
    public int getItemCount() {
        if(MemberList!= null){
            return MemberList.size();
        }
        return 0;
    }
    public void setOnItemClickListener(MemberAdapter.OnItemClickListener listener) {
        //设置一个Item的监听器
        clickListener = listener;
    }

    @Override
    public void onItemDelete(int position) {
        DeleteApiBody deleteApi=new DeleteApiBody();
        System.out.println("班级id:"+schoolclassid+"  成员id"+classMemberList.get(position).getMemberId());
        String bodyString="{blogId:"+MinePool.getMinePool().users.BlogId+"}";
        RequestBody body=RequestBody.create(bodyString,mediaType);
        deleteApi.Delete(handler,"https://api.cnblogs.com/api/edu/member/remove/"+schoolclassid+"/"+classMemberList.get(position).getMemberId(),body,0);
        MemberList.remove(position);
        classMemberList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemRefresh(int position) {
        notifyItemChanged(position);
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public class innerHolder extends RecyclerView.ViewHolder {
        private int mPosition;
        private TextView name;
        private ImageView avater;
        public innerHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.MemberManagerName);
            avater=itemView.findViewById(R.id.MemberManagerImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.OnItemClick(mPosition);
                    }
                }
            });
        }
        public void setData(Member itembean, int position){
           this.mPosition=position;
           name.setText(itembean.getName());
           Glide.with(avater.getContext()).load("https:"+itembean.getAvater()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avater);
        }
    }
}
