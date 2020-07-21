package allthings.iot.bb808.das.packet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Recorder0x15
 * @CreateDate :  2017/12/21
 * @Description : 采集指定的速度状态日志
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x15 extends JBT19506Packet{
    public Recorder0x15() {
        super("15");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        byte[] tmpByte = null;

        List<Map<String,Object>> speedStatusList = Lists.newArrayList();
        while(buf.readableBytes() > 0){
            Map<String, Object> speedStatusLog = Maps.newHashMap();
            speedStatusLog.put(JBT19506MsgParam.RECODER_SPEED_STATUS,buf.readUnsignedByte());

            tmpByte = new byte[6];
            buf.readBytes(tmpByte);
            speedStatusLog.put(JBT19506MsgParam.START_TIME, ByteUtils.bytesToHexString(tmpByte));
            tmpByte = new byte[6];
            buf.readBytes(tmpByte);
            speedStatusLog.put(JBT19506MsgParam.END_TIME,ByteUtils.bytesToHexString(tmpByte));
            List<Map<String,Object>> secondDatas = Lists.newArrayList();
            for(int i = 0;i< 60;i++){
                Map<String,Object> secondData = Maps.newHashMap();
                secondData.put(JBT19506MsgParam.RECODER_SPEED, buf.readByte());
                secondData.put(JBT19506MsgParam.RECODER_REFERENCE_SPEED, buf.readByte());
                secondDatas.add(secondData);
            }
            speedStatusLog.put(JBT19506MsgParam.RECODER_SEC_LIST,secondDatas);
            speedStatusList.add(speedStatusLog);
        }
        super.put(JBT19506MsgParam.SPEED_STATUS_LOG,speedStatusList);
    }
}
