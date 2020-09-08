package com.example.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Author zhangrui
 * @time 2020-09-03-14:37
 * @description 功能描述
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachement) {
        /*
        * 已经成功了，为什么这里再次调用accept方法
        *
        *
        * 调用这个方法，如果有新的客户端接入，会回调我们传入CompletionHandler的completed方法，表示新的通道调用成功
        * 一个异步通道可以接收成千上万个客户端，这里异步调用accept()来接收其他的客户端连接。
        * */
        attachement.asynchronousServerSocketChannel.accept(attachement,this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer,buffer,new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
