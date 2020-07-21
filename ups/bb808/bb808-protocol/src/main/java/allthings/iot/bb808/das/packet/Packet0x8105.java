package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;
import allthings.iot.util.misc.StringUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x8105
 * @CreateDate :  2017/12/21
 * @Description : 终端控制
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8105 extends AbstractPacket {
    public Packet0x8105(){
        super("8105");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        //控制字
        byte cw = Byte.parseByte(String.valueOf(super.getParamMap().get(MsgParam.COMMAND_WORD)));
        if(cw == 1){
            buf.writeByte(cw);
            wirelessUpgrade(buf);
        }else if(cw == 2){
            buf.writeByte(cw);
            ConnectServer(buf);
        }else if(cw >= 3 && cw <=7){
            buf.writeByte(cw);
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }

    /**
     * 无线升级
     * @param buf
     */
    private void wirelessUpgrade(ByteBuf buf) {
        String tmp = String.valueOf(super.getParamMap().get(MsgParam.URL));
        if(isNotBlank(tmp)){
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.DIAL_POINT_NAME));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.DIAL_POINT_USERNAME));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.DIAL_POINT_PWD));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.ADDR));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.TCP_PORT));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_UTF8));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.UDP_PORT));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_UTF8));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.MAKER_ID));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_UTF8));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.DEV_HARDWARE_VERSION));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.DEV_FIRMWARE_VERSION));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.CONNECT_TIME_LIMIT));
        if (isNotBlank(tmp)){
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_UTF8));
        }
    }

    /**
     * 控制终端连接指定服务器
     * @param buf
     */
    private void ConnectServer(ByteBuf buf) {
        String tmp = String.valueOf(super.getParamMap().get(MsgParam.CONNECT_CONTROL));
        if (isNotBlank(tmp)){
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_UTF8));
            if ("1".equals(tmp)){
                return;
            }
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.PLAT_JIANQUAN));
        if (isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.DIAL_POINT_NAME));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.DIAL_POINT_USERNAME));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.DIAL_POINT_PWD));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.ADDR));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_NAME_GBK));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.TCP_PORT));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_UTF8));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.UDP_PORT));
        if(isNotBlank(tmp)) {
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_UTF8));
        }
        buf.writeBytes(";".getBytes(ByteUtils.CHARSET_UTF8));

        tmp = String.valueOf(super.getParamMap().get(MsgParam.CONNECT_TIME_LIMIT));
        if (isNotBlank(tmp)){
            buf.writeBytes(tmp.getBytes(ByteUtils.CHARSET_UTF8));
        }
    }

    private boolean isNotBlank(String str){
        return StringUtils.isNotBlank(str) && (!("null".equals(str)));
    }
}
