package com.example.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static void main(String[] args) {
       testServer();
    }

    public static void testServer(){
        ServerSocket server = null;
        Socket client = null;
        BufferedReader reader = null;
        PrintWriter printWriter = null;
        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        try {
            server = new ServerSocket(9999);
            System.out.println("服务器已启动，开始监听消息");
            while (true){
                client = server.accept();
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String msg = null;
                while ((msg = reader.readLine()) != null){
                    System.out.println("客户端："+msg);
                    if ("bye".equals(msg.trim().toLowerCase())){
                        end = true;
                        break;
                    }
                }
                client.shutdownInput();
                if (end){
                    System.out.println("聊天结束");
                    return;
                }
                System.out.println("服务器：");
                String myMsg = scanner.nextLine();
                printWriter = new PrintWriter(client.getOutputStream());
                printWriter.write(myMsg);
                printWriter.flush();
                client.shutdownOutput();
                if ("bye".equals(myMsg.trim().toLowerCase())){
                    System.out.println("聊天结束");
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            scanner.close();
            printWriter.close();
            try {
                reader.close();
                server.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
