package allthings.iot.dms.service;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.dms.IDeviceConnectionLogService;
import allthings.iot.dms.dao.DeviceConnectionLogDao;
import allthings.iot.dms.dto.DeviceConnectionLogDto;
import allthings.iot.dms.entity.IotDeviceConnectionLog;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  DeviceConnectionLogServiceImpl
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
public class DeviceConnectionLogServiceImpl implements IDmsMsgProcessor<DeviceConnectionMsg>,
        IDeviceConnectionLogService {
    @Autowired
    DeviceConnectionLogDao dao;

    @Autowired
    DeviceStatusServiceImpl deviceStatusServiceImpl;

    @Override
    public void processMsg(DeviceConnectionMsg msg) {
        IotDeviceConnectionLog log = new IotDeviceConnectionLog();
        log.setDeviceType(msg.getSourceDeviceType());
        log.setDeviceId(msg.getSourceDeviceId());
        log.setNodeId(msg.getDasNodeId());
        log.setTerminalIp(msg.getTerminalIp());
        log.setConnected(msg.isConnected());

        deviceStatusServiceImpl.processMsg(msg);
        dao.saveAndFlush(log);
    }

    @Override
    public QueryResult<DeviceConnectionLogDto> getDeviceConnectionLogsByDeviceId(String deviceId, long beginTime, long
            endTime, int pageIndex, int pageSize) {
        Page<IotDeviceConnectionLog> page = dao.findAll(new Specification<IotDeviceConnectionLog>() {
            @Override
            public Predicate toPredicate(Root<IotDeviceConnectionLog> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                return PredicateUtil.newPredicateByDeviceIdAndCreateTime(root, criteriaBuilder, deviceId, beginTime,
                        endTime);
            }
        }, new PageRequest(pageIndex - 1, pageSize));

        List<DeviceConnectionLogDto> deviceConnectionLogDtoList = Lists.newArrayList();
        PredicateUtil.entity2Dto(page.getContent(), deviceConnectionLogDtoList, DeviceConnectionLogDto.class);
        return new QueryResult<>(deviceConnectionLogDtoList, page.getTotalElements());
    }

}
