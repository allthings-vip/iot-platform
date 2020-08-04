package allthings.iot.dos.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.query.IotFactorRangeValueQueryDTO;
import allthings.iot.dos.dto.query.IotFactorValueQueryDTO;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotKvDataBiz
 * @CreateDate :  2018-5-18
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
public interface IotKvDataService {

    /**
     * 获取最新的kv数据
     *
     * @param deviceCode
     * @param partyId
     * @param iotProjectId
     * @return
     */
    ResultDTO<List<IotFactorValueQueryDTO>> getKVLatest(String deviceCode, String partyId, Long iotProjectId, Long userId,
                                                        String roleCode);

    /**
     * 查询时间段内的因子数据
     *
     * @param deviceCode
     * @param partyId
     * @param factorCodes
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    ResultDTO<List<IotFactorRangeValueQueryDTO>> getKVRange(String deviceCode, String partyId, String[] factorCodes,
                                                            Long startDatetime, Long endDatetime, Long iotProjectId,
                                                            Long userId, String roleCode);

}
