package com.yusys.test;

import com.yusys.socket.MyServerSocket;

public class Test {
    public static void main(String[] args) {
        MyServerSocket server = new MyServerSocket();
        while (true) {
            String inputFilePath = server.acceptAndDeal();
            System.out.println(inputFilePath);
        }
    }
}
