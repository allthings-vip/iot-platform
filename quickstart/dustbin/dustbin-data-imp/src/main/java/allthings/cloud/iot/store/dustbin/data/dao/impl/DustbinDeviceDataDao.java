package tf56.cloud.iot.store.dustbin.data.dao.impl;

import com.google.common.collect.Lists;
import tf56.cloud.iot.store.dustbin.data.dao.IDustbinDeviceDataDao;
import tf56.cloud.iot.store.dustbin.data.dto.DustbinDeviceDataDto;
import tf56.cloud.iot.store.dustbin.data.entity.DustbinDeviceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  DustbinDeviceDataDao
 * @CreateDate :  2017/11/22
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Repository
public class DustbinDeviceDataDao implements IDustbinDeviceDataDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<DustbinDeviceData> save(Iterable<DustbinDeviceData> entities) {
        List<DustbinDeviceData> result = new ArrayList<>();
        if (entities == null) {
            return result;
        }

        for (DustbinDeviceData entity : entities) {
            result.add(save(entity));
        }

        return result;
    }

    @Override
    public DustbinDeviceData save(DustbinDeviceData dustbinDeviceData) {
        entityManager.persist(dustbinDeviceData);
        return dustbinDeviceData;
    }

    @Override
    public List<DustbinDeviceDataDto> getDustbinDeviceDataList(String deviceId, long startDatetime, long endDatetime,
                                                               List<String> factorCodes, int pageIndex, int pageSize) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(DustbinDeviceDataDao.class);
        Root root = criteriaQuery.from(DustbinDeviceData.class);

        List<Predicate> predicateList = Lists.newArrayList();

        predicateList.add(criteriaBuilder.equal(root.get("deviceId").as(String.class), deviceId));
        predicateList.add(criteriaBuilder.gt(root.get("inputDate").as(Date.class), startDatetime));
        predicateList.add(criteriaBuilder.le(root.get("inputDate").as(Date.class), endDatetime));
        if (factorCodes != null && factorCodes.size() > 0) {
            predicateList.add(root.get("factorCode").as(String.class).in(factorCodes.toArray()));
        }

        criteriaQuery = criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));

        criteriaQuery = criteriaQuery.multiselect(root.get("id"), root.get("inputDate"), root.get("deviceId"),
                root.get("deviceType"), root.get("factorCode"), root.get("correctValue"));

        Query query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(pageSize);
        query.setFirstResult((pageIndex - 1) * pageSize);

        return query.getResultList();
    }
}
