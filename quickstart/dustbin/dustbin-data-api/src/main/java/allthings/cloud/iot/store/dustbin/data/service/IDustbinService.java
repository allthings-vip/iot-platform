package tf56.cloud.iot.store.dustbin.data.service;

import tf56.cloud.iot.store.dustbin.data.dto.DustbinDeviceDataDto;
import tf56.cloud.iot.store.dustbin.data.dto.DustbinParamDto;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  IDustbinService
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
public interface IDustbinService {

    /**
     * 增加
     *
     * @param dustbinParamDto
     */
    void add(DustbinParamDto dustbinParamDto);

    /**
     * get the record by the deviceId of the dustbin
     *
     * @param deviceId data format: deviceType + deviceNumber. For example, TRCAN867587029315130
     * @return
     */
    DustbinParamDto getByDeviceId(String deviceId);

    /**
     * 更新
     *
     * @param dustbinParamDto
     */
    void update(DustbinParamDto dustbinParamDto);

    /**
     * 增加or更新
     *
     * @param dustbinParamDto
     */
    void addOrUpdate(DustbinParamDto dustbinParamDto);

    /**
     * 查询设备数据
     *
     * @param deviceId
     * @param startDatetime
     * @param endDatetime
     * @param factorCodes
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<DustbinDeviceDataDto> getDustbinDeviceDataList(String deviceId, long startDatetime, long endDatetime,
                                                     List<String> factorCodes, int pageIndex, int pageSize);
}
