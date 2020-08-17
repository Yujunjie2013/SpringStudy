package com.junjie.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static NettyClientHandler clientHandler;

    //获取一个代理对象
    public Object getBean(final Class<?> serverClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serverClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("开始了吗？");
                        if (clientHandler == null) {
                            initClient();
                        }
                        //设置要发给服务器的参数,providerName协议头，args[0]就是参数
                        clientHandler.setParams(providerName + "xxxxx");
                        String result = executorService.submit(clientHandler).get();
                        return result;
                    }
                });
    }

    private static void initClient() {
        clientHandler = new NettyClientHandler();
        //创建EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(clientHandler);
                    }
                });
        try {
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 7000).sync();
            System.out.println("客户端开启");
            sync
                    .channel()
                    .closeFuture()
                    .sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            try {
//                group.shutdownGracefully().sync();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
