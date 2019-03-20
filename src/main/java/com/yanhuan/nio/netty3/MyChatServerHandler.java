package com.yanhuan.nio.netty3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * MyChatServer处理器
 *
 * @author yanhuan
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 获取到ChannelGroup组
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 实现消息广播
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + "发送的消息:" + msg + "\n");
            } else {
                ch.writeAndFlush("【自己】" + msg + "\n");
            }
        });
    }

    /**
     * 当客户端与服务端建立连接的时候触发
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //告知其他的连接有新的连接建立,先广播在加入到group
        channelGroup.writeAndFlush("【服务器】-" + channel.remoteAddress() + "加入\n");
        channelGroup.add(channel);
    }

    /**
     * 连接断开
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //先广播在移除，Netty会自动调用remove方法
        channelGroup.writeAndFlush("【服务器】-" + channel.remoteAddress() + "离开\n");
    }

    /**
     * 连接处于活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //先广播在移除，Netty会自动调用remove方法
        System.out.println("【服务器】-" + channel.remoteAddress() + "上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //先广播在移除，Netty会自动调用remove方法
        channelGroup.writeAndFlush("【服务器】-" + channel.remoteAddress() + "下线\n");
    }

    /**
     * 如果出现异常则打印并关闭连接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
