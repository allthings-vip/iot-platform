package allthings.iot.geely.ns.das.business.api;

/**
 * @author :  luhao
 * @FileName :  IGeelyNsDasService
 * @CreateDate :  2018/4/23
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
public interface IGeelyNsDasService {
    /**
     *
     * @param vins
     * @param sign
     */
    void uploadVins(String[] vins,String sign);
    /**
     *
     * @param encryptMsg
     * @param sign
     */
    void pushAlarm(String encryptMsg,String sign);

}
