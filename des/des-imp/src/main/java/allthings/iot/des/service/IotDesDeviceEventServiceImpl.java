package allthings.iot.des.service;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.Result;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.common.msg.DeviceEventMsg;
import allthings.iot.des.api.IotDesDeviceEventService;
import allthings.iot.des.dto.IotDesEventTypeDto;
import allthings.iot.des.dto.query.IotDesDeviceEventListQueryDto;
import allthings.iot.des.dto.query.IotDesDeviceEventSaveDto;
import allthings.iot.des.dto.query.IotDesEventDetailDto;
import allthings.iot.des.manager.IotDesDeviceEventPush;
import allthings.iot.des.mapper.IotDesDeviceEventMapper;
import allthings.iot.des.mapper.IotDesEventContentMapper;
import allthings.iot.des.mapper.IotDesEventTypeMapper;
import allthings.iot.des.model.IotDesDeviceEvent;
import allthings.iot.des.model.IotDesEventContent;
import allthings.iot.dos.dto.IotEventPushUrlDto;
import allthings.iot.dss.ui.IotDssApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author tyf
 * @date 2019/03/05 13:49
 */
@Service("iotDesDeviceEventService")
public class IotDesDeviceEventServiceImpl implements IotDesDeviceEventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotDesDeviceEventServiceImpl.class);

    @Autowired
    private IotDesDeviceEventMapper iotDesDeviceEventMapper;

    @Autowired
    private IotDesEventContentMapper iotDesEventContentMapper;

    @Autowired
    private IotDesEventTypeMapper iotDesEventTypeMapper;
    @Autowired
    private IotDssApi dssFeignClient;
    @Autowired
    private IotDesDeviceEventPush iotDesDeviceEventPush;

    private DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveIotDesDeviceEvent(IotDesDeviceEventSaveDto iotDesDeviceEventSaveDto) {
        String eventTypeCode = iotDesDeviceEventSaveDto.getEventTypeCode();
        if (StringUtils.isBlank(eventTypeCode)) {
            LOGGER.warn("事件类型编码为空。");
            return;
        }
        Long iotDesEventTypeId = iotDesEventTypeMapper.getEventTypeByEventTypeCode(eventTypeCode);
        if (iotDesEventTypeId == null) {
            LOGGER.warn("根据事件类型编码【{}】查询事件类型id为空.", eventTypeCode);
            return;
        }

        String deviceCode = iotDesDeviceEventSaveDto.getDeviceCode();
        if (StringUtils.isBlank(deviceCode)) {
            LOGGER.warn("设备号为空。");
            return;
        }

        ResultDTO<List<Long>> resultDTO = dssFeignClient.getIotDeviceIdByDeviceCode(deviceCode);
        if (!resultDTO.isSuccess()) {
            LOGGER.warn("根据设备编码【{}】查询设备id失败.", deviceCode);
            return;
        }
        for (Long iotDeviceId : resultDTO.getData()) {
            IotDesDeviceEvent deviceEvent = new IotDesDeviceEvent();
            deviceEvent.setEventDescription(iotDesDeviceEventSaveDto.getEventDescription());
            deviceEvent.setEventSource(iotDesDeviceEventSaveDto.getEventSource());
            deviceEvent.setEventTime(new Date(iotDesDeviceEventSaveDto.getEventTime()));
            deviceEvent.setIotDesEventTypeId(iotDesEventTypeId);
            deviceEvent.setIotDeviceId(iotDeviceId);
            deviceEvent.setCreateOperatorId(iotDesDeviceEventSaveDto.getCreateOperatorId());
            deviceEvent.setModifyOperatorId(iotDesDeviceEventSaveDto.getCreateOperatorId());
            iotDesDeviceEventMapper.saveIotDesDeviceEvent(deviceEvent);

            IotDesEventContent eventContent = new IotDesEventContent();
            eventContent.setEventData(iotDesDeviceEventSaveDto.getEventData());
            eventContent.setImage(iotDesDeviceEventSaveDto.getImage() == null ? StringUtils.EMPTY : iotDesDeviceEventSaveDto.getImage());
            eventContent.setIotDesDeviceEventId(deviceEvent.getIotDesDeviceEventId());
            eventContent.setVideo(iotDesDeviceEventSaveDto.getVideo() == null ? StringUtils.EMPTY : iotDesDeviceEventSaveDto.getVideo());
            eventContent.setCreateOperatorId(iotDesDeviceEventSaveDto.getCreateOperatorId());
            eventContent.setModifyOperatorId(iotDesDeviceEventSaveDto.getCreateOperatorId());
            iotDesEventContentMapper.saveIotDesEventContent(eventContent);
        }

    }

    @Override
    public Result<PageResult<IotDesDeviceEventListQueryDto>> getDeviceEventListByIotDeviceId(Long iotDeviceId, Long startTime, Long endTime, Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        if (startTime == null || endTime == null || startTime > endTime) {
            LOGGER.warn("时间参数错误，设备id：{}，开始时间：{}， 结束时间：{}", iotDeviceId, startTime, endTime);
            return Result.newFaild("时间参数异常");
        }
        if (pageIndex <= 0 || pageSize <= 0) {
            LOGGER.warn("分页参数错误，设备id：{}，页码：{}， 每页条数：{}", iotDeviceId, pageIndex, pageSize);
            return Result.newFaild("分页参数异常");
        }
        String startDate = dateFormat.format(new Date(startTime));
        String endDate = dateFormat.format(new Date(endTime));
        List<IotDesDeviceEventListQueryDto> eventListQueryDtos = iotDesDeviceEventMapper.getEventListByIotDeviceId(iotDeviceId,
                startDate, endDate);
        PageInfo<IotDesDeviceEventListQueryDto> pageInfo = new PageInfo<>(eventListQueryDtos);
        PageResult<IotDesDeviceEventListQueryDto> queryResult = new PageResult<>((int) pageInfo.getTotal(), pageInfo.getList());
        return Result.newSuccess(queryResult);
    }

    @Override
    public Result<IotDesEventDetailDto> getEventDetailByIotDesDeviceEventId(Long iotDesDeviceEventId) {
        return Result.newSuccess(iotDesDeviceEventMapper.queryEventDetailByDeviceEventId(iotDesDeviceEventId));
    }

    @Override
    public void pushDeviceEvent(DeviceEventMsg msg) throws Exception {
        String deviceCode = msg.getSourceDeviceId();
        ResultDTO<List<Long>> resultDTO = dssFeignClient.getIotProjectIdByDeviceCode(deviceCode);
        if (!resultDTO.isSuccess()) {
            LOGGER.warn("根据设备编码【{}】查询设备所属项目id失败。", deviceCode);
            return;
        }
        for (Long iotProjectId : resultDTO.getData()) {
            ResultDTO<IotEventPushUrlDto> bizReturn = dssFeignClient.getEventPushUrlByIotProjectId(iotProjectId);

            if (!bizReturn.isSuccess()) {
                LOGGER.warn("根据设备编码【{}】查询推送地址失败。", deviceCode);
                continue;
            }
            IotEventPushUrlDto eventPushUrlDto = bizReturn.getData();
            if (StringUtils.isBlank(eventPushUrlDto.getPushUrl())) {
                LOGGER.warn("设备号【{}】推送地址为空。", deviceCode);
                continue;
            }
            iotDesDeviceEventPush.publishDeviceEvent(eventPushUrlDto.getPushUrl(), msg);
        }
    }

    @Override
    public Result<IotDesEventTypeDto> getEventTypeByEventTypeCode(String eventTypeCode) {
        if (StringUtils.isBlank(eventTypeCode)) {
            LOGGER.warn("事件类型编码为空。");
            return Result.newFaild("事件类型编码不能为空");
        }
        return Result.newSuccess(iotDesEventTypeMapper.getIotEventTypeByEventTypeCode(eventTypeCode));
    }
}