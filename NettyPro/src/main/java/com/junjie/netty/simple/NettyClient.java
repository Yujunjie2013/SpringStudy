package com.junjie.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;

public class NettyClient {

    @SneakyThrows
    public static void main(String[] args) {
        //客户端需要一个时间循环组
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建客户端启动对象,服务端的启动对象是ServerBootstrap
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group(group)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端 OK");

            //启动客户端连接服务器
            //关于ChannelFuture要分析，涉及到netty的异步模型
            bootstrap
                    .connect("127.0.0.1", 6668)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } finally {
            group.shutdownGracefully();
        }

    }
}
