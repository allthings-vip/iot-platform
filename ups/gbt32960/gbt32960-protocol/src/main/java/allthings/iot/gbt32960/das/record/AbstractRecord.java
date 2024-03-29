package allthings.iot.gbt32960.das.record;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  AbstractRecord
 * @CreateDate :  2018/2/9
 * @Description : 子指令的抽象类
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractRecord {
    /**
     * 消息编码
     */
    private String recordId;

    /**
     * 消息数据体
     */
    private byte[] messageBody = new byte[0];

    /**
     * 参数集
     */
    private Map<String, Object> paramMap = new HashMap<>();


    public AbstractRecord(String recordId) {
        super();
        this.recordId = recordId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }


    public void put(String paramKey, Object paramValue) {
        paramMap.put(paramKey, paramValue);
    }

    public <T> T get(String paramKey) {
        if (!paramMap.containsKey(paramKey)) {
            return null;
        }

        return (T) paramMap.get(paramKey);
    }

    /**
     * 解码子类型
     *
     * @param byteBuf
     * @throws Exception
     */
    public abstract void unpack(ByteBuf byteBuf) throws Exception;
}
