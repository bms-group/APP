package com.asyf.app.netty;

import android.os.Handler;

import com.asyf.app.common.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;

@ChannelHandler.Sharable //1@Sharable  标记这个类的实例可以在 channel 里共享
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private static final String TAG = "EchoClientHandler";
    private static final String MSG_TYPE_LOGIN = "1";
    private static final String MSG_TYPE_HEART = "0";
    private static final String MSG_TYPE_DATA = "2";
    private Handler handler;
    private String token;
    private String appKey;
    private String alias;
    private String group;
    private String userId;

    public EchoClientHandler(Handler handler, String token, String appKey, String alias, String group, String userId) {
        this.handler = handler;
        this.token = token;
        this.appKey = appKey;
        this.alias = alias;
        this.group = group;
        this.userId = userId;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Logger.d(TAG, "channelActive激活,时间是：" + new Date());
        //激活的时候发送登录请求
        boolean a = login(ctx.channel());
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String str = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
        Message message = JsonUtil.fromJson(str, Message.class);
        Logger.d(TAG, Thread.currentThread() + "收到了数据：" + message);
        if (message.getType().equals("1")) {
            Logger.e(TAG, "登录结果：" + message.toString());
        } else if ("2".equals(message.getType())) {
            //收到消息的处理，返回到服务
            android.os.Message m = new android.os.Message();
            m.what = 123;
            m.obj = message;
            handler.sendMessage(m);
        } else if ("0".equals(message.getType())) {
            Logger.e(TAG, "心跳检测：" + message.toString());
        } else {
            //未知类型的消息，不处理
            Logger.e(TAG, "收到了未知类型的数据,message=" + message);
        }
        ReferenceCountUtil.release(msg);//释放
    }

    private boolean login(Channel channel) {
        Message message = new Message();
        message.setUserId(userId);
        message.setToken(token);
        message.setAppKey(appKey);
        message.setGroup(group);
        message.setAlias(alias);
        message.setType(MSG_TYPE_LOGIN);
        ByteBuf byteBuf = Unpooled.copiedBuffer(JsonUtil.toJson(message), CharsetUtil.UTF_8);
        channel.writeAndFlush(byteBuf);
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Logger.e(TAG, "channelInactive,停止时间是：" + new Date());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        //4记录日志错误并关闭 channel
        Logger.e(TAG, cause.getMessage());
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Logger.d(TAG, "userEventTriggered循环触发时间：" + new Date());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            //心跳数据发送,channel的关闭在服务端处理
            Logger.d(TAG, "发送心跳信息，次数currentTime:" + new Date());
            Message message = new Message(MSG_TYPE_HEART, "心跳信息");
            ByteBuf byteBuf = Unpooled.copiedBuffer(JsonUtil.toJson(message), CharsetUtil.UTF_8);
            ctx.channel().writeAndFlush(byteBuf);
        }
    }

}
