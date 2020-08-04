package allthings.iot.dos.open.security.oauth2;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.open.IotOauth2Api;
import allthings.iot.dos.dto.oauth2.BaseClientDTO;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;


/**
 * @author :  luhao
 * @FileName :  com.tf56.iot.dos.security.oauth2.RpcClientDetailsService
 * @CreateDate :  2018/4/24
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
public class RpcClientDetailsService implements ClientDetailsService {
    private IotOauth2Api iotOauth2Api;

    public RpcClientDetailsService(IotOauth2Api iotOauth2Api) {
        this.iotOauth2Api = iotOauth2Api;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ResultDTO<BaseClientDTO> bizReturn = iotOauth2Api.loadClientByClientId(clientId);
        if (!bizReturn.isSuccess()) {
            throw new NoSuchClientException("查询不到该ClientId");
        }

        BaseClientDTO dto = bizReturn.getData();
        if (dto == null) {
            throw new NoSuchClientException("查询不到该ClientId");
        }

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(dto.getClientId());
        clientDetails.setClientSecret(dto.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(dto.getAccessTokenValiditySeconds());
        clientDetails.setRefreshTokenValiditySeconds(dto.getRefreshTokenValiditySeconds());
        clientDetails.setAdditionalInformation(dto.getAdditionalInformation());
        clientDetails.setAutoApproveScopes(dto.getAutoApproveScopes());
        clientDetails.setScope(dto.getScope());

        return clientDetails;
    }
}
