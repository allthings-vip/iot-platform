package allthings.iot.dos.dao;

import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import allthings.iot.dos.dto.query.IotProtocolFactorDTO;
import allthings.iot.dos.model.IotFactor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotFactorDao
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
public interface IotFactorDao extends BaseRepository<IotFactor, Long> {

    @Query("select new com.allthings.iot.dos.dto.query.IotProtocolFactorDTO(factor.iotFactorId, factor.factorName) from " +
            "IotFactor factor where exists (select iotFactorId from IotProtocolFactor t " +
            "where t.iotProtocolId=:iotProtocolId and t.isDeleted=:isDeleted " +
            "and factor.iotFactorId = t.iotFactorId) and factor.isDeleted=:isDeleted ")
    List<IotProtocolFactorDTO> getByIotProtocolId(@Param("iotProtocolId") Long iotProtocolId, @Param
            ("isDeleted") boolean isDeleted);

    @Modifying
    @Query(" update IotFactor set isDeleted=true ,operator=:operator where iotFactorId=:iotFactorId and " +
            "isDeleted=false ")
    Integer deleteByIotFactorId(@Param("iotFactorId") Long iotFactorId, @Param("operator") String operator);


    @Query(" select new com.allthings.iot.dos.dto.query.IotFactorQueryDTO(t.iotFactorId,t.factorCode,t.factorName," +
            "ia.dataAggTypeName, ia.dataAggTypeCode,t.unitName, t.unitSymbol) " +
            " from IotFactor t,IotDataAggType ia " +
            "where  (t.factorCode like :keywords or t.factorName like :keywords)" +
            " and t.isDeleted=false and ia.iotDataAggTypeId=t.iotDataAggTypeId order by t.stampDate desc")
    Page<IotFactorQueryDTO> getAllByKeyword(@Param("keywords") String keywords, @Param("pageable") Pageable pageable);

    @Query(" select new com.allthings.iot.dos.dto.query.IotFactorQueryDTO(t.iotFactorId,t.factorCode,t.factorName," +
            "ia.dataAggTypeName, ia.dataAggTypeCode, t.unitName, t.unitSymbol) " +
            " from IotFactor t, IotDataAggType ia, IotProtocolFactor pf " +
            " where pf.iotProtocolId=:iotProtocolId and t.iotFactorId=pf.iotFactorId and t.isDeleted=false " +
            " and ia.iotDataAggTypeId=t.iotDataAggTypeId order by t.stampDate desc")
    List<IotFactorQueryDTO> getAll(@Param("iotProtocolId") Long iotProtocolId);

    @Query(" select new com.allthings.iot.dos.dto.query.IotFactorQueryDTO(t.iotFactorId,t.factorCode,t.factorName,ia" +
            ".iotDataAggTypeId, ia.dataAggTypeName, ia.dataAggTypeCode,t .unitName, t.unitSymbol) " +
            " from IotFactor t,IotDataAggType ia " +
            " where t.iotFactorId=:iotFactorId" +
            " and t.isDeleted=:isDeleted and ia.iotDataAggTypeId=t.iotDataAggTypeId")
    IotFactorQueryDTO getByIotFactorIdAndDeleted(@Param("iotFactorId") Long iotFactorId, @Param("isDeleted") boolean
            isDeleted);

    @Query(" select iotFactorId from IotFactor where factorCode=:factorCode and isDeleted=false ")
    Long getIotFactorIdByFactorCode(@Param("factorCode") String factorCode);

}
