package allthings.iot.das.simple;

import io.netty.channel.ChannelHandlerContext;
import allthings.iot.common.msg.IMsg;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  ISimpleMsgResolver
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public interface ISimpleMsgResolver {

    /**
     * 读取ByteBuffer数据转换成Imsg
     *
     * @param ctx 上下文
     * @param buf ByteBuffer
     * @return Imsg
     */
    List<IMsg> bufToMsg(ChannelHandlerContext ctx, ByteBuffer buf);

    /**
     * Imsg 转换成 ByteBuffer
     *
     * @param msg Imsg
     * @return ByteBuffer
     */
    ByteBuffer msgToBuf(IMsg msg);
}
