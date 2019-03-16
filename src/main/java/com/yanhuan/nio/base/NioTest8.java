package com.yanhuan.nio.base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 直接缓冲DirectByteBuffer
 *
 * @author yanhuan
 */
public class NioTest8 {

    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("input.txt");
        FileOutputStream out = new FileOutputStream("outputDirect.txt");
        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(512);
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
