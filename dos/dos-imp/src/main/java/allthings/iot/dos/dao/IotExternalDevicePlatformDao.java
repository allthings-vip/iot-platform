package allthings.iot.dos.dao;

import allthings.iot.dos.model.IotExternalDevicePlatform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotExternalDevicePlatformDao
 * @CreateDate :  2019/6/14
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface IotExternalDevicePlatformDao extends BaseRepository<IotExternalDevicePlatform, Long> {

    /**
     * 查询所有外部平台
     *
     * @return
     */
    @Query("FROM IotExternalDevicePlatform iedp WHERE iedp.isDeleted = false")
    List<IotExternalDevicePlatform> getPlatFormList();

    /**
     * 根据平台码查询平台信息
     *
     * @param code
     * @return
     */
    @Query("FROM IotExternalDevicePlatform iedp WHERE iedp.isDeleted = false AND iedp.platformCode=:code")
    IotExternalDevicePlatform getByCode(@Param("code") String code);

    /**
     * 根据依赖服务名查询平台信息
     *
     * @param serviceCode
     * @return
     */
    @Query("FROM IotExternalDevicePlatform iedp WHERE iedp.isDeleted = false AND iedp.dependencyService=:serviceName")
    List<IotExternalDevicePlatform> getPlatFormByDependencyService(@Param("serviceName") String serviceCode);
}
