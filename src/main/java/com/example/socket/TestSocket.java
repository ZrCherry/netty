package com.example.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author zhangrui
 * @time 2020-08-18-15:26
 * @description 功能描述
 */
public class TestSocket {
    public static void main(String[] args) {
        testScoket();
    }

    public static void testScoket(){
        Socket client = null;
        BufferedReader reader = null;
        PrintWriter printWriter = null;
        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        try {
            System.out.println("发送bye结束聊天");
            while (true){
                client = new Socket("127.0.0.1",9999);
                System.out.println("client:");
                String myMsg = scanner.nextLine();
                printWriter = new PrintWriter(client.getOutputStream());
                printWriter.write(myMsg);
                printWriter.flush();
                client.shutdownOutput();
                if ("bye".equals(myMsg.trim().toLowerCase())){
                    System.out.println("聊天结束");
                    return;
                }
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String msg = null;
                while ((msg = reader.readLine()) != null){
                    System.out.println("服务器："+msg);
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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            scanner.close();
            printWriter.close();
            try {
                client.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
