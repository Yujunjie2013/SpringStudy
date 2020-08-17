package com.junjie.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<String> {

    private ChannelHandlerContext context;
    private String result;//返回的结果
    private String params;//客户端传入的参数

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context=ctx;//因为要在call方法中使用
    }

    //收到服务器的数据，唤醒线程
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //收到服务器的数据
        this.result=msg.toString();
        notify();//唤醒等待线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //被代理对象调用,发送数据给服务器，并等待唤醒,
    @Override
    public synchronized String call() throws Exception {
        context.writeAndFlush(params);
        wait();//等待channelRead方法获取服务器结果后唤醒
        return result;//服务器返回的结果
    }

    public void setParams(String params) {
        this.params = params;
    }
}
