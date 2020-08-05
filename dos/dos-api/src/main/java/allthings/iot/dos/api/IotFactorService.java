package allthings.iot.dos.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotFactorDTO;
import allthings.iot.dos.dto.query.IotFactorQuerListDTO;
import allthings.iot.dos.dto.query.IotFactorQueryDTO;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotFactorBiz
 * @CreateDate :  2018/5/7
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
public interface IotFactorService {

    /**
     * 保存因子
     *
     * @param iotFactorDTO
     * @return
     */
    ResultDTO<Long> saveIotFactor(IotFactorDTO iotFactorDTO);

    /**
     * 修改因子
     *
     * @param iotFactorDTO
     * @return
     */
    ResultDTO<Long> updateIotFactor(IotFactorDTO iotFactorDTO);


    /**
     * 删除因子
     *
     * @param iotFactorIds
     * @param modifyOperatorId
     * @return
     */
    ResultDTO<Integer> deleteIotFactor(Long[] iotFactorIds, Long modifyOperatorId);

    /**
     * 查询因子列表
     *
     * @param iotFactorQuerListDTO
     * @return
     */
    ResultDTO<List<IotFactorQueryDTO>> getIotFactorList(IotFactorQuerListDTO iotFactorQuerListDTO);

    /**
     * 通过设备类型编码查询因子列表
     *
     * @param deviceTypeCode
     * @param iotProjectId
     * @return
     */
    ResultDTO<List<IotFactorQueryDTO>> getIotFactorListByDeviceCode(String deviceTypeCode, Long iotProjectId);

    /**
     * 查询因子详情
     *
     * @param iotFactorId
     * @param operator
     * @return
     */
    ResultDTO<IotFactorQueryDTO> getIotFactorDetail(Long iotFactorId, String operator);

}
