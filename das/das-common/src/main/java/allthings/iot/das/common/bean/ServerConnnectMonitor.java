package allthings.iot.das.common.bean;

import allthings.iot.common.msg.DasConnectionMsg;
import allthings.iot.das.common.event.ServerConnectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * @author :  sylar
 * @FileName :  ServerConnnectMonitor
 * @CreateDate :  2017/11/08
 * @Description : 服务状态监控 :上下线时发送消息给DMS
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class ServerConnnectMonitor implements ApplicationListener<ServerConnectEvent> {
    @Autowired
    MsgThrower msgThrower;

    @Override
    public void onApplicationEvent(ServerConnectEvent event) {
        DasConnectionMsg msg = new DasConnectionMsg();
        msg.setDasInfo(event.getDasNodeId(), event.getIp(), event.getPort(), event.isConnected());
        msgThrower.sendToQueue(msg);
    }
}
