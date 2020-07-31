package allthings.iot.dos.dao.impl;

import allthings.iot.dos.dao.IotLoggerQueryDao;
import allthings.iot.dos.dto.query.IotDeviceLoggerListDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerQueryListDto;
import allthings.iot.dos.dto.query.IotSystemLoggerQueryListDto;
import allthings.iot.dos.model.IotDevice;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author tyf
 * @date 2019/03/12 8:46
 */
@Repository("iotDeviceLoggerQueryDao")
public class IotLoggerQueryDaoImpl implements IotLoggerQueryDao {

    @Autowired
    private EntityManager entityManager;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<IotDeviceLoggerListDto> queryDeviceLoggerList(IotDeviceLoggerQueryListDto deviceLoggerQueryListDto) {

        String starDate = sdf.format(new Date(deviceLoggerQueryListDto.getStartTime()));
        String endDate = sdf.format(new Date(deviceLoggerQueryListDto.getEndTime()));
        String loggerTypeCode = deviceLoggerQueryListDto.getLoggerTypeCode();
        Long iotDeviceId = deviceLoggerQueryListDto.getIotDeviceId();
        String associationType = IotDevice.class.getSimpleName();
        Integer pageIndex = deviceLoggerQueryListDto.getPageIndex();
        Integer pageSize = deviceLoggerQueryListDto.getPageSize();

        String sql =
                "select idl.logger_time loggerTime,idu.username userName,idlt.logger_type_name loggerTypeName,idl" +
                        ".logger_content loggerContent, " +
                        " idl.iot_dos_logger_id iotDeviceLoggerId, " +
                        " idlt.iot_dos_logger_type_id iotLoggerTypeId,idu.iot_dos_user_id iotUserId from " +
                        "iot_dos_logger idl left join iot_dos_user idu " +
                        " on idl.iot_dos_user_id = idu.iot_dos_user_id, iot_dos_logger_relation idlr, " +
                        "iot_dos_logger_type idlt " +
                        " where idl.iot_dos_logger_id=idlr.iot_dos_logger_id and idl.iot_dos_logger_type_id=idlt" +
                        ".iot_dos_logger_type_id " +
                        " and idl.logger_time >=:startDate and idl.logger_time <=:endDate and idlt" +
                        ".logger_type_code=:loggerTypeCode " +
                        " and idl.is_deleted = false and idlr.is_deleted = false and idlt.is_deleted = false and idlr" +
                        ".association_id=:iotDeviceId " +
                        " and idlr.association_type=:associationType order by idl.logger_time desc ";

        Query query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class)
                .addScalar("loggerTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("userName", StandardBasicTypes.STRING)
                .addScalar("loggerTypeName", StandardBasicTypes.STRING)
                .addScalar("loggerContent", StandardBasicTypes.STRING)
                .addScalar("iotDeviceLoggerId", StandardBasicTypes.LONG)
                .addScalar("iotLoggerTypeId", StandardBasicTypes.LONG)
                .addScalar("iotUserId", StandardBasicTypes.LONG)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceLoggerListDto.class));
        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);

        query.setParameter("startDate", starDate);
        query.setParameter("endDate", endDate);
        query.setParameter("loggerTypeCode", loggerTypeCode);
        query.setParameter("iotDeviceId", iotDeviceId);
        query.setParameter("associationType", associationType);

        return query.list();
    }

    @Override
    public List<IotDeviceLoggerListDto> querySystemLoggerList(IotSystemLoggerQueryListDto iotSystemLoggerQueryListDto) {
        String starDate = sdf.format(new Date(iotSystemLoggerQueryListDto.getStartTime()));
        String endDate = sdf.format(new Date(iotSystemLoggerQueryListDto.getEndTime()));
        String loggerTypeCode = iotSystemLoggerQueryListDto.getLoggerTypeCode();
        Integer pageIndex = iotSystemLoggerQueryListDto.getPageIndex();
        Integer pageSize = iotSystemLoggerQueryListDto.getPageSize();


        String sql =
                "select idl.logger_time loggerTime,idu.username userName,idlt.logger_type_name loggerTypeName,idl" +
                        ".logger_content loggerContent, " +
                        " idl.iot_dos_logger_id iotDeviceLoggerId, " +
                        " idlt.iot_dos_logger_type_id iotLoggerTypeId,idu.iot_dos_user_id iotUserId from " +
                        "iot_dos_logger idl left join iot_dos_user idu " +
                        " on idl.iot_dos_user_id = idu.iot_dos_user_id, iot_dos_logger_relation idlr, " +
                        "iot_dos_logger_type idlt " +
                        " where idl.iot_dos_logger_id=idlr.iot_dos_logger_id and idl.iot_dos_logger_type_id=idlt" +
                        ".iot_dos_logger_type_id " +
                        " and idl.logger_time >=:startDate and idl.logger_time <=:endDate " +
                        " and idl.is_deleted = false and idlr.is_deleted = false and idlt.is_deleted = false";

        if (iotSystemLoggerQueryListDto.getIotProjectId() != null) {
            sql = sql + " and idlr.iot_dos_project_id =:iotProjectId ";
        }

        if (!StringUtils.isEmpty(loggerTypeCode)) {
            sql = sql + " and idlt.logger_type_code in (:loggerTypeCode) ";
        }
        sql = sql + " order by idl.logger_time desc ";

        Query query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class)
                .addScalar("loggerTime", StandardBasicTypes.TIMESTAMP)
                .addScalar("userName", StandardBasicTypes.STRING)
                .addScalar("loggerTypeName", StandardBasicTypes.STRING)
                .addScalar("loggerContent", StandardBasicTypes.STRING)
                .addScalar("iotDeviceLoggerId", StandardBasicTypes.LONG)
                .addScalar("iotLoggerTypeId", StandardBasicTypes.LONG)
                .addScalar("iotUserId", StandardBasicTypes.LONG)
                .setResultTransformer(Transformers.aliasToBean(IotDeviceLoggerListDto.class));
        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);

        query.setParameter("startDate", starDate);
        query.setParameter("endDate", endDate);

        if (iotSystemLoggerQueryListDto.getIotProjectId() != null) {
            query.setParameter("iotProjectId", iotSystemLoggerQueryListDto.getIotProjectId());
        }

        if (!StringUtils.isEmpty(loggerTypeCode)) {
            query.setParameter("loggerTypeCode", Arrays.asList(loggerTypeCode.split(",")));
        }

        return query.list();
    }

    @Override
    public Integer queryDeviceLoggerListCount(IotDeviceLoggerQueryListDto deviceLoggerQueryListDto) {

        String loggerTypeCode = deviceLoggerQueryListDto.getLoggerTypeCode();
        Long iotDeviceId = deviceLoggerQueryListDto.getIotDeviceId();
        String associationType = IotDevice.class.getSimpleName();
        String endDate = sdf.format(new Date(deviceLoggerQueryListDto.getEndTime()));
        String starDate = sdf.format(new Date(deviceLoggerQueryListDto.getStartTime()));

        String sql =
                "select count(1) from iot_dos_logger idl left join iot_dos_user idu on idl.iot_dos_user_id = idu" +
                        ".iot_dos_user_id, " +
                        " iot_dos_logger_relation idlr, iot_dos_logger_type idlt where idl.iot_dos_logger_id=idlr" +
                        ".iot_dos_logger_id and " +
                        " idl.iot_dos_logger_type_id=idlt.iot_dos_logger_type_id and idl.logger_time >=:startDate and" +
                        " idl.logger_time <=:endDate " +
                        " and idlt.logger_type_code=:loggerTypeCode and idl.is_deleted = false and idlr.is_deleted = " +
                        "false and idlt.is_deleted = false " +
                        " and idlr.association_id=:iotDeviceId and idlr.association_type=:associationType";

        Query query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        query.setParameter("loggerTypeCode", loggerTypeCode);
        query.setParameter("iotDeviceId", iotDeviceId);
        query.setParameter("associationType", associationType);
        query.setParameter("startDate", starDate);
        query.setParameter("endDate", endDate);

        return Integer.valueOf(String.valueOf(query.uniqueResult()));

    }

    @Override
    public Integer querySystemLoggerListCount(IotSystemLoggerQueryListDto iotSystemLoggerQueryListDto) {
        String loggerTypeCode = iotSystemLoggerQueryListDto.getLoggerTypeCode();
        String endDate = sdf.format(new Date(iotSystemLoggerQueryListDto.getEndTime()));
        String starDate = sdf.format(new Date(iotSystemLoggerQueryListDto.getStartTime()));

        String sql =
                "select count(1) from iot_dos_logger idl left join iot_dos_user idu on idl.iot_dos_user_id = idu" +
                        ".iot_dos_user_id, " +
                        " iot_dos_logger_relation idlr, iot_dos_logger_type idlt where idl.iot_dos_logger_id=idlr" +
                        ".iot_dos_logger_id and " +
                        " idl.iot_dos_logger_type_id=idlt.iot_dos_logger_type_id and idl.logger_time >=:startDate and" +
                        " idl.logger_time <=:endDate " +
                        " and idl.is_deleted = false and idlr.is_deleted = false and idlt.is_deleted = false ";


        if (iotSystemLoggerQueryListDto.getIotProjectId() != null) {
            sql = sql + " and idlr.iot_dos_project_id =:iotProjectId ";
        }

        if (!StringUtils.isEmpty(loggerTypeCode)) {
            sql = sql + " and idlt.logger_type_code in (:loggerTypeCode) ";
        }

        Query query = entityManager.createNativeQuery(sql).unwrap(SQLQuery.class);

        if (iotSystemLoggerQueryListDto.getIotProjectId() != null) {
            query.setParameter("iotProjectId", iotSystemLoggerQueryListDto.getIotProjectId());
        }

        if (!StringUtils.isEmpty(loggerTypeCode)) {
            query.setParameter("loggerTypeCode", Arrays.asList(loggerTypeCode.split(",")));
        }
        query.setParameter("startDate", starDate);
        query.setParameter("endDate", endDate);

        return Integer.valueOf(String.valueOf(query.uniqueResult()));
    }
}
