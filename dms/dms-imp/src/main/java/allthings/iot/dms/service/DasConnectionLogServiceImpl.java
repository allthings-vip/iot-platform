package allthings.iot.dms.service;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.msg.DasConnectionMsg;
import allthings.iot.dms.IDasConnectionLogService;
import allthings.iot.dms.dao.DasConnectionLogDao;
import allthings.iot.dms.dto.DasConnectionLogDto;
import allthings.iot.dms.entity.IotDasConnectionLog;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  DasConnectionLogServiceImpl
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
public class DasConnectionLogServiceImpl implements IDmsMsgProcessor<DasConnectionMsg>, IDasConnectionLogService {
    @Autowired
    DasConnectionLogDao dao;

    @Autowired
    DasStatusServiceImpl dasStatusServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processMsg(DasConnectionMsg msg) {
        IotDasConnectionLog log = new IotDasConnectionLog();
        log.setNodeId(msg.getDasNodeId());
        log.setNodeIp(msg.getIp());
        log.setNodePort(msg.getPort());
        log.setConnected(msg.isConnected());
        dao.saveAndFlush(log);

        dasStatusServiceImpl.processMsg(msg);
    }

    @Override
    public QueryResult<DasConnectionLogDto> getDasConnectionLogsByNodeId(String nodeId, long beginTime, long endTime,
                                                                         int pageIndex, int pageSize) {
        Page<IotDasConnectionLog> page = dao.findAll(new Specification<IotDasConnectionLog>() {
            @Override
            public Predicate toPredicate(Root<IotDasConnectionLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                List<Predicate> predicateList = Lists.newArrayList();
                if (!Strings.isNullOrEmpty(nodeId)) {
                    predicateList.add(criteriaBuilder.equal(root.get("nodeId").as(String.class), nodeId));
                }

                Predicate prTime = PredicateUtil.newPredicateByCreateTime(root, criteriaBuilder, beginTime, endTime);
                if (prTime != null) {
                    predicateList.add(prTime);
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        }, new PageRequest(pageIndex - 1, pageSize));

        List<DasConnectionLogDto> dasConnectionLogDtoList = Lists.newArrayList();
        PredicateUtil.entity2Dto(page.getContent(), dasConnectionLogDtoList, DasConnectionLogDto.class);
        return new QueryResult<>(dasConnectionLogDtoList, page.getTotalElements());
    }

}
