package allthings.iot.dos.api;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotProtocolDTO;
import allthings.iot.dos.dto.query.IotProtocolDetailDTO;
import allthings.iot.dos.dto.query.IotProtocolQueryDTO;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProtocolBiz
 * @CreateDate :  2018-5-10
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
public interface IotProtocolService {
    /**
     * 获取协议列表
     *
     * @return
     */
    ResultDTO<List<IotProtocolQueryDTO>> getIotProtocolList();

    /**
     * 查询协议列表
     *
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ResultDTO<QueryResult<IotProtocolDetailDTO>> getIotProtocolList(String keywords, Integer pageIndex,
                                                                    Integer pageSize);

    /**
     * 新增协议
     *
     * @param iotProtocolDTO
     * @return
     */
    ResultDTO<Integer> saveIotProtocol(IotProtocolDTO iotProtocolDTO);

    /**
     * 修改协议
     *
     * @param iotProtocolDTO
     * @return
     */
    ResultDTO<Integer> updateIotProtocol(IotProtocolDTO iotProtocolDTO);

    /**
     * 删除协议
     *
     * @param iotProtocolIds
     * @param operator
     * @return
     */
    ResultDTO<Integer> deleteIotProtocol(Long[] iotProtocolIds, String operator);

    /**
     * 查询协议详情
     *
     * @param iotProtocolId
     * @return
     */
    ResultDTO<IotProtocolDetailDTO> getIotProtocolDetail(Long iotProtocolId);

    /**
     * 通过协议编码获取协议ID
     *
     * @param protocolCode
     * @return
     */
    Long getIdByCode(String protocolCode);

    /**
     * 通过类型编码获取协议编码
     *
     * @param deviceTypeCode
     * @param projectId
     * @return
     */
    String getCodeByDeviceTypeCode(String deviceTypeCode, Long projectId);
}
