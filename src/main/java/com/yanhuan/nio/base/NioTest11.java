package com.yanhuan.nio.base;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering:将来自一个Channel中的数据读到多个Buffer中
 * Gathering:将多个Buffer中的数据写到同一个Channel中
 *
 * @author yanhuan
 */
public class NioTest11 {

    /**
     * 监听8899端口号
     */
    public static void main(String[] args) throws Exception {
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        InetSocketAddress address = new InetSocketAddress(8899);

        ssChannel.socket().bind(address);

        int msgLen = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel accept = ssChannel.accept();
        while (true) {
            int bytesRead = 0;
            while (bytesRead < msgLen) {
                long read = accept.read(buffers);
                bytesRead += read;
                System.out.println("bytesRead:" + bytesRead);
                Arrays.asList(buffers).stream().map(x -> x.toString()).forEach(System.out::println);
            }

            Arrays.asList(buffers).forEach(b -> b.flip());

            long writeLen = 0;

            while (writeLen < msgLen) {
                long write = accept.write(buffers);
                writeLen += write;
            }

            Arrays.asList(buffers).forEach(b -> b.clear());

            System.out.println("bytesRead:" + bytesRead + "writeLen" + writeLen);
        }
    }

}
