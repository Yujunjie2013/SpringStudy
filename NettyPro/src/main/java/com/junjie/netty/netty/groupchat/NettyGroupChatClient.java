package com.junjie.netty.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyGroupChatClient {

    private String host;
    private int port;

    public NettyGroupChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            ChannelFuture channelFuture = bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new IdleStateHandler(5,10,15, TimeUnit.SECONDS))
                                    .addLast(new MyHeartbeatHandler())
                                    .addLast(new GroupChatClientHandler());
                        }
                    })
                    .connect(host, port)
                    .sync();
            Channel channel = channelFuture.channel();
            System.out.println("--------" + channel.localAddress() + "-------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                //通过channel发送消息到服务器端
                channel.writeAndFlush(msg + "\r\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new NettyGroupChatClient("127.0.0.1", 7000).run();
    }
}
