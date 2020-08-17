package com.junjie.netty;

import io.netty.util.NettyRuntime;

public class Test {
    public static void main(String[] args) {
        int processors = NettyRuntime.availableProcessors();
        System.out.println("---"+processors);
    }
}
