package com.example.my_test6.edu_module.Manager;

public class Pool {
    private static Pool pool = null;
    public int bulletinid;
    private Pool(){

    }

    public static  Pool getMinePool(){
        if(pool==null){
            pool = new Pool();
        }
        return pool;
    }
}
