package com.junjie.netty.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenerChannel;
    private final int PORT = 9999;

    public GroupChatServer() {
        try {
            listenerChannel = ServerSocketChannel.open();
            selector = Selector.open();
            listenerChannel.configureBlocking(false);
            listenerChannel.bind(new InetSocketAddress(PORT));
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

    private void listen() {
        try {
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = listenerChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        //提示上线
                        System.out.println(socketChannel.getRemoteAddress() + "：上线了");
                    }
                    if (selectionKey.isReadable()) {
                        //处理读
                        readData(selectionKey);
                    }
                    iterator.remove();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取客户端的消息
     *
     * @param selectionKey
     */
    private void readData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(buffer);
            if (read > 0) {
                String msg = new String(buffer.array()).trim();
                System.out.println("from 客户端：" + msg);
                //向其他的客户端发送消息
                sendInfo2OtherClient(socketChannel, msg);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线了...");
                //离线之后，取消注册
                selectionKey.cancel();
                //关闭通道
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 将消息转发到其他客户端
     *
     * @param self
     * @param msg
     */
    private void sendInfo2OtherClient(SocketChannel self, String msg) throws IOException {
        System.out.println("服务器转发消息");
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            Channel channel = key.channel();
            //排除自己
            if (channel instanceof SocketChannel && channel != self) {
                //转发
                SocketChannel socketChannel = (SocketChannel) channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                socketChannel.write(buffer);
            }
        }
    }
}
