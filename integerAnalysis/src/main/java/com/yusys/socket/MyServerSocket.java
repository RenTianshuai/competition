package com.yusys.socket;

import com.yusys.analysis.Analysis;

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
        System.out.println("等待服务器发送信息......");

        String inputString = acceptAndDeal();
        String inputFilePath = inputString.substring(6); // 去掉字节码长度得到路径

        return inputFilePath;

    }

    public String acceptAndDeal(){
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("监听端口"+port+"等待服务器发送信息......");
            Socket socket = server.accept();
            Reader reader = new InputStreamReader(socket.getInputStream());

            char chars[] = new char[1024];
            int len;
            StringBuilder builder = new StringBuilder();
            while ((len = reader.read(chars)) != -1) {
                builder.append(new String(chars, 0, len));
            }
            String inputFilePath = builder.toString();
            inputFilePath = inputFilePath.substring(6);
//            System.out.println("Receive from client message=: " + builder);


            PrintStream printStream = new PrintStream(socket.getOutputStream());

            Analysis analysis = new Analysis();
            analysis.start(inputFilePath);
            String returnData = analysis.getReturnData(analysis.getOutputFilePath());

            printStream.println(returnData);

            reader.close();
            socket.close();
            server.close();
            return returnData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
