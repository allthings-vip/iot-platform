package allthings.iot.gbt32960.das;

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
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class Server extends AbstractSimpleTcpServer {
    @Override
    protected ByteToMessageDecoder getFrameDecoder() {
        return new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 21, 2, 1, 0, true);
    }
}
