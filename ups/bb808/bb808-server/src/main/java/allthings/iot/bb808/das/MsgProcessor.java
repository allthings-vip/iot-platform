package allthings.iot.bb808.das;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import allthings.iot.bb808.common.MsgCode;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.bb808.das.packet.AbstractPacket;
import allthings.iot.bb808.das.packet.Packet0x8100;
import allthings.iot.bb808.das.util.UniversalResponseUtil;
import allthings.iot.common.msg.DeviceMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.das.common.bean.MsgSender;
import allthings.iot.das.common.core.InboundMsgProcessor;

/**
 * @author :  luhao
 * @FileName :  MsgProcessor
 * @CreateDate :  2017/12/21
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
    ApplicationContext ctx;

    @Autowired
    MsgSender msgSender;

    @Override
    public boolean processInboundMsg(IMsg msg) {
        // 构造回应给设备，先设置source device（即云平台，云平台本身也认为是一个设备）和 target device，再设置msgCode
        DeviceMsg res = DeviceMsg.newMsgFromCloud();
        res.setTargetDevice(msg.getSourceDeviceType(), msg.getSourceDeviceId());

        //默认都上报。除非某些特定指令不需要让上层 DMS 知道
        boolean needThrowUp = true;

        String msgCode = msg.getMsgCode();

        // 根据设备指令码,分别处理,若需要回应指令,须设置 res 的指令码与相应参数
        AbstractPacket resBody = null;
        switch (msgCode) {
            case MsgCode.MSG_REGISTER:
                res.setMsgCode(MsgCode.MSG_REGISTER_RES);

                resBody = new Packet0x8100();
                resBody.put(MsgParam.ACK_RUNNING_NO, msg.get(MsgParam.RUNNING_NO));
                resBody.put(MsgParam.ACK_RESULT, 0);
                resBody.put(MsgParam.JIAN_QUAN, msg.get(MsgParam.PHONE_NO));
                break;

            case MsgCode.MSG_LOGIN:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;

            case MsgCode.MSG_DEV_CANCEL:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;

            case MsgCode.MSG_HEARTBEAT:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;

            case MsgCode.MSG_POSITION_UPLOAD:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;

            case MsgCode.MSG_DRIVER_IC:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;

            case MsgCode.MSG_POSITION_BATCH_UPLOAD:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;

            case MsgCode.MSG_MULTIMEDIA_UPLOAD:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;

            case MsgCode.MSG_TRANSPARENT_TRANSMISSION_UP:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;

            case MsgCode.MSG_SEARCH_TERMINAL_PARAMETER_RES:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_SEARCH_TERMINAL_ATTRIBUTE_RES:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_EVENT_UPLOAD:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_QES_ANSWER:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_INFO_SUB:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_CONTROL_RES:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_CAN_DATA_UPLOAD:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_RECORDER_DATA_UPLOAD:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;

            case MsgCode.MSG_WAYBILL_UPLOAD:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_MULTIMEDIA_SEARCH_RES:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_GZIP_DATA_UPLOAD:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_MULTIMEDIA_EVENT_UPLOAD:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_DEV_RSA:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_UPGRADE_RESULT:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;
            case MsgCode.MSG_TAKE_PHOTO_RES:
                res.setMsgCode(MsgCode.MSG_PLAT_GENERAL_RES);

                resBody = UniversalResponseUtil.generateGeneralAckOfPlat(msg);
                break;

            default:
                break;
        }

        if (!Strings.isNullOrEmpty(res.getMsgCode())) {
            // 请求报文的消息头
            res.getParams().putAll(msg.getParams());

            // 回应报文的消息体
            if (resBody != null) {
                res.getParams().putAll(resBody.getParamMap());
            }
            msgSender.sendMsg(res);
        }

        return needThrowUp;
    }

}
