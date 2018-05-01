package com.asyf.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/4/30.
 */

public class MyBRReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "收到了通知~", Toast.LENGTH_SHORT).show();
    }
}
