package allthings.iot.dms;

import allthings.iot.common.msg.IMsg;

/**
 * @author :  sylar
 * @FileName :  IDeviceMessageService
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
public interface IDeviceMessageService {

    /**
     * dms发送消息到das
     *
     * @param msg
     */
    void sendMsg(IMsg msg) throws Exception;

}
