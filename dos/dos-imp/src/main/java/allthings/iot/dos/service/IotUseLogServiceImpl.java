package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.dao.IotUseLogDao;
import allthings.iot.dos.dto.IotUseLogDTO;
import allthings.iot.dos.model.IotUseLog;
import allthings.iot.dos.api.IotUseLogService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: fengchangxin
 * @create: 2019-12-16 15:57
 * @description:
 **/
@Service("iotUseLogService")
public class IotUseLogServiceImpl implements IotUseLogService {
    @Autowired
    private IotUseLogDao iotUseLogDao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO saveUseLog(IotUseLogDTO iotUseLogDTO) {
        IotUseLog iotUseLog = new IotUseLog();
        iotUseLog.setClientId(Strings.nullToEmpty(iotUseLogDTO.getClientId()));
        iotUseLog.setParam(Strings.nullToEmpty(iotUseLogDTO.getParam()));
        iotUseLog.setUserIp(Strings.nullToEmpty(iotUseLogDTO.getUserIp()));
        iotUseLog.setPath(Strings.nullToEmpty(iotUseLogDTO.getPath()));
        iotUseLog.setCreateOperatorId(iotUseLogDTO.getCreateOperatorId() != null ? iotUseLogDTO.getCreateOperatorId() :
                Constants.LONG_OF_NULL);
        iotUseLog.setModifyOperatorId(iotUseLogDTO.getModifyOperatorId() != null ? iotUseLogDTO.getModifyOperatorId() :
                Constants.LONG_OF_NULL);
        iotUseLogDao.saveAndFlush(iotUseLog);
        return ResultDTO.newSuccess();
    }
}
