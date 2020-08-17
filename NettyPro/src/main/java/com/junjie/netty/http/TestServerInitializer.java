package com.junjie.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();
        //加入hetty提供的HttpServerCode codec=>decoder
        //1、HttpServerCodec是netty提供的处理http的编解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2、增加一个自定义的http Handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

    }
}
