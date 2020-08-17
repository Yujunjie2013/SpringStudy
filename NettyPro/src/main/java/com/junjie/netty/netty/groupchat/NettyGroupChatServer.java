package com.junjie.netty.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyGroupChatServer {
    private int port;//监听端口

    public NettyGroupChatServer(int port) {
        this.port = port;
    }

    /**
     * 处理客户端请求
     */
    public void run() {
        //创建2个线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //添加解码器
                            ch.pipeline()
                                    .addLast("decoder", new StringDecoder())//解码器
                                    .addLast("encoder", new StringEncoder())//编码器
                                    //3秒未读、5秒未写，7秒未读写
                                    .addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS))
                                    .addLast(new MyHeartbeatHandler())//心跳事件处理
                                    .addLast(new GroupChatServerHandler());
                        }
                    });

            serverBootstrap.bind(port)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                bossGroup.shutdownGracefully().sync();
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new NettyGroupChatServer(7000).run();
    }
}
