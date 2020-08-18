package BIOsocket;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author zhangrui
 * @time 2020-08-18-15:25
 * @description 功能描述
 */
public class TestServerSocket {
    ServerSocket serverSocket = null;
    PrintWriter printWriter = null;
    Scanner returnsc = null;
    Scanner localsc = null;
    {
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("-----------------等待客户端连接-----------------");
            Socket socket = serverSocket.accept();
            System.out.println(socket.getInetAddress()+"已连接到服务器");
            OutputStream out = socket.getOutputStream();
            printWriter = new PrintWriter(out);
            printWriter.write("已连接到远程服务器，可以开始说话了");
            printWriter.flush();
            localsc = new Scanner(System.in);
            returnsc = new Scanner(socket.getInputStream());
            while (returnsc.hasNextLine()){
                String recvDate = returnsc.nextLine();
                printWriter.write("客户端："+recvDate);
                System.out.println("服务器：");
                String sendDate = localsc.nextLine();
                printWriter.write(sendDate);
                printWriter.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            printWriter.close();
            localsc.close();
            returnsc.close();
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
