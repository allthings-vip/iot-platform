package allthings.iot.dos.dao.impl;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dos.dao.IotProjectQueryDao;
import allthings.iot.dos.dto.query.IotProjectQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-27 17:25
 */
@Repository("iotProjectQueryDao")
public class IotProjectQueryDaoImpl implements IotProjectQueryDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public QueryResult<IotProjectQueryDTO> getIotProjectList(Long iotUserId, String keywords, Integer pageIndex,
                                                             Integer pageSize) {
        List<IotProjectQueryDTO> resultList = queryProjectList(iotUserId, keywords, pageIndex, pageSize);
        Long total = queryProjectCount(iotUserId, keywords);
        QueryResult<IotProjectQueryDTO> queryResult = new QueryResult<>(resultList, total);
        return queryResult;
    }

    private List<IotProjectQueryDTO> queryProjectList(Long iotUserId, String keywords, Integer pageIndex,
                                                      Integer pageSize) {
        StringBuffer sql = new StringBuffer(
                "select ip.gmt_create inputDate,ip.create_operator_id createOperatorId,ip.iot_dos_project_id " +
                        "iotProjectId,ip.project_name projectName,ip.company_name companyName,ip.description " +
                        "description," +
                        "ip.image_url imageUrl,ip.is_review isReview ");
        sql.append(getSql(iotUserId, keywords));
        sql.append("order by ip.gmt_modified desc ");
        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class)
                .addScalar("inputDate", StandardBasicTypes.DATE)
                .addScalar("createOperatorId", StandardBasicTypes.LONG)
                .addScalar("iotProjectId", StandardBasicTypes.LONG)
                .addScalar("projectName", StandardBasicTypes.STRING)
                .addScalar("companyName", StandardBasicTypes.STRING)
                .addScalar("description", StandardBasicTypes.STRING)
                .addScalar("imageUrl", StandardBasicTypes.STRING)
                .addScalar("isReview", StandardBasicTypes.BOOLEAN)
                .setResultTransformer(Transformers.aliasToBean(IotProjectQueryDTO.class));

        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);
        if (iotUserId != null) {
            query.setParameter("iotUserId", iotUserId);
        }
        if (StringUtils.isNotEmpty(keywords)) {
            query.setParameter("keywords", keywords);
        }
        return query.list();
    }

    private Long queryProjectCount(Long iotUserId, String keywords) {
        StringBuffer sql = new StringBuffer("select count(ip.iot_dos_project_id)");
        sql.append(getSql(iotUserId, keywords));
        Query query = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class);
        if (iotUserId != null) {
            query.setParameter("iotUserId", iotUserId);
        }
        if (StringUtils.isNotEmpty(keywords)) {
            query.setParameter("keywords", keywords);
        }
        BigInteger count = (BigInteger) query.uniqueResult();
        return count.longValue();
    }

    private String getSql(Long iotUserId, String keywords) {
        StringBuffer sql = new StringBuffer("from iot_dos_project ip,iot_dos_user_project up where " +
                "ip.is_deleted = false and up.is_deleted = false and ip.iot_dos_project_id=" +
                "up.iot_dos_project_id ");
        if (StringUtils.isNotEmpty(keywords)) {
            sql.append("and ip.project_name like concat('%',:keywords,'%') ");
        }
        if (iotUserId != null) {
            sql.append("and up.iot_dos_user_id=:iotUserId ");
        }
        return sql.toString();
    }
}
