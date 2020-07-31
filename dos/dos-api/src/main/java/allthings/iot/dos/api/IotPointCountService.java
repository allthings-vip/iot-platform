package allthings.iot.dos.api;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.query.IotDosQueryDTO;
import allthings.iot.dos.dto.query.IotPointCountQueryDTO;
import allthings.iot.dos.dto.query.IotPointCountTitleDTO;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotPointCountBiz
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
public interface IotPointCountService {

    /**
     * 获取点位数量
     *
     * @param iotDosQueryDTO
     * @return
     */
    ResultDTO<List<IotPointCountQueryDTO>> getByDateRange(IotDosQueryDTO iotDosQueryDTO);

    /**
     * 获取点位数量排名
     *
     * @param startDatetime
     * @param endDatetime
     * @param type
     * @param top
     * @return
     */
    ResultDTO<QueryResult<IotPointCountTitleDTO>> getTopByDateRange(Long startDatetime, Long endDatetime, String type
            , Integer top);

}
