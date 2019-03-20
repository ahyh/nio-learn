package com.yanhuan.nio.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 服务端处理器
 *
 * @author yanhuan
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 用户事件触发
     *
     * @param ctx 上下文对象
     * @param evt 事件对象
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
                case READER_IDLE:
                    //读空闲
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                default:
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "超时事件" + eventType);
            //关闭连接
            ctx.channel().closeFuture();
        }
    }
}
