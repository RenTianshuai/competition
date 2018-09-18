package com.yusys;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    private String ip = "127.0.0.1";
    private int port = 7001;
    private Socket socket = null;
    public String connect(String info) throws IOException {
        socket = new Socket(ip,port);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw.println(info);
        pw.flush();
        String result = br.readLine();

        br.close();
        pw.close();
        socket.close();
        return result;
    }
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        SocketClient sc = new SocketClient();
        String output = sc.connect("D://input.txt");
        System.out.println(output);
        System.out.println("用时ms"+(System.currentTimeMillis()-start));
    }
}
