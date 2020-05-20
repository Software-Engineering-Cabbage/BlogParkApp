package com.example.my_test6.Pool;

import com.example.my_test6.Bean.Token;
import com.example.my_test6.netWork.TokenString;

public class TokenPool {
    private static  TokenPool tokenPool = null;
    public String UserToken = "";
    public boolean isLogin = false;
    private TokenString token = new TokenString();
    private TokenPool(){

    }
    public static  TokenPool getTokenPool(){
        if(tokenPool==null){
            tokenPool = new TokenPool();
        }
        return tokenPool;
    }
    public  String getMyToken(){
        synchronized (token){
            if(token.token=="") {
                try {
                    token.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return token.token;
    }
    public  void setMyToken(String token1){
        synchronized (token){
            token.token = token1;
            token.notifyAll();
        }
    }
}
