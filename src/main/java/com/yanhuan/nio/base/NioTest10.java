package com.yanhuan.nio.base;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * NIO文件锁
 *
 * @author yanhuan
 */
public class NioTest10 {

    public static void main(String[] args) throws Exception {
        RandomAccessFile rsf = new RandomAccessFile("NioTest2.txt", "rw");

        FileChannel rsfChannel = rsf.getChannel();

        FileLock lock = rsfChannel.lock(3, 6, true);

        System.out.println("valid:" + lock.isValid());
        System.out.println("shared:" + lock.isShared());

        lock.release();

        rsf.close();
    }
}
