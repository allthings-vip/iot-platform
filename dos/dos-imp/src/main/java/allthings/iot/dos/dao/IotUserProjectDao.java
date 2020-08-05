package allthings.iot.dos.dao;

import allthings.iot.dos.dto.IotUserProjectDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import allthings.iot.dos.model.IotUserProject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-10-31 10:40
 */

public interface IotUserProjectDao extends BaseRepository<IotUserProject, Long> {

    /**
     * 通过用户ID查询拥有项目列表
     *
     * @param iotUserId
     * @return
     */
    @Query("from IotUserProject where iotUserId=:iotUserId and isDeleted=false ")
    List<IotUserProject> getIotUserProjectByIotUserId(@Param("iotUserId") Long iotUserId);

    /**
     * 查询用户拥有的项目
     *
     * @param iotUserId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.query.IotProjectSimpleDTO(ip.iotProjectId,ip.projectName) " +
            "from IotProject ip,IotUserProject up where up.iotUserId=:iotUserId " +
            "and up.iotProjectId=ip.iotProjectId and up.isDeleted=false and ip.isDeleted=false ")
    List<IotProjectSimpleDTO> getProjectListByIotUserId(@Param("iotUserId") Long iotUserId);

    /**
     * 通过项目ID查询列表
     *
     * @param iotProjectId
     * @param isOwner
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotUserProjectDTO(ip.projectName, up.iotProjectId, u.iotUserId, u" +
            ".username, ip.isReview) " +
            "from IotUserProject up, IotProject ip, IotUser u where up.iotProjectId=:iotProjectId and " +
            "up.iotProjectId=ip.iotProjectId and up.iotUserId=u.iotUserId and up.isOwner=:isOwner and " +
            "u.isDeleted=false and up.isDeleted=false and ip.isDeleted=false ")
    List<IotUserProjectDTO> getIotUserByProjectId(@Param("iotProjectId") Long iotProjectId,
                                                  @Param("isOwner") Boolean isOwner);

    /**
     * 删除项目协作人
     *
     * @param iotProjectId
     * @param modifyOperatorId
     * @return
     */
    @Modifying
    @Query("update IotUserProject set isDeleted=true, modifyOperatorId=:modifyOperatorId where " +
            "iotProjectId=:iotProjectId" +
            " and isOwner=false and isDeleted=false ")
    Integer deleteUserProject(@Param("iotProjectId") Long iotProjectId,
                              @Param("modifyOperatorId") Long modifyOperatorId);

    /**
     * 删除项目
     *
     * @param iotProjectId
     * @param modifyOperatorId
     * @return
     */
    @Modifying
    @Query("update IotUserProject set isDeleted=true, modifyOperatorId=:modifyOperatorId where " +
            "iotProjectId=:iotProjectId" +
            " and isDeleted=false ")
    Integer deleteUserProjectByIotProjectId(@Param("iotProjectId") Long iotProjectId,
                                            @Param("modifyOperatorId") Long modifyOperatorId);

    /**
     * 查询项目协作人
     *
     * @param iotProjectId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.query.IotUserQueryDTO(u.iotUserId, u.username) from IotUserProject up, " +
            "IotUser u where " +
            "up.iotProjectId=:iotProjectId and up.iotUserId=u.iotUserId and up.isOwner=false and up.isDeleted=false " +
            "and u.isDeleted=false ")
    List<IotUserQueryDTO> getIotUserListByProjectId(@Param("iotProjectId") Long iotProjectId);

    /**
     * 通过项目ID和用户ID查询项目记录
     *
     * @param iotUserId
     * @param iotProjectId
     * @return
     */
    @Query("from IotUserProject where iotUserId=:iotUserId and iotProjectId=:iotProjectId and isDeleted=false ")
    IotUserProject getIotUserProjectByIotUserIdAndIotProjectId(@Param("iotUserId") Long iotUserId, @Param(
            "iotProjectId") Long iotProjectId);
}
