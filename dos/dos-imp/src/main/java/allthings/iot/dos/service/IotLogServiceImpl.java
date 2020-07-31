package allthings.iot.dos.service;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dms.dto.MsgLogDto;
import allthings.iot.dms.ui.service.IDmsFeignClient;
import allthings.iot.dos.api.IotDeviceService;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.api.IotLogService;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dao.IotDeviceDao;
import allthings.iot.dos.dao.IotProtocolDao;
import allthings.iot.dos.dto.IotDeviceSimpleDTO;
import allthings.iot.dos.dto.IotLogContentDTO;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotProtocolQueryDTO;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static allthings.iot.common.msg.MsgType.DasConnection;
import static allthings.iot.common.msg.MsgType.DeviceAlarm;
import static allthings.iot.common.msg.MsgType.DeviceConnection;
import static allthings.iot.common.msg.MsgType.DeviceData;
import static allthings.iot.common.msg.MsgType.DeviceEvent;
import static allthings.iot.common.msg.MsgType.DeviceInfo;
import static allthings.iot.common.msg.MsgType.DeviceLog;
import static allthings.iot.common.msg.MsgType.DeviceOta;
import static allthings.iot.common.msg.MsgType.Undefine;

/**
 * @author :  luhao
 * @FileName :  IotLogBizImpl
 * @CreateDate :  2018-5-30
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
@Service("iotLogService")
public class IotLogServiceImpl implements IotLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotLogServiceImpl.class);
    private static final Map<String, String> logTypeMap = Maps.newHashMap();

    static {
        logTypeMap.put(Undefine.name(), "未定义");
        logTypeMap.put(DasConnection.name(), "连接服务节点");
        logTypeMap.put(DeviceConnection.name(), "设备连接");
        logTypeMap.put(DeviceAlarm.name(), "报警");
        logTypeMap.put(DeviceData.name(), "数据上报");
        logTypeMap.put(DeviceEvent.name(), "事件");
        logTypeMap.put(DeviceInfo.name(), "设备信息");
        logTypeMap.put(DeviceLog.name(), "设备日志");
        logTypeMap.put(DeviceOta.name(), "设备升级");
    }

    @Autowired
    private IDmsFeignClient dmsFeignClient;
    @Autowired
    private IotDeviceDao iotDeviceDao;
    @Autowired
    private IotProtocolDao iotProtocolDao;
    @Autowired
    private IotDeviceService iotDeviceService;
    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<PageResult<IotLogDTO>> getMsgLogs(String deviceCode, String msgType, long beginDatetime, long
            endDatetime, int pageIndex, int pageSize, Long iotProjectId, Long userId, String roleCode) {
        try {


            ResultDTO<Integer> judge = iotDeviceTypeService.judgeProject(iotProjectId, userId, roleCode);
            if (!judge.isSuccess()) {
                return ResultDTO.newFail(ErrorCode.ERROR_8014.getCode(),
                        ErrorCode.ERROR_8014.getMessage());
            }

            Long time = System.currentTimeMillis();
            System.out.println("----------------------------------------查询设备日志----------" + time);
            List<IotProtocolQueryDTO> dtoList = iotProtocolDao.getAllByIsDeleted(false);
            String deviceType = "";
            for (IotProtocolQueryDTO dto : dtoList) {
                if (deviceCode.startsWith(dto.getProtocolCode())) {
                    deviceType = dto.getProtocolCode();
                    deviceCode = deviceCode.replaceFirst(dto.getProtocolCode(), "");
                    break;
                }
            }

            if (StringUtils.isEmpty(deviceType)) {
                IotDeviceSimpleDTO simpleDTO = iotDeviceDao.getIotDeviceSimpleByDeviceCode(deviceCode, iotProjectId);
                if (simpleDTO == null) {
                    LOGGER.error("设备编码{}不存在", deviceCode);
                    return ResultDTO.newFail("设备编码不存在");
                } else {
                    deviceType = simpleDTO.getProtocolCode();
                }
            }
            System.out.println(
                    "----------------------------------------查询设备dms日志----------" + (System.currentTimeMillis() - time));
            //TODO:deviceId
            String deviceId = iotDeviceService.getDeviceIdByDeviceCode(deviceCode).getData();
            QueryResult<MsgLogDto> queryResult = dmsFeignClient.getMsgLogs(deviceType, deviceId, msgType, beginDatetime,
                    endDatetime, pageIndex, pageSize).getRet();
            System.out.println(
                    "----------------------------------------查询设备dms日志结束----------" + (System.currentTimeMillis() - time));

            if (CollectionUtils.isEmpty(queryResult.getItems())) {
                PageResult<IotLogDTO> pageResult = new PageResult<>(0, Lists.newArrayList());
                return ResultDTO.newSuccess(pageResult);
            }

            List<MsgLogDto> msgLogDtos = queryResult.getItems();
            List<IotLogDTO> iotLogDTOList = Lists.newArrayList();
            for (MsgLogDto msgLogDto : msgLogDtos) {
                IotLogDTO iotLogDTO = new IotLogDTO();
//            iotLogDTO.setInputDate(msgLogDto.getInputDate().getTime());
//            iotLogDTO.setLogContent(getLogContent(msgLogDto.getMsgContent()));
//            iotLogDTO.setUsername("");
//            iotLogDTO.setLogType(logTypeMap.get(msgLogDto.getMsgType()));
                iotLogDTOList.add(iotLogDTO);
            }

            PageResult<IotLogDTO> pageResult = new PageResult<>((int) queryResult.getRowCount(), iotLogDTOList);
            return ResultDTO.newSuccess(pageResult);
        } catch (Exception e) {
            LOGGER.error("查询设备日志异常，异常信息：", e);
            return ResultDTO.newFail("查询日志失败");
        }
    }

    private IotLogContentDTO getLogContent(String content) {
        IotLogContentDTO iotLogContentDTO = JSON.parseObject(content, IotLogContentDTO.class);
        return iotLogContentDTO;
    }
}
