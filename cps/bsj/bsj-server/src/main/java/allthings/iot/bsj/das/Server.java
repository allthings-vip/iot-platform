package allthings.iot.bsj.das;

import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.stereotype.Component;
import allthings.iot.das.simple.tcp.AbstractSimpleTcpServer;

import java.nio.ByteOrder;

/**
 * @author :  luhao
 * @FileName :  Server
 * @CreateDate :  2017/12/28
 * @Description : 服务端帧解码器
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class Server extends AbstractSimpleTcpServer {
    @Override
    protected ByteToMessageDecoder getFrameDecoder() {
        return new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 3, 2, 0, 0, true);
    }
}
