package com.junjie.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;

public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * 读取客户端数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("服务端收到数据");
        //判断msg是不是一个httprequest请求
        if(msg instanceof HttpRequest){
            System.out.println("类型:"+msg.getClass());
            System.out.println("客户端地址："+ctx.channel().remoteAddress());

            ByteBuf buffer = Unpooled.copiedBuffer("我收到了客户端请求", CharsetUtil.UTF_8);
            //构造一个http响应
            DefaultFullHttpResponse defaultHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buffer);
            defaultHttpResponse.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8")
                    .set(HttpHeaderNames.CONTENT_LENGTH,buffer.readableBytes());
            //将构建好的response返回
            ctx.writeAndFlush(defaultHttpResponse);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
