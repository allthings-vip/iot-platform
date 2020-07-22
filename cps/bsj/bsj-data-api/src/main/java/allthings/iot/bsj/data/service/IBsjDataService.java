package allthings.iot.bsj.data.service;

import allthings.iot.bsj.data.service.dto.BsjCmdDto;

/**
 * @author :  luhao
 * @FileName :  IBsjDataService
 * @CreateDate :  2018/1/11
 * @Description : bsj 2929协议数据服务
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public interface IBsjDataService {

    /**
     * 发送设置，查询等指令
     *
     * @param bsjCmdDto
     */
    void sendCmd(BsjCmdDto bsjCmdDto) throws Exception;

}
