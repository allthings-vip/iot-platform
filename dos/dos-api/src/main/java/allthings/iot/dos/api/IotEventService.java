package allthings.iot.dos.api;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.open.IotEventQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceEventDTO;
import allthings.iot.dos.dto.query.IotEventQueryListDTO;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotEventBiz
 * @CreateDate :  2018-5-12
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
public interface IotEventService {

    /**
     * 查询事件信息
     *
     * @param queryListDTO
     * @param
     * @return
     */
    ResultDTO<PageResult<IotDeviceEventDTO>> getDeviceEventsByDeviceId(IotEventQueryDTO iotEventQueryDTO);
}
