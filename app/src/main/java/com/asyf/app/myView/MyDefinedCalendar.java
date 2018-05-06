package com.asyf.app.myView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.asyf.app.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyDefinedCalendar extends LinearLayout {

    private List<String> list;
    private ArrayAdapter<String> adapter;
    private MyView myView;

    public MyDefinedCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.my_defined_calendar, this);
        list = new ArrayList<>();
        //创建ArrayAdapter
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, list);
        //获取ListView对象，通过调用setAdapter方法为ListView设置Adapter设置适配器
        ListView listView = (ListView) findViewById(R.id.my_defined_listView);
        listView.setAdapter(adapter);
        myView = findViewById(R.id.my_defined_my_view);
        myView.setOnDateClickListener(new MyView.OnDateClickListener() {
            @Override
            public void onMonthClick(String date) {
                //日期变化的时候清空列表数据
                adapter.clear();
                adapter.notifyDataSetChanged();
                if (onDateChangeListener != null)
                    onDateChangeListener.onMonthClick(date);
            }

            @Override
            public void onDayClick(String date) {
                adapter.clear();
                adapter.notifyDataSetChanged();
                if (onDateChangeListener != null)
                    onDateChangeListener.onDayClick(date);
            }
        });
    }

    //==============================public ↓↓↓↓↓↓↓==============================
    public void addListData(List<String> list) {
        adapter.clear();
        for (String s : list) {
            adapter.add(s);
        }
        adapter.notifyDataSetChanged();
    }

    public void addEvents(Set<Integer> set) {
        myView.AddEvents(set);
    }

    public String getCurrentDate() {
        return myView.getCurrentDate();
    }

    //==============================public ↑↑↑↑↑↑↑==============================

    //==============================接口 ↓↓↓↓↓↓↓==============================
    private OnDateChangeListener onDateChangeListener;

    public interface OnDateChangeListener {
        public void onDayClick(String date);

        public void onMonthClick(String date);

    }

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }

    //==============================接口 ↑↑↑↑↑↑↑==============================

}
