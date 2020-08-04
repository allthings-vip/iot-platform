package allthings.iot.dos.open.oauth2;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dao.oauth2.IotOauthClientDetailsDao;
import allthings.iot.dos.dto.oauth2.BaseClientDTO;
import allthings.iot.dos.dto.oauth2.GrantedAuthorityDTO;
import allthings.iot.dos.model.oauth2.IotOauthClientDetails;
import allthings.iot.dos.oauth2.api.IotOauthClientDetailsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author :  luhao
 * @FileName :  IotOauthClientDetailsServiceImpl
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
@Service
public class IotOauthClientDetailsServiceImpl implements IotOauthClientDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotOauthClientDetailsServiceImpl.class);
    @Autowired
    private IotOauthClientDetailsDao iotOauthClientDetailsDao;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public ResultDTO<?> saveIotOauthClientDetails(BaseClientDTO baseClientDTO) throws Exception {
        IotOauthClientDetails clientDetails = new IotOauthClientDetails();
        clientDetails.setClientId(baseClientDTO.getClientId());
        clientDetails.setClientSecret(baseClientDTO.getClientSecret());
        clientDetails.setAccessTokenValidity(baseClientDTO.getAccessTokenValiditySeconds());
        clientDetails.setRefreshTokenValidity(baseClientDTO.getRefreshTokenValiditySeconds());

        clientDetails.setAdditionalInformation(mapper.writeValueAsString(baseClientDTO.getAdditionalInformation()));
        clientDetails.setAuthorizedGrantTypes(baseClientDTO.getAuthorizedGrantTypes() != null ? StringUtils
                .collectionToCommaDelimitedString(baseClientDTO.getAuthorizedGrantTypes()) : null);
        clientDetails.setAuthorities(baseClientDTO.getAuthorities() != null ? mapper.writeValueAsString(baseClientDTO
                .getAuthorities()) : null);
        clientDetails.setResourceIds(baseClientDTO.getResourceIds() != null ?
                StringUtils.collectionToCommaDelimitedString(baseClientDTO
                        .getResourceIds()) : null);
        clientDetails.setWebServerRedirectUri(baseClientDTO.getRegisteredRedirectUris() != null ? StringUtils
                .collectionToCommaDelimitedString(baseClientDTO.getRegisteredRedirectUris()) : null);
        clientDetails.setScope(baseClientDTO.getScope() != null ?
                StringUtils.collectionToCommaDelimitedString(baseClientDTO
                        .getScope()) : null);
        clientDetails.setAutoApprove(getAutoApproveScopes(baseClientDTO));

        iotOauthClientDetailsDao.saveAndFlush(clientDetails);

        return ResultDTO.newSuccess();
    }

    @Override
    public ResultDTO<?> updateIotOauthClientDetails(String clientId) throws Exception {
        String clientSecret = RandomStringUtils.randomAlphanumeric(32);
        iotOauthClientDetailsDao.updateClientSecret(clientSecret, clientId);
        return ResultDTO.newSuccess(clientSecret);
    }

    @Override
    public ResultDTO<BaseClientDTO> getIotOauthClientDetailsByClientId(String clientId) throws Exception {
        IotOauthClientDetails clientDetails = iotOauthClientDetailsDao.selectClientDetails(clientId);
        if (clientDetails == null) {
            LOGGER.info("client detail is null clientId: {}", clientId);
            return ResultDTO.newSuccess();
        }

        BaseClientDTO baseClientDTO = new BaseClientDTO();
        baseClientDTO.setClientId(clientDetails.getClientId());
        baseClientDTO.setClientSecret(clientDetails.getClientSecret());
        if (!StringUtils.isEmpty(clientDetails.getAdditionalInformation())) {
            baseClientDTO.setAdditionalInformation(mapper.readValue(clientDetails.getAdditionalInformation(),
                    Map.class));
        }
        baseClientDTO.setAuthorities((!StringUtils.isEmpty(clientDetails.getAuthorities())) ?
                mapper.readValue(clientDetails.getAuthorities(), new TypeReference<List<GrantedAuthorityDTO>>() {
                }) : null);
        baseClientDTO.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValidity());
        baseClientDTO.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValidity());
        baseClientDTO.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(clientDetails.getAutoApprove()));
        baseClientDTO.setRegisteredRedirectUris(StringUtils.commaDelimitedListToSet(clientDetails.getWebServerRedirectUri()));
        baseClientDTO.setResourceIds(StringUtils.commaDelimitedListToSet(clientDetails.getResourceIds()));
        baseClientDTO.setScope(StringUtils.commaDelimitedListToSet(clientDetails.getScope()));
        baseClientDTO.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet(clientDetails.getAuthorizedGrantTypes()));

        return ResultDTO.newSuccess(baseClientDTO);
    }

    private String getAutoApproveScopes(BaseClientDTO baseClientDTO) {
        if (baseClientDTO.isAutoApprove("true")) {
            return "true"; // all scopes autoapproved
        }
        Set<String> scopes = new HashSet<String>();
        for (String scope : baseClientDTO.getScope()) {
            if (baseClientDTO.isAutoApprove(scope)) {
                scopes.add(scope);
            }
        }
        return StringUtils.collectionToCommaDelimitedString(scopes);
    }

}
