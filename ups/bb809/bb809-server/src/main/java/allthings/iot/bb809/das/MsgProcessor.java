package allthings.iot.bb809.das;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import allthings.iot.bb809.das.protocol.MsgType;
import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.das.common.bean.MsgSender;
import allthings.iot.das.common.core.InboundMsgProcessor;

/**
 * @author :  luhao
 * @FileName :  MsgProcessor
 * @CreateDate :  2018/3/13
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
@Component
public class MsgProcessor implements InboundMsgProcessor {

    @Autowired
    MsgSender msgSender;

    @Override
    public boolean processInboundMsg(IMsg msg) {
        // 构造回应给设备，先设置source device（即云平台，云平台本身也认为是一个设备）和 target device，再设置msgCode
        DeviceDataMsg res = DeviceDataMsg.newMsgFromCloud();
        res.setTargetDevice(msg.getSourceDeviceType(), msg.getSourceDeviceId());

        // 默认都上报。除非某些特定指令不需要让上层 DMS 知道
        boolean needThrowUp = true;

        String msgCode = msg.getMsgCode();

        // 根据设备指令码,分别处理,若需要回应指令,须设置 res 的指令码与相应参数
        switch (msgCode) {
            case MsgType.UP_CONNECT_REQ:
                res.setMsgCode("" + MsgType.UP_CONNECT_RSP);
                break;
            case MsgType.UP_DISCONNECT_REQ:
                res.setMsgCode("" + MsgType.UP_DISCONNECT_RSP);
                break;
            case MsgType.UP_EXG_MSG:
                break;
            default:
                break;
        }

        if (!Strings.isNullOrEmpty(res.getMsgCode())) {
            // 请求报文的消息头
            res.setParams(msg.getParams());
            msgSender.sendMsg(res);
        }

        return needThrowUp;
    }
}
