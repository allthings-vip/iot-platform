package allthings.iot.dos.api;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotAuthorityDTO;
import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;

/**
 * @author :  luhao
 * @FileName :  IotUserBiz
 * @CreateDate :  2018-5-25
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
public interface IotUserService {

    /**
     * 根据用户名获取user
     *
     * @return
     */
    ResultDTO<IotUserDTO> getUserByUsername(String username);

    /**
     * 添加用户
     *
     * @param iotUserDTO
     * @return
     */
    ResultDTO<Long> saveUser(IotUserDTO iotUserDTO);

    /**
     * 编辑用户
     *
     * @param iotUserDTO
     * @return
     */
    ResultDTO<Integer> updateUser(IotUserDTO iotUserDTO);

    /**
     * 管理员编辑用户
     *
     * @param iotUserDTO
     * @return
     */
    ResultDTO<Integer> updateUserByAdmin(IotUserDTO iotUserDTO);

    /**
     * 删除用户
     *
     * @param iotUserDTO
     * @return
     */
    ResultDTO<Integer> deleteUser(IotUserDTO iotUserDTO);

    /**
     * 查询用户列表
     *
     * @param iotUserQueryDTO
     * @return
     */
    ResultDTO<PageResult<IotUserDTO>> getUserList(IotUserQueryDTO iotUserQueryDTO);

    /**
     * 获取协作人员列表
     *
     * @param iotUserQueryDTO
     * @return
     */
    ResultDTO<IotUserDTO> getCollaborators(IotUserQueryDTO iotUserQueryDTO);

    /**
     * 通过用户ID获取登录用户信息
     *
     * @param iotUserQueryDTO
     * @return
     */
    ResultDTO<IotUserDTO> getUserById(IotUserQueryDTO iotUserQueryDTO);

    /**
     * 管理员获取普通用户信息
     *
     * @return
     */
    ResultDTO<IotUserDTO> getUserByAdminAndIotUserId(IotUserQueryDTO iotUserQueryDTO);

    /**
     * 修改密码
     *
     * @param iotUserDTO
     * @return
     */
    ResultDTO<Integer> updatePassword(IotUserDTO iotUserDTO);

    /**
     * 修改状态
     *
     * @param iotUserDTO
     * @return
     */
    ResultDTO<Integer> updateUserStatus(IotUserDTO iotUserDTO);

    /**
     * 权限判断
     *
     * @param iotAuthorityDTO
     * @return
     */
    ResultDTO<Integer> getAdminAuthority(IotAuthorityDTO iotAuthorityDTO);

}
