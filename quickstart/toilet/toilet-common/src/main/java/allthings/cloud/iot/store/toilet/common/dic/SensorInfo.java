package tf56.cloud.iot.store.toilet.common.dic;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  MqttConst
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
public class SensorInfo {

    private String id;
    private List<FactorInfo> factors;

    public boolean checkValid() {
        try {
            if (factors != null) {
                for (FactorInfo factor : factors) {
                    factor.checkValid();
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @JSONField(serialize = false, deserialize = false)
    public int getFactorCount() {
        if (factors != null) {
            return factors.size();
        } else {
            return 0;
        }
    }

    @JSONField(serialize = false, deserialize = false)
    public int getByteLength() {
        int len = 0;
        if (factors != null) {
            for (FactorInfo factor : factors) {
                len += factor.getLen();
            }
        }
        return len;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FactorInfo> getFactors() {
        return factors;
    }

    public void setFactors(List<FactorInfo> factors) {
        this.factors = factors;
    }
}
