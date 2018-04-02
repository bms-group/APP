package com.asyf.app.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.asyf.app.R;
import com.asyf.app.common.Logger;
import com.asyf.app.fragment.HomeFragment;
import com.asyf.app.fragment.MyFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarUtils;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements OnDateSelectedListener {
    CalendarDay currentDate;//自定义的日期对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //日历
        //initCalendar();

        //toolbar
        initToolBar();

        //底部导航
        initButtom();
    }

    private void initToolBar() {
        QMUITopBarLayout qmuiTopBarLayout = findViewById(R.id.qmui_top_bar);
        qmuiTopBarLayout.setTitle("Title");
        //qmuiTopBarLayout.setSubTitle("subTitle");
        //qmuiTopBarLayout.addRightTextButton("?", R.mipmap.test);
        QMUIAlphaImageButton qmuiAlphaImageButton = qmuiTopBarLayout.addRightImageButton(R.mipmap.test, R.id.qmui_top_bar);
        qmuiAlphaImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击右侧", Toast.LENGTH_LONG).show();
            }
        });
        qmuiTopBarLayout.addLeftImageButton(R.mipmap.test, R.id.qmui_top_bar);
        qmuiTopBarLayout.setTitleGravity(Gravity.CENTER);
    }

    /*private void initCalendar() {
        MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        CalendarDay calendarDay = CalendarDay.from(CalendarUtils.getInstance(new Date()));
        Set<CalendarDay> set = new HashSet<>();
        set.add(calendarDay);
        EventDecorator eventDecorator = new EventDecorator(Color.parseColor("red"), set);
        calendarView.addDecorator(eventDecorator);

        calendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_SHORT).show();
            }
        });
        //时间改变
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Toast.makeText(MainActivity.this, "月份变化", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void initButtom() {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.test, "Home").setActiveColorResource(R.color.qmui_config_color_blue))
                .addItem(new BottomNavigationItem(R.mipmap.test, "Books").setActiveColorResource(R.color.qmui_config_color_blue))
                .addItem(new BottomNavigationItem(R.mipmap.test, "Music").setActiveColorResource(R.color.qmui_config_color_blue))
                .addItem(new BottomNavigationItem(R.mipmap.test, "Movies & TV").setActiveColorResource(R.color.qmui_config_color_blue))
                .addItem(new BottomNavigationItem(R.mipmap.test, "Games").setActiveColorResource(R.color.qmui_config_color_blue))
                .setFirstSelectedPosition(0)
                .initialise();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        //初始化主页
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.ly_content, homeFragment);
        fragmentTransaction.commit();
        //点击回调
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Toast.makeText(MainActivity.this, "position=" + position, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        HomeFragment homeFragment = new HomeFragment();
                        fragmentTransaction.replace(R.id.ly_content, homeFragment);
                        break;
                    case 1:
                        //fg_content fg_content = com.asyf.app.fragment.fg_content.newInstance("p1", "p2");
                        MyFragment myFragment = new MyFragment();
                        fragmentTransaction.replace(R.id.ly_content, myFragment);
                        break;
                    case 2:
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "position=" + position, Toast.LENGTH_SHORT).show();
                        break;
                }
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Logger.e("aa", "saasahduiqyd78t7e2t测试");
        Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_SHORT).show();
        currentDate = date;

    }

    /**
     * 获取点击后的日期数
     */
    public void getTime(View view) {
        if (currentDate != null) {
            int year = currentDate.getYear();
            int month = currentDate.getMonth() + 1; //月份跟系统一样是从0开始的，实际获取时要加1
            int day = currentDate.getDay();
            Toast.makeText(this, currentDate.toString() + "你选中的是：" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "请选择时间", Toast.LENGTH_LONG).show();
        }


    }

    public class EventDecorator implements DayViewDecorator {

        private int color;
        private HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }


}
