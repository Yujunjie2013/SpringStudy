package com.junjie.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10条数据
        for (int i = 0; i < 10; i++) {
            String str = "天气冷，我想吃火锅" + i;
            byte[] bytes = str.getBytes(CharsetUtil.UTF_8);
            //创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol(bytes.length, bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int lenght = msg.getLenght();
        byte[] content = msg.getContent();
        System.out.println("客户端接收到消息...长度:"+lenght+"---内容:"+new String(content,CharsetUtil.UTF_8));
        System.out.println("客户端接收消息数量:"+(++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }
}
