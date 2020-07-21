package allthings.iot.dms;

import allthings.iot.dms.dto.DasStatusDto;

/**
 * @author :  sylar
 * @FileName :  MqttConst
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
public interface IDasStatusService {
    /**
     * get 设备接入服务的状态
     *
     * @param nodeId
     * @return
     */
    DasStatusDto getDasStatus(String nodeId);
}
