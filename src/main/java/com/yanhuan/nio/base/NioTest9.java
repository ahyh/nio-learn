package com.yanhuan.nio.base;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer:内存映射文件
 *
 * @author yanhuan
 */
public class NioTest9 {

    public static void main(String[] args) throws Exception {
        RandomAccessFile rsf = new RandomAccessFile("NioTest3.txt", "rw");
        FileChannel rsfChannel = rsf.getChannel();

        MappedByteBuffer mapBuffer = rsfChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        //通过文件管理器可以发现变化了，通过操作系统完成文件内容的修改
        mapBuffer.put(0, (byte) 'a');
        mapBuffer.put(3, (byte) 'c');

        rsf.close();
    }
}
