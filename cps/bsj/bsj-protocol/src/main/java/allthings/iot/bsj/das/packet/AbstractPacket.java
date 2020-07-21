package allthings.iot.bsj.das.packet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  AbstractPacket
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
public abstract class AbstractPacket {
    public static final byte[] HEAD = {0x29, 0x29};
    public static final byte[] TAIL = {0x0D};
    /**
     * 消息编码
     */
    private String packetId;

    /**
     * 消息数据体
     */
    private byte[] messageBody = new byte[0];

    /**
     * 参数集
     */
    private Map<String, Object> paramMap = new HashMap<>();


    public AbstractPacket(String packetId) {
        super();
        this.packetId = packetId;
    }

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
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
     * 解包
     */
    public void unpack(byte[] content) throws Exception {

    }

    /**
     * 组包
     */
    public byte[] pack() {
        return new byte[0];
    }
}
