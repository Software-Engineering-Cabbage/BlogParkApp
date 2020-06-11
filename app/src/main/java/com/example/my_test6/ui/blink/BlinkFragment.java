package com.example.my_test6.ui.blink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.my_test6.R;

public class BlinkFragment extends Fragment {

    private BlinkViewModel blinkViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        blinkViewModel =
                ViewModelProviders.of(this).get(BlinkViewModel.class);
        View root = inflater.inflate(R.layout.user_fragment_blink, container, false);
        final TextView textView = root.findViewById(R.id.text_blink);
        blinkViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
