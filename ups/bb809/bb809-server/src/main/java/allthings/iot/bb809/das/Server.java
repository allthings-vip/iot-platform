package allthings.iot.bb809.das;

import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.stereotype.Component;
import allthings.iot.das.simple.tcp.AbstractSimpleTcpServer;

/**
 * @author :  luhao
 * @FileName :  Server
 * @CreateDate :  2018/3/13
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
@Component
public class Server extends AbstractSimpleTcpServer {
    @Override
    protected ByteToMessageDecoder getFrameDecoder() {
        return new Bb809FrameDecoder();
    }
}
