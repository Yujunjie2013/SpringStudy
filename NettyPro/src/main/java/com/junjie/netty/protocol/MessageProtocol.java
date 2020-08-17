package com.junjie.netty.protocol;

public class MessageProtocol {
    private int lenght;
    private byte[] content;

    public MessageProtocol() {
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public MessageProtocol(int lenght, byte[] content) {
        this.lenght = lenght;
        this.content = content;
    }
}
