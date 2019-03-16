package com.yanhuan.nio.base;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Nio测试
 *
 * @author yanhuan
 */
public class NioTest3 {

    public static void main(String[] args) throws Exception {
        FileOutputStream outputStream = new FileOutputStream("NioTest3.txt");

        //根据FileOutputStream获取FileChannel
        FileChannel channel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        byte[] bytes = "hello world!I love NIO".getBytes();

        //将字节数据放入buffer中
        for (int i = 0; i < bytes.length; i++) {
            buffer.put(bytes[i]);
        }

        //翻转buffer，从写到读
        buffer.flip();

        //通过channel写入数据到文件中
        channel.write(buffer);
        outputStream.close();
    }
}
