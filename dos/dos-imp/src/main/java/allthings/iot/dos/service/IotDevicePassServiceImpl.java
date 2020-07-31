package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotDevicePassService;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.DevicePass;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.constant.IotVisParam;
import allthings.iot.dos.consumer.IotVisProducer;
import allthings.iot.dos.dao.IotDevicePassDao;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDevicePassDto;
import allthings.iot.dos.dto.IotVisResultDTO;
import allthings.iot.dos.dto.query.IotDevicePassQueryDTO;
import allthings.iot.dos.model.IotDevicePass;
import allthings.iot.util.redis.ICentralCacheService;
import com.alibaba.fastjson.JSON;
import com.allthings.iot.vis.backend.commons.dto.mq.aps.request.APSGetLiveStreamRequest;
import com.allthings.iot.vis.backend.commons.dto.mq.aps.request.APSGetPlayBackRequest;
import com.allthings.iot.vis.backend.commons.dto.mq.aps.request.APSStartPTZRequest;
import com.allthings.iot.vis.backend.commons.dto.mq.aps.request.APSStopPTZRequest;
import com.allthings.iot.vis.backend.commons.enums.mq.MQMessageType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author tyf
 * @date 2019/02/27 13:56
 */
@Service
public class IotDevicePassServiceImpl implements IotDevicePassService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotDevicePassServiceImpl.class);
    @Autowired
    private IotDevicePassDao devicePassDao;
    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;
    @Autowired
    private IotVisProducer iotVisProducer;
    @Autowired
    private ICentralCacheService cache;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultDTO<Integer> saveOrUpdateDevicePass(IotDevicePassDto devicePassDto) {
        ResultDTO<Integer> bizReturn = validateSave(devicePassDto);
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }

        IotDevicePass devicePass = devicePassDao.queryIotDevicePassByIotDeviceIdAndPassCode(devicePassDto.getIotDeviceId(), devicePassDto.getPassCode());
        String passId = DigestUtils.md5Hex(devicePassDto.getPassTypeCode() + devicePassDto.getPassCode());
        if (devicePass == null) {
            devicePass = new IotDevicePass();
            BeanUtils.copyProperties(devicePassDto, devicePass);
            devicePass.setPassId(passId);
            devicePass.setExtendProperties(JSON.toJSONString(devicePassDto.getExtendProperties()));
            devicePassDao.saveAndFlush(devicePass);
        } else {
            devicePassDao.updateDevicePass(devicePassDto.getPassCode(), devicePassDto.getPassName(),
                    JSON.toJSONString(devicePassDto.getExtendProperties()), devicePassDto.getIotDevicePassId(),
                    devicePassDto.getIotPassTypeId(), passId);
        }

        return ResultDTO.newSuccess();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultDTO<Integer> batchSaveOrUpdateDevicePass(List<IotDevicePassDto> devicePassDtoList) {
        if (CollectionUtils.isEmpty(devicePassDtoList)) {
            LOGGER.warn("通道批量保存，参数为空");
            return ResultDTO.newSuccess();
        }

        List<IotDevicePass> devicePassList = Lists.newArrayList();
        Long iotDeviceId = devicePassDtoList.get(0).getIotDeviceId();
        devicePassDao.deleteIotDevicePassByIotDeviceId(iotDeviceId);
        Set<IotDevicePassDto> devicePassDtoSet = Sets.newHashSet(devicePassDtoList);
        for (IotDevicePassDto devicePassDto : devicePassDtoSet) {
            String passId = DigestUtils.md5Hex(devicePassDto.getPassTypeCode() + devicePassDto.getPassCode());
            IotDevicePass devicePass = new IotDevicePass();
            BeanUtils.copyProperties(devicePassDto, devicePass);
            devicePass.setPassId(passId);
            devicePass.setExtendProperties(JSON.toJSONString(devicePassDto.getExtendProperties()));
            devicePassList.add(devicePass);
        }

        devicePassDao.saveAll(devicePassList);
        return ResultDTO.newSuccess();
    }

    /**
     * 校验通道数据的合法性
     *
     * @param devicePassDto
     * @return
     */
    private ResultDTO<Integer> validateSave(IotDevicePassDto devicePassDto) {
        if (devicePassDto == null) {
            return ResultDTO.newSuccess(ErrorCode.ERROR_12003.getCode(),
                    ErrorCode.ERROR_12003.getMessage());
        }

        if (StringUtils.isBlank(devicePassDto.getPassCode())) {
            return ResultDTO.newSuccess(ErrorCode.ERROR_12000.getCode(),
                    ErrorCode.ERROR_12000.getMessage());
        }

        if (StringUtils.isBlank(devicePassDto.getPassName())) {
            return ResultDTO.newSuccess(ErrorCode.ERROR_12001.getCode(),
                    ErrorCode.ERROR_12001.getMessage());
        }

        if (devicePassDto.getIotDeviceId() == null) {
            return ResultDTO.newSuccess(ErrorCode.ERROR_12002.getCode(),
                    ErrorCode.ERROR_12002.getMessage());
        }

        if (StringUtils.isBlank(devicePassDto.getPassTypeCode())) {
            return ResultDTO.newSuccess(ErrorCode.ERROR_12004.getCode(),
                    ErrorCode.ERROR_12004.getMessage());
        }

        return ResultDTO.newSuccess();
    }

    @Override
    public ResultDTO<List<IotDevicePassDto>> getDevicePassList(IotDeviceDTO deviceDTO) {
        ResultDTO<Integer> bizReturn = iotDeviceTypeService.judgeProject(deviceDTO.getIotProjectId(), deviceDTO.getCreateOperatorId(), deviceDTO.getRoleCode());
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }

        Long iotDeviceId = deviceDTO.getIotDeviceId();
        if (iotDeviceId == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_3004.getCode(), ErrorCode.ERROR_3004.getMessage());
        }

        List<IotDevicePassDto> devicePassDtoList = devicePassDao.queryDevicePassByIotDeviceId(iotDeviceId);

        return ResultDTO.newSuccess(devicePassDtoList);
    }

    @Override
    public ResultDTO getDevicePassDetail(Long iotDosDevicePassId) {
        // TODO: 2019/2/28 0028 查询通道信息的实时数据，历史数据暂不实现
        return null;
    }

    @Override
    public ResultDTO<IotVisResultDTO> getPassLiveStream(IotDevicePassQueryDTO iotDevicePassQueryDto) {
        ResultDTO<IotDevicePassDto> judgeResult = judgeDevicePass(iotDevicePassQueryDto);
        if (!judgeResult.isSuccess()) {
            return ResultDTO.newFail(judgeResult.getCode(), judgeResult.getMsg());
        }

        String hlsLive = null;
        String hlsHD = null;
        String rtmpLive = null;
        IotDevicePassDto iotDevicePassDto = judgeResult.getData();
        List<Map<String, String>> extendList = iotDevicePassDto.getExtendProperties();
        if (extendList == null) {
            extendList = Lists.newArrayList();
        }

        for (Map<String, String> map : extendList) {
            String extendCode = map.get(DevicePass.EXTEND_CODE);
            if (IotVisParam.HLS_LIVE.equalsIgnoreCase(extendCode)) {
                String ob = map.get(DevicePass.EXTEND_VALUE);
                hlsLive = StringUtils.isBlank(ob) ? null : ob;
            }
            if (IotVisParam.HLS_HD.equalsIgnoreCase(extendCode)) {
                String ob = map.get(DevicePass.EXTEND_VALUE);
                hlsHD = StringUtils.isBlank(ob) ? null : ob;
            }

            if (IotVisParam.RTMP_LIVE.equalsIgnoreCase(extendCode)) {
                String ob = map.get(DevicePass.EXTEND_VALUE);
                rtmpLive = StringUtils.isBlank(ob) ? null : ob;
            }
        }

        //返回本地数据
        if (StringUtils.isNotBlank(hlsLive) || StringUtils.isNotBlank(hlsHD)) {
            IotVisResultDTO iotVisResultDTO = new IotVisResultDTO();
            iotVisResultDTO.setHlsLive(hlsLive);
            iotVisResultDTO.setHlsHD(hlsHD);
            return ResultDTO.newSuccess(iotVisResultDTO);
        }

        if (StringUtils.isNotBlank(rtmpLive)) {
            IotVisResultDTO iotVisResultDTO = new IotVisResultDTO();
            iotVisResultDTO.setHlsLive(rtmpLive);
            iotVisResultDTO.setHlsHD(rtmpLive);
            return ResultDTO.newSuccess(iotVisResultDTO);
        }

        //本地没有数据，发消息从VIS获取
        iotDevicePassQueryDto.setPassCode(iotDevicePassDto.getPassCode());
        ResultDTO<IotVisResultDTO> visResult = getVisResult(iotDevicePassQueryDto, IotVisParam.GET_LIVE_STREAM);
        if (!visResult.isSuccess()) {
            return visResult;
        }

        IotVisResultDTO iotVisResultDTO = visResult.getData();
        if (iotVisResultDTO == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_12012.getCode(), ErrorCode.ERROR_12012.getMessage());
        }

        if (StringUtils.isBlank(iotVisResultDTO.getHlsHD()) && StringUtils.isBlank(iotVisResultDTO.getHlsLive())) {
            return ResultDTO.newFail(ErrorCode.ERROR_12012.getCode(), ErrorCode.ERROR_12012.getMessage());
        }

        return visResult;
    }

    @Override
    public ResultDTO<IotVisResultDTO> getPassPlayBack(IotDevicePassQueryDTO iotDevicePassQueryDto) {
        ResultDTO<IotDevicePassDto> judgeResult = judgeDevicePass(iotDevicePassQueryDto);
        if (!judgeResult.isSuccess()) {
            return ResultDTO.newFail(judgeResult.getCode(), judgeResult.getMsg());
        }

        IotDevicePassDto iotDevicePassDto = judgeResult.getData();
        iotDevicePassQueryDto.setPassCode(iotDevicePassDto.getPassCode());
        ResultDTO<IotVisResultDTO> visResult = getVisResult(iotDevicePassQueryDto, IotVisParam.GET_PLAY_BACK);
        if (!visResult.isSuccess()) {
            return visResult;
        }

        IotVisResultDTO iotVisResultDTO = visResult.getData();
        if (iotVisResultDTO == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_12013.getCode(), ErrorCode.ERROR_12013.getMessage());
        }

        if (StringUtils.isBlank(iotVisResultDTO.getAddress())) {
            return ResultDTO.newFail(ErrorCode.ERROR_12013.getCode(), ErrorCode.ERROR_12013.getMessage());
        }

        return visResult;
    }

    @Override
    public ResultDTO<Integer> controlDevicePass(IotDevicePassQueryDTO iotDevicePassQueryDTO) {
        if (!DevicePass.COMMAND.contains(iotDevicePassQueryDTO.getCommand())) {
            return ResultDTO.newFail(ErrorCode.ERROR_12008.getCode(), ErrorCode.ERROR_12008.getMessage());
        }
        if (!DevicePass.SPEED.contains(iotDevicePassQueryDTO.getSpeed())) {
            return ResultDTO.newFail(ErrorCode.ERROR_12009.getCode(), ErrorCode.ERROR_12009.getMessage());
        }
        ResultDTO<IotDevicePassDto> judgeResult = judgeDevicePass(iotDevicePassQueryDTO);
        if (!judgeResult.isSuccess()) {
            return ResultDTO.newFail(judgeResult.getCode(), judgeResult.getMsg());
        }
        IotDevicePassDto iotDevicePassDto = judgeResult.getData();
        APSStartPTZRequest startPTZRequest = new APSStartPTZRequest();
        startPTZRequest.setCommand(iotDevicePassQueryDTO.getCommand());
        startPTZRequest.setSpeed(iotDevicePassQueryDTO.getSpeed());
        startPTZRequest.setCameraCode(iotDevicePassDto.getPassCode());
        startPTZRequest.setMqMessageType(MQMessageType.MQ_START_PTZ_REQUEST);
        String msgId = UUID.randomUUID().toString();
        startPTZRequest.setSequence(msgId);
        iotVisProducer.sendToQueue(JSON.toJSONString(startPTZRequest));
        return ResultDTO.newSuccess();
    }

    @Override
    public ResultDTO<Integer> stopControlDevicePass(IotDevicePassQueryDTO iotDevicePassQueryDTO) {
        ResultDTO<IotDevicePassDto> judgeResult = judgeDevicePass(iotDevicePassQueryDTO);
        if (!judgeResult.isSuccess()) {
            return ResultDTO.newFail(judgeResult.getCode(), judgeResult.getMsg());
        }
        IotDevicePassDto iotDevicePassDto = judgeResult.getData();
        //停止转动
        APSStopPTZRequest stopPTZRequest = new APSStopPTZRequest();
        stopPTZRequest.setCameraCode(iotDevicePassDto.getPassCode());
        stopPTZRequest.setMqMessageType(MQMessageType.MQ_STOP_PTZ_REQUEST);
        iotVisProducer.sendToQueue(JSON.toJSONString(stopPTZRequest));
        return ResultDTO.newSuccess();
    }

    /**
     * 发消息获取VIS视频地址
     *
     * @param iotDevicePassQueryDTO
     * @return
     */
    private ResultDTO<IotVisResultDTO> getVisResult(IotDevicePassQueryDTO iotDevicePassQueryDTO, String methodCode) {
        String msgId = UUID.randomUUID().toString();
        String toVisContent = "";
        if (IotVisParam.GET_LIVE_STREAM.equals(methodCode)) {
            APSGetLiveStreamRequest liveStreamRequest = new APSGetLiveStreamRequest();
            liveStreamRequest.setSequence(msgId);
            liveStreamRequest.setExpireTime(Constants.HOUR);
            liveStreamRequest.setCameraCode(iotDevicePassQueryDTO.getPassCode());
            liveStreamRequest.setMqMessageType(MQMessageType.MQ_GET_LIVE_STREAM_REQUEST);
            toVisContent = JSON.toJSONString(liveStreamRequest);
        } else if (IotVisParam.GET_PLAY_BACK.equals(methodCode)) {
            APSGetPlayBackRequest playBackRequest = new APSGetPlayBackRequest();
            playBackRequest.setCameraCode(iotDevicePassQueryDTO.getPassCode());
            Long startTime = iotDevicePassQueryDTO.getStartTime() / 1000;
            playBackRequest.setStartTime(startTime.intValue());
            Long endTime = iotDevicePassQueryDTO.getEndTime() / 1000;
            playBackRequest.setEndTime(endTime.intValue());
            playBackRequest.setSequence(msgId);
            playBackRequest.setMqMessageType(MQMessageType.MQ_GET_PLAY_BACK_REQUEST);
            toVisContent = JSON.toJSONString(playBackRequest);
        }

        iotVisProducer.sendToQueue(toVisContent);
        Long nowTime = System.currentTimeMillis();
        while (true) {
            String result = cache.getObject(msgId, String.class);
            if (StringUtils.isNotBlank(result)) {
                IotVisResultDTO iotVisResultDTO = JSON.parseObject(result, IotVisResultDTO.class);
                return ResultDTO.newSuccess(iotVisResultDTO);
            }
            Long time = System.currentTimeMillis() - nowTime;
            if (time > Constants.GET_VIS_TIMEOUT) {
                return ResultDTO.newFail(ErrorCode.ERROR_12006.getCode(), ErrorCode.ERROR_12006.getMessage());
            }
        }
    }


    /**
     * 判断通道权限以及是否是视频设备通道
     *
     * @return
     */
    private ResultDTO<IotDevicePassDto> judgeDevicePass(IotDevicePassQueryDTO iotDevicePassQueryDto) {
        Long iotProjectId = iotDevicePassQueryDto.getIotProjectId();
        Long iotDevicePassId = iotDevicePassQueryDto.getIotDevicePassId();
        ResultDTO<Integer> bizReturn = iotDeviceTypeService.judgeProject(iotProjectId,
                iotDevicePassQueryDto.getCreateOperatorId(), iotDevicePassQueryDto.getRoleCode());
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }

        //判断是否是视频通道
        IotDevicePassDto iotDevicePassDto = devicePassDao.queryDevicePassByProjectId(iotDevicePassId, iotProjectId, DevicePass.PASS_TYPE_CODE);
        if (iotDevicePassDto == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_12005.getCode(), ErrorCode.ERROR_12005.getMessage());
        }
        return ResultDTO.newSuccess(iotDevicePassDto);
    }

    @Override
    public ResultDTO<List<IotDevicePassDto>> getIotDevicePassList(Long iotDeviceId, Long iotPassTypeId) {
        return ResultDTO.newSuccess(devicePassDao.queryIotDevicePassByIotDeviceIdAndIotPassTypeId(iotDeviceId, iotPassTypeId));
    }
}
