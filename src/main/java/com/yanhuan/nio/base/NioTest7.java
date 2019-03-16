package com.yanhuan.nio.base;

import java.nio.ByteBuffer;

/**
 * 只读Buffer
 *
 * @author yanhuan
 */
public class NioTest7 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        //切换为读模式后才能读取
        readOnlyBuffer.flip();
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
        System.out.println(readOnlyBuffer);
    }
}
