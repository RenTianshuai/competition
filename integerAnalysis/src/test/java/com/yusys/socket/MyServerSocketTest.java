package com.yusys.socket;

import org.junit.Test;

public class MyServerSocketTest {

    @Test
    public void getInputFilePath() {
        MyServerSocket server = new MyServerSocket();
        String inputFilePath = server.acceptAndDeal();
        System.out.println(inputFilePath);
    }
}