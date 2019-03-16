package com.yanhuan.nio.base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel使用，实现文件的拷贝
 * 通过NIO读取文件有3个步骤
 * 1-从FileInputStream获取到FileChannel对象
 * 2-创建Buffer对象
 * 3-将数据从Channel读取到Buffer中
 *
 * @author yanhuan
 */
public class NioTest4 {

    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("input.txt");
        FileOutputStream out = new FileOutputStream("output.txt");
        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);
        while (true) {
            //并没有清空buffer中内容，只是改变了pos、limit和mark的值
            buffer.clear();
            int read = inChannel.read(buffer);
            if (read == -1) {
                break;
            }
            buffer.flip();
            outChannel.write(buffer);
        }
        inChannel.close();
        outChannel.close();
    }
}
