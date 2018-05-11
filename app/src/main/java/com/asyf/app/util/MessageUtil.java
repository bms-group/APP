package com.asyf.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.asyf.app.application.Myapplication;
import com.asyf.app.netty.JsonUtil;
import com.asyf.app.netty.Message;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {

    public static void saveMessage(Message m) {
        SharedPreferences sharedPreferences = Myapplication.getAppContext().getSharedPreferences("userId", 0);
        String msgList = sharedPreferences.getString("user_msg_" + m.getUserId(), "");
        List<Message> list;
        if (msgList == "") {
            list = new ArrayList<>();
        } else {
            list = JsonUtil.toJson(msgList, new TypeToken<List<Message>>() {
            }.getType());
        }
        list.add(m);
        String s = JsonUtil.toJson(list);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("user_msg_" + m.getUserId(), s);
        edit.apply();
        //edit.commit();
    }

    public static List<Message> getMessage(String userId) {
        Context appContext = Myapplication.getAppContext();
        SharedPreferences sharedPreferences = appContext.getSharedPreferences("userId", 0);
        String msgList = sharedPreferences.getString("user_msg_" + userId, "");
        List<Message> list;
        if (msgList == "") {
            list = new ArrayList<>();
        } else {
            list = JsonUtil.toJson(msgList, new TypeToken<List<Message>>() {
            }.getType());
        }
        return list;
    }
}
