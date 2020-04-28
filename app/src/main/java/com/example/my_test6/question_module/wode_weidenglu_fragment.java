package com.example.my_test6.question_module;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;
import com.example.my_test6.Pool.login;

public class wode_weidenglu_fragment extends Fragment {

    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.question_view_wode_weidenglu, container, false);

        button = root.findViewById(R.id.question_denglu_button);
        button.setOnClickListener(l);

        return root;
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), login.class);
            startActivity(intent);
            Log.d("TAG","点击登录按钮，启动跳转到登陆网页");
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (TokenPool.getTokenPool().isLogin) {
            setUI();
        }
    }

    private void setUI() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        wode_fragment f = new wode_fragment();
        ft.replace(R.id.wode_fragment_weidenglu,f);
        ft.commit();
        Log.d("TAG: ","已从未登陆界面到我的界面");
    }
}
