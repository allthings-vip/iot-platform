package allthings.iot.das.common.core;

import allthings.iot.das.common.DasConfig;
import allthings.iot.das.common.DasProperties;
import allthings.iot.das.common.event.ServerConnectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author :  sylar
 * @FileName :  AbstractServer
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractServer implements IServer {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ApplicationContext ctx;

    @Autowired
    protected DasConfig dasConfig;

    @Autowired
    protected InboundMsgHandler inboundMsgHandler;

    protected boolean isRunning;

    protected DasProperties dasProperties;

    @PostConstruct
    protected void init() {
        dasProperties = dasConfig.getDasProperties();
        onInitServer();
    }

    @PreDestroy
    private void dispose() {
        onDisposeServer();
    }


    @Override
    public void restart() {
        stop();
        start();
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    protected void onInitServer() {
        ctx.publishEvent(new ServerConnectEvent(this, dasConfig.getDasNodeId(), dasConfig.getDasProperties().getHost
                (), dasConfig.getDasProperties().getPort(), true));
    }

    protected void onDisposeServer() {
        ctx.publishEvent(new ServerConnectEvent(this, dasConfig.getDasNodeId(), dasConfig.getDasProperties().getHost
                (), dasConfig.getDasProperties().getPort(), false));
    }

}
