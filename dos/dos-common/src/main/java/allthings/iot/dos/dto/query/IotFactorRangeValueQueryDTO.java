package allthings.iot.dos.dto.query;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author :  luhao
 * @FileName :  IotFactorRangeValueQueryDTO
 * @CreateDate :  2018-5-19
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
public class IotFactorRangeValueQueryDTO implements Serializable {
    /**
     * 因子代码
     */
    private String factorCode;

    /**
     * 因子名称
     */
    private String factorName;

    /**
     * 值列表
     */
    private List<IotFactorValueQueryDTO> data;

    public String getFactorCode() {
        return factorCode;
    }

    public void setFactorCode(String factorCode) {
        this.factorCode = factorCode;
    }

    public List<IotFactorValueQueryDTO> getData() {
        return data;
    }

    public void setData(List<IotFactorValueQueryDTO> data) {
        this.data = data;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IotFactorRangeValueQueryDTO)) {
            return false;
        }
        IotFactorRangeValueQueryDTO that = (IotFactorRangeValueQueryDTO) o;
        return Objects.equals(factorCode, that.factorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factorCode);
    }
}
