package com.asyf.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.asyf.app.R;
import com.asyf.app.activity.ListActivity;
import com.asyf.app.common.Logger;
import com.asyf.app.netty.EchoClient;

import org.apache.commons.lang3.StringUtils;

import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * Created by Administrator on 2018/4/30.
 */

public class PushService extends Service {

    private final String TAG = "PushService";
    final MyHandler handler = new MyHandler(this);
    private String token;
    private String appKey;
    private String userId;
    private String alias;
    private String group;

    //必须要实现的方法
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Service被创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i(TAG, "onCreate方法被调用!");
    }

    //Service被启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(TAG, "onStartCommand方法被调用!");
        //初始化基础数据
        initPushData(intent);
        boolean flag = true;
        if (StringUtils.isBlank(userId)) {
            flag = false;
            Logger.e(TAG, "userId 不能为空");
        }
        if (StringUtils.isBlank(token)) {
            flag = false;
            Logger.e(TAG, "token 不能为空");
        }
        if (StringUtils.isBlank(appKey)) {
            flag = false;
            Logger.e(TAG, "appKey 不能为空");
        }
        if (flag) {
            //新的线程启动服务
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new EchoClient("192.168.1.7", 8088, handler, token, appKey, userId)
                                .setAlias(alias)
                                .setGroup(group)
                                .start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //Service被关闭之前回调
    @Override
    public void onDestroy() {
        Logger.i(TAG, "onDestory方法被调用!");
        super.onDestroy();
    }

    //======================================================================私有方法
    private void initPushData(Intent intent) {
        SharedPreferences sp = getSharedPreferences("push_data", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", null);
        if (StringUtils.isBlank(userId)) {
            userId = String.valueOf(UUID.randomUUID());
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("userId", userId);
            edit.apply();
        }
        this.userId = userId;
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            this.token = applicationInfo.metaData.getString("PUSH_TOKEN");
            this.appKey = applicationInfo.metaData.getString("PUSH_APPKEY");
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(TAG, e.getCause().getMessage());
        }
        Bundle extras = intent.getExtras();
        this.alias = extras.getString("alias", "");
        this.group = extras.getString("group", "");
        Logger.d(TAG, "userId=" + userId);
        Logger.d(TAG, "token=" + token);
        Logger.d(TAG, "appKey=" + appKey);
        Logger.d(TAG, "alias=" + alias);
        Logger.d(TAG, "group=" + group);
    }

    //=======================================================================内部类
    //弱引用，解决内存泄露问题
    static class MyHandler extends Handler {

        private final WeakReference<PushService> pushServiceWeakReference;

        public MyHandler(PushService pushService) {
            this.pushServiceWeakReference = new WeakReference<PushService>(pushService);
        }

        @Override
        public void handleMessage(Message msg) {
            PushService pushService = pushServiceWeakReference.get();
            Context mContext = pushService.getApplicationContext();
            if (msg.what == 123) {
                //Intent intent = new Intent("android.intent.action.push");
                //pushService.sendBroadcast(intent);
                //启动通知，然后在去进行广播
                //全局通知管理者，通过获取系统服务获取
                NotificationManager mNotificationManager = (NotificationManager) pushService.getSystemService(NOTIFICATION_SERVICE);
                //通知栏构造器,创建通知栏样式
                //Intent it = new Intent(mContext, ListActivity.class);
                Intent itBroadcast = new Intent("android.intent.action.push");

                //PendingIntent pit = PendingIntent.getActivity(mContext, 0, it, 0);
                PendingIntent pit = PendingIntent.getBroadcast(mContext, 0, itBroadcast, 0);

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
                        .setDefaults(Notification.DEFAULT_ALL)
                        //设置通知小ICON(应用默认图标)
                        .setSmallIcon(R.drawable.qmui_icon_checkmark);
                //发起通知
                mNotificationManager.notify(1212, mBuilder.build());
            }

        }
    }
}
