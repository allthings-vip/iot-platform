package allthings.iot.dos.api;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotProjectDTO;
import allthings.iot.dos.dto.query.IotAppSecretQueryDTO;
import allthings.iot.dos.dto.query.IotProjectDeleteQueryDTO;
import allthings.iot.dos.dto.query.IotProjectQueryDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProjectBiz
 * @CreateDate :  2018/5/4
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
public interface IotProjectService {

    /**
     * 保存项目
     *
     * @param iotProjectDTO
     * @return
     */
    ResultDTO<Long> saveIotProject(IotProjectDTO iotProjectDTO);

    /**
     * 删除项目
     *
     * @param iotProjectDeleteQueryDTO
     * @return
     */
    ResultDTO<Integer> deleteIotProject(IotProjectDeleteQueryDTO iotProjectDeleteQueryDTO);

    /**
     * 更新项目
     *
     * @param iotProjectDTO
     * @return
     */
    ResultDTO<Integer> updateIotProject(IotProjectDTO iotProjectDTO);

    /**
     * 获取项目列表
     *
     * @param iotProjectSimpleDTO
     * @return
     */
    ResultDTO<PageResult<IotProjectQueryDTO>> getIotProjectList(IotProjectSimpleDTO iotProjectSimpleDTO);

    /**
     * 查询项目名称列表
     *
     * @param iotProjectSimpleDTO
     * @return
     */
    ResultDTO<List<IotProjectSimpleDTO>> getIotProjectNameList(IotProjectSimpleDTO iotProjectSimpleDTO);

    /**
     * 获取项目详情
     *
     * @param iotProjectSimpleDTO
     * @return
     */
    ResultDTO<IotProjectDTO> getIotProjectDetail(IotProjectSimpleDTO iotProjectSimpleDTO);

    /**
     * 项目审核
     *
     * @param iotProjectDTO
     * @return
     */
    ResultDTO<Integer> reviewProject(IotProjectDTO iotProjectDTO);

    /**
     * 获取AppSecret
     *
     * @param iotAppSecretQueryDTO
     * @return
     */
    ResultDTO<String> getAppSecret(IotAppSecretQueryDTO iotAppSecretQueryDTO);

    /**
     * 通过clientId获取项目信息
     *
     * @param clientId
     * @return
     */
    IotProjectDTO getProjectByClientId(String clientId);
}
