package com.asyf.app.netty;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {

    public static <T> String toJson(T t) {
        Gson gson = new Gson();
        String s = gson.toJson(t);
        return s;
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        Gson gson = new Gson();
        T t = gson.fromJson(json, tClass);
        return t;
    }

    public static void main(String[] args) {
        Message message = new Message("1", "test");
        String json = JsonUtil.toJson(message);
        System.out.println(json);
        Message m = JsonUtil.fromJson(json, Message.class);
        System.out.println(m);
    }

    public static <T> List<T> toJson(String json, Type type) {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(json, type);
        return list;
    }
}
