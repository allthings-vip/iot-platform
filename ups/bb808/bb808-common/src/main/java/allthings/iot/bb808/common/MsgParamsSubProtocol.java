package allthings.iot.bb808.common;


/**
 * @author :  luhao
 * @FileName :  MsgParamsSubProtocol
 * @CreateDate :  2017/12/21
 * @Description : 车载设备 - 各子协议公共参数定义
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface MsgParamsSubProtocol {
    /**
     * 子协议编码
     */
    String SUB_PROTOCOL_CODE = "subProtocolCode";

    /**
     * 主设备（一体机、部标机）的数据透传口编号、地址
     */
    String INTERFACE_ID = "interfaceId";
}
