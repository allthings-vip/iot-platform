package allthings.iot.das.common.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import allthings.iot.das.common.DasProperties;
import allthings.iot.das.common.NettyConst;

/**
 * @author :  sylar
 * @FileName :  AbstractUdpServer
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractUdpServer extends AbstractServer {

    protected NioEventLoopGroup nioEventLoopGroup;

    /**
     * 构建通道处理器
     *
     * @param pipeline 管道
     */
    protected abstract void buildChannelHandler(ChannelPipeline pipeline);

    @Override
    public void start() {

        if (isRunning) {
            return;
        }

        LOG.info("udp server is starting……");
        nioEventLoopGroup = new NioEventLoopGroup();

        try {
            int port = dasProperties.getPort();

            ChannelInitializer<DatagramChannel> channelInitializer = getChannelInitializer(dasProperties);

            Bootstrap b = new Bootstrap();
            b.group(nioEventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .handler(channelInitializer);

            ChannelFuture future = b.bind(port).sync();
            isRunning = true;
            LOG.info("udp server started at port: {}", port);

            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            LOG.info("udp server started error: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            nioEventLoopGroup.shutdownGracefully();
        }
    }


    @Override
    public void stop() {
        if (!isRunning) {
            return;
        }

        nioEventLoopGroup.shutdownGracefully();
        isRunning = false;
    }


    protected ChannelInitializer<DatagramChannel> getChannelInitializer(DasProperties dasProperties) {
        return new ChannelInitializer<DatagramChannel>() {

            @Override
            protected void initChannel(DatagramChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

                //Duplex
                pipeline.addLast(NettyConst.LOG_HANDLER_NAME, new LoggingHandler(LogLevel.INFO));

                //abstract build
                buildChannelHandler(pipeline);
            }
        };
    }

}
