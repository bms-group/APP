package com.asyf.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.asyf.app.R;
import com.asyf.app.myView.MyDefinedCalendar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScheduleFragment extends Fragment {


    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fg_schedule, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        final MyDefinedCalendar myDefinedCalendar = activity.findViewById(R.id.my_calendar);
        myDefinedCalendar.setOnDateChangeListener(new MyDefinedCalendar.OnDateChangeListener() {
            @Override
            public void onDayClick(String date) {
                Toast.makeText(getContext(), "您点击了" + date, Toast.LENGTH_SHORT).show();
                List<String> list = new ArrayList<>();
                list.add("qwqjhoq" + date);
                list.add("adssd" + date);
                list.add("第三行数据楼上的说的宏伟好红红好我好 三生三世" + date);
                list.add("第4行数据" + date);
                list.add("第5行数据" + date);
                list.add("第6行数据" + date);
                list.add("第7行数据" + date);
                list.add("第8行数据" + date);
                myDefinedCalendar.addListData(list);
                Set<Integer> set = new HashSet<>();
                set.add(Integer.valueOf(date.split("-")[2]));
                myDefinedCalendar.addEvents(set);
            }

            @Override
            public void onMonthClick(String date) {
                Toast.makeText(getContext(), "您点击了" + date, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
