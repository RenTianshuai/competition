package com.yusys.socket;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class ClientSocket {
    String host = "127.0.0.1";
    int port = 7701;
    public static void main(String args[]) {

    }

    public ClientSocket() {
    }

    public ClientSocket(String host, int port) {
        this.host = host;
        this.port = port;
    }



    public void sendData(String data){
        try {
            Socket client = new Socket(host, port);
            Writer writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}