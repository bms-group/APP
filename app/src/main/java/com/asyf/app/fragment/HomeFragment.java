package com.asyf.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asyf.app.R;
import com.asyf.app.activity.ListActivity;
import com.asyf.app.activity.LoginTestActivity;
import com.asyf.app.activity.VideoPlayerActivity;
import com.asyf.app.common.Logger;
import com.asyf.app.jni.JniTest;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private final String TAG = "HomeFragment";
    private String content = "12ss";

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_home, container, false);
        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        //txt_content.setText(content);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FragmentActivity activity = getActivity();
        Button button = activity.findViewById(R.id.swipe_refresh);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e(TAG, "点击碎片中的按钮");
                Intent intent = new Intent(activity, ListActivity.class);
                startActivity(intent);
            }
        });
        Button test = activity.findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginTestActivity.class);
                startActivity(intent);
            }
        });
        Button jtc = activity.findViewById(R.id.javatoc);
        jtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = new JniTest().getStrFromC();
                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
            }
        });
        Button jiecao = activity.findViewById(R.id.jiaozi);
        jiecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, VideoPlayerActivity.class);
                startActivity(intent);
            }
        });
    }
}
