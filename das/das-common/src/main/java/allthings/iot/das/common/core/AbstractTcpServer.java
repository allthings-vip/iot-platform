package allthings.iot.das.common.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.IdleStateHandler;
import allthings.iot.das.common.DasProperties;
import allthings.iot.das.common.NettyConst;

/**
 * @author :  sylar
 * @FileName :  AbstractTcpServer
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
public abstract class AbstractTcpServer extends AbstractServer {

    protected NioEventLoopGroup acceptorGroup;
    protected NioEventLoopGroup workerGroup;

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

        LOG.info("tcp server is starting……");
        acceptorGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {
            int port = dasProperties.getPort();

            ChannelInitializer<SocketChannel> channelInitializer = getChannelInitializer(dasProperties);

            ServerBootstrap b = new ServerBootstrap();
            b.group(acceptorGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(channelInitializer);
            ChannelFuture future = b.bind(port).sync();
            isRunning = true;
            LOG.info("tcp server started at port: {}", port);

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOG.info("tcp server started error: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            acceptorGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            isRunning = false;
        }
    }

    @Override
    public void stop() {
        if (!isRunning) {
            return;
        }

        workerGroup.shutdownGracefully();
        acceptorGroup.shutdownGracefully();
        isRunning = false;
    }


    protected ChannelInitializer<SocketChannel> getChannelInitializer(final DasProperties dasProperties) {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                IdleStateHandler idleHandler = new IdleStateHandler(dasProperties.getIdleTime(), 0, 0);
                ChannelPipeline pipeline = ch.pipeline();

                //Duplex
                pipeline.addLast(NettyConst.IDLE_STATE_HANDLER_NAME, idleHandler);
                pipeline.addLast(NettyConst.LOG_HANDLER_NAME, new ExLoggingHandler(LogLevel.INFO));

                //abstract build
                buildChannelHandler(pipeline);
            }
        };
    }

}
