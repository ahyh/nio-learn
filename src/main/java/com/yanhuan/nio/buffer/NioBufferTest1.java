package com.yanhuan.nio.buffer;

/**
 * Buffer中的3个属性：position、limit、capacity
 *
 * position:下一个将被读/写的元素的索引,不能超过limit
 * limit:不能再读/写的第一个元素的索引，limit不能为负数且必须小于capacity
 * capacity:是一个Buffer的容量大小，永远不会变化且不能为负数
 *
 *
 * @author yanhuan
 */
public class NioBufferTest1 {

    public static void main(String[] args) {

    }
}
