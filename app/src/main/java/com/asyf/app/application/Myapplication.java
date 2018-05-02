package com.asyf.app.application;

import android.app.Application;
import android.content.Intent;

import com.asyf.app.common.Logger;
import com.asyf.app.service.PushService;

import org.xutils.x;

/**
 * Created by Administrator on 2018/3/29.
 */

public class Myapplication extends Application {
    private static final String TAG = "Myapplication";
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onCreate() {
        Logger.d(TAG, "Application创建============");
        super.onCreate();
        this.name = "sjajaod";
        x.Ext.init(this);
        x.Ext.setDebug(true);
        //启动推送service
        Intent intent = new Intent(this, PushService.class);
        intent.putExtra("alias", "alias_123");
        intent.putExtra("group", "group_123");
        //这时候会报错：
        //IllegalArgumentException: Service Intent must be explicit
        //经过查找相关资料，发现是因为Android5.0中service的intent一定要显性声明，当这样绑定的时候不会报错。
        //intent.setAction("com.asyf.app.service.PushService");
        startService(intent);
    }
}
