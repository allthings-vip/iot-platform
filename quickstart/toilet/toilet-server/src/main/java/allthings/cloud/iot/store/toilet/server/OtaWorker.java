package tf56.cloud.iot.store.toilet.server;

import tf56.cloud.iot.store.toilet.common.protolcol.MsgCodes;
import tf56.cloud.iot.store.toilet.common.protolcol.MsgParams;
import allthings.iot.common.msg.DeviceMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.usual.DeviceTypes;
import allthings.iot.das.common.ota.AbstractOtaWorker;
import allthings.iot.das.common.ota.OtaSendType;
import org.springframework.stereotype.Component;

/**
 * @author :  sylar
 * @FileName :  OtaWorker
 * @CreateDate :  2017/11/08
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
public class OtaWorker extends AbstractOtaWorker {

    @Override
    public String getDeviceType() {
        return DeviceTypes.DEVICE_TYPE_VTXGC;
    }

    @Override
    public OtaSendType getSendType() {
        return OtaSendType.LastPacketResponse;
    }

    @Override
    public int getMaxFrameLength() {
        return 1024;
    }

    @Override
    protected IMsg buildEachPacketMsg(String deviceId, int packetCount, int packetIndex, byte[] eachPacketData) {
        DeviceMsg msg = DeviceMsg.newMsgFromCloud(String.valueOf(MsgCodes.OTA_SEND), getDeviceType(), deviceId);

        msg.put(MsgParams.OTAPACKETCOUNT, packetCount);
        msg.put(MsgParams.OTACURRENTPACKETINDEX, packetIndex);
        msg.put(MsgParams.OTAPACKETDATA, eachPacketData);
        return msg;
    }
}
