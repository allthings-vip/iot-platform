package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x8302
 * @CreateDate :  2017/12/21
 * @Description :  提问下发
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8302 extends AbstractPacket {

    public Packet0x8302() {
        super("8302");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(super.get(MsgParam.QES_FLAG));
        String qesContent = super.get(MsgParam.QES_CONTENT);
        byte[] questionLen = qesContent.getBytes(ByteUtils.CHARSET_NAME_GBK);
        buf.writeByte(questionLen.length);
        buf.writeBytes(questionLen);

        List<Map<String, Object>> answerList = (List<Map<String, Object>>) super.getParamMap().get(MsgParam
                .ANSWER_LIST);
        for (Map<String, Object> answer : answerList) {
            buf.writeByte((int) answer.get(MsgParam.ANSWER_ID));
            String answerContent = (String) answer.get(MsgParam.ANSWER_CONTENT);
            byte[] answerLen = answerContent.getBytes(ByteUtils.CHARSET_NAME_GBK);
            buf.writeShort(answerLen.length);
            buf.writeBytes(answerLen);
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }

}
