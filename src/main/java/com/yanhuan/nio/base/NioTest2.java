package com.yanhuan.nio.base;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 将Stream转换成Channel
 *
 * @author yanhuan
 */
public class NioTest2 {

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("NioTest2.txt");
        //通过流来获取channel
        FileChannel channel = inputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        //通过FileChannel将数据读入buffer中
        channel.read(buffer);

        //buffer翻转
        buffer.flip();

        //如果有数据就打印出来
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.println("Channel Byte:" + (char) b);
        }

        inputStream.close();
    }
}
