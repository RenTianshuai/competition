package com.yusys;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    private int port = 7001;
    private ServerSocket serverSocket = null;
    public void startServer() throws IOException {
        serverSocket = new ServerSocket(port);
        while (true){
            System.out.println("等待连接...");
            Socket socket = serverSocket.accept();
            System.out.println(socket.getInetAddress() + "接入成功！");
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String fileName = br.readLine();
            System.out.println("需读取的文件名："+fileName);
            List<String> list = FileUtil.readFile(fileName);
            List<String> results = new ArrayList<String>();
            for (String l:list){
                String result = IntegerAnalysis.analysis(Long.parseLong(l));
                results.add(result);
            }
            String outFileName = "D://output.txt";
            FileUtil.writeFile(outFileName,results);
            pw.println(outFileName);
            pw.flush();

            br.close();
            pw.close();
            socket.close();
        }
    }
    public static void main(String[] args) throws IOException {
        new SocketServer().startServer();
    }
}
