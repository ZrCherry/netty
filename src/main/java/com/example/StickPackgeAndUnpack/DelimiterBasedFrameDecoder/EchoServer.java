package com.example.StickPackgeAndUnpack.DelimiterBasedFrameDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author zhangrui
 * @time 2020-09-07-15:19
 * @description 功能描述
 */
public class EchoServer {

    public void bind(int port) {
        // 服务器线程组 用于网络事件的处理 一个用于服务器接收客户端的连接
        // 另一个线程组用于处理SocketChannel的网络读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //NIO服务器端的辅助启动类 降低服务器开发难度
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)    // 类似NIO中serverSocketChannel
                    .option(ChannelOption.SO_BACKLOG, 100)   // 配置TCP参数
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                      @Override
                                      protected void initChannel(SocketChannel ch) throws Exception {
                                          ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                                          ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter)); //自定义分隔符
                                          ch.pipeline().addLast(new StringDecoder());  //设置字符串解码器 自动将报文转为字符串
                                          ch.pipeline().addLast(new EchoServerHandler());
                                      }
                                  }
                    ); // 最后绑定I/O事件的处理类
            // 服务器启动后 绑定监听端口 同步等待成功 主要用于异步操作的通知回调 回调处理用的ChildChannelHandler
            ChannelFuture f = b.bind(port).sync();
            System.out.println("The time Server is start : "+port);
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            // 优雅退出 释放线程池资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("服务器优雅的释放了线程资源...");
        }
    }

    public static void main(String[] args) {
        int port = 8080;

        if(null != args && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        new EchoServer().bind(port);
    }
}
