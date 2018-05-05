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

import java.util.HashSet;
import java.util.Set;

public class MyDefinedCalendar extends LinearLayout {

    public MyDefinedCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.my_defined_calendar, this);
        //要显示的数据
        String[] strs = {"基神", "B神", "翔神", "曹神", "J神"};
        //创建ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, strs);
        //获取ListView对象，通过调用setAdapter方法为ListView设置Adapter设置适配器
        ListView list_test = (ListView) findViewById(R.id.my_defined_listView);
        list_test.setAdapter(adapter);
        MyView myView = findViewById(R.id.my_defined_my_view);
        myView.setOnDateClickListener(new MyView.OnDateClickListener() {
            @Override
            public void onMonthClick(String date) {
                //Toast.makeText(getContext(), "您点击了" + date, Toast.LENGTH_SHORT).show();
                if (onDateChangeListener != null)
                    onDateChangeListener.onMonthClick(date);
            }

            @Override
            public void onDayClick(String date) {
                //Toast.makeText(getContext(), "您点击了" + date, Toast.LENGTH_SHORT).show();
                if (onDateChangeListener != null)
                    onDateChangeListener.onDayClick(date);
            }
        });
        Set<Integer> set = new HashSet<>();
        for (int i = 1; i < 32; i++) {
            set.add(i);
        }
        myView.AddEvents(set);
    }

    //==============================接口 ↓↓↓↓↓↓↓==============================
    private OnDateChangeListener onDateChangeListener;

    public interface OnDateChangeListener {
        public void onDayClick(String date);

        public void onMonthClick(String date);

    }

    public void setOnDateClickListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }

    //==============================接口 ↑↑↑↑↑↑↑==============================

}
