package com.junjie.netty.dubborpc.provider;

import com.junjie.netty.dubborpc.publicinterface.HelloServer;

public class HelloServerImpl implements HelloServer {
    @Override
    public String sayHello(String msg) {
        return "我收到了你的消息:" + msg;
    }
}
