package com.yanhuan.nio.base;


import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * NIO测试类
 * <p>
 * java.nio中有3个核心概念：Selector、Channel、Buffer
 * java.nio是面向块(Block)或者缓冲区(Buffer)来编程的,Buffer本身就是一块内存，底层实现上就是一个数组
 * Buffer提供了对于数组的结构化访问方式，并且可以追踪到数据访问方式
 * <p>
 * Channel指的是可以向其写入数据或是从中读取数据的对象，所有数据的读写都是通过Buffer进行的，
 * 永远不会出现直接从Channel读取或者写入数据的情况
 * Channel打开后可以进行读取、写入，Channel是双向的，可以进行读取和写入
 * Channel可以更好的反映出底层操作系统的真实情况，底层操作系统的通道就是双向的
 *
 * @author yanhuan
 */
public class NioTest1 {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            int randomNum = new SecureRandom().nextInt(20);
            buffer.put(randomNum);
        }
        //在buffer放入数据完成后，通过flip实现翻转，实现读写切换
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
