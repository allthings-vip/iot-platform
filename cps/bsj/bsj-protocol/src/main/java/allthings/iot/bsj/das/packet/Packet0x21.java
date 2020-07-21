package allthings.iot.bsj.das.packet;

import allthings.iot.common.Constant;
import allthings.iot.util.misc.ByteUtils;
import allthings.iot.util.misc.Crc16Utils;

import java.util.Arrays;

/**
 * @author :  luhao
 * @FileName :  Packet0x21
 * @CreateDate :  2018/1/8
 * @Description : 0x21 中心确认包
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x21 extends AbstractPacket {
    public Packet0x21() {
        super("21");
    }

    @Override
    public byte[] pack() {
        //主信令转换成字节数组
        byte[] msgCodeBytes = ByteUtils.hexStringToBytes(get(Constant.ATTR_MSG_CODE));
        //组装下发指令
        byte[] crcBytes = {0x29, 0x29, 0x21, 0x00, 0x05, get(Constant.ATTR_REP_CRC), msgCodeBytes[0],
                get(Constant.ATTR_CHILD_MSG_CODE)};
        //校验
        int crc = Crc16Utils.calcCrc(crcBytes);

        byte[] responseBytes = Arrays.copyOf(crcBytes, 10);
        //包尾
        responseBytes[8] = (byte) crc;
        responseBytes[9] = 0x0D;

        return responseBytes;
    }
}
