package com.junjie.netty.dubborpc.provider;

import com.junjie.netty.dubborpc.netty.NettyClient;
import com.junjie.netty.dubborpc.publicinterface.HelloServer;

public class ClientBootstrap {
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {

        NettyClient nettyClient = new NettyClient();

        HelloServer helloServer = (HelloServer) nettyClient.getBean(HelloServer.class, providerName);
        //通过代理对象调用服务提供的方法
        String result = helloServer.sayHello("你好，dubbo~");
        System.out.println("调用的结果:"+result);
    }
}
