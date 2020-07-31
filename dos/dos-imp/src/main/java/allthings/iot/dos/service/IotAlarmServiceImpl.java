package allthings.iot.dos.service;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dms.dto.DeviceAlarmDto;
import allthings.iot.dms.ui.service.IDmsFeignClient;
import allthings.iot.dos.api.IotAlarmService;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.query.IotDeviceAlarmDTO;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotAlarmBizImpl
 * @CreateDate :  2018-5-12
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
@Service("iotAlarmService")
public class IotAlarmServiceImpl implements IotAlarmService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotAlarmServiceImpl.class);
    @Autowired
    private IDmsFeignClient dmsFeignClient;

    /**
     * 查询报警
     *
     * @param deviceId
     * @param alarmCodes
     * @param beginTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<QueryResult<IotDeviceAlarmDTO>> getDeviceAlarmsByDeviceId(String deviceId,
                                                                               List<String> alarmCodes,
                                                                               long beginTime, long
                                                                                       endTime, int pageIndex, int pageSize) {
        try {
            QueryResult<DeviceAlarmDto> queryResult = dmsFeignClient.getDeviceAlarmsByDeviceId(deviceId,
                    alarmCodes.toArray(new String[alarmCodes.size()]), beginTime,
                    endTime, pageIndex, pageSize).getRet();
            List<DeviceAlarmDto> alarmList = queryResult.getItems();
            List<IotDeviceAlarmDTO> iotAlarmList = Lists.newArrayList();
            if (alarmList.size() > 0) {
                for (DeviceAlarmDto deviceAlarmDto : alarmList) {
                    IotDeviceAlarmDTO iotDeviceAlarmDTO = new IotDeviceAlarmDTO(deviceAlarmDto.getAlarmDesc(),
                            deviceAlarmDto.getDeviceId(), deviceAlarmDto.getAlarmCode(), deviceAlarmDto.getInputDate()
                            .getTime());
                    iotAlarmList.add(iotDeviceAlarmDTO);
                }
            }

            QueryResult<IotDeviceAlarmDTO> lastResult = new QueryResult<>(iotAlarmList, queryResult.getRowCount());
            return ResultDTO.newSuccess(lastResult);
        } catch (Exception ee) {
            LOGGER.error("query alarm error , deviceId:{},beginTime:{},endTime:{}", deviceId, beginTime, endTime, ee);
            return ResultDTO.newFail(ErrorCode.ERROR_6000.getCode(), ErrorCode.ERROR_6000.getMessage());
        }

    }
}
