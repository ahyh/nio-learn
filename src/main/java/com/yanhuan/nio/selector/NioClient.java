package com.yanhuan.nio.selector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NioClient
 *
 * @author yanhuan
 */
public class NioClient {

    public static void main(String[] args) {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("localhost", 8899));
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    //如果已经建立好了连接
                    if (key.isConnectable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        if (client.isConnectionPending()) {
                            //完成连接
                            client.finishConnect();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                            writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);
                            //启动一个线程读取一行数据
                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.submit(() -> {
                                while (true) {
                                    try {
                                        writeBuffer.clear();
                                        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                        String line = bufferedReader.readLine();
                                        writeBuffer.put(line.getBytes());
                                        writeBuffer.flip();
                                        client.write(writeBuffer);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(512);
                        int read = client.read(readBuffer);
                        if (read > 0) {
                            String receiveMessage = new String(readBuffer.array(), 0, read);
                            System.out.println("receiveMessage:" + receiveMessage);
                        }
                    }
                }
                //清空集合
                selectionKeys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
