package tf56.cloud.iot.store.toilet.common.dic;

/**
 * @author :  sylar
 * @FileName :  FactorNodes
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
public class FactorNode {
    private final static int AREA_PUBLIC = 1;
    private final static int AREA_MALE = 2;
    private final static int AREA_FEMALE = 3;


    public String id;
    public int areaFlag;
    public int index;
    public Object value;

    public FactorNode(String id, int areaFlag) {
        this.id = id;
        this.areaFlag = areaFlag;
    }

    private static String toAreaStr(int areaFlag) {
        switch (areaFlag) {
            case AREA_PUBLIC:
                return "P";
            case AREA_MALE:
                return "M";
            case AREA_FEMALE:
                return "F";
            default:
                return "U";
        }
    }

    public String getFactorId() {
        return SensorInfoManager.generateFactorId(id, areaFlag);
    }

    public String getFullFactorId() {
        return SensorInfoManager.generateFactorId(id, areaFlag, index);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getArea() {
        return areaFlag;
    }

    public void setArea(int areaFlag) {
        this.areaFlag = areaFlag;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
