package allthings.iot.bsj.das;

import org.springframework.stereotype.Component;
import allthings.iot.common.msg.IMsg;
import allthings.iot.das.common.core.InboundMsgProcessor;

/**
 * @author :  luhao
 * @FileName :  MsgProcessor
 * @CreateDate :  2017/12/29
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
