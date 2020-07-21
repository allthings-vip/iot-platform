package allthings.iot.dms.service;


import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  PredicateUtil
 * @CreateDate :  2017/11/08
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
public class PredicateUtil {

    public static <T> Predicate newPredicateByDeviceTypeAndCreateTime(Root<T> root,
                                                                      CriteriaBuilder criteriaBuilder,
                                                                      String deviceType,
                                                                      long beginTime,
                                                                      long endTime) {
        List<Predicate> predicateList = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(deviceType)) {
            predicateList.add(newPredicateByDeviceType(root, criteriaBuilder, deviceType));
        }
        Predicate prTime = newPredicateByCreateTime(root, criteriaBuilder, beginTime, endTime);
        if (prTime != null) {
            predicateList.add(prTime);
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }

    public static <T> Predicate newPredicateByDeviceIdAndCreateTime(Root<T> root,
                                                                    CriteriaBuilder criteriaBuilder,
                                                                    String deviceId,
                                                                    long beginTime,
                                                                    long endTime) {
        List<Predicate> predicateList = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(deviceId)) {
            predicateList.add(newPredicateByDeviceId(root, criteriaBuilder, deviceId));
        }
        Predicate prTime = newPredicateByCreateTime(root, criteriaBuilder, beginTime, endTime);
        if (prTime != null) {
            predicateList.add(prTime);
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }


    public static <T> Predicate newPredicateByDeviceType(Root<T> root, CriteriaBuilder criteriaBuilder, String
            deviceType) {
        return criteriaBuilder.equal(root.get("deviceType").as(String.class), deviceType);
    }

    public static <T> Predicate newPredicateByDeviceId(Root<T> root, CriteriaBuilder criteriaBuilder, String deviceId) {
        return criteriaBuilder.equal(root.get("deviceId").as(String.class), deviceId);
    }

    public static <T> Predicate newPredicateByCreateTime(Root<T> root, CriteriaBuilder criteriaBuilder, long
            beginTime, long endTime) {
        List<Predicate> predicateList = Lists.newArrayList();
        if (beginTime > 0 && endTime > 0 && endTime > beginTime) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("inputDate").as(Date.class), new Date
                    (beginTime)
            ));
            predicateList.add(criteriaBuilder.lessThan(root.get("inputDate").as(Date.class), new Date(endTime)));
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }


    /**
     * 对象拷贝
     *
     * @param sList
     * @param tList
     * @return
     */
    public static <S, T> void entity2Dto(List<S> sList, List<T> tList, Class<T> clazz) {
        try {
            for (S k : sList) {
                T t = clazz.newInstance();
                BeanUtils.copyProperties(k, t);
                tList.add(t);
            }
        } catch (Exception ee) {
        }
    }
}
