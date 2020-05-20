package com.example.my_test6.blink_module;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.my_test6.R;
import com.example.my_test6.Pool.login;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    private Button blink_login;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blink_fragment_login, container, false);
        blink_login = view.findViewById(R.id.blink_login);
        blink_login.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.blink_login:
                Intent intent = new Intent(getActivity(), login.class);
                startActivity(intent);
                break;
        }
    }
}
