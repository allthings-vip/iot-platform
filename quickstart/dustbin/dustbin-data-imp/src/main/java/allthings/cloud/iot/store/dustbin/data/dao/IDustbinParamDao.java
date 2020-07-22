package tf56.cloud.iot.store.dustbin.data.dao;

import tf56.cloud.iot.store.dustbin.data.entity.DustbinParam;
import allthings.iot.util.jpa.BaseRepository;

/**
 * @author :  sylar
 * @FileName :  IDustbinParamDao
 * @CreateDate :  2016/9/22
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip   All Rights Reserved
 * *******************************************************************************************
 */
public interface IDustbinParamDao extends BaseRepository<DustbinParam, Long> {

    /**
     * get the record by the deviceId of the dustbin
     *
     * @param deviceId
     * @return
     */
    DustbinParam getByDeviceId(String deviceId);
}
