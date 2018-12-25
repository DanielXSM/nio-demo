package com.nio.day1;

import io.netty.channel.ServerChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author daniel
 * @date 2018-12-25 17:56
 */
public class NioServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverChannel=ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(9999));
        System.out.println("NIO NIOServer has tarted ,listening on port"+serverChannel.getLocalAddress());
        Selector selector=Selector.open();
        //将服务端的channel注册到选择器上
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        //不断的轮训
        while(true){
            // select
            int select =selector.select();
            if(select==0){
                System.out.println("=======================0");
                continue;
            }
            //selectionkey 代表了客户端和服务端的channel
            Set<SelectionKey>selectionKeys=selector.selectedKeys();
            Iterator<SelectionKey> iterator=selectionKeys.iterator();
            System.out.println("------------");
            while (iterator.hasNext()){
                System.out.println("有没有啊");
                SelectionKey key=iterator.next();
                System.out.println("----------"+key.toString());
                if(key.isAcceptable()){
                   ServerSocketChannel channel =(ServerSocketChannel)key.channel();
                    SocketChannel clientChannel=channel.accept();
                    System.out.println("Connection from"+clientChannel.getRemoteAddress());
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector,SelectionKey.OP_READ);

                }

                if(key.isReadable()){
                    SocketChannel channel =(SocketChannel)key.channel();
                    channel.read(buffer);
                    String request=new String (buffer.array()).trim();
                    buffer.clear();
                    System.out.println(String.format("FROM %s  : %s",channel.getRemoteAddress(),request));
                    String str="===========/n"+request;
                    channel.write(ByteBuffer.wrap(str.getBytes()));

                }
            }

        }
    }

}
