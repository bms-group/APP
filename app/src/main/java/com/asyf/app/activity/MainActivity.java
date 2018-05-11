package com.asyf.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.asyf.app.R;
import com.asyf.app.fragment.ContactFragment;
import com.asyf.app.fragment.HomeFragment;
import com.asyf.app.fragment.MessageFragment;
import com.asyf.app.fragment.ScheduleFragment;
import com.asyf.app.fragment.TestFragment;
import com.asyf.app.observe.EventBadgeItem;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private HomeFragment homeFragment;
    private ScheduleFragment scheduleFragment;
    private ContactFragment contactFragment;
    private MessageFragment messageFragment;
    private FragmentManager fragmentManager;
    private TestFragment testFragment;
    private TextBadgeItem badgeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        //顶部工具栏
        initToolBar();
        //底部导航
        initButtom();
    }

    /**
     * 顶部工具栏
     */
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

    /**
     * 底部导航栏
     */
    private void initButtom() {
        EventBadgeItem.getInstance().register(this);//观察者注册
        badgeItem = new TextBadgeItem();
        badgeItem.setHideOnSelect(false)
                .setText("10")
                .setBorderWidth(0);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBackgroundColor(getResources().getColor(R.color.background));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.test, "消息").setActiveColorResource(R.color.nav_active_color))
                .addItem(new BottomNavigationItem(R.mipmap.test, "联系人").setActiveColorResource(R.color.nav_active_color))
                .addItem(new BottomNavigationItem(R.mipmap.test, "课程").setActiveColorResource(R.color.nav_active_color))
                .addItem(new BottomNavigationItem(R.mipmap.test, "通知").setActiveColorResource(R.color.nav_active_color))
                .addItem(new BottomNavigationItem(R.mipmap.test, "测试").setActiveColorResource(R.color.nav_active_color).setBadgeItem(badgeItem))
                .setFirstSelectedPosition(0)
                .initialise();
        //初始化主页
        setDefaultFragment();

        //点击回调
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                //显示碎片
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //隐藏所有
                //hideAllFragment(fragmentTransaction);
                //Toast.makeText(MainActivity.this, "position=" + position, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        /*if (myFragment == null) {
                            myFragment = new MyFragment();
                            fragmentTransaction.add(R.id.ly_content, myFragment);
                        } else {
                            fragmentTransaction.show(myFragment);
                        }*/
                        if (messageFragment == null) {
                            messageFragment = new MessageFragment();
                        }
                        fragmentTransaction.replace(R.id.ly_content, messageFragment);
                        break;
                    case 1:
                        /*if (planFragment == null) {
                            planFragment = new PlanFragment();
                            fragmentTransaction.replace(R.id.ly_content, planFragment);
                        } else {
                            fragmentTransaction.show(planFragment);
                        }*/
                        if (contactFragment == null) {
                            contactFragment = new ContactFragment();
                        }
                        fragmentTransaction.replace(R.id.ly_content, contactFragment);
                        break;
                    case 2:
                       /* if (scheduleFragment == null) {
                            scheduleFragment = new ScheduleFragment();
                            fragmentTransaction.add(R.id.ly_content, scheduleFragment);
                        } else {
                            fragmentTransaction.show(scheduleFragment);
                        }*/
                        if (scheduleFragment == null) {
                            scheduleFragment = new ScheduleFragment();
                        }
                        fragmentTransaction.replace(R.id.ly_content, scheduleFragment);
                        break;
                    case 3:
                        /*if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            fragmentTransaction.add(R.id.ly_content, homeFragment);
                        } else {
                            fragmentTransaction.show(homeFragment);
                        }*/
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                        }
                        fragmentTransaction.replace(R.id.ly_content, homeFragment);
                        break;
                    case 4:
                        /*if (testFragment == null) {
                            testFragment = new TestFragment();
                            fragmentTransaction.add(R.id.ly_content, testFragment);
                        } else {
                            fragmentTransaction.show(testFragment);
                        }*/
                        if (testFragment == null) {
                            testFragment = new TestFragment();
                        }
                        fragmentTransaction.replace(R.id.ly_content, testFragment);
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

    /**
     * 隐藏所有Fragment
     *
     * @param fragmentTransaction
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) fragmentTransaction.hide(homeFragment);
        if (scheduleFragment != null) fragmentTransaction.hide(scheduleFragment);
        if (contactFragment != null) fragmentTransaction.hide(contactFragment);
        if (messageFragment != null) fragmentTransaction.hide(messageFragment);
        if (testFragment != null) fragmentTransaction.hide(testFragment);
    }

    /**
     * 设置默认碎片
     */
    private void setDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MessageFragment messageFragment = new MessageFragment();
        fragmentTransaction.replace(R.id.ly_content, messageFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            badgeItem.setText((String) arg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBadgeItem.getInstance().unregister(this);//取消注册
    }

}
