package tf56.cloud.iot.store.toilet.common.dic;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Enums;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.Serializable;

/**
 * @author :  sylar
 * @FileName :  FactorInfo
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class FactorInfo implements Serializable {

    /**
     * 因子模板编码
     */
    private String id;

    /**
     * 因子名称
     */
    private String name;

    /**
     * 因子值的字节长度
     */
    private int len;

    /**
     * 因子值类型
     */
    private String type;

    /**
     * 系数,结果须乘以系数
     */
    private Float k;

    /**
     * 因子描述
     */
    private String desc;

    private DataTypeEnum dataTypeEnum;

    private static final String BOOLEAN_TYPE = "boolean";
    private static final String INTEGER_TYPE = "integer";
    private static final String BYTE_ARRAY_TYPE = "byte[]";
    private static final String STRINGS_TYPE = "strings";
    private static final String ARRAY_TYPE = "array";

    void checkValid() {

        Preconditions.checkState(!Strings.isNullOrEmpty(type), "param type 为空");
        if (Objects.equal(type.toLowerCase(), BOOLEAN_TYPE)) {
            type = "bool";
        }
        if (Objects.equal(type.toLowerCase(), INTEGER_TYPE)) {
            type = "int";
        }
        if (Objects.equal(type.toLowerCase(), BYTE_ARRAY_TYPE)) {
            type = "bytes";
        }
        if (Objects.equal(type.toLowerCase(), STRINGS_TYPE)) {
            type = "string";
        }
        if (Objects.equal(type.toLowerCase(), ARRAY_TYPE)) {
            type = "list";
        }

        boolean isValid = Enums.getIfPresent(DataTypeEnum.class, type.toUpperCase()).isPresent();
        Preconditions.checkState(isValid, "param type 无效");
        dataTypeEnum = DataTypeEnum.valueOf(type.toUpperCase());

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getK() {
        return k;
    }

    public void setK(Float k) {
        this.k = k;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @JSONField(serialize = false, deserialize = false)
    public DataTypeEnum getDataTypeEnum() {
        return dataTypeEnum;
    }

}
