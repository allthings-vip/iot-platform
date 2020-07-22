package tf56.cloud.iot.store.dustbin.data.dao;

import tf56.cloud.iot.store.dustbin.data.dto.DustbinDeviceDataDto;
import tf56.cloud.iot.store.dustbin.data.entity.DustbinDeviceData;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IDustbinDeviceDataDao
 * @CreateDate :  2017/11/17
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
public interface IDustbinDeviceDataDao {

    /**
     * 批量保存
     *
     * @param iterable
     */
    List<DustbinDeviceData> save(Iterable<DustbinDeviceData> iterable);

    /**
     * 单个保存
     *
     * @param dustbinDeviceData
     * @return
     */
    DustbinDeviceData save(DustbinDeviceData dustbinDeviceData);

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