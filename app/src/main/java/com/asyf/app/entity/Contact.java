package com.asyf.app.entity;

import android.content.Context;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Contact {
    private int imgtou;
    private String name;
    private String says;

    public Contact() {
    }

    public Contact(int imgtou, String name, String says) {
        this.imgtou = imgtou;
        this.name = name;
        this.says = says;
    }

    public int getImgtou() {
        return imgtou;
    }

    public void setImgtou(int imgtou) {
        this.imgtou = imgtou;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSays() {
        return says;
    }

    public void setSays(String says) {
        this.says = says;
    }
}
