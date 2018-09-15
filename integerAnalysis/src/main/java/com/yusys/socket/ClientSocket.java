package com.yusys.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class ClientSocket {
    String host = "127.0.0.1";
    int port = 7701;

    public ClientSocket() {
    }

    public ClientSocket(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void sendData(String data) {
        try {
            Socket client = new Socket(host, port);
            Writer writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(data);
            writer.flush();
            client.shutdownOutput();


            InputStreamReader reader = new InputStreamReader(client.getInputStream());

            BufferedReader br = new BufferedReader(reader);
            System.out.println(br);
            while (true) {
                if(client.getInputStream().available() == 0){

                }else {
                    System.out.println(br.readLine());
                    break;
                }
            }
//            String s = br.readLine();
            reader.close();
            writer.close();
//            System.out.println(builder.toString());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}