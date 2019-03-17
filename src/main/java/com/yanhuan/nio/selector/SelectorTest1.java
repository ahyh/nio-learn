package com.yanhuan.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector选择器测试
 *
 * @author yanhuan
 */
public class SelectorTest1 {

    public static void main(String[] args) throws IOException {
        int[] ports = {5000, 5001, 5002, 5003, 5004};
        Selector selector = Selector.open();

        System.out.println(SelectorProvider.provider().getClass());

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //调整channel的阻塞模式为false，非阻塞
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            //绑定
            serverSocket.bind(address);

            //注册
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("监听端口：" + ports[i]);
        }

        while (true) {
            int select = selector.select();
            System.out.println("select:" + select);

            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            System.out.println("selectionKeySet:" + selectionKeySet);

            Iterator<SelectionKey> iterator = selectionKeySet.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);

                    iterator.remove();

                    System.out.println("获取到连接：" + socketChannel);
                } else if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    int bytesRead = 0;
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        buffer.clear();
                        int read = channel.read(buffer);
                        if (read <= 0) {
                            break;
                        }
                        buffer.flip();
                        channel.write(buffer);
                        bytesRead += read;
                    }
                    System.out.println("读取：" + bytesRead + ",来自于" + channel);
                    iterator.remove();
                }
            }
        }
    }


}
