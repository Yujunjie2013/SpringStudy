package com.junjie.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 我们自定义一个handler，需要继承netty规定好的某个HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据（这里我们可以读取客户端发送的消息）
     *
     * @param ctx 上下文对象，含有管道pipeline,通道chnnel、地址
     * @param msg 就是客户端发送的数据，默认Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx:" + ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        String message = byteBuf.toString(CharsetUtil.UTF_8).trim();
        System.out.println("客户端" + ctx.channel().remoteAddress() + "发送的消息是：" + message);
        if ("heart".equals(message)) {
            System.out.println("是否一致");
//            ctx.write("heart");
            ctx.writeAndFlush(Unpooled.copiedBuffer("heart", CharsetUtil.UTF_8));
        } else {
            ctx.writeAndFlush(Unpooled.copiedBuffer("你好！客户端，我收到了信息", CharsetUtil.UTF_8));
        }
        super.channelRead(ctx, msg);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        //将数据写到缓冲区，并刷新（相当于发出去了）
//        System.out.println("--------------------------channelReadComplete---------------------");
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            channel.close();
            ctx.close();
        }
    }
}
