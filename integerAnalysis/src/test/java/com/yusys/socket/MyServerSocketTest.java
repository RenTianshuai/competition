package com.yusys.socket;

import org.junit.Test;

import javax.sound.midi.Soundbank;

public class MyServerSocketTest {

    @Test
    public void getInputFilePath() {
        MyServerSocket server = new MyServerSocket();
        String inputFilePath = server.getInputFilePath();
        System.out.println(inputFilePath);
    }
}