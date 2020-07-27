package allthings.iot.dms.service;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.msg.DeviceAlarmMsg;
import allthings.iot.dms.IDeviceAlarmService;
import allthings.iot.dms.dao.DeviceAlarmDao;
import allthings.iot.dms.dto.DeviceAlarmDto;
import allthings.iot.dms.entity.IotDeviceAlarm;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  DeviceAlarmServiceImpl
 * @CreateDate :  2017/11/08
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
@Service
public class DeviceAlarmServiceImpl implements IDmsMsgProcessor<DeviceAlarmMsg>, IDeviceAlarmService {

    @Autowired
    DeviceAlarmDao dao;

    @Override
    public void processMsg(DeviceAlarmMsg msg) {

        IotDeviceAlarm pojo = new IotDeviceAlarm();
        pojo.setDeviceType(msg.getSourceDeviceType());
        pojo.setDeviceId(msg.getSourceDeviceId());
        pojo.setAlarmCode(msg.getAlarmCode());
        pojo.setAlarmDesc(msg.getAlarmDescription());

        dao.saveAndFlush(pojo);
    }

    @Override
    public long countOfDeviceAlarm(long beginTime, long endTime) {
        return dao.count(new Specification<IotDeviceAlarm>() {
            @Override
            public Predicate toPredicate(Root<IotDeviceAlarm> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                return PredicateUtil.newPredicateByCreateTime(root, criteriaBuilder, beginTime, endTime);
            }
        });
    }

    @Override
    public long countOfDeviceAlarmByDeviceType(String deviceType, long beginTime, long endTime) {
        return dao.count(new Specification<IotDeviceAlarm>() {
            @Override
            public Predicate toPredicate(Root<IotDeviceAlarm> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                return PredicateUtil.newPredicateByDeviceTypeAndCreateTime(root, criteriaBuilder, deviceType,
                        beginTime, endTime);
            }
        });
    }

    @Override
    public long countOfDeviceAlarmByDeviceId(String deviceId, long beginTime, long endTime) {
        return dao.count(new Specification<IotDeviceAlarm>() {
            @Override
            public Predicate toPredicate(Root<IotDeviceAlarm> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                return PredicateUtil.newPredicateByDeviceIdAndCreateTime(root, criteriaBuilder, deviceId, beginTime,
                        endTime);
            }
        });
    }

    @Override
    public QueryResult<DeviceAlarmDto> getDeviceAlarmsByDeviceId(String deviceId, List<String> alarmCodes, long
            beginTime, long endTime, int pageIndex, int pageSize) {
        Page<IotDeviceAlarm> page = dao.findAll(new Specification<IotDeviceAlarm>() {
            @Override
            public Predicate toPredicate(Root<IotDeviceAlarm> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                List<Predicate> predicateList = Lists.newArrayList();
                predicateList.add(PredicateUtil.newPredicateByDeviceIdAndCreateTime(root, criteriaBuilder, deviceId,
                        beginTime, endTime));

                if (alarmCodes != null && !alarmCodes.isEmpty()) {
                    predicateList.add(criteriaBuilder.and(root.get("alarmCode").as(String.class).in(alarmCodes)));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        }, new PageRequest(pageIndex - 1, pageSize, Sort.Direction.DESC, "inputDate"));

        List<DeviceAlarmDto> deviceAlarmDtoList = Lists.newArrayList();
        PredicateUtil.entity2Dto(page.getContent(), deviceAlarmDtoList, DeviceAlarmDto.class);
        return new QueryResult<>(deviceAlarmDtoList, page.getTotalElements());
    }
}
