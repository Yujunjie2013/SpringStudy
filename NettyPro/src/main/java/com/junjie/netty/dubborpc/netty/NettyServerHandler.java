package com.junjie.netty.dubborpc.netty;

import com.junjie.netty.dubborpc.provider.HelloServerImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端信息
        System.out.println("msg:"+msg);
        //客户端在调式服务的api时，我们需要定义一个协议
        //比如我们要求，每次发消息都必须以xxx字符开头，例如"HelloService#hello#你好"
        if(msg.toString().startsWith("HelloService#hello#")){
            String hello = new HelloServerImpl().sayHello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(hello);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
