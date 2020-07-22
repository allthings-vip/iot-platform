package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.das.util.DateUtil;
import allthings.iot.util.misc.ByteUtils;

import java.util.Arrays;
import java.util.Date;


/**
 * @author :  luhao
 * @FileName :  Packet0x0702
 * @CreateDate :  2017/12/21
 * @Description : 驾驶员身份信息采集上报 ;终端从业资格证IC卡插入或拔出后，自动触发本指令。收到0x8702指令后，使用本指令 应答。
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0702 extends AbstractPacket {
    private static final Logger LOGGER = LoggerFactory.getLogger(Packet0x0702.class);

    public Packet0x0702() {
        super("0702");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        short icState = buf.readUnsignedByte();
        super.put(MsgParam.IC_STATE, icState);

        byte[] tmpBytes = new byte[6];
        buf.readBytes(tmpBytes);
        String tmpStr = ByteUtils.bytesToHexString(tmpBytes);
        Date tmpDate = null;
        try {
            tmpDate = DateUtil.parse(tmpStr, "yyMMddHHmmss");
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
        }
        super.put(MsgParam.IC_OPERATE_DATE, tmpDate);

        if (icState != 0x01) {
            return;
        }

        // 以下字段在状态为 0x01 时才有效并做填充
        super.put(MsgParam.IC_READ_RESULT, buf.readUnsignedByte());

        short nameLength = buf.readUnsignedByte();
        super.put(MsgParam.IC_DRIVER_NAME_LENGTH, nameLength);

        tmpBytes = new byte[nameLength];
        buf.readBytes(tmpBytes);
        tmpStr = ByteUtils.toString(tmpBytes, 0, tmpBytes.length);
        super.put(MsgParam.IC_DRIVER_NAME, tmpStr);

        tmpBytes = new byte[20];
        buf.readBytes(tmpBytes);
        int index = tmpBytes.length - 1;
        for (; index >= 0; index--) {
            if (tmpBytes[index] != 0) {
                break;
            }
        }
        tmpBytes = Arrays.copyOfRange(tmpBytes, 0, index + 1);
        tmpStr = ByteUtils.toString(tmpBytes, 0, tmpBytes.length);
        super.put(MsgParam.IC_CODE, tmpStr);

        nameLength = buf.readUnsignedByte();
        super.put(MsgParam.IC_CA_NAME_LENGTH, nameLength);

        tmpBytes = new byte[nameLength];
        buf.readBytes(tmpBytes);
        tmpStr = ByteUtils.toString(tmpBytes, 0, tmpBytes.length);
        super.put(MsgParam.IC_CA_NAME, tmpStr);

        tmpBytes = new byte[4];
        buf.readBytes(tmpBytes);
        tmpStr = ByteUtils.toString(tmpBytes, 0, tmpBytes.length);
        tmpDate = null;
        try {
            tmpDate = DateUtil.parse(tmpStr, "yyyyMMdd");
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
        }
        super.put(MsgParam.IC_EXPIRY_DATE, tmpDate);
    }
}
