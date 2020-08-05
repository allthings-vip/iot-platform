package allthings.iot.dos.dao;

import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import allthings.iot.dos.dto.query.IotProtocolFactorDTO;
import allthings.iot.dos.model.IotProtocolFactor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProtocolFactorDao
 * @CreateDate :  2018/5/2
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
public interface IotProtocolFactorDao extends BaseRepository<IotProtocolFactor, Long> {
    /**
     * 根据iotProtocolId查询因子列表
     *
     * @param iotProtocolId
     * @param isDeleted
     * @return
     */
    @Query(" from IotProtocolFactor where iotProtocolId=:iotProtocolId and isDeleted=:isDeleted ")
    List<IotProtocolFactor> getIotProtocolFactorByIotProtocolIdAndDeleted(@Param("iotProtocolId") Long iotProtocolId,
                                                                          @Param("isDeleted") boolean isDeleted);

    /**
     * 删除协议因子
     *
     * @param iotProtocolId
     * @param modifyOperatorId
     * @return
     */
    @Modifying
    @Query(" update IotProtocolFactor set isDeleted=true ,modifyOperatorId=:modifyOperatorId where iotProtocolId=:iotProtocolId" +
            " and isDeleted=false ")
    Integer deleteByIotProtocolId(@Param("iotProtocolId") Long iotProtocolId, @Param
            ("modifyOperatorId") Long modifyOperatorId);

    @Query(value =
            " select new allthings.iot.dos.dto.query.IotProtocolFactorDTO(itf.iotFactorId,(select iff.factorName from" +
                    " IotFactor iff  where iff.iotFactorId = itf.iotFactorId)) " +
                    " from IotProtocolFactor itf where itf.iotFactorId in (:iotFactorIds) and itf.isDeleted = false" +
                    " group by itf.iotFactorId having count(itf.iotFactorId) > 0 ")
    List<IotProtocolFactorDTO> getIotProtocolFactorByIotFactorId(@Param("iotFactorIds") List<Long> iotFactorIds);


    @Query(" select new allthings.iot.dos.dto.query.IotFactorQueryDTO(iff.iotFactorId,iff.factorCode,iff.factorName," +
            "idat" +
            ".iotDataAggTypeId,idat.dataAggTypeName, idat.dataAggTypeCode,iff.unitName,iff.unitSymbol) from IotFactor" +
            " iff ,IotDataAggType " +
            "idat ,IotProtocolFactor idtf where iff.iotDataAggTypeId = idat.iotDataAggTypeId and iff.iotFactorId = " +
            "idtf.iotFactorId and idtf.iotProtocolId =:iotProtocolId and idtf.isDeleted = false")
    List<IotFactorQueryDTO> getIotProtocolFactorByiotProtocolId(@Param("iotProtocolId") Long iotProtocolId);


    @Query(" select new allthings.iot.dos.dto.query.IotProtocolFactorDTO(iff.iotFactorId,iff.factorName) from " +
            "IotFactor " +
            "iff ,IotProtocolFactor idtf where iff.iotFactorId = " +
            "idtf.iotFactorId and idtf.iotProtocolId =:iotProtocolId and idtf.isDeleted = false")
    List<IotProtocolFactorDTO> getIotProtocolByIotProtocolId(@Param("iotProtocolId") Long iotProtocolId);
}
