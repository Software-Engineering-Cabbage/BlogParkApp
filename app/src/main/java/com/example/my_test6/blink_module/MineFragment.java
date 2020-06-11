package com.example.my_test6.blink_module;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;

public class MineFragment extends Fragment {

    private static String TAG = "MineFragment";

    private boolean isReplaceMine;
    private boolean isReplaceLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isReplaceMine = false;
        isReplaceLogin = false;
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.blink_fragment_mine, container, false);
        setUI();
        return view;
    }

    private void setUI(){
            if(TokenPool.getTokenPool().isLogin){
                if(!isReplaceMine){
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mine_frame,new MineLoginFragment());
                    fragmentTransaction.commit();
                    isReplaceMine = true;
                    isReplaceLogin = false;
                }
            }else{
                if(!isReplaceLogin){
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mine_frame,new LoginFragment());
                    fragmentTransaction.commit();
                    isReplaceLogin = true;
                    isReplaceLogin = false;
                }
            }
    }


    @Override
    public void onResume() {
        super.onResume();
        setUI();
        Log.d(TAG, "onResume: ");
    }
}
