package allthings.iot.dms.service;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.msg.DasConnectionMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.dms.IMsgLogService;
import allthings.iot.dms.dao.MsgLogDao;
import allthings.iot.dms.dto.MsgLogDto;
import allthings.iot.dms.entity.IotMsgLog;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  MsgLogServiceImpl
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
public class MsgLogServiceImpl implements IDmsMsgProcessor<IMsg>, IMsgLogService {

    private static final Logger LOG = LoggerFactory.getLogger(MsgLogServiceImpl.class);
    /**
     * HBASE 表名
     */
    private static final String DMS_TABLE_NAME = "IOT:IOT_DMS_LOG";
    /**
     * HBASE 列簇
     */
    private static final String DMS_COL_FAMILY = "log";

    @Autowired
    MsgLogDao dao;
//    @Autowired
//    private HbaseTemplate hbaseTemplate;

    @Override
    public void processMsg(IMsg msg) {
        saveMsgLog(msg);
    }

    @Override
    public QueryResult<MsgLogDto> getMsgLogs(String deviceType, String deviceId, String msgType, long beginTime, long
            endTime, int pageIndex, int pageSize) {
        Page<IotMsgLog> page = dao.findAll(new Specification<IotMsgLog>() {
            @Override
            public Predicate toPredicate(Root<IotMsgLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                List<Predicate> predicateList = Lists.newArrayList();
                if (!Strings.isNullOrEmpty(deviceType)) {
                    predicateList.add(PredicateUtil.newPredicateByDeviceType(root, criteriaBuilder, deviceType));
                }
                if (!Strings.isNullOrEmpty(deviceId)) {
                    predicateList.add(PredicateUtil.newPredicateByDeviceId(root, criteriaBuilder, deviceId));
                }
                if (!Strings.isNullOrEmpty(msgType)) {
                    predicateList.add(criteriaBuilder.equal(root.get("msgType").as(String.class), msgType));
                }
                Predicate prTime = PredicateUtil.newPredicateByCreateTime(root, criteriaBuilder, beginTime, endTime);
                if (prTime != null) {
                    predicateList.add(prTime);
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        }, new PageRequest(pageIndex - 1, pageSize, Sort.Direction.DESC, "inputDate"));

        List<MsgLogDto> msgLogDtoList = Lists.newArrayList();
        PredicateUtil.entity2Dto(page.getContent(), msgLogDtoList, MsgLogDto.class);
        return new QueryResult<>(msgLogDtoList, page.getTotalElements());
    }


    void saveMsgLog(IMsg msg) {
        if (msg instanceof DasConnectionMsg) {
            return;
        }

        if (Strings.isNullOrEmpty(msg.getSourceDeviceType())
                || Strings.isNullOrEmpty(msg.getSourceDeviceId()
        )) {
            LOG.warn("sourceDeviceType or sourceDeviceId is null:{}", msg);
            return;
        }

        try {
            String msgType = String.valueOf(msg.getMsgType());
            String deviceType = msg.getSourceDeviceType();
            String deviceId = msg.getSourceDeviceId();
            String msgContent = msg.toString().replace("\\", "");
            Map<String, Object> params = msg.getParams();
            Object gpsDateTime = params.get("gps_datetime");
            long dateTime;
            if (gpsDateTime == null) {
                dateTime = msg.getOccurTime();
            } else {
                dateTime = Long.parseLong(String.valueOf(gpsDateTime));
            }
//            String rowKey = deviceType + deviceId + dateTime;
//            byte[] colFamilyBytes = Bytes.toBytes(DMS_COL_FAMILY);
//            Put put = new Put(Bytes.toBytes(rowKey), dateTime);
//            put.addColumn(colFamilyBytes, Bytes.toBytes("deviceType"), Bytes.toBytes(deviceType));
//            put.addColumn(colFamilyBytes, Bytes.toBytes("deviceId"), Bytes.toBytes(deviceId));
//            put.addColumn(colFamilyBytes, Bytes.toBytes("msgType"), Bytes.toBytes(msgType));
//            put.addColumn(colFamilyBytes, Bytes.toBytes("msgContent"), Bytes.toBytes(msgContent));
//            List<Mutation> mutations = Lists.newArrayList();
//            mutations.add(put);
//            hbaseTemplate.saveOrUpdates(DMS_TABLE_NAME, mutations);

//            long time = System.currentTimeMillis();
//            IotMsgLog pojo = new IotMsgLog();
//            pojo.setDeviceType(msg.getSourceDeviceType());
//            pojo.setDeviceId(msg.getSourceDeviceId());
//            pojo.setMsgType(msg.getMsgType().toString());
//
//            String msgContent = msg.toString();
//            msgContent = msgContent.replace("\\", "");
//            pojo.setMsgContent(msgContent);
//
//            dao.saveAndFlush(pojo);
//            LOG.info("保存耗时：{}", System.currentTimeMillis() - time);
        } catch (Exception e) {
            LOG.warn(String.format("saveMsgLog error. \ndevice msg content:%s\nexception:", msg.toString()), e);
        }
    }

}
