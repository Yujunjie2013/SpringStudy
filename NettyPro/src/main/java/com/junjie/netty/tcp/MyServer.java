package com.junjie.netty.tcp;

import com.junjie.netty.simple.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务端通道实现
                .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列等待连接的个数
                .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象
                    //向通道pipeline设置处理器
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyServerHandler());
                    }
                });//给WorkerGroup的EventLoop对应的管道设置处理器

        //绑定一个端口，并且同步启动服务
        ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
        //对关闭通道进行监听，当有关闭事件时进行关闭
        channelFuture.channel().closeFuture().sync();
    }
}
