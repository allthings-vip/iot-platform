package tf56.cloud.iot.store.toilet.common.dic;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import allthings.iot.util.misc.ByteUtils;

import java.io.Serializable;
import java.nio.ByteOrder;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  SensorDataWrap
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
public class SensorDataWrap implements Serializable {
    public static final ByteOrder ORDER = ByteOrder.LITTLE_ENDIAN;

    private String sensorGuid;
    private int sensorFlag;
    private boolean online;
    private List<FactorNode> nodes = Lists.newLinkedList();

    public SensorDataWrap(String sensorGuid, int sensorFlag, boolean online) {
        this(sensorGuid, sensorFlag, online, null);
    }

    public SensorDataWrap(String sensorGuid, int sensorFlag, boolean online, byte[] data) {
        this.sensorGuid = sensorGuid;
        this.sensorFlag = sensorFlag;
        this.online = online;

        if (online && data != null) {
            onDecodeSensorData(data);
        }
    }

    private static String getAreaFlagStr(int areaFlag) {
        switch (areaFlag) {
            case 1:
                return "P";
            case 2:
                return "M";
            case 3:
                return "F";
            default:
                return "U";
        }
    }

    public String getSensorGuid() {
        return sensorGuid;
    }

    public void setSensorGuid(String sensorGuid) {
        this.sensorGuid = sensorGuid;
    }

    public int getSensorFlag() {
        return sensorFlag;
    }

    public void setSensorFlag(int sensorFlag) {
        this.sensorFlag = sensorFlag;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @JSONField(serialize = false, deserialize = false)
    public List<FactorNode> getNodes() {
        return nodes;
    }

    void onDecodeSensorData(byte[] payload) {

        SensorInfo sensorInfo = SensorInfoManager.getInstance().getSensorInfoById(sensorGuid);
        List<FactorInfo> factors = sensorInfo.getFactors();
        int offset = 0;
        try {
            for (int i = 0; i < factors.size(); i++) {
                FactorNode node = new FactorNode(factors.get(i).getId(), sensorFlag);
                offset = onDecodeFactorValue(factors.get(i), node, payload, offset);
                nodes.add(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int onDecodeFactorValue(FactorInfo cfg, FactorNode node, byte[] payload, int offset) throws Exception {

        DataTypeEnum dataTypeEnum = cfg.getDataTypeEnum();
        float k = cfg.getK() != null ? cfg.getK() : 1f;

        try {
            byte[] bytes;
            switch (dataTypeEnum) {
                case BYTE:
                    node.setValue(k * payload[offset++]);
                    break;
                case BOOL:
                    node.setValue(1 == payload[offset++]);
                    break;
                case SHORT:
                    if (cfg.getLen() == 1) {
                        node.setValue(k * payload[offset++]);
                    } else {
                        node.setValue(k * ByteUtils.toInt16(payload, offset, ORDER));
                        offset += 2;
                    }
                    break;
                case INT:
                    node.setValue(k * ByteUtils.toInt32(payload, offset, ORDER));
                    offset += 4;
                    break;
                case FLOAT:
                    node.setValue(k * ByteUtils.toFloat(payload, offset, ORDER));
                    offset += 4;
                    break;
                case LONG:
                    node.setValue(k * ByteUtils.toInt64(payload, offset, ORDER));
                    offset += 8;
                    break;
                case DOUBLE:
                    node.setValue(k * ByteUtils.toDouble(payload, offset, ORDER));
                    offset += 8;
                    break;
                case BYTES:
                    bytes = new byte[cfg.getLen()];
                    System.arraycopy(payload, offset, bytes, 0, bytes.length);
                    node.setValue(bytes);
                    offset += bytes.length;
                    break;
                case STRING:
                    bytes = new byte[cfg.getLen()];
                    System.arraycopy(payload, offset, bytes, 0, bytes.length);
                    node.setValue(new String(bytes));
                    offset += bytes.length;
                    break;
                case LIST:
                    break;
                default:
                    break;
            }
            return offset;
        } catch (Exception e) {
            throw new Exception(String.format("onDecodeFactorValue Error!  guid:%s, FactorInfo:%s", sensorGuid, cfg),
                    e);
        }
    }
}
