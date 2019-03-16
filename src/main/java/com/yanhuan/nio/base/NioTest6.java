package com.yanhuan.nio.base;

import java.nio.ByteBuffer;

/**
 * 分片buffer，sliceBuffer
 * 原有的Buffer和SliceBuffer底层的数据时同一份，对任意一份的修改都是影响到另外一份
 *
 * @author yanhuan
 */
public class NioTest6 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        buffer.position(2);
        buffer.limit(6);
        ByteBuffer slice = buffer.slice();

        for (int j = 0; j < slice.capacity(); j++) {
            byte b = slice.get(j);
            b *= 2;
            slice.put(j, b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }
}
