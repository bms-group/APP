package com.asyf.app.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.asyf.app.R;
import com.asyf.app.adapter.MyAdapter;
import com.asyf.app.entity.Icon;
import com.asyf.app.observe.EventBadgeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    private GridView grid_photo;
    private BaseAdapter mAdapter = null;
    private ArrayList<Icon> mData = null;
    private int count = 0;

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fg_test, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FragmentActivity activity = getActivity();
        Toast.makeText(activity, "sss", Toast.LENGTH_SHORT).show();
        grid_photo = activity.findViewById(R.id.grid_photo);
        mData = new ArrayList<Icon>();
        for (int i = 0; i < 100; i++) {
            mData.add(new Icon(R.mipmap.iv_icon_1, "图标1"));
            mData.add(new Icon(R.mipmap.iv_icon_2, "图标2"));
            mData.add(new Icon(R.mipmap.iv_icon_3, "图标3"));
            mData.add(new Icon(R.mipmap.iv_icon_4, "图标4"));
            mData.add(new Icon(R.mipmap.iv_icon_5, "图标5"));
            mData.add(new Icon(R.mipmap.iv_icon_6, "图标6"));
            mData.add(new Icon(R.mipmap.iv_icon_7, "图标7"));
        }
        mAdapter = new MyAdapter<Icon>(mData, R.layout.item_grid_icon) {
            @Override
            public void bindView(ViewHolder holder, Icon obj) {
                holder.setImageResource(R.id.img_icon, obj.getiId());
                holder.setText(R.id.txt_icon, obj.getiName());
            }
        };

        grid_photo.setAdapter(mAdapter);

        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(activity, "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
                EventBadgeItem.getInstance().post(String.valueOf(count++));
            }
        });
    }
}
