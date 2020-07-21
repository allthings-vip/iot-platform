package allthings.iot.gbt32960.das;

import org.springframework.stereotype.Component;
import allthings.iot.common.msg.IMsg;
import allthings.iot.das.common.core.InboundMsgProcessor;

/**
 * @author :  luhao
 * @FileName :  MsgProcessor
 * @CreateDate :  2018/01/29
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
public class MsgProcessor implements InboundMsgProcessor {
    @Override
    public boolean processInboundMsg(IMsg msg) {
        return true;
    }
}
