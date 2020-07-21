package allthings.iot.dms.service;

import allthings.iot.common.msg.IMsg;

/**
 * @author :  sylar
 * @FileName :  IDmsMsgProcessor
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
public interface IDmsMsgProcessor<T extends IMsg> {
    /**
     * 处理IMsg消息
     *
     * @param msg
     */
    void processMsg(T msg) throws Exception;
}
