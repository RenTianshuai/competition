package com.yusys.socket;

import com.yusys.analysis.Analysis;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
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
            socket.shutdownInput();

            Analysis analysis = new Analysis();
            analysis.start(inputFilePath);
            String returnData = analysis.getReturnData(analysis.getOutputFilePath());

//            Writer writer = new OutputStreamWriter(socket.getOutputStream());
//            writer.write(returnData);
//            writer.flush();
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            printStream.println(returnData);
            printStream.flush();
            printStream.close();

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
