package com.asyf.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateUtil {

    private static final boolean isDebug = true;

    public static List<Map<String, String>> getCalendar(String date) {
        List<Map<String, String>> list = new ArrayList<>();
        return list;
    }

    /**
     * 获取一个月的所有的日期，返回list集合
     *
     * @param year
     * @param month
     * @return
     */
    public static List<String> getMonthDate(int year, int month) {
        List<String> list = new ArrayList<>();
        //获取一个月一共有多少天，将日期设置到1号，roll 操作 date 只会改变天不改变其他的
        //例如 2018-05-01   roll -1 变成 2018-05-31
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        a.set(Calendar.DATE, 1);
        //获取当前月所有的日期
        for (int i = 0; i < maxDate; i++) {
            String date = formatDate(a.getTime(), "yyyy-MM-dd");
            print(date);
            list.add(date);
            a.add(Calendar.DATE, 1);//每次循环增加一天
        }
        return list;
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static Date parseStringToDate(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void print(String msg) {
        if (isDebug) {
            System.out.println(msg);
        }
    }

    public static void main(String[] args) {
      //  String s = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
       // System.out.println(s);
        //System.out.println(parseStringToDate(s, "yyyy-MM-dd"));
       // getMonthDate(2018, 5);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, 1);
        int i = c.get(Calendar.DAY_OF_WEEK);
        System.out.println(i);
        int actualMaximum = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.print(actualMaximum);
    }

}
