package com.yusys.socket;

import com.yusys.analysis.Analysis;
import com.yusys.analysis.StockAnalysis;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {

    int port = 7701;

    public static void main(String[] args) {
        // declaration section:
        // declare a server socket and a client socket for the server
        // declare an input and an output stream
        ServerSocket echoServer = null;
        String line;
        DataInputStream is;
        PrintStream os;
        Socket clientSocket = null;
        // Try to open a server socket on port 9999
        // Note that we can't choose a port less than 1023 if we are not
        // privileged users (root)
        try {
            echoServer = new ServerSocket(9999);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        // Create a socket object from the ServerSocket to listen and accept
        // connections.
        // Open input and output streams
        try {
            clientSocket = echoServer.accept();
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            // As long as we receive data, echo that data back to the client.
            while (true) {
                line = is.readLine();
                os.println(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }


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
            String inputFilePath = builder.toString().substring(6);

            StockAnalysis analysis = new StockAnalysis();
            String returnData = analysis.analysis(inputFilePath);

            PrintStream printStream = new PrintStream(socket.getOutputStream());
            printStream.println(returnData);
            printStream.flush();
            printStream.close();

//            writer.close();
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
