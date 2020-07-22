package allthings.iot.util.rocketmq.msg;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  IRocketMsgListener
 * @CreateDate :  2017/11/08
 * @Description :  消息监听
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public interface IRocketMsgListener {
    /**
     * 成功回调
     *
     * @param messages
     * @throws Exception
     */
    void onSuccess(List<RocketMsg> messages) throws Exception;

    /**
     * 失败回调
     *
     * @param throwable
     */
    void onFailed(Throwable throwable);
}
