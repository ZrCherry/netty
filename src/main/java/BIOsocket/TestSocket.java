package BIOsocket;

import java.io.IOException;
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
        Socket socket = null;
        Scanner localsc = null;
        Scanner returnsc = null;
        PrintWriter printWriter = null;
        try {
            System.out.println("-----------------正在请求连接-----------------");
            socket = new Socket("127.0.0.1",9999);
            returnsc = new Scanner(socket.getInputStream());
            if (returnsc.hasNextLine()) {
                System.out.println(returnsc.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
