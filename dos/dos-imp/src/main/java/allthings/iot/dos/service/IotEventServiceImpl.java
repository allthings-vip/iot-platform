package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.query.IotDeviceEventDTO;
import allthings.iot.dos.api.IotEventService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.dms.dto.DeviceEventDto;
import allthings.iot.dms.ui.service.IDmsFeignClient;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotEventBizImpl
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
@Service("iotEventService")
public class IotEventServiceImpl implements IotEventService {
    @Autowired
    private IDmsFeignClient dms;

    /**
     * 查询事件信息
     *
     * @param deviceCode
     * @param eventCodes
     * @param beginTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<PageResult<IotDeviceEventDTO>> getDeviceEventsByDeviceId(String deviceCode,
                                                                              List<String> eventCodes,
                                                                              long beginTime,
                                                                              long endTime,
                                                                              int pageIndex,
                                                                              int pageSize) {
        QueryResult<DeviceEventDto> queryResult = dms.getDeviceEventsByDeviceId(deviceCode,
                eventCodes.toArray(new String[eventCodes.size()]), beginTime, endTime, pageIndex, pageSize).getRet();

        List<DeviceEventDto> eventList = queryResult.getItems();
        List<IotDeviceEventDTO> iotEventList = Lists.newArrayList();
        if (eventList.size() > 0) {
            for (DeviceEventDto deviceEventDto : eventList) {
                IotDeviceEventDTO iotDeviceEventDTO =
                        new IotDeviceEventDTO(deviceEventDto.getEventDesc(), deviceEventDto.getDeviceId(),
                                deviceEventDto.getEventCode(), deviceEventDto.getInputDate().getTime());
                iotEventList.add(iotDeviceEventDTO);
            }
        }

        PageResult<IotDeviceEventDTO> pageResult = new PageResult<>((int) queryResult.getRowCount(), iotEventList);
        return ResultDTO.newSuccess(pageResult);
    }
}
