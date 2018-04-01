package com.asyf.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asyf.app.R;

public class HomeFragment extends Fragment {

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
}
