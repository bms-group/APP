package com.asyf.app.netty;

import android.os.Handler;

import com.asyf.app.common.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class EchoClient {

    private static final String TAG = "EchoClient";
    private EventLoopGroup eventLoopGroup = null;
    private final String host;
    private final int port;
    private Handler handler;
    private String token;
    private String appKey;
    private String alias;
    private String group;
    private String userId;

    public EchoClient(String host, int port, Handler handler, String token, String appKey, String userId) {
        this.host = host;
        this.port = port;
        this.handler = handler;//回调机制发起通知使用
        this.token = token;
        this.appKey = appKey;
        this.userId = userId;
        this.alias = null;
        this.group = null;
    }

    public EchoClient setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public EchoClient setGroup(String group) {
        this.group = group;
        return this;
    }

    public void stop() {
        int quietPeriod = 5;
        int timeout = 30;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully(quietPeriod, timeout, timeUnit);
        }
    }

    public void start() throws Exception {
        eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); //1创建 Bootstrap
            b.group(eventLoopGroup) //2
                    .channel(NioSocketChannel.class) //3
                    .option(ChannelOption.TCP_NODELAY, true)
                    .remoteAddress(new InetSocketAddress(host, port)) //4
                    .handler(new ChannelInitializer<SocketChannel>() { //5
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline()
                                    .addLast("ping", new IdleStateHandler(0, 300, 0, TimeUnit.SECONDS))
                                    .addLast(new EchoClientHandler(handler, token, appKey, alias, group, userId));
                        }
                    });
            ChannelFuture f = b.connect().sync(); //6
            f.channel().closeFuture().sync(); //7
        } finally {
            Logger.e(TAG, "失去链接");
            eventLoopGroup.shutdownGracefully().sync(); //8
            //Thread.sleep(10000);
            Logger.e(TAG, "准备重连");
            //start();
        }
    }

}
