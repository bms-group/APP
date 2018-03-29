package com.asyf.app.application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2018/3/29.
 */

public class Myapplication extends Application {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.name = "sjajaod";
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
