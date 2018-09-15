package com.yusys.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {

    int port = 7701;

    public MyServerSocket(int port) {
        this.port = port;
    }

    public MyServerSocket() {
    }

    public static void main(String args[]) {
//        int port = 8919;
//        try {
//            ServerSocket server = new ServerSocket(port);
//            Socket socket = server.accept();
//            Reader reader = new InputStreamReader(socket.getInputStream());
//            char chars[] = new char[1024];
//            int len;
//            StringBuilder builder = new StringBuilder();
//            while ((len = reader.read(chars)) != -1) {
//                builder.append(new String(chars, 0, len));
//            }
//            System.out.println("Receive from client message=: " + builder);
//            reader.close();
//            socket.close();
//            server.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }



    /**
     * 通过socket获取输入文件路径
     * @return
     */
    public String getInputFilePath(){

        String inputString = getInputSocketStream();
        String inputFilePath = inputString.substring(6); // 去掉字节码长度得到路径

        return inputFilePath;

    }

    public String getInputSocketStream (){
        try {
            ServerSocket server = new ServerSocket(port);
            Socket socket = server.accept();
            Reader reader = new InputStreamReader(socket.getInputStream());

            char chars[] = new char[1024];
            int len;
            StringBuilder builder = new StringBuilder();
            while ((len = reader.read(chars)) != -1) {
                builder.append(new String(chars, 0, len));
            }
//            System.out.println("Receive from client message=: " + builder);
            reader.close();
            socket.close();
            server.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
