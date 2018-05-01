package com.asyf.app.fragment;


import android.content.Context;
import android.content.Intent;
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
import com.asyf.app.activity.RecyclerviewActivity;
import com.asyf.app.activity.ServiceActivity;
import com.asyf.app.adapter.MyAdapter;
import com.asyf.app.entity.Icon;
import com.asyf.app.observe.EventBadgeItem;
import com.asyf.app.service.PushService;

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
    private FragmentActivity activity;

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
        activity = getActivity();
        Toast.makeText(activity, "sss", Toast.LENGTH_SHORT).show();
        grid_photo = activity.findViewById(R.id.grid_photo);
        mData = new ArrayList<Icon>();
        //for (int i = 0; i < 10; i++) {
        mData.add(new Icon(R.mipmap.iv_icon_1, "RecyclerView"));
        mData.add(new Icon(R.mipmap.iv_icon_2, "服务"));
        mData.add(new Icon(R.mipmap.iv_icon_3, "图标3"));
        mData.add(new Icon(R.mipmap.iv_icon_4, "图标4"));
        mData.add(new Icon(R.mipmap.iv_icon_5, "图标5"));
        mData.add(new Icon(R.mipmap.iv_icon_6, "图标6"));
        mData.add(new Icon(R.mipmap.iv_icon_7, "图标7"));
        //}
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
                switch (position) {
                    case 0:
                        Intent intent = new Intent(activity, RecyclerviewActivity.class);
                        activity.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(activity, ServiceActivity.class);
                        activity.startActivity(intent1);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
