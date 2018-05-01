package com.asyf.app.netty;

public class Message {

    private String type;//消息类型0错误 1登录 2普通消息
    private String msg;//消息内容
    private String alias;//别名
    private String group;//分组
    private String token;//秘钥
    private String appKey;//appKey用来区分app
    private String userId;//用户id,安卓app首次启动时生成的uuid
    private String msgCode;//消息代码

    public Message() {
    }

    public Message(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public Message(String type, String msg, String alias, String group, String token, String appKey, String userId) {
        this.type = type;
        this.msg = msg;
        this.alias = alias;
        this.group = group;
        this.token = token;
        this.appKey = appKey;
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", msg='" + msg + '\'' +
                ", alias='" + alias + '\'' +
                ", group='" + group + '\'' +
                ", token='" + token + '\'' +
                ", appKey='" + appKey + '\'' +
                ", userId='" + userId + '\'' +
                ", msgCode='" + msgCode + '\'' +
                '}';
    }
}
