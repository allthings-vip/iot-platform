package allthings.iot.des.dto.query;

import java.io.Serializable;

/**
 * @author tyf
 * @date 2019/03/15 11:08
 */
public class IotDesEventData implements Serializable {

    /**
     * 数据编码
     */
    private String dataCode;

    /**
     * 数据名称
     */
    private String dataName;

    /**
     * 数据值
     */
    private String dataValue;

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    @Override
    public String toString() {
        return "IotDesEventData{" +
                "dataCode='" + dataCode + '\'' +
                ", dataName='" + dataName + '\'' +
                ", dataValue='" + dataValue + '\'' +
                '}';
    }
}
