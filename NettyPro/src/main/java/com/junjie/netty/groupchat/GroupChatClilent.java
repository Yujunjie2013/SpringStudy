package com.junjie.netty.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClilent {

    private final String HOST = "127.0.0.1";
    private final int PORT = 9999;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public GroupChatClilent() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            //得到userName
            userName = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //启动客户端
        GroupChatClilent chatClilent = new GroupChatClilent();
        new Thread(chatClilent::readInfo).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClilent.sendInfo(s);
        }

    }

    public void sendInfo(String msg) {
        msg = userName + "说:" + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取从服务器发过来的消息
    public void readInfo() {
        try {
            System.out.println("开始读取");
            while (selector.select() > 0) {
                //有可用的通道
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        channel.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        //把读到缓冲区的数据，转成字符串
                        String msg = new String(buffer.array()).trim();
                        System.out.println("接收到内容:" + msg);
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
