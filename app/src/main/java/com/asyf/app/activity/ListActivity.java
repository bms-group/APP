package com.asyf.app.activity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.asyf.app.R;
import com.asyf.app.common.Logger;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private final String TAG = "ListActivity";
    private boolean isRefresh = false;//是否刷新中
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView mListView = findViewById(R.id.lv);
        data = new ArrayList<String>();

        for (int i = 1; i < 10; i++) {
            data.add("这是第" + i + "个数据");
        }
        //初始化adapter
        mAdapter = new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_list_item_1, data);

        mListView.setAdapter(mAdapter);
        mListView.setItemsCanFocus(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = mAdapter.getItem(position);
                Toast.makeText(ListActivity.this, "你点击了,item=" + item,
                        Toast.LENGTH_SHORT).show();
            }
        });

        //初始化下拉刷新
        initSwipeRefesh();

    }

    private void initSwipeRefesh() {


        final SwipeRefreshLayout mSwipeLayout = findViewById(R.id.swipeLayout);
        //设置进度条的颜色主题，最多能设置四种 加载颜色是循环播放的，只要没有完成刷新就会一直循环，holo
        // _blue_bright>holo_green_light>holo_orange_light>holo_red_light
        mSwipeLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);

        //设置下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Logger.e(TAG, "刷新");
                //检查是否处于刷新状态
                if (!isRefresh) {
                    isRefresh = true;
                    //模拟加载网络数据，这里设置4秒，正好能看到4色进度条
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            //显示或隐藏刷新进度条
                            mSwipeLayout.setRefreshing(false);
                            //修改adapter的数据
                            data.add(0, "这是新添加的数据");
                            mAdapter.notifyDataSetChanged();
                            isRefresh = false;
                        }
                    }, 4000);
                }
            }
        });
    }
}
