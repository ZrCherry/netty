package com.example.aio;

/**
 * @Author zhangrui
 * @time 2020-09-03-14:25
 * @description 功能描述
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        AsyncTimeServerHandler asyncTimeServerHandler = new AsyncTimeServerHandler(port);
        new Thread(asyncTimeServerHandler,"AIO-AsyncTimeServerHandler-001").start();
    }
}
