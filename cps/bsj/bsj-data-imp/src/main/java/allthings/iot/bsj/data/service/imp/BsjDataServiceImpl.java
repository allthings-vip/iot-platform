package allthings.iot.bsj.data.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import allthings.iot.bsj.data.BsjDataConfig;
import allthings.iot.bsj.data.service.IBsjDataService;
import allthings.iot.bsj.data.service.dto.BsjCmdDto;
import allthings.iot.common.msg.DeviceMsg;
import allthings.iot.common.usual.DeviceTypes;
import allthings.iot.dms.IDeviceManageService;

import javax.annotation.PostConstruct;

/**
 * @author :  luhao
 * @FileName :  BsjDataServiceImpl
 * @CreateDate :  2018/1/11
 * @Description : bsj 2929协议数据服务
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Service("bsjData")
public class BsjDataServiceImpl implements IBsjDataService {

    private static final Logger LOG = LoggerFactory.getLogger(BsjDataServiceImpl.class);

    @Autowired
    private BsjDataConfig bsjDataConfig;

    private IDeviceManageService dms;

    @PostConstruct
    public void init() {
        dms = bsjDataConfig.getDms();
    }

    @Override
    public void sendCmd(BsjCmdDto bsjCmdDto) throws Exception {
        DeviceMsg deviceMsg = DeviceMsg.newMsgFromCloud(bsjCmdDto.getMsgCode());
        deviceMsg.setTargetDeviceId(bsjCmdDto.getDeviceId());
        deviceMsg.setTargetDeviceType(DeviceTypes.DEVICE_TYPE_BSJ);

        deviceMsg.setParams(bsjCmdDto.getParams());

        dms.sendMsg(deviceMsg);
    }
}
