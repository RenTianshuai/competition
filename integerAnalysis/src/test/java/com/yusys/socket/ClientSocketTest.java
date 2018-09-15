package com.yusys.socket;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClientSocketTest {

    @Test
    public void sendData() {
        ClientSocket clientSocket = new ClientSocket();
        clientSocket.sendData("000027/home/jike/intdiv/input.txt");
    }
}