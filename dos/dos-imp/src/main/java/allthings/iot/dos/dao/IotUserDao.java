package allthings.iot.dos.dao;

import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.model.IotUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotUserDao
 * @CreateDate :  2018-6-5
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
public interface IotUserDao extends BaseRepository<IotUser, Long> {

    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return
     */
    @Query(" from IotUser where username=:username and isDeleted=false ")
    IotUser getIotUserByUsername(@Param("username") String username);

    /**
     * 通过用户ID获取用户信息
     *
     * @param iotUserId
     * @param isDeleted
     * @return
     */
    IotUser findByIotUserIdAndIsDeleted(Long iotUserId, Boolean isDeleted);

    /**
     * 通过用户ID查询用户名
     *
     * @param iotUserId
     * @return
     */
    @Query("select username from IotUser where iotUserId=:iotUserId and isDeleted=false ")
    String getIotUserByIotUserId(@Param("iotUserId") Long iotUserId);

    /**
     * 通过手机号查询
     *
     * @param mobile
     * @return
     */
    @Query("from IotUser where mobile=:mobile and isDeleted=false ")
    IotUser getIotUserByMobile(@Param("mobile") String mobile);

    /**
     * 修改用户
     *
     * @param modifyOperatorId
     * @param realName
     * @param mobile
     * @param email
     * @param companyName
     * @param iotUserId
     * @return
     */
    @Modifying
    @Query("update IotUser set modifyOperatorId=:modifyOperatorId, realName=:realName, mobile=:mobile, email=:email," +
            "companyName=:companyName where iotUserId=:iotUserId and isDeleted=false ")
    Integer updateUser(@Param("modifyOperatorId") Long modifyOperatorId, @Param("realName") String realName, @Param(
            "mobile") String mobile,
                       @Param("email") String email, @Param("companyName") String companyName,
                       @Param("iotUserId") Long iotUserId);

    /**
     * 删除用户
     *
     * @param iotUserId
     * @param modifyOperatorId
     * @return
     */
    @Modifying
    @Query("update IotUser set modifyOperatorId=:modifyOperatorId, isDeleted=true where iotUserId=:iotUserId and " +
            "isDeleted=false ")
    Integer deleteUser(@Param("iotUserId") Long iotUserId, @Param("modifyOperatorId") Long modifyOperatorId);

    @Query("select new allthings.iot.dos.dto.IotUserDTO(iotUserId,username) from IotUser where roleCode<>:roleCode and" +
            " iotUserId<>:iotUserId and isDeleted=false and" +
            " (username=:keywords or mobile=:keywords)")
    IotUserDTO getCollaborators(@Param("iotUserId") Long iotUserId,
                                @Param("roleCode") String roleCode,
                                @Param("keywords") String keywords);

    @Modifying
    @Query("update IotUser set password=:password,salt=:salt where iotUserId=:iotUserId and isDeleted=false ")
    Integer updatePassword(@Param("iotUserId") Long iotUserId, @Param("password") String password,
                           @Param("salt") String salt);

    /**
     * 启用和禁用
     *
     * @param iotUserId
     * @param isEnabled
     * @return
     */
    @Modifying
    @Query("update IotUser set isEnabled=:isEnabled, modifyOperatorId=:modifyOperatorId where iotUserId=:iotUserId " +
            "and isDeleted=false ")
    Integer updateUserStatus(@Param("iotUserId") Long iotUserId, @Param("modifyOperatorId") Long modifyOperatorId,
                             @Param("isEnabled") Boolean isEnabled);

    /**
     * 通过角色编码查询
     *
     * @param roleCode
     * @return
     */
    @Query("from IotUser where roleCode=:roleCode and isDeleted=false ")
    List<IotUser> getAdminAuthority(@Param("roleCode") String roleCode);

    /**
     * 查询账号有管理员账号
     *
     * @param iotUserIds
     * @param roleCode
     * @return
     */
    @Query("select count(iotUserId) from IotUser where iotUserId in (:iotUserIds) and roleCode=:roleCode and " +
            "isDeleted=false ")
    Long getAdminCount(@Param("iotUserIds") Long[] iotUserIds, @Param("roleCode") String roleCode);
}
