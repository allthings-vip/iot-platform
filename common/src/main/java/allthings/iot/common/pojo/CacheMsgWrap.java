package allthings.iot.common.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.msg.MsgType;
import allthings.iot.common.msg.MsgUtil;

import java.io.Serializable;

/**
 * Created by sylar on 16/5/29.
 */

/**
 * @author :  sylar
 * @FileName :  CacheMsgWrap
 * @CreateDate :  2017/11/08
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
public class CacheMsgWrap implements Serializable {

    private int msgTypeValue;
    private String msgContent;

    public CacheMsgWrap() {
    }

    public CacheMsgWrap(IMsg msg) {
        this.msgTypeValue = msg.getMsgType().getValue();
        this.msgContent = JSON.toJSONString(msg);
    }


    @JSONField(serialize = false, deserialize = false)
    public MsgType getMsgType() {
        return MsgType.valueOf(msgTypeValue);
    }

    @JSONField(serialize = false, deserialize = false)
    public IMsg getMsg() {
        MsgType msgType = getMsgType();
        Class<IMsg> clazz = MsgUtil.classOf(msgType);
        return JSON.parseObject(msgContent, clazz);
    }

    public int getMsgTypeValue() {
        return msgTypeValue;
    }

    public void setMsgTypeValue(int msgTypeValue) {
        this.msgTypeValue = msgTypeValue;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

}
