//package iot.tf56.das.mqtt.core;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.codec.MessageToMessageDecoder;
//import io.netty.handler.codec.MessageToMessageEncoder;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpRequestDecoder;
//import io.netty.handler.codec.http.HttpResponseEncoder;
//import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
//import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
//import io.netty.handler.timeout.IdleStateHandler;
//import DasProperties;
//import AbstractTcpServer;
//import MqttConst;
//import MqttDecoder;
//import MqttEncoder;
//import IMessaging;
//import LmaxDiscuptorMessaging;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import java.util.List;
//
////@Service
//public class MqttWebSocketServer extends AbstractTcpServer {
//    private static final Logger LOG = LoggerFactory.getLogger(MqttWebSocketServer.class);
//
//    @Autowired
//    @Qualifier("mqttProtocolProcessor")
//    private MqttProtocolProcessor protocolProcessor;
//    private ChannelInitializer<SocketChannel> channelInitializer;
//    private IMessaging messagingService;
//
//    @Override
//    public void start() {
//        if (dasProperties.getPort() == 0) {
//            LOG.info("mqtt broker webSocket transport is disabled");
//            return;
//        }
//        if (isRunning()) {
//            LOG.warn("mqtt broker webSocket transport is already running for port:" + dasProperties.getPort());
//            return;
//        }
//        LOG.info("mqtt broker webSocket core is starting");
//        super.start();
//    }
//
//    @Override
//    public void stop() {
//        LOG.info("mqtt broker webSocket core is stopping...");
//        messagingService.stop();
//        super.stop();
//        LOG.info("mqtt broker webSocket core stopped");
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public ChannelInitializer<SocketChannel> getChannelInitializer(DasProperties dasProperties) {
//        if (channelInitializer == null) {
//            messagingService = new LmaxDiscuptorMessaging(protocolProcessor);
//            channelInitializer = new ChannelInitializer<SocketChannel>() {
//                @Override
//                public void initChannel(SocketChannel ch) throws Exception {
//                    IdleStateHandler idleStateHandler = new IdleStateHandler(dasProperties.getIdleTime(), 0, 0);
//
//                    ch.pipeline().addLast("httpEncoder", new HttpResponseEncoder())
//                            .addLast("httpDecoder", new HttpRequestDecoder())
//                            .addLast("aggregator", new HttpObjectAggregator(65536))
//                            .addLast("webSocketHandler", new WebSocketServerProtocolHandler("/mqtt", "mqttv3.1, " +
//                                    "mqttv3.1.1"))
//                            .addLast("ws2bytebufDecoder", new WebSocketFrameToByteBufDecoder())
//                            .addLast("bytebuf2wsEncoder", new ByteBufToWebSocketFrameEncoder())
//                            .addFirst(MqttConst.IDLE_STATE_HANDLER_NAME, idleStateHandler)
//                            .addLast("loggingHandler", new LoggingHandler(LogLevel.DEBUG))
//                            .addLast("decoder", new MqttDecoder())
//                            .addLast("encoder", new MqttEncoder())
//                            .addLast("handler", new MqttChannelHandler(messagingService));
//                }
//            };
//        }
//        return channelInitializer;
//    }
//
//    static class WebSocketFrameToByteBufDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {
//        @Override
//        protected void decode(ChannelHandlerContext chc, BinaryWebSocketFrame frame, List<Object>
//                out) throws Exception {
//            //convert the frame to a ByteBuf
//            ByteBuf bb = frame.content();
//            bb.retain();
//            out.add(bb);
//        }
//    }
//
//    static class ByteBufToWebSocketFrameEncoder extends MessageToMessageEncoder<ByteBuf> {
//        @Override
//        protected void encode(ChannelHandlerContext chc, ByteBuf bb, List<Object> out) throws Exception {
//            //convert the ByteBuf to a WebSocketFrame
//            BinaryWebSocketFrame result = new BinaryWebSocketFrame();
//            result.content().writeBytes(bb);
//            out.add(result);
//        }
//    }
//
//}
