package com.junjie.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Date;

/**
 * TextWebSocketFrame类型 表示一个文本帧
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务端收到消息:" + msg.text());
        //回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间:" + new Date() + "---》" + msg.text()));
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //当web客户端连接后 id表示唯一的值
        System.out.println("handlerAdded被调用:" + ctx.channel().id().asLongText());
        //在这个不是唯一的
        System.out.println("handlerAdded被调用:" + ctx.channel().id().asShortText());
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("---handlerRemoved被调用----" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("异常发生");
        ctx.close();
    }
}
