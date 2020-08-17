package com.junjie.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收到数据并处理
        int lenght = msg.getLenght();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到信息--长度:"+lenght+"--内容:"+new String(content,CharsetUtil.UTF_8));
        System.out.println("服务器接收到消息数量:"+(++count));

        //回复消息
        String uuid = UUID.randomUUID().toString().trim();
        byte[] bytes = uuid.getBytes(CharsetUtil.UTF_8);
        //构建协议包
        MessageProtocol messageProtocol = new MessageProtocol(bytes.length, bytes);
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }
}
