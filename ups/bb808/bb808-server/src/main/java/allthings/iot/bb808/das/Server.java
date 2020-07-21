package allthings.iot.bb808.das;

import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.stereotype.Component;
import allthings.iot.das.simple.tcp.AbstractSimpleTcpServer;

/**
 * @author :  luhao
 * @FileName :  Server
 * @CreateDate :  2017/12/21
 * @Description :
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
        return new Bb808SplitDecoder();
    }
}
