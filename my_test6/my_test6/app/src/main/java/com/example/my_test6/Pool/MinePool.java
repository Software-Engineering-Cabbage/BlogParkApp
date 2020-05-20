package com.example.my_test6.Pool;

import com.example.my_test6.ui.user.GsonBean.MyBlogs;
import com.example.my_test6.ui.user.GsonBean.Users;

public class MinePool {
    private static  MinePool minePool = null;
    public Users users;
    public MyBlogs myblogs;
    private MinePool(){

    }

    public static  MinePool getMinePool(){
        if(minePool==null){
            minePool = new MinePool();
        }
        return minePool;
    }
}
