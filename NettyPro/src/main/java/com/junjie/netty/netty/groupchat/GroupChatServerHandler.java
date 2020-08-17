package com.junjie.netty.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channel组，管理所有的channel,GlobalEventExecutor.INSTANCE)是一个全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 当连接建立,第一个被执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //加入到集合,将加入聊天信息推送给其他客户
        //该方法会将Channel中所有的方法遍历，并发送消息
        channelGroup.writeAndFlush("客户端" + channel.remoteAddress() + "加入聊天\n");
        channelGroup.add(channel);
    }

    /**
     * 表示channel 处于活跃状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线");
    }

    /**
     * 表示channel 出于不活跃状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "下线");
    }

    /**
     * 断开连接,将xx客户离开信息推送给当前在线客户
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("客户端:" + channel.remoteAddress() + "离开了\n");
        System.out.println("当前channelGroup大小：" + channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        //遍历channelGroup，根据不同的情况，返回不同的消息
        for (Channel ch : channelGroup) {
            if (ch != channel) {//不是当前的转发
                ch.writeAndFlush("客户:" + channel.remoteAddress() + " 发送了消息:" + msg + "\n");
            } else {
                ch.writeAndFlush("自己发送了消息: " + msg + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
