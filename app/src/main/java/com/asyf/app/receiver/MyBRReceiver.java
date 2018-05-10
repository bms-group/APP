package com.asyf.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/30.
 */

public class MyBRReceiver extends BroadcastReceiver {

    public static final Map<String, Handler> h = new HashMap();

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "收到了通知~", Toast.LENGTH_SHORT).show();
        Handler handler = h.get("user");
        android.os.Message m = new android.os.Message();
        m.what = 123;
        m.obj = "122";
        //handler.sendMessage(m);
    }
}
