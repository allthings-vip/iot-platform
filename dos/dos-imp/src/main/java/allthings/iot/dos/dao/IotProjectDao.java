package allthings.iot.dos.dao;

import allthings.iot.dos.dto.IotProjectDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.dos.model.IotProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProjectDao
 * @CreateDate :  2018/4/27
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
public interface IotProjectDao extends BaseRepository<IotProject, Long> {

    /**
     * 删除项目
     *
     * @param iotProjectId
     * @param modifyOperatorId
     * @return
     */
    @Modifying
    @Query(" update IotProject set isDeleted=true, modifyOperatorId=:modifyOperatorId where " +
            "iotProjectId=:iotProjectId and " +
            "isDeleted=false ")
    Integer deleteByIotProjectId(@Param("iotProjectId") Long iotProjectId,
                                 @Param("modifyOperatorId") Long modifyOperatorId);

    /**
     * 修改项目
     *
     * @param iotProjectId
     * @param projectName
     * @param imageUrl
     * @param description
     * @param companyName
     * @param modifyOperatorId
     * @return
     */
    @Modifying
    @Query(" UPDATE IotProject SET  modifyOperatorId=:modifyOperatorId, companyName=:companyName, " +
            "description=:description, " +
            "imageUrl=:imageUrl, projectName=:projectName WHERE iotProjectId=:iotProjectId and isDeleted=false ")
    Integer updateIotProject(@Param("iotProjectId") Long iotProjectId, @Param("projectName") String projectName, @Param
            ("imageUrl") String imageUrl, @Param("description") String description, @Param("companyName") String
                                     companyName, @Param("modifyOperatorId") Long modifyOperatorId);

    /**
     * 查询项目名称列表
     *
     * @param isDeleted
     * @return
     */
    @Query(" select new allthings.iot.dos.dto.query.IotProjectSimpleDTO(iotProjectId,projectName) from IotProject " +
            "where " +
            "isDeleted=:isDeleted and isReview=true")
    List<IotProjectSimpleDTO> getProjectNameByDeleted(@Param("isDeleted") boolean isDeleted);

    /**
     * 通过项目ID查询
     *
     * @param iotProjectId
     * @param isDeleted
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotProjectDTO(ip.iotProjectId,ip.projectName,ip.companyName,ip" +
            ".description,ip.imageUrl," +
            " (select u.username from IotUser u where u.iotUserId=ip.createOperatorId and u.isDeleted=false ), ip" +
            ".inputDate, ip.clientId)" +
            " from IotProject ip where ip.iotProjectId=:iotProjectId and ip.isDeleted=:isDeleted ")
    IotProjectDTO getByIotProjectIdAndDeleted(@Param("iotProjectId") Long iotProjectId, @Param("isDeleted") boolean
            isDeleted);

    /**
     * 通过用户ID和项目ID查询
     *
     * @param iotProjectId
     * @param iotUserId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotProjectDTO(ip.iotProjectId,ip.projectName,ip.companyName,ip" +
            ".description,ip.imageUrl, " +
            "(select u.username from IotUser u where u.iotUserId=ip.createOperatorId and u.isDeleted=false ), ip" +
            ".inputDate, ip.clientId) " +
            "from IotProject ip, IotUserProject up where ip.iotProjectId=:iotProjectId and ip.iotProjectId=up" +
            ".iotProjectId and " +
            "up.iotUserId=:iotUserId and ip.isDeleted=false and up.isDeleted=false ")
    IotProjectDTO getByIotProjectIdAndIotUserId(@Param("iotProjectId") Long iotProjectId,
                                                @Param("iotUserId") Long iotUserId);

    /**
     * 分页查询项目ID
     *
     * @param pageable
     * @return
     */
    @Query(" select t.iotProjectId from IotProject t where t.isDeleted = false and t.isReview=true ")
    Page<Long> getIotProjectIdByPage(@Param("pageable") Pageable pageable);

    /**
     * 管理员统计所有项目数量
     *
     * @return
     */
    @Query(" select count(iotProjectId) from IotProject  where isDeleted=false ")
    Long getIotProjectCount();

    /**
     * 查询通过审核的项目数量
     *
     * @return
     */
    @Query("select count(iotProjectId) from IotProject where isDeleted=false and isReview=true ")
    Integer getIotProjectCountByReview();

    /**
     * 用户统计拥有项目数量
     *
     * @param iotUserId
     * @return
     */
    @Query(" select count(up.iotProjectId) from IotProject ip, IotUserProject up  where up.iotUserId=:iotUserId " +
            "and ip.iotProjectId=up.iotProjectId and ip.isDeleted=false and up.isDeleted=false ")
    Long getIotProjectCountByIotUserId(@Param("iotUserId") Long iotUserId);

    /**
     * 通过项目名和用户ID查询
     *
     * @param projectName
     * @param iotUserId
     * @return
     */
    @Query(" select p.iotProjectId from IotProject p, IotUserProject up where p.iotProjectId=up.iotProjectId and p" +
            ".projectName=:projectName " +
            "and up.iotUserId=:iotUserId and p.isDeleted = false and up.isDeleted = false")
    Long getIotProjectIdByProjectName(@Param("projectName") String projectName, @Param("iotUserId") Long iotUserId);

    /**
     * 项目审核
     *
     * @param iotProjectId
     * @param status
     * @return
     */
    @Modifying
    @Query("update IotProject set isReview=:status, clientId=:clientId where iotProjectId=:iotProjectId and " +
            "isDeleted=false ")
    Integer reviewProject(@Param("iotProjectId") Long iotProjectId, @Param("status") Boolean status,
                          @Param("clientId") String clientId);

    /**
     * 通过项目ID查询项目是否存在
     *
     * @param iotProjectId
     * @param isDeleted
     * @return
     */
    IotProject findByIotProjectIdAndIsDeleted(Long iotProjectId, Boolean isDeleted);

    /**
     * 通过clientId查询
     *
     * @param clientId
     * @param isDeleted
     * @return
     */
    IotProject findByClientIdAndIsDeleted(String clientId, Boolean isDeleted);

    @Query("from IotProject where iotProjectId=:iotProjectId")
    IotProject getIotProjectByIotProjectId(@Param("iotProjectId") Long iotProjectId);

}
