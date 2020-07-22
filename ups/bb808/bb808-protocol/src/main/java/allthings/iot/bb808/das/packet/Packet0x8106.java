package allthings.iot.bb808.das.packet;

import com.google.common.base.Splitter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.StringUtils;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  Packet0x8106
 * @CreateDate :  2017/12/21
 * @Description : 查询指定终端参数
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8106 extends AbstractPacket {

    public Packet0x8106() {
        super("8106");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        //查询参数总数
        buf.writeByte(super.get(MsgParam.TERMINAL_PARAMETERS_COUNT));
        //查询参数id列表
        String ids = super.get(MsgParam.TERMINAL_PARAMETERS_ID_LIST);
        if (StringUtils.isNotBlank(ids)) {
            List<String> idList = Splitter.on(";").splitToList(ids);
            for (String id : idList) {
                buf.writeInt((int) Long.parseLong(id));
            }
        }
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }
}
