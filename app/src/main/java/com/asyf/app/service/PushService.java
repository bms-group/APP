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
import com.asyf.app.activity.MessageListActivity;
import com.asyf.app.common.Logger;
import com.asyf.app.netty.EchoClient;

import org.apache.commons.lang3.StringUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 2018/4/30.
 */

public class PushService extends Service {

    public static final Map<String, Handler> h = new HashMap();
    private static final String TAG = "PushService";
    final MyHandler handler = new MyHandler(this);
    //private EchoClient echoClient;
    private String token;
    private String appKey;
    private String userId;
    private String alias;
    private String group;
    private Thread thread;
    private EchoClient echoClient;

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
        startEchoClient();
    }

    //Service被启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(TAG, "onStartCommand方法被调用!" + Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

    //Service被关闭之前回调
    @Override
    public void onDestroy() {
        Logger.i(TAG, "onDestory方法被调用!");
        if (thread != null) {
            Logger.i(TAG, "关闭线程");
            //关闭netty
            echoClient.stop();
            //关闭线程
            //thread.interrupt();
            Logger.e(TAG, "thread___" + thread.isAlive());//此处是 true，说明线程未执行完毕
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.e(TAG, "thread___" + thread.isAlive());//此处是false，说明10秒后线程执行完毕
        }
        super.onDestroy();
    }

    //======================================================================私有方法

    private void startEchoClient() {
        //初始化基础数据
        //initPushData(intent);//用这种方式会导致，app被清理时报错
        initPushData();
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
        if (echoClient != null) {
            echoClient.stop();
        }
        if (flag) {
            //新的线程启动服务
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        echoClient = new EchoClient("192.168.0.131", 8088, handler, token, appKey, userId)
                                .setAlias(alias)
                                .setGroup(group);
                        echoClient.start();
                        Logger.e(TAG, "thread执行到最后");
                        Thread.sleep(10000);
                        Logger.e(TAG, "重启netty");
                        startEchoClient();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }) {
                @Override
                protected void finalize() throws Throwable {
                    super.finalize();
                    Logger.e(TAG, "thread===================================finalize");
                }
            };
            thread.start();
        }
    }

    private void initPushData() {
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
        //Bundle extras = intent.getExtras();
        //this.alias = extras.getString("alias", "");
        //this.group = extras.getString("group", "");
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
            Logger.i(TAG, "弱引用构造函数");
            this.pushServiceWeakReference = new WeakReference<PushService>(pushService);
        }

        @Override
        public void handleMessage(Message msg) {
            PushService pushService = pushServiceWeakReference.get();
            Context mContext = pushService.getApplicationContext();
            if (msg.what == 123) {
                com.asyf.app.netty.Message m = (com.asyf.app.netty.Message) msg.obj;
                //Intent intent = new Intent("android.intent.action.push");
                //pushService.sendBroadcast(intent);
                //启动通知，然后在去进行广播
                //全局通知管理者，通过获取系统服务获取
                NotificationManager mNotificationManager = (NotificationManager) pushService.getSystemService(NOTIFICATION_SERVICE);
                //通知栏构造器,创建通知栏样式
                //Intent it = new Intent(mContext, ListActivity.class);
                //点击事件出发广播，然后在广播中处理
                //TODO 此处可以写成在AndroidManifest.xml中配置，这样就可以随便新建服务接收通知了
                Intent itBroadcast = new Intent("android.intent.action.push");

                //PendingIntent pit = PendingIntent.getActivity(mContext, 0, it, 0);
                PendingIntent pit = PendingIntent.getBroadcast(mContext, 0, itBroadcast, 0);

                //设置图片,通知标题,发送时间,提示方式等属性
                Notification.Builder mBuilder = new Notification.Builder(mContext);
                //设置通知栏标题
                mBuilder.setContentTitle("测试标题")
                        //设置通知栏显示内容
                        .setContentText("测试内容" + m.toString())
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
                /*if (h.get("user") != null) {
                    Handler handler = h.get("user");
                    android.os.Message m2 = new android.os.Message();
                    m2.what = 123;
                    m2.obj = m;
                    handler.sendMessage(m2);
                }*/
            } else if (msg.what == 10003) {
                com.asyf.app.netty.Message m = (com.asyf.app.netty.Message) msg.obj;
                //全局通知管理者，通过获取系统服务获取
                NotificationManager mNotificationManager = (NotificationManager) pushService.getSystemService(NOTIFICATION_SERVICE);
                //通知栏构造器,创建通知栏样式
                Intent it = new Intent(mContext, MessageListActivity.class);
                //点击事件出发广播，然后在广播中处理
                it.putExtra("userId", m.getUserId());
                PendingIntent pit = PendingIntent.getActivity(mContext, 0, it, 0);
                //PendingIntent pit = PendingIntent.getBroadcast(mContext, 0, itBroadcast, 0);

                //设置图片,通知标题,发送时间,提示方式等属性
                Notification.Builder mBuilder = new Notification.Builder(mContext);
                //设置通知栏标题
                mBuilder.setContentTitle("测试标题" + m.getUserId())
                        //设置通知栏显示内容
                        .setContentText("测试内容" + m.getMsg())
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
                mNotificationManager.notify(new Random().nextInt(10000), mBuilder.build());
                if (h.get(m.getUserId()) != null) {
                    Handler handler = h.get(m.getUserId());
                    android.os.Message m2 = new android.os.Message();
                    m2.what = 123;
                    m2.obj = m;
                    handler.sendMessage(m2);
                }
            }

        }
    }
}
