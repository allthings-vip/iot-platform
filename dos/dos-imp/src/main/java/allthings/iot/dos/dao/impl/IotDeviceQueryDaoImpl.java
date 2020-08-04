package allthings.iot.dos.dao.impl;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dos.dao.IotDeviceQueryDao;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotOpenApiResponseDeviceDTO;
import allthings.iot.dos.dto.query.IotDeviceDetailQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceListBaseQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusCountDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusQueryDTO;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import allthings.iot.dos.model.offline.IotDeviceCount;
import allthings.iot.dos.model.offline.IotDeviceCountTag;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author :  luhao
 * @FileName :  IotDeviceQueryDaoImpl
 * @CreateDate :  2018-5-15
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
@Repository(value = "iotDeviceQueryDao")
public class IotDeviceQueryDaoImpl implements IotDeviceQueryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotDeviceQueryDaoImpl.class);
    private final static Pattern DB2CLASS_PATTERN = Pattern.compile("_");
    private final static String DEVICE_TYPE_DB = "Devicetype";
    private final static String DEVICE_TYPE = "DeviceType";
    @Autowired
    private EntityManager entityManager;

    private static String makeFieldDB2Class(String str) {
        Matcher m = DB2CLASS_PATTERN.matcher(str);
        while (m.find()) {
            System.out.println(m.start());
            str = str.replaceFirst(str.substring(m.start(), m.start() + 2), str.substring(m.start(),
                    m.start() + 2).toUpperCase());

        }
        str = str.replace("_", "");
        str = str.replace(DEVICE_TYPE_DB, DEVICE_TYPE);
        return str;
    }

    /**
     * @param connected
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @param iotTagId
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public QueryResult<IotDeviceQueryDTO> getIotDeviceByIotProjectId(Boolean connected, Long iotDeviceTypeId, Long
            iotProjectId, Long iotTagId, String keywords, Integer pageIndex, Integer pageSize) {
        List<IotDeviceQueryDTO> resultList = queryIotDeviceList(connected, iotDeviceTypeId, iotProjectId, iotTagId,
                keywords, pageIndex, pageSize);
        Long totalCount = queryTotalCountIotDevice(connected, iotDeviceTypeId, iotProjectId, iotTagId, keywords);
        QueryResult<IotDeviceQueryDTO> queryDTOQueryResult = new QueryResult<>(resultList, totalCount);
        return queryDTOQueryResult;
    }

    @Override
    public IotDeviceStatusCountDTO getDeviceCountByOnlineStatus(Boolean connected, Long iotDeviceTypeId,
                                                                Long iotProjectId, Long iotTagId, String keywords) {
        IotDeviceStatusCountDTO iotDeviceStatusCountDTO = new IotDeviceStatusCountDTO();
        Long totalCount = queryTotalCountIotDevice(connected, iotDeviceTypeId, iotProjectId, iotTagId, keywords);
        if (totalCount == null) {
            return iotDeviceStatusCountDTO;
        }
        iotDeviceStatusCountDTO.setDeviceTotal(totalCount);
        if (connected == null) {
            Long onlineCount = queryTotalCountIotDevice(true, iotDeviceTypeId, iotProjectId, iotTagId, keywords);
            iotDeviceStatusCountDTO.setOnlineTotal(onlineCount);
            iotDeviceStatusCountDTO.setOfflineTotal(totalCount - onlineCount);
        } else if (connected == true) {
            iotDeviceStatusCountDTO.setOnlineTotal(totalCount);
            iotDeviceStatusCountDTO.setOfflineTotal(0L);
        } else if (connected == false) {
            iotDeviceStatusCountDTO.setOfflineTotal(totalCount);
            iotDeviceStatusCountDTO.setOnlineTotal(0L);
        }

        return iotDeviceStatusCountDTO;
    }

    @Override
    public QueryResult<IotDeviceQueryDTO> getIotOpenDeviceList(Boolean connected, Long iotProjectId, String keywords,
                                                               Integer pageIndex, Integer pageSize, Boolean enabled) {
        List<IotDeviceQueryDTO> resultList = queryIotOpenDeviceList(connected, iotProjectId, keywords, pageIndex,
                pageSize, enabled);
        List<IotDeviceQueryDTO> all = queryIotOpenDeviceList(connected, iotProjectId, keywords, 1, Integer.MAX_VALUE,
                enabled);
        int totalCount = 0;
        if (!CollectionUtils.isEmpty(all)) {
            totalCount = all.size();
        }
        QueryResult<IotDeviceQueryDTO> queryDTOQueryResult = new QueryResult<>(resultList, totalCount);
        return queryDTOQueryResult;
    }

    @Override
    public QueryResult<IotDeviceQueryDTO> getIotOpenDeviceList(Long iotProjectId, List<String> deviceCodes,
                                                               Boolean enabled) {

        StringBuffer sql = new StringBuffer(
                " SELECT t.biz_code bizCode, it.is_connected connected, t.device_code deviceCode, " +
                        " t.device_name deviceName, t.register_date inputDate, it.latest_connect_datetime " +
                        "latestConnectDatetime, " +
                        " ip.project_name projectName, t.is_enabled enabled, t.agency_name agencyName");
        sql.append(
                " FROM iot_dos_device t LEFT JOIN iot_dos_device_status it ON (t.iot_dos_device_id = it" +
                        ".iot_dos_device_id), " +
                        " iot_dos_project ip");

        sql.append(" where ip.iot_dos_project_id = t.iot_dos_project_id and t.is_deleted = false and ip.is_deleted = " +
                "false");
        sql.append(" and t.register_status = true and t.iot_dos_project_id =:iotProjectId ");
        sql.append(" and t.iot_dos_project_id = ip.iot_dos_project_id ");
        if (enabled != null && enabled) {
            sql.append(" and t.is_enabled = true ");
        }
        if (!CollectionUtils.isEmpty(deviceCodes)) {
            sql.append("and t.device_code in (:deviceCodes) ");
        }

        org.hibernate.query.Query<IotDeviceQueryDTO> query =
                entityManager.createNativeQuery(sql.toString()).unwrap(NativeQuery.class)
                        .addScalar("bizCode", StandardBasicTypes.STRING)
                        .addScalar("connected", StandardBasicTypes.BOOLEAN)
                        .addScalar("deviceCode", StandardBasicTypes.STRING)
                        .addScalar("deviceName", StandardBasicTypes.STRING)
                        .addScalar("inputDate", StandardBasicTypes.TIMESTAMP)
                        .addScalar("latestConnectDatetime", StandardBasicTypes.TIMESTAMP)
                        .addScalar("projectName", StandardBasicTypes.STRING)
                        .addScalar("enabled", StandardBasicTypes.BOOLEAN)
                        .addScalar("agencyName", StandardBasicTypes.STRING)
                        .setResultTransformer(Transformers.aliasToBean(IotDeviceQueryDTO.class));


        if (iotProjectId != null) {
            query.setParameter("iotProjectId", iotProjectId);
        }
        if (!CollectionUtils.isEmpty(deviceCodes)) {
            query.setParameter("deviceCodes", deviceCodes);
        }

        List<IotDeviceQueryDTO> queryList = query.list();

        return new QueryResult<>(queryList, queryList.size());
    }

    /**
     * @param connected
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @param iotTagId
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    private List<IotDeviceQueryDTO> queryIotDeviceList(Boolean connected, Long iotDeviceTypeId, Long
            iotProjectId, Long iotTagId, String keywords, Integer pageIndex, Integer pageSize) {
        StringBuffer sql = new StringBuffer(" SELECT t.iot_dos_project_id iotProjectId, ip.project_name projectName," +
                "t.device_code deviceCode,t.device_name deviceName, idt.devicetype_name deviceTypeName, " +
                "t.register_date inputDate, t.iot_dos_device_id iotDeviceId, it.latest_connect_datetime " +
                "latestConnectDatetime, " +
                "it.latest_disconnect_datetime latestDisconnectDatetime, it.is_connected connected, t.biz_code " +
                "bizCode, t.is_enabled enabled, t.longitude longitude, " +
                "t.latitude latitude, t.device_id deviceId, t.agency_name agencyName,t.iot_dos_devicetype_id " +
                " iotDeviceTypeId");
        sql.append(getSql(connected, iotDeviceTypeId, iotProjectId, iotTagId, null, null, keywords, false));
        sql.append(" and t.register_status = true ");
        sql.append(" order by t.gmt_modified desc ");

        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class)
                .addScalar("iotDeviceId", StandardBasicTypes.LONG)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("deviceName", StandardBasicTypes.STRING)
                .addScalar("deviceTypeName", StandardBasicTypes.STRING)
                .addScalar("inputDate", StandardBasicTypes.TIMESTAMP)
                .addScalar("latestConnectDatetime", StandardBasicTypes.TIMESTAMP)
                .addScalar("latestDisconnectDatetime", StandardBasicTypes.TIMESTAMP)
                .addScalar("connected", StandardBasicTypes.BOOLEAN)
                .addScalar("bizCode", StandardBasicTypes.STRING)
                .addScalar("enabled", StandardBasicTypes.BOOLEAN)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("iotProjectId", StandardBasicTypes.LONG)
                .addScalar("projectName", StandardBasicTypes.STRING)
                .addScalar("deviceId", StandardBasicTypes.STRING)
                .addScalar("agencyName", StandardBasicTypes.STRING)
                .addScalar("iotDeviceTypeId", StandardBasicTypes.LONG)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceQueryDTO.class));

        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);

        if (iotTagId != null) {
            query.setParameter("iotTagId", iotTagId);
        }

        if (connected != null) {
            query.setParameter("connected", connected);
        }

        if (iotDeviceTypeId != null) {
            query.setParameter("iotDeviceTypeId", iotDeviceTypeId);
        }

        if (iotProjectId != null) {
            query.setParameter("iotProjectId", iotProjectId);
        }

        if (StringUtils.isNotEmpty(keywords)) {
            query.setParameter("keywords", "%" + keywords + "%");
        }

        return query.list();
    }

    /**
     * @param connected
     * @param iotProjectId
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    private List<IotDeviceQueryDTO> queryIotOpenDeviceList(Boolean connected, Long iotProjectId, String keywords,
                                                           Integer pageIndex, Integer pageSize, Boolean enabled) {
        StringBuffer sql = new StringBuffer(
                " SELECT t.biz_code bizCode, it.is_connected connected, t.device_code deviceCode, " +
                        " t.device_name deviceName, t.register_date inputDate, it.latest_connect_datetime " +
                        "latestConnectDatetime, " +
                        " ip.project_name projectName, t.is_enabled enabled, t.agency_name agencyName, idp" +
                        ".protocol_code protocolCode");
        sql.append(
                " FROM iot_dos_device t LEFT JOIN iot_dos_device_status it ON (t.iot_dos_device_id = it" +
                        ".iot_dos_device_id) " +
                        " LEFT JOIN iot_dos_devicetype idt ON(idt.iot_dos_devicetype_id = t.iot_dos_devicetype_id) " +
                        " LEFT JOIN iot_dos_protocol idp ON(idt.iot_dos_protocol_id = idp.iot_dos_protocol_id) ");
        if (!StringUtils.isEmpty(keywords)) {
            sql.append(" LEFT JOIN iot_dos_device_tag dt ON (t.iot_dos_device_id =dt.iot_dos_device_id ) " +
                    " LEFT JOIN iot_dos_tag tag ON (tag.iot_dos_tag_id = dt.iot_dos_tag_id ) ");
        }
        sql.append(", iot_dos_project ip");

        sql.append(" where ip.iot_dos_project_id = t.iot_dos_project_id and t.is_deleted = false and ip.is_deleted = " +
                "false");
        sql.append(" and t.register_status = true and t.iot_dos_project_id =:iotProjectId ");
        sql.append(" and t.iot_dos_project_id = ip.iot_dos_project_id ");
        if (enabled != null && enabled) {
            sql.append(" and t.is_enabled = true ");
        }

//            sql.append(" and ip.iot_dos_project_id = idt.iot_dos_project_id ");
        if (!StringUtils.isEmpty(keywords)) {
            sql.append(" and ((tag.tag_name like :keywords and tag.is_deleted = false) " +
                    " or t.device_name like :keywords ");
            sql.append(" or (idt.devicetype_name like :keywords and idt.is_deleted = false)) ");
        }

        if (connected != null && connected == true) {
            sql.append(" and it.is_connected=:connected ");
        } else if (connected != null && connected == false) {
            sql.append(" and (it.is_connected=:connected or it.is_connected is null) ");
        }

        sql.append("group by t.iot_dos_device_id order by t.gmt_modified desc ");

        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class)
                .addScalar("bizCode", StandardBasicTypes.STRING)
                .addScalar("connected", StandardBasicTypes.BOOLEAN)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("deviceName", StandardBasicTypes.STRING)
                .addScalar("inputDate", StandardBasicTypes.TIMESTAMP)
                .addScalar("latestConnectDatetime", StandardBasicTypes.TIMESTAMP)
                .addScalar("projectName", StandardBasicTypes.STRING)
                .addScalar("enabled", StandardBasicTypes.BOOLEAN)
                .addScalar("agencyName", StandardBasicTypes.STRING)
                .addScalar("protocolCode", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceQueryDTO.class));

        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);

        if (connected != null) {
            query.setParameter("connected", connected);
        }

        if (iotProjectId != null) {
            query.setParameter("iotProjectId", iotProjectId);
        }

        if (StringUtils.isNotEmpty(keywords)) {
            query.setParameter("keywords", "%" + keywords + "%");
        }

        return query.list();
    }

    /**
     * @param onlineStatus
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @param iotTagId
     * @param keywords
     * @return
     */
    private Long queryTotalCountIotDevice(Boolean onlineStatus, Long iotDeviceTypeId, Long
            iotProjectId, Long iotTagId, String keywords) {
        StringBuffer sql = new StringBuffer(" SELECT count(1) count ");
        sql.append(getSql(onlineStatus, iotDeviceTypeId, iotProjectId, iotTagId, null, null, keywords, false));
        sql.append(" and t.register_status = true ");
        org.hibernate.query.Query query = entityManager.createNativeQuery(sql.toString()).unwrap(NativeQueryImpl.class);

        if (iotTagId != null) {
            query.setParameter("iotTagId", iotTagId);
        }

        if (onlineStatus != null) {
            query.setParameter("connected", onlineStatus);
        }

        if (iotDeviceTypeId != null) {
            query.setParameter("iotDeviceTypeId", iotDeviceTypeId);
        }

        if (iotProjectId != null) {
            query.setParameter("iotProjectId", iotProjectId);
        }

        if (StringUtils.isNotEmpty(keywords)) {
            query.setParameter("keywords", "%" + keywords + "%");
        }

        BigInteger count = (BigInteger) query.uniqueResult();

        return count.longValue();
    }

    /**
     * 拼装sql
     *
     * @param connected
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @param iotTagId
     * @param keywords
     * @return
     */
    private String getSql(Boolean connected, Long iotDeviceTypeId, Long iotProjectId, Long iotTagId, Long
            iotDeviceId, String deviceCode, String keywords, boolean isDetail) {
        StringBuffer sql = new StringBuffer(
                " FROM iot_dos_device t LEFT JOIN iot_dos_device_status it ON (t.iot_dos_device_id = it" +
                        ".iot_dos_device_id) " +
                        "LEFT JOIN iot_dos_devicetype idt ON (idt.iot_dos_devicetype_id = t.iot_dos_devicetype_id) " +
                        "LEFT JOIN iot_dos_project ip ON (ip.iot_dos_project_id=t.iot_dos_project_id ) ");

//        if (iotTagId != null) {
//            sql.append(" where exists ( select itt.iot_device_tag_id iotTagId from iot_dos_device_tag itt where " +
//                    "itt.iot_dos_device_id=t.iot_dos_device_id and itt.iot_dos_tag_id=:iotTagId )  ");
//        } else {
//            sql.append(" where 1 = 1 ");
//        }
        if (iotTagId != null) {
            sql.append(", iot_dos_device_tag itt where " +
                    "itt.iot_dos_device_id = t.iot_dos_device_id and itt.iot_dos_tag_id=:iotTagId " +
                    "and itt.is_deleted = false ");
        } else {
            sql.append(" where 1 = 1");
        }


        sql.append(" and t.is_deleted=false ");

        if (connected != null && connected == true) {
            sql.append(" and it.is_connected=:connected ");
        } else if (connected != null && connected == false) {
            sql.append(" and (it.is_connected=:connected or it.is_connected is null) ");
        }


        if (iotDeviceTypeId != null) {
            sql.append(" and t.iot_dos_devicetype_id=:iotDeviceTypeId ");
        }

        if (iotProjectId != null) {
            sql.append(" and t.iot_dos_project_id=:iotProjectId ");
        }

        if (StringUtils.isNotEmpty(keywords)) {
            sql.append(" and (t.device_code like :keywords or t.biz_code like :keywords or t.device_name like " +
                    ":keywords or t.agency_name like :keywords) ");
        }

        if (iotDeviceId != null) {
            sql.append(" and t.iot_dos_device_id=:iotDeviceId ");
        }

        if (StringUtils.isNotEmpty(deviceCode)) {
            sql.append(" and t.device_code=:deviceCode ");
        }

        return sql.toString();
    }

    /**
     * @param iotDeviceId
     * @return
     */
    @Override
    public IotDeviceDetailQueryDTO getIotDeviceByIotDeviceId(Long iotDeviceId) {
        StringBuffer sql = new StringBuffer(" SELECT t.iot_dos_project_id iotProjectId, ip.project_name projectName, " +
                "t.device_code deviceCode,t.device_name deviceName, idt.iot_dos_devicetype_id iotDeviceTypeId, " +
                "idt.devicetype_name deviceTypeName, t.register_date inputDate, t.iot_dos_device_id iotDeviceId, " +
                "it.latest_connect_datetime latestConnectDatetime, it.is_connected connected, " +
                "t.biz_code bizCode, t.is_enabled enabled, t.longitude longitude, t.latitude latitude, t.mac mac, " +
                "t.firmware_model firmwareModel, t.firmware_version firmwareVersion, t.create_operator_id " +
                "createOperatorId, " +
                "t.description description, t.extend_properties extendPropertiesAlias, t.agency_name agencyName");
        sql.append(getSql(null, null, null, null, iotDeviceId, null, null, true));

        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class)
                .addScalar("iotDeviceId", StandardBasicTypes.LONG)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("deviceName", StandardBasicTypes.STRING)
                .addScalar("deviceTypeName", StandardBasicTypes.STRING)
                .addScalar("inputDate", StandardBasicTypes.TIMESTAMP)
                .addScalar("latestConnectDatetime", StandardBasicTypes.TIMESTAMP)
                .addScalar("connected", StandardBasicTypes.BOOLEAN)
                .addScalar("bizCode", StandardBasicTypes.STRING)
                .addScalar("enabled", StandardBasicTypes.BOOLEAN)
                .addScalar("iotProjectId", StandardBasicTypes.LONG)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("mac", StandardBasicTypes.STRING)
                .addScalar("firmwareModel", StandardBasicTypes.STRING)
                .addScalar("firmwareVersion", StandardBasicTypes.STRING)
                .addScalar("iotDeviceTypeId", StandardBasicTypes.LONG)
                .addScalar("projectName", StandardBasicTypes.STRING)
                .addScalar("description", StandardBasicTypes.STRING)
                .addScalar("createOperatorId", StandardBasicTypes.LONG)
                .addScalar("extendPropertiesAlias", StandardBasicTypes.STRING)
                .addScalar("agencyName", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceDetailQueryDTO.class));


        if (iotDeviceId != null) {
            query.setParameter("iotDeviceId", iotDeviceId);
        }

        return (IotDeviceDetailQueryDTO) query.uniqueResult();
    }

    /**
     * 统计设备数量
     *
     * @return
     */
    @Override
    public List<IotDeviceCount> getIotDeviceCount(Long dayBeforeZeroHour, Long zeroHour, List<Long> iotProjectIds) {
        StringBuffer sql =
                new StringBuffer(" select count(1) dayQuantity ,t.iotProjectId,t.iotDeviceTypeId,:datetime inputDate," +
                        "(select count(1) from IotDevice tt where tt.iotProjectId " +
                        "= t.iotProjectId and tt.iotDeviceTypeId = t.iotDeviceTypeId and tt.inputDate<:endDatetime) " +
                        "dayBeforeQuantity " +
                        "from IotDevice t " +
                        "where" +
                        " t.inputDate>=:startDatetime and t.inputDate<:endDatetime and t.iotProjectId in " +
                        "(:iotProjectIds) group by" +
                        " t.iotProjectId,t" +
                        ".iotDeviceTypeId");

        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).addScalar("dayQuantity",
                StandardBasicTypes.LONG).addScalar("iotProjectId", StandardBasicTypes.LONG).addScalar
                ("iotDeviceTypeId", StandardBasicTypes.LONG).addScalar("inputDate", StandardBasicTypes.TIMESTAMP)
                .addScalar("dayBeforeQuantity", StandardBasicTypes.LONG)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceCount.class));
        query.setParameter("datetime", new Date(dayBeforeZeroHour));
        query.setParameter("startDatetime", new Date(dayBeforeZeroHour));
        query.setParameter("endDatetime", new Date(zeroHour));
        query.setParameterList("iotProjectIds", iotProjectIds);

        return query.list();
    }

    /**
     * @param dayBeforeZeroHour
     * @param zeroHour
     * @param iotTags
     * @return
     */
    @Override
    public List<IotDeviceCountTag> getIotDeviceCountTag(Long dayBeforeZeroHour, Long zeroHour, List<Long> iotTags) {
        StringBuffer sql = new StringBuffer("SELECT count(t.iotDeviceId) dayQuantity, tyt.iotTagId, :datetime " +
                "inputDate " +
                "FROM IotDevice t, IotDeviceTypeTag tyt " +
                "WHERE t.iotDeviceTypeId = tyt.iotDeviceTypeId AND tyt.isDeleted = FALSE " +
                "AND t.isDeleted = FALSE and t.inputDate >= :startDatetime and t.inputDate < :endDatetime and tyt" +
                ".iotTagId in (:iotTags) group by tyt.iotTagId");

        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class).addScalar("dayQuantity",
                StandardBasicTypes.LONG).addScalar("iotTagId", StandardBasicTypes.LONG).addScalar("inputDate",
                StandardBasicTypes.TIMESTAMP)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceCountTag.class));
        query.setParameter("datetime", new Date(dayBeforeZeroHour));
        query.setParameter("startDatetime", new Date(dayBeforeZeroHour));
        query.setParameter("endDatetime", new Date(zeroHour));
        query.setParameterList("iotTags", iotTags);

        return query.list();
    }

    @Override
    public List<IotDeviceStatusQueryDTO> getIotDeviceStatus(List<String> deviceCodes, Long iotProjectId) {
        String sql = "SELECT d.device_code deviceCode, d.iot_dos_device_id iotDeviceId, ds.is_connected connected, " +
                " ds.latest_disconnect_datetime latestDisConnectDatetime, p.project_name projectName" +
                " FROM iot_dos_device d LEFT JOIN iot_dos_device_status ds ON (d.iot_dos_device_id = ds" +
                ".iot_dos_device_id), " +
                " iot_dos_project p " +
                " WHERE d.device_code IN (:deviceCodes) AND d.is_deleted = false " +
                " AND p.is_deleted=false AND p.iot_dos_project_id=d.iot_dos_project_id " +
                " AND d.iot_dos_project_id = :iotProjectId ";
        Query query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("iotDeviceId", StandardBasicTypes.LONG)
                .addScalar("connected", StandardBasicTypes.BOOLEAN)
                .addScalar("latestDisConnectDatetime", StandardBasicTypes.TIMESTAMP)
                .addScalar("projectName", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceStatusQueryDTO.class));
        query.setParameterList("deviceCodes", deviceCodes);
        query.setParameter("iotProjectId", iotProjectId);
        return query.list();
    }

    @Override
    public List<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(String deviceCode) {
        String sql = "SELECT " +
                " iod.iot_dos_device_id iotDeviceId, " +
                " iod.device_code deviceCode, " +
                " iop.protocol_code protocolCode " +
                " FROM " +
                " iot_dos_device iod, " +
                " iot_dos_devicetype idt, " +
                " iot_dos_protocol iop  " +
                "WHERE " +
                " iod.iot_dos_devicetype_id = idt.iot_dos_devicetype_id  " +
                " AND idt.iot_dos_protocol_id = iop.iot_dos_protocol_id  " +
                " AND iod.device_code=:deviceCode" +
                " AND iod.is_deleted = false limit 1";
        Query query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class)
                .addScalar("iotDeviceId", StandardBasicTypes.LONG)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("protocolCode", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(IotIovProtocolCodeQueryDto.class));
        query.setParameter("deviceCode", deviceCode);
        return query.list();
    }

    @Override
    public List<IotIovProtocolCodeQueryDto> getProtocolByDeviceCodes(List<String> deviceCodes) {
        String sql = "SELECT " +
                " iod.iot_dos_device_id iotDeviceId, " +
                " iod.device_code deviceCode, " +
                " iop.protocol_code protocolCode " +
                " FROM " +
                " iot_dos_device iod, " +
                " iot_dos_devicetype idt, " +
                " iot_dos_protocol iop  " +
                "WHERE " +
                " iod.iot_dos_devicetype_id = idt.iot_dos_devicetype_id  " +
                " AND idt.iot_dos_protocol_id = iop.iot_dos_protocol_id  " +
                " AND iod.device_code in (:deviceCodes)" +
                " AND iod.is_deleted = false ";
        org.hibernate.query.Query query = entityManager.createNativeQuery(sql).unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("iotDeviceId", StandardBasicTypes.LONG)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("protocolCode", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(IotIovProtocolCodeQueryDto.class));
        query.setParameter("deviceCodes", deviceCodes);
        return query.list();
    }

    @Override
    public List<IotIovProtocolCodeQueryDto> getProtocolByDeviceIds(List<String> deviceIds) {
        String sql = "SELECT " +
                " iod.iot_dos_device_id iotDeviceId, " +
                " iod.device_code deviceCode, " +
                " iop.protocol_code protocolCode, " +
                " iod.device_id deviceId" +
                " FROM " +
                " iot_dos_device iod, " +
                " iot_dos_devicetype idt, " +
                " iot_dos_protocol iop  " +
                "WHERE " +
                " iod.iot_dos_devicetype_id = idt.iot_dos_devicetype_id  " +
                " AND idt.iot_dos_protocol_id = iop.iot_dos_protocol_id  " +
                " AND iod.device_id in (:deviceIds)" +
                " AND iod.is_deleted = false ";
        org.hibernate.query.Query query = entityManager.createNativeQuery(sql).unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("iotDeviceId", StandardBasicTypes.LONG)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("protocolCode", StandardBasicTypes.STRING)
                .addScalar("deviceId", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(IotIovProtocolCodeQueryDto.class));
        query.setParameter("deviceIds", deviceIds);
        return query.list();
    }

    @Override
    public List<IotOpenApiResponseDeviceDTO> getProtocolByIotDeviceId(List<Long> iotDeviceIds) {
        String sql = "SELECT " +
                " iod.device_code deviceCode, " +
                " iop.protocol_code protocolCode " +
                " FROM " +
                " iot_dos_device iod, " +
                " iot_dos_devicetype idt, " +
                " iot_dos_protocol iop  " +
                "WHERE " +
                " iod.iot_dos_devicetype_id = idt.iot_dos_devicetype_id  " +
                " AND idt.iot_dos_protocol_id = iop.iot_dos_protocol_id  " +
                " AND iod.iot_dos_device_id in (:iotDeviceIds)" +
                " AND iod.is_deleted = false";
        Query query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("protocolCode", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(IotOpenApiResponseDeviceDTO.class));
        query.setParameter("iotDeviceIds", iotDeviceIds);
        return query.list();
    }

    @Override
    public List<IotDeviceDTO> getIotDeviceByRegister(Long iotProtocolId, String deviceCode, Boolean registerStatus,
                                                     Integer pageIndex, Integer pageSize) {
        StringBuffer sql = new StringBuffer(
                "select d.iot_dos_device_id iotDeviceId, dt.devicetype_name deviceTypeName, " +
                        " d.device_code deviceCode, d.biz_code bizCode, p.protocol_name protocolName, pro" +
                        ".project_name projectName, " +
                        " d.register_date registerDate, d.register_status registerStatus " +
                        " FROM iot_dos_device d " +
                        " LEFT JOIN iot_dos_project pro ON d.iot_dos_project_id = pro.iot_dos_project_id " +
                        " LEFT JOIN iot_dos_devicetype dt ON d.iot_dos_devicetype_id = dt.iot_dos_devicetype_id " +
                        " LEFT JOIN iot_dos_protocol p on dt.iot_dos_protocol_id = p.iot_dos_protocol_id " +
                        " where d.is_deleted=false ");
        if (registerStatus != null) {
            sql.append(" and d.register_status =:registerStatus ");
        }

//        if (registerStatus != null && registerStatus == true) {
//            sql.append(" and d.register_status=:registerStatus ");
//        } else if(registerStatus != null && registerStatus == false){
//            sql.append(" and (d.register_status=:registerStatus or d.register_status is null) ");
//        }


        if (StringUtils.isNotEmpty(deviceCode)) {
            sql.append(" and d.device_code like '%" + deviceCode + "%' ");
        }
        if (iotProtocolId != null) {
            sql.append(" and dt.iot_dos_protocol_id =:iotProtocolId ");
        }
        sql.append(" order by d.iot_dos_device_id desc");

        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class)
                .addScalar("iotDeviceId", StandardBasicTypes.LONG)
                .addScalar("deviceTypeName", StandardBasicTypes.STRING)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("bizCode", StandardBasicTypes.STRING)
                .addScalar("protocolName", StandardBasicTypes.STRING)
                .addScalar("projectName", StandardBasicTypes.STRING)
                .addScalar("registerDate", StandardBasicTypes.TIMESTAMP)
                .addScalar("registerStatus", StandardBasicTypes.BOOLEAN)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceDTO.class));
        if (registerStatus != null) {
            query.setParameter("registerStatus", registerStatus);
        }
        if (iotProtocolId != null) {
            query.setParameter("iotProtocolId", iotProtocolId);
        }

        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public IotIovProtocolCodeQueryDto getProtocolByBizCode(String bizCode) {
        String sql = "SELECT " +
                " iod.iot_dos_device_id iotDeviceId, " +
                " iod.device_code deviceCode, " +
                " iop.protocol_code protocolCode " +
                " FROM " +
                " iot_dos_device iod, " +
                " iot_dos_devicetype idt, " +
                " iot_dos_protocol iop  " +
                "WHERE " +
                " iod.iot_dos_devicetype_id = idt.iot_dos_devicetype_id  " +
                " AND idt.iot_dos_protocol_id = iop.iot_dos_protocol_id  " +
                " AND iod.biz_code=:bizCode";
        Query query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class)
                .addScalar("iotDeviceId", StandardBasicTypes.LONG)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("protocolCode", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(IotIovProtocolCodeQueryDto.class));
        query.setParameter("bizCode", bizCode);
        return (IotIovProtocolCodeQueryDto) query.uniqueResult();
    }

    @Override
    public List<IotDeviceQueryDTO> getDeviceListCustom(IotDeviceListBaseQueryDTO iotDeviceListBaseQueryDTO) {
        List<String> fields = iotDeviceListBaseQueryDTO.getFields();
        Boolean connected = iotDeviceListBaseQueryDTO.getStatus();
        Boolean enabled = iotDeviceListBaseQueryDTO.getEnabled();
        Long iotProjectId = iotDeviceListBaseQueryDTO.getIotProjectId();
        StringBuffer sql = new StringBuffer(" SELECT t.iot_dos_project_id iotProjectId, ip.project_name projectName, " +
                "t.device_code deviceCode, t.device_name deviceName, idt.iot_dos_devicetype_id iotDeviceTypeId, " +
                "idt.devicetype_name deviceTypeName, t.register_date inputDate, t.iot_dos_device_id iotDeviceId, " +
                "it.latest_connect_datetime latestConnectDatetime, it.is_connected connected, t.biz_code bizCode, t" +
                ".is_enabled enabled, " +
                "t.longitude longitude, t.latitude latitude, t.mac mac, t.firmware_model firmwareModel, " +
                "t .firmware_version firmwareVersion, it.latest_disconnect_datetime latestDisconnectDatetime, " +
                "idt.devicetype_code deviceTypeCode, t.register_status registerStatus ");

        sql.append(
                "FROM iot_dos_device t LEFT JOIN iot_dos_device_status it ON (t.iot_dos_device_id = it" +
                        ".iot_dos_device_id) " +
                        "LEFT JOIN iot_dos_devicetype idt ON (idt.iot_dos_devicetype_id = t.iot_dos_devicetype_id) " +
                        "LEFT JOIN iot_dos_project ip ON (ip.iot_dos_project_id=t.iot_dos_project_id ) ");

        sql.append("WHERE 1=1 ");
        if (iotProjectId != null) {
            sql.append(" and t.iot_dos_project_id =:iotProjectId ");
        }

        if (connected != null && connected) {
            sql.append(" AND it.is_connected=true ");
        } else if (connected != null) {
            sql.append(" AND (it.is_connected=false OR it.is_connected is null) ");
        }

        if (enabled != null && enabled) {
            sql.append(" AND t.is_enabled = true");
        } else if (enabled != null) {
            sql.append(" AND t.is_enabled = false");
        }

        if (!CollectionUtils.isEmpty(fields) && !StringUtils.isEmpty(iotDeviceListBaseQueryDTO.getKeywords())) {
            sql.append("AND ( ");
            for (int i = 0; i < fields.size() - 1; i++) {
                if (iotDeviceListBaseQueryDTO.getVague() != null && iotDeviceListBaseQueryDTO.getVague()) {
                    sql.append(fields.get(i) + " like '%" + iotDeviceListBaseQueryDTO.getKeywords() + "%' OR ");
                } else {
                    sql.append(fields.get(i) + " = '" + iotDeviceListBaseQueryDTO.getKeywords() + "' OR ");
                }
            }
            if (iotDeviceListBaseQueryDTO.getVague() != null && iotDeviceListBaseQueryDTO.getVague()) {
                sql.append(
                        fields.get(fields.size() - 1) + " like '%" + iotDeviceListBaseQueryDTO.getKeywords() + "%') ");
            } else {
                sql.append(fields.get(fields.size() - 1) + " = '" + iotDeviceListBaseQueryDTO.getKeywords() + "') ");
            }
        }


        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class)
                .addScalar("iotDeviceId", StandardBasicTypes.LONG)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("deviceName", StandardBasicTypes.STRING)
                .addScalar("deviceTypeName", StandardBasicTypes.STRING)
                .addScalar("deviceTypeCode", StandardBasicTypes.STRING)
                .addScalar("inputDate", StandardBasicTypes.TIMESTAMP)
                .addScalar("latestConnectDatetime", StandardBasicTypes.TIMESTAMP)
                .addScalar("latestDisconnectDatetime", StandardBasicTypes.TIMESTAMP)
                .addScalar("connected", StandardBasicTypes.BOOLEAN)
                .addScalar("bizCode", StandardBasicTypes.STRING)
                .addScalar("enabled", StandardBasicTypes.BOOLEAN)
                .addScalar("iotProjectId", StandardBasicTypes.LONG)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("mac", StandardBasicTypes.STRING)
                .addScalar("firmwareModel", StandardBasicTypes.STRING)
                .addScalar("firmwareVersion", StandardBasicTypes.STRING)
                .addScalar("iotDeviceTypeId", StandardBasicTypes.LONG)
                .addScalar("projectName", StandardBasicTypes.STRING)
                .addScalar("registerStatus", StandardBasicTypes.BOOLEAN)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceDetailQueryDTO.class));

        if (iotProjectId != null) {
            query.setParameter("iotProjectId", iotProjectId);
        }

        if (iotDeviceListBaseQueryDTO.getPageSize() != null && iotDeviceListBaseQueryDTO.getPageIndex() != null) {
            query.setFirstResult(
                    (iotDeviceListBaseQueryDTO.getPageIndex() - 1) * iotDeviceListBaseQueryDTO.getPageSize());
            query.setMaxResults(iotDeviceListBaseQueryDTO.getPageSize());
        }
//        LOGGER.info("自定义查询语句：" + JSON.toJSONString(query));

        return query.list();
    }

    /**
     * 设备详情
     *
     * @param deviceCode
     * @return
     */
    @Override
    public IotDeviceDetailQueryDTO getIotDeviceByDeviceCode(String deviceCode, Long iotProjectId) {
        StringBuffer sql = new StringBuffer(" SELECT t.iot_dos_project_id iotProjectId, ip.project_name projectName, " +
                "t.device_code deviceCode, t.device_name deviceName, idt.iot_dos_devicetype_id iotDeviceTypeId, " +
                "idt.devicetype_name deviceTypeName, t.register_date inputDate, t.iot_dos_device_id iotDeviceId, " +
                "it.latest_connect_datetime latestConnectDatetime, it.is_connected connected, t.biz_code bizCode, t" +
                ".is_enabled enabled, " +
                "t.longitude longitude, t.latitude latitude, t.mac mac, t.firmware_model firmwareModel, " +
                "t .firmware_version firmwareVersion, t.register_status registerStatus");
        sql.append(getSql(null, null, iotProjectId, null, null, deviceCode, null, true));

        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class)
                .addScalar("iotDeviceId", StandardBasicTypes.LONG)
                .addScalar("deviceCode", StandardBasicTypes.STRING)
                .addScalar("deviceName", StandardBasicTypes.STRING)
                .addScalar("deviceTypeName", StandardBasicTypes.STRING)
                .addScalar("inputDate", StandardBasicTypes.TIMESTAMP)
                .addScalar("latestConnectDatetime", StandardBasicTypes.TIMESTAMP)
                .addScalar("connected", StandardBasicTypes.BOOLEAN)
                .addScalar("bizCode", StandardBasicTypes.STRING)
                .addScalar("enabled", StandardBasicTypes.BOOLEAN)
                .addScalar("iotProjectId", StandardBasicTypes.LONG)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("mac", StandardBasicTypes.STRING)
                .addScalar("firmwareModel", StandardBasicTypes.STRING)
                .addScalar("firmwareVersion", StandardBasicTypes.STRING)
                .addScalar("iotDeviceTypeId", StandardBasicTypes.LONG)
                .addScalar("projectName", StandardBasicTypes.STRING)
                .addScalar("registerStatus", StandardBasicTypes.BOOLEAN)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceDetailQueryDTO.class));


        if (StringUtils.isNotEmpty(deviceCode)) {
            query.setParameter("deviceCode", deviceCode);
        }
        if (iotProjectId != null) {
            query.setParameter("iotProjectId", iotProjectId);
        }

        return (IotDeviceDetailQueryDTO) query.uniqueResult();
    }

    /**
     * 获取在线设备数量
     *
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    @Override
    public Long getOnlineDeviceCount(Long startDatetime, Long endDatetime) {
        return null;
    }
}
