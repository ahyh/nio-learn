package com.yanhuan.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * NIO服务端
 *
 * @author yanhuan
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        //服务端样板式代码
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(new InetSocketAddress(8899));

        //创建选择器对象
        Selector selector = Selector.open();
        //将channel注册到selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final Map<String, SocketChannel> clientMap = new HashMap<>();
        while (true) {
            try {
                //阻塞方法，返回关注事件的channel的数量
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(key -> {
                    final SocketChannel client;
                    try {
                        //表示客户端向服务端发起连接
                        if (key.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            //客户端注册
                            client.register(selector, SelectionKey.OP_READ);
                            String uuid = UUID.randomUUID().toString();
                            clientMap.put(uuid, client);
                        } else if (key.isReadable()) {
                            //读取数据
                            client = (SocketChannel) key.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(512);
                            int read = client.read(readBuffer);
                            String message = null;
                            if (read > 0) {
                                readBuffer.flip();
                                Charset charset = Charset.forName("UTF-8");
                                message = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println(client + ":" + message);
                            }
                            String senderKey = null;
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                if (client == entry.getValue()) {
                                    senderKey = entry.getKey();
                                    break;
                                }
                            }
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                SocketChannel socketChannel = entry.getValue();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                                writeBuffer.put((senderKey + ":" + message).getBytes());
                                writeBuffer.flip();
                                socketChannel.write(writeBuffer);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
