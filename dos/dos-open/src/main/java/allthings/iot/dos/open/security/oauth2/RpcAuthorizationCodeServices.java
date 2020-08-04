package allthings.iot.dos.open.security.oauth2;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.open.IotOauth2Api;
import allthings.iot.dos.dto.oauth2.OauthCodeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class RpcAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
    private static final Log LOG = LogFactory.getLog(RpcAuthorizationCodeServices.class);
    private IotOauth2Api iotOauth2Api;

    public RpcAuthorizationCodeServices(IotOauth2Api iotOauth2Api) {
        this.iotOauth2Api = iotOauth2Api;
    }

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            OauthCodeDTO oauthCodeDTO = new OauthCodeDTO();
            oauthCodeDTO.setCode(code);
            oauthCodeDTO.setAuthentication(mapper.writeValueAsString(authentication));

            iotOauth2Api.saveAuthorization(oauthCodeDTO);
        } catch (IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failed to find access token for authentication " + authentication);
            }
        }
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        OAuth2Authentication authentication = null;
        ResultDTO<String> bizReturn = iotOauth2Api.getAuthorizationByCode(code);
        if (!bizReturn.isSuccess()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failed to find authentication for code " + code);
            }
        }

        String authenticationString = bizReturn.getData();
        if (StringUtils.isEmpty(authenticationString)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failed to find authentication for code " + code);
            }
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            authentication = mapper.readValue(authenticationString, OAuth2Authentication.class);
        } catch (IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failed to find authentication for code " + code);
            }
        }

        if (authentication != null) {
            iotOauth2Api.deleteAuthorizationByCode(code);
        }

        return authentication;
    }
}
