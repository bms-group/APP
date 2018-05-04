package com.asyf.app.myView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.asyf.app.R;

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

    }


}
