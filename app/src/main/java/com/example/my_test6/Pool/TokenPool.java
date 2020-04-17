package com.example.my_test6.Pool;

public class TokenPool {
    private static  TokenPool tokenPool = null;
    public static String myToken = "";
    private TokenPool(){

    }
    private  TokenPool getTokenPool(){
        if(tokenPool==null){
            tokenPool = new TokenPool();
        }
        return tokenPool;
    }
}
