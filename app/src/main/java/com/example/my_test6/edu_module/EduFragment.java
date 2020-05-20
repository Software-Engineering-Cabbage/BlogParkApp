package com.example.my_test6.edu_module;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.my_test6.Pool.TokenPool;
import com.example.my_test6.R;
import com.example.my_test6.Pool.netWork.GetUserApi;
import com.example.my_test6.edu_module.Notice.ClassInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EduFragment extends Fragment {
    private EduViewModel eduViewModel;
    private List<ClassInfo> ClassInfoList;
    private  View root;
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        eduViewModel =
                ViewModelProviders.of(this).get(EduViewModel.class);
        root = inflater.inflate(R.layout.edu_fragment_edu, container, false);
        if(TokenPool.getTokenPool().isLogin) {
            final GetUserApi getApi = new GetUserApi();
            @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    final List<EduClass> ctype = new ArrayList<>();
                    String text = (String) msg.obj;
                    //Log.d("Debug",text);
                    final ListView listView = root.findViewById(R.id.EduListView);
                    Gson gson = new Gson();
                    ClassInfoList = gson.fromJson(text, new TypeToken<List<ClassInfo>>() {
                    }.getType());
                    if(ClassInfoList!=null) {
                        for (int i = 0; i < ClassInfoList.size(); i++) {
                            EduClass eduClass = new EduClass(ClassInfoList.get(i).getIcon(), ClassInfoList.get(i).getNameCn());
                            ctype.add(eduClass);
                        }
                        EduAdapter adapter = new EduAdapter(getActivity(), R.layout.edu_class_member, ctype);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getActivity(), new ClassActivity().getClass());
                                intent.putExtra("name", ctype.get(position).getName());
                                intent.putExtra("id", ClassInfoList.get(position).getSchoolClassId());
                                intent.putExtra("url", ClassInfoList.get(position).getUrl());
                                startActivity(intent);
                            }
                        });
                    }
                    else{
                        TextView textView=root.findViewById(R.id.emptyClass);
                        textView.setText("您尚未加入任何班级...");
                    }
                }
            };
            getApi.getMyApi(handler, "https://api.cnblogs.com/api/edu/member/schoolclasses", 0);
        }
        else{
            Intent intent = new Intent();
            ComponentName componentname = new ComponentName("com.example.my_test6", "com.example.my_test6.Pool.login");
            intent.setComponent(componentname);
            startActivity(intent);
        }
        return root;
    }
    public void onResume(){
        if(TokenPool.getTokenPool().isLogin) {
            final GetUserApi getApi = new GetUserApi();
            @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    final List<EduClass> ctype = new ArrayList<>();
                    String text = (String) msg.obj;
                    //Log.d("Debug",text);
                    Gson gson = new Gson();
                    ClassInfoList = gson.fromJson(text, new TypeToken<List<ClassInfo>>() {
                    }.getType());
                    if (ClassInfoList != null) {
                        for (int i = 0; i < ClassInfoList.size(); i++) {
                            EduClass eduClass = new EduClass(ClassInfoList.get(i).getIcon(), ClassInfoList.get(i).getNameCn());
                            ctype.add(eduClass);
                        }
                        EduAdapter adapter = new EduAdapter(getActivity(), R.layout.edu_class_member, ctype);
                        final ListView listView = root.findViewById(R.id.EduListView);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getActivity(), new ClassActivity().getClass());
                                intent.putExtra("name", ctype.get(position).getName());
                                intent.putExtra("id", ClassInfoList.get(position).getSchoolClassId());
                                intent.putExtra("url", ClassInfoList.get(position).getUrl());
                                startActivity(intent);
                            }
                        });
                    }
                    else{
                        TextView textView=root.findViewById(R.id.emptyClass);
                        textView.setText("您尚未加入任何班级...");
                    }
                }
            };
            getApi.getMyApi(handler, "https://api.cnblogs.com/api/edu/member/schoolclasses", 0);
        }
        super.onResume();
    }
}
