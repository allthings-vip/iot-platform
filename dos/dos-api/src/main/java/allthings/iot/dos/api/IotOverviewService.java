package allthings.iot.dos.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.query.IotDosOverviewDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;

/**
 * @author :  luhao
 * @FileName :  IotOverviewBiz
 * @CreateDate :  2018-5-20
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
public interface IotOverviewService {
    /**
     * 首页总览
     *
     * @param iotUserQueryDTO
     * @return
     */
    ResultDTO<IotDosOverviewDTO> overview(IotUserQueryDTO iotUserQueryDTO);

    /**
     * 项目总览
     *
     * @param iotUserQueryDTO
     * @return
     */
    ResultDTO<IotDosOverviewDTO> overviewByIotProjectId(IotUserQueryDTO iotUserQueryDTO);

}
