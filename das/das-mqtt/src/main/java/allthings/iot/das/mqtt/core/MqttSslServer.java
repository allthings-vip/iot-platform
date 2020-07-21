//package allthings.iot.das.mqtt.core;
//
//import com.google.common.base.Strings;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
//import io.netty.handler.ssl.SslHandler;
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
//import javax.annotation.PostConstruct;
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLEngine;
//import java.io.InputStream;
//import java.security.KeyStore;
//
//public class MqttSslServer extends AbstractTcpServer {
//    private static final Logger LOG = LoggerFactory.getLogger(MqttSslServer.class);
//
//    private static final String ParamKey_SslJksPath = "SslJksPath";
//    private static final String ParamKey_SslKeyStorePassword = "SslKeyStorePassword";
//    private static final String ParamKey_SslKeyManagerPassword = "SslKeyManagerPassword";
//
//    @Autowired
//    @Qualifier("mqttProtocolProcessor")
//    private MqttProtocolProcessor protocolProcessor;
//    private ChannelInitializer<SocketChannel> channelInitializer;
//    private IMessaging messagingService;
//    private String jksPath, storePwd, managerPwd;
//
//    @PostConstruct
//    public void init() {
//        super.init();
//        jksPath = dasProperties.getParam(ParamKey_SslJksPath);
//        storePwd = dasProperties.getParam(ParamKey_SslKeyStorePassword);
//        managerPwd = dasProperties.getParam(ParamKey_SslKeyManagerPassword);
//    }
//
//    @Override
//    public void start() {
//        int port = dasProperties.getPort();
//        if (port == 0) {
//            LOG.info("mqtt broker ssl transport is disabled");
//            return;
//        }
//        if (isRunning()) {
//            LOG.warn("mqtt broker ssl transport is already running for port:" + port);
//            return;
//        }
//        if (Strings.isNullOrEmpty(jksPath)) {
//            //key_store_password or key_manager_password are empty
//            LOG.warn("You have configured the SSL port but not the jks_path, SSL not started");
//            return;
//        }
//
//        //if we have the port also the jks then keyStorePassword and keyManagerPassword has to be defined
//        if (Strings.isNullOrEmpty(storePwd)) {
//            //key_store_password or key_manager_password are empty
//            LOG.warn("You have configured the SSL port but not the key_store_password, SSL not started");
//            return;
//        }
//        if (Strings.isNullOrEmpty(managerPwd)) {
//            //key_manager_password or key_manager_password are empty
//            LOG.warn("You have configured the SSL port but not the key_manager_password, SSL not started");
//            return;
//        }
//        LOG.info("mqtt broker ssl core is starting");
//        super.start();
//    }
//
//    @Override
//    public void stop() {
//        LOG.info("mqtt broker ssl core is stopping...");
//        messagingService.stop();
//        super.stop();
//        LOG.info("mqtt broker ssl core stopped");
//    }
//
//
//    @Override
//    public ChannelInitializer<SocketChannel> getChannelInitializer(DasProperties dasProperties) {
//        if (channelInitializer == null) {
//            messagingService = new LmaxDiscuptorMessaging(protocolProcessor);
//            channelInitializer = new ChannelInitializer<SocketChannel>() {
//                @Override
//                public void initChannel(SocketChannel ch) throws Exception {
//                    InputStream jksInputStream = getClass().getClassLoader().getResourceAsStream(jksPath);
//                    SSLContext serverContext = SSLContext.getInstance("TLS");
//                    final KeyStore ks = KeyStore.getInstance("JKS");
//                    ks.load(jksInputStream, storePwd.toCharArray());
//                    final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
//                            .getDefaultAlgorithm());
//                    kmf.init(ks, managerPwd.toCharArray());
//                    serverContext.init(kmf.getKeyManagers(), null, null);
//
//                    SSLEngine engine = serverContext.createSSLEngine();
//                    engine.setUseClientMode(false);
//                    final SslHandler sslHandler = new SslHandler(engine);
//
//                    IdleStateHandler idleStateHandler = new IdleStateHandler(dasProperties.getIdleTime(), 0, 0);
//                    ch.pipeline().addLast("ssl", sslHandler)
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
//}
