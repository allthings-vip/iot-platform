package allthings.iot.bb808.das.util;

import allthings.iot.bb808.common.MsgParam;
import allthings.iot.bb808.das.packet.Packet0x8001;
import allthings.iot.common.msg.IMsg;

/**
 * @author :  luhao
 * @FileName :  UniversalResponseUtil
 * @CreateDate :  2017/12/21
 * @Description : 平台通用应答消息
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class UniversalResponseUtil {

    /**
     * 平台通用应答消息
     * @param msg
     * @return
     */
    public static Packet0x8001 generateGeneralAckOfPlat(IMsg msg) {
        Packet0x8001 resBody = new Packet0x8001();
        resBody.put(MsgParam.ACK_RUNNING_NO, msg.get(MsgParam.RUNNING_NO));
        resBody.put(MsgParam.ACK_ID, msg.get(MsgParam.MSG_TAG));
        resBody.put(MsgParam.ACK_RESULT, 0);

        return resBody;
    }
}
