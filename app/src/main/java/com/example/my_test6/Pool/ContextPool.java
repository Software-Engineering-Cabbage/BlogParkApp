package com.example.my_test6.Pool;

import android.content.Context;

import com.example.my_test6.user_module.GsonBean.MyBlogs;
import com.example.my_test6.user_module.GsonBean.Users;

public class ContextPool {
    private static  ContextPool contextPool = null;
    public Context context;
    private ContextPool(){

    }

    public static  ContextPool getContextPool(){
        if(contextPool==null){
            contextPool = new ContextPool();
        }
        return contextPool;
    }
}
