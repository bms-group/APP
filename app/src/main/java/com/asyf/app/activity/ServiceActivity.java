package com.asyf.app.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.asyf.app.R;

public class ServiceActivity extends Activity {

    private Button start;
    private Button stop;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mContext = ServiceActivity.this;
        start = (Button) findViewById(R.id.btnstart);
        stop = (Button) findViewById(R.id.btnstop);
        //创建启动Service的Intent,以及Intent属性
        final Intent intent = new Intent();
        intent.setAction("com.asyf.app.service.PushService");
        //为两个按钮设置点击事件,分别是启动与停止service
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startService(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopService(intent);
            }
        });
        View notify = findViewById(R.id.notify);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //全局通知管理者，通过获取系统服务获取
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //通知栏构造器,创建通知栏样式
                Intent it = new Intent(mContext, ListActivity.class);
                PendingIntent pit = PendingIntent.getActivity(mContext, 0, it, 0);

                //设置图片,通知标题,发送时间,提示方式等属性
                Notification.Builder mBuilder = new Notification.Builder(mContext);
                //设置通知栏标题
                mBuilder.setContentTitle("测试标题")
//设置通知栏显示内容
                        .setContentText("测试内容")
//设置通知栏点击意图
                        .setContentIntent(pit)
//通知首次出现在通知栏，带上升动画效果的
                        .setTicker("测试通知来啦")
//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                        .setWhen(System.currentTimeMillis())
                        //设置该通知优先级
                        .setPriority(Notification.PRIORITY_DEFAULT)
//设置这个标志当用户单击面板就可以让通知将自动取消
                        .setAutoCancel(true)
//使用当前的用户默认设置
                        .setDefaults(Notification.DEFAULT_VIBRATE)
//设置通知小ICON(应用默认图标)
                        .setSmallIcon(R.drawable.qmui_icon_checkmark);

                mNotificationManager.notify(1212, mBuilder.build());
            }
        });
    }
}
