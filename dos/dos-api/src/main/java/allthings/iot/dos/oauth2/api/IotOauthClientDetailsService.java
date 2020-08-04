package allthings.iot.dos.oauth2.api;


import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.oauth2.BaseClientDTO;

/**
 * @author :  luhao
 * @FileName :  IotOauthClientDetailsService
 * @CreateDate :  2018-11-14
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
public interface IotOauthClientDetailsService {

    /**
     * 保存client info
     *
     * @param baseClientDTO
     * @return
     */
    ResultDTO<?> saveIotOauthClientDetails(BaseClientDTO baseClientDTO) throws Exception;


    /**
     * 重置client secret
     *
     * @param clientId
     * @return
     */
    ResultDTO<?> updateIotOauthClientDetails(String clientId) throws Exception;

    /**
     * 获取client detail
     *
     * @param clientId
     * @return
     */
    ResultDTO<BaseClientDTO> getIotOauthClientDetailsByClientId(String clientId) throws Exception;

}
