package com.yanhuan.nio.base;


import java.nio.ByteBuffer;

/**
 * 类型化put和get
 * 在buffer中放入什么数据就可以取出什么数据
 *
 * @author yanhuan
 */
public class NioTest5 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(512);
        buffer.putInt(12);
        buffer.putChar('a');
        buffer.putShort(new Short("12"));
        buffer.putLong(123L);
        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getLong());
    }
}
