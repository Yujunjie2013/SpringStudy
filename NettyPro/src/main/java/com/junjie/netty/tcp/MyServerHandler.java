package com.junjie.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * 主要演示tcp/粘包拆包问题;解决方案是自定义协议；具体可以看protocol包实现
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        //将byte转换成字符串
        String string = new String(bytes, CharsetUtil.UTF_8);
        System.out.println("收到数据:" + string);
        System.out.println("服务器接收到消息" + (++count));

        //服务器会送消息给客户端
        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString().trim(), CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }
}
