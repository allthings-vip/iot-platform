package allthings.iot.dos.dao.impl;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dos.dao.IotServiceQueryDao;
import allthings.iot.dos.model.IotService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotServiceDaoImpl
 * @CreateDate :  2019/5/13
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
@Repository("iotServiceQueryDao")
public class IotServiceQueryDaoImpl implements IotServiceQueryDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public QueryResult<IotService> getIotServiceLists(Boolean status, String keywords, Integer pageIndex,
                                                      Integer pageSize) {
        List<IotService> resultList = queryIotServiceLists(status, keywords, pageIndex, pageSize);
        Long totalCount = queryTotalCountIotService(status, keywords);
        QueryResult<IotService> queryDTOQueryResult = new QueryResult<>(resultList, totalCount);
        return queryDTOQueryResult;
    }

    @Override
    public QueryResult<IotService> getServiceInfoTopology() {
        List<IotService> resultList = queryIotServiceLists(null, null, 1, Integer.MAX_VALUE);
        Long totalCount = queryTotalCountIotService(null, null);
        QueryResult<IotService> queryDTOQueryResult = new QueryResult<>(resultList, totalCount);
        return queryDTOQueryResult;
    }

    /**
     * @param status
     * @param keywords
     * @return
     */
    private Long queryTotalCountIotService(Boolean status, String keywords) {
        StringBuffer sql = new StringBuffer(" SELECT count(1) ");
        sql.append(" from iot_dos_service s ");
        sql.append(" where s.is_deleted = false ");

        if (status != null && !status) {
            sql.append(" and (s.status =:status or s.status is null) ");
        } else if (status != null) {
            sql.append(" and s.status =:status ");
        }
        if (!StringUtils.isEmpty(keywords)) {
            sql.append(" and (s.service_name like :keywords or s.ip like :keywords or s.port like :keywords) ");
        }

        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(NativeQuery.class);

        if (status != null) {
            query.setParameter("status", status);
        }

        if (StringUtils.isNotEmpty(keywords)) {
            query.setParameter("keywords", "%" + keywords + "%");
        }

        BigInteger count = (BigInteger) query.uniqueResult();

        return count.longValue();
    }

    private List<IotService> queryIotServiceLists(Boolean status, String keywords, Integer pageIndex,
                                                  Integer pageSize) {
        StringBuffer sql = new StringBuffer(
                " SELECT s.iot_dos_service_id iotServiceId, create_operator_id createOperatorId, " +
                        "modify_operator_id modifyOperatorId, service_name serviceName, service_code serviceCode, ip " +
                        "ip, " +
                        "port port, levels levels, dependency_service dependencyService, status status ");
        sql.append(" from iot_dos_service s ");
        sql.append(" where s.is_deleted = false ");

        if (status != null && !status) {
            sql.append(" and (s.status =:status or s.status is null) ");
        } else if (status != null) {
            sql.append(" and s.status =:status ");
        }
        if (!StringUtils.isEmpty(keywords)) {
            sql.append(" and (s.service_name like :keywords or s.ip like :keywords or s.port like :keywords) ");
        }
        sql.append(" order by s.gmt_modified desc ");

        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(NativeQuery.class)
                .addScalar("iotServiceId", StandardBasicTypes.LONG)
                .addScalar("createOperatorId", StandardBasicTypes.LONG)
                .addScalar("modifyOperatorId", StandardBasicTypes.LONG)
                .addScalar("serviceName", StandardBasicTypes.STRING)
                .addScalar("serviceCode", StandardBasicTypes.STRING)
                .addScalar("ip", StandardBasicTypes.STRING)
                .addScalar("port", StandardBasicTypes.STRING)
                .addScalar("levels", StandardBasicTypes.INTEGER)
                .addScalar("dependencyService", StandardBasicTypes.STRING)
                .addScalar("status", StandardBasicTypes.BOOLEAN)
                .setResultTransformer(Transformers.aliasToBean(IotService.class));

        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);

        if (status != null) {
            query.setParameter("status", status);
        }

        if (StringUtils.isNotEmpty(keywords)) {
            query.setParameter("keywords", "%" + keywords + "%");
        }

        return query.list();
    }
}
