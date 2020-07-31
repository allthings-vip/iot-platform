package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.api.IotEventPushUrlService;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dao.IotEventPushUrlDao;
import allthings.iot.dos.dto.IotEventPushUrlDto;
import allthings.iot.dos.model.IotEventPushUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tyf
 * @date 2019/03/08 10:20
 */
@Service("iotEventPushUrlService")
public class IotEventPushUrlServiceImpl implements IotEventPushUrlService {

    @Autowired
    private IotEventPushUrlDao iotEventPushUrlDao;
    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultDTO<Long> saveIotEventPushUrl(IotEventPushUrlDto iotEventPushUrlDto) {
        Long iotProjectId = iotEventPushUrlDto.getIotProjectId();
        if (iotProjectId == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_2004.getCode(),
                    ErrorCode.ERROR_2004.getMessage());
        }
        String pushUrl = iotEventPushUrlDto.getPushUrl();
        if (StringUtils.isBlank(pushUrl)) {
            iotEventPushUrlDto.setPushUrl(StringUtils.EMPTY);
        }
        ResultDTO<Integer> bizReturn = iotDeviceTypeService.judgeProject(iotProjectId,
                iotEventPushUrlDto.getCreateOperatorId(), iotEventPushUrlDto.getRoleCode());
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        IotEventPushUrl eventPushUrl =
                iotEventPushUrlDao.getPushUrlByIotProjectId(iotEventPushUrlDto.getIotProjectId());
        if (eventPushUrl != null) {
            return updateIotEventPushUrl(iotEventPushUrlDto);
        }
        IotEventPushUrl iotEventPushUrl = new IotEventPushUrl();
        BeanUtils.copyProperties(iotEventPushUrlDto, iotEventPushUrl);
        return ResultDTO.newSuccess(iotEventPushUrlDao.saveAndFlush(iotEventPushUrl).getIotEventPushUrlId());
    }

    @Override
    public ResultDTO<IotEventPushUrlDto> getEventPushUrlByIotProjectId(IotEventPushUrlDto iotEventPushUrlDto) {
        ResultDTO<Integer> bizReturn = iotDeviceTypeService.judgeProject(iotEventPushUrlDto.getIotProjectId(),
                iotEventPushUrlDto.getCreateOperatorId(), iotEventPushUrlDto.getRoleCode());
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        IotEventPushUrl eventPushUrl =
                iotEventPushUrlDao.getPushUrlByIotProjectId(iotEventPushUrlDto.getIotProjectId());
        if (eventPushUrl == null) {
            return ResultDTO.newSuccess(null);
        }
        IotEventPushUrlDto eventPushUrlDto = new IotEventPushUrlDto();
        BeanUtils.copyProperties(eventPushUrl, eventPushUrlDto);
        return ResultDTO.newSuccess(eventPushUrlDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultDTO<Long> updateIotEventPushUrl(IotEventPushUrlDto iotEventPushUrlDto) {
        String pushUrl = iotEventPushUrlDto.getPushUrl();
        if (StringUtils.isBlank(pushUrl)) {
            iotEventPushUrlDto.setPushUrl(StringUtils.EMPTY);
        }
        Long iotProjectId = iotEventPushUrlDto.getIotProjectId();
        if (iotProjectId == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_2004.getCode(),
                    ErrorCode.ERROR_2004.getMessage());
        }
        ResultDTO<Integer> bizReturn = iotDeviceTypeService.judgeProject(iotProjectId,
                iotEventPushUrlDto.getCreateOperatorId(), iotEventPushUrlDto.getRoleCode());
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        Integer eventPushUrlId = iotEventPushUrlDao.updateEventPushUrl(iotEventPushUrlDto.getIotProjectId(),
                iotEventPushUrlDto.getPushUrl(), iotEventPushUrlDto.getModifyOperatorId());
        return ResultDTO.newSuccess(Long.valueOf(eventPushUrlId));
    }
}
