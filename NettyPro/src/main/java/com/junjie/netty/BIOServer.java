package com.junjie.netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.IntBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器已启动");

        while (true) {
            //监听等待获取连接
            Socket socket = serverSocket.accept();
            System.out.println("连接过来一个客户端");
            //创建一个线程与之通讯
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private static void handler(Socket socket) throws IOException {
        System.out.println("线程名称：" + Thread.currentThread().getName());
        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            //输出客户端发送的数据
            System.out.println(new String(bytes, 0, len));
        }
        inputStream.close();
        socket.close();
    }
}
