package com.junjie.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<MessageProtocol> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder--decoder被调用");
        //将得到的二进制字节码转成MessageProtocol对象
        int lenght = in.readInt();
        byte[] bytes = new byte[lenght];
        in.readBytes(bytes);
        //封装成MessageProtocol对象，放入out,传递到下一个Handler处理
        MessageProtocol messageProtocol = new MessageProtocol(lenght, bytes);

        out.add(messageProtocol);

    }
}
