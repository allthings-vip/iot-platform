package allthings.iot.dos.open.oauth2;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.RoleCode;
import allthings.iot.dos.dto.IotOauthAccessTokenDto;
import allthings.iot.dos.dto.IotOauthRefreshTokenDto;
import allthings.iot.dos.dto.oauth2.AccessTokenDTO;
import allthings.iot.dos.dto.oauth2.BaseClientDTO;
import allthings.iot.dos.dto.oauth2.IotOauthCodeDto;
import allthings.iot.dos.dto.oauth2.OauthCodeDTO;
import allthings.iot.dos.dto.oauth2.RefreshTokenDTO;
import allthings.iot.dos.oauth2.api.IotAuthorizationCodeService;
import allthings.iot.dos.oauth2.api.IotOauthAccessTokenService;
import allthings.iot.dos.oauth2.api.IotOauthClientDetailsService;
import allthings.iot.dos.oauth2.api.IotOauthRefreshTokenService;
import allthings.iot.dos.oauth2.api.RpcOauth2Service;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("rpcOauth2Biz")
public class RpcOauth2ServiceImpl implements RpcOauth2Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcOauth2ServiceImpl.class);

    @Autowired
    private IotAuthorizationCodeService iotAuthorizationCodeService;

    @Autowired
    private IotOauthClientDetailsService iotOauthClientDetailsService;

    @Autowired
    private IotOauthAccessTokenService iotOauthAccessTokenService;

    @Autowired
    private IotOauthRefreshTokenService iotOauthRefreshTokenService;

    @Override
    public ResultDTO<BaseClientDTO> loadClientByClientId(String clientId) {
        try {
            return iotOauthClientDetailsService.getIotOauthClientDetailsByClientId(clientId);
        } catch (Exception ee) {
            LOGGER.error("find client detail error ,clientId : {}", clientId, ee);
            return ResultDTO.newSuccess(null);
        }
    }

    @Override
    public ResultDTO<String> getAccessTokenByAuthenticationId(String authenticationId) {
        IotOauthAccessTokenDto iotOauthAccessToken =
                iotOauthAccessTokenService.findIotOauthAccessTokenByAuthenticationId(authenticationId);
        if (iotOauthAccessToken == null) {
            LOGGER.warn("find access token error ,authenticationId : {}", authenticationId);
            return ResultDTO.newSuccess(null);
        }

        return ResultDTO.newSuccess(iotOauthAccessToken.getToken());
    }

    @Override
    public ResultDTO<String> getAccessTokenByTokenId(String tokenId) {
        IotOauthAccessTokenDto iotOauthAccessToken = iotOauthAccessTokenService.findIotOauthAccessTokenByTokenId(tokenId);
        if (iotOauthAccessToken == null) {
            LOGGER.warn("find access token error ,tokenId : {}", tokenId);
            return ResultDTO.newSuccess(null);
        }

        return ResultDTO.newSuccess(iotOauthAccessToken.getToken());
    }

    @Override
    public ResultDTO<List<String>> getAccessTokenByClientId(String clientId) {
        IotOauthAccessTokenDto iotOauthAccessToken = iotOauthAccessTokenService.findByClientId(clientId);
        if (iotOauthAccessToken == null) {
            LOGGER.warn("find access token error ,clientId : {}", clientId);
            return ResultDTO.newSuccess(null);
        }

        return ResultDTO.newSuccess(Lists.newArrayList(iotOauthAccessToken.getToken()));
    }

    @Override
    public ResultDTO<List<String>> getAccessTokenByUsername(String username) {
        IotOauthAccessTokenDto iotOauthAccessToken = iotOauthAccessTokenService.findByUserName(username);
        if (iotOauthAccessToken == null) {
            LOGGER.warn("find access token error ,username : {}", username);
            return ResultDTO.newSuccess(null);
        }

        return ResultDTO.newSuccess(Lists.newArrayList(iotOauthAccessToken.getToken()));
    }

    @Override
    public ResultDTO<List<String>> getAccessTokenByClientIdAndUsername(String clientId, String username) {
        IotOauthAccessTokenDto iotOauthAccessToken = iotOauthAccessTokenService.findByUserNameAndClientId(username,
                clientId);
        if (iotOauthAccessToken == null) {
            LOGGER.warn("find access token error ,clientId : {}, username : {}", clientId, username);
            return ResultDTO.newSuccess(null);
        }

        return ResultDTO.newSuccess(Lists.newArrayList(iotOauthAccessToken.getToken()));
    }

    @Override
    public ResultDTO<?> saveAccessToken(AccessTokenDTO accessTokenDTO) {
        IotOauthAccessTokenDto iotOauthAccessToken = new IotOauthAccessTokenDto();

        iotOauthAccessToken.setClientId(accessTokenDTO.getClientId());
        iotOauthAccessToken.setRefreshToken(
                accessTokenDTO.getRefreshToken() == null ? "" : accessTokenDTO.getRefreshToken());
        iotOauthAccessToken.setAuthentication(accessTokenDTO.getAuthentication());
        iotOauthAccessToken.setAuthenticationId(accessTokenDTO.getAuthenticationId());
        iotOauthAccessToken.setTokenId(accessTokenDTO.getTokenId());
        iotOauthAccessToken.setUsername(accessTokenDTO.getUsername() == null ? "" : accessTokenDTO.getUsername());
        iotOauthAccessToken.setToken(accessTokenDTO.getToken());
        iotOauthAccessToken.setCreateOperatorId(RoleCode.CREATE_OPERATOR_ID);
        iotOauthAccessToken.setModifyOperatorId(RoleCode.CREATE_OPERATOR_ID);
        iotOauthAccessToken.setInputDate(new Date());
        iotOauthAccessToken.setIsDeleted(false);

        iotOauthAccessTokenService.saveIotOauthAccessToken(iotOauthAccessToken);

        return ResultDTO.newSuccess(null);
    }

    @Override
    public ResultDTO<?> saveRefreshToken(RefreshTokenDTO refreshTokenDTO) {
        IotOauthRefreshTokenDto iotOauthRefreshToken = new IotOauthRefreshTokenDto();
        iotOauthRefreshToken.setAuthentication(refreshTokenDTO.getAuthentication());
        iotOauthRefreshToken.setToken(refreshTokenDTO.getToken());
        iotOauthRefreshToken.setTokenId(refreshTokenDTO.getTokenId());
        iotOauthRefreshToken.setCreateOperatorId(RoleCode.CREATE_OPERATOR_ID);
        iotOauthRefreshToken.setInputDate(new Date());
        iotOauthRefreshToken.setIsDeleted(false);

        iotOauthRefreshTokenService.saveIotOauthRefreshToken(iotOauthRefreshToken);

        return ResultDTO.newSuccess(null);
    }

    @Override
    public ResultDTO<String> getRefreshTokenByTokenId(String tokenId) {
        IotOauthRefreshTokenDto iotOauthRefreshToken =
                iotOauthRefreshTokenService.findIotOauthRefreshTokenByTokenId(tokenId);
        if (iotOauthRefreshToken == null) {
            LOGGER.warn("find refresh token error ,tokenId : {}", tokenId);
            return ResultDTO.newSuccess(null);
        }

        return ResultDTO.newSuccess(iotOauthRefreshToken.getToken());
    }

    @Override
    public ResultDTO<?> deleteAccessTokenByTokenId(String tokenId) {
        iotOauthAccessTokenService.deletedByTokenId(tokenId);
        return ResultDTO.newSuccess(null);
    }

    @Override
    public ResultDTO<String> getAuthenticationByTokenId(String tokenId) {
        IotOauthAccessTokenDto iotOauthAccessToken =
                iotOauthAccessTokenService.findIotOauthAccessTokenByTokenId(tokenId);
        if (iotOauthAccessToken == null) {
            LOGGER.warn("find access token error ,tokenId : {}", tokenId);
            return ResultDTO.newSuccess(null);
        }

        return ResultDTO.newSuccess(iotOauthAccessToken.getAuthentication());
    }

    @Override
    public ResultDTO<?> deleteRefreshTokenByTokenId(String tokenId) {
        iotOauthRefreshTokenService.deletedIotOauthRefreshTokenByTokenId(tokenId);
        return ResultDTO.newSuccess(null);
    }

    @Override
    public ResultDTO<String> getAuthenticationByTokenIdForRefreshToken(String tokenId) {
        IotOauthRefreshTokenDto iotOauthRefreshToken =
                iotOauthRefreshTokenService.findIotOauthRefreshTokenByTokenId(tokenId);
        if (iotOauthRefreshToken == null) {
            LOGGER.warn("find refresh token error ,tokenId : {}", tokenId);
            return ResultDTO.newSuccess(null);
        }

        return ResultDTO.newSuccess(iotOauthRefreshToken.getAuthentication());
    }

    @Override
    public ResultDTO<?> deleteAccessTokenByRefreshToken(String refreshToken) {
        iotOauthAccessTokenService.deletedByRefreshToken(refreshToken);
        return ResultDTO.newSuccess(null);
    }

    @Override
    public ResultDTO<?> saveAuthorization(OauthCodeDTO oauthCodeDTO) {
        IotOauthCodeDto iotOauthCode = new IotOauthCodeDto();
        BeanUtils.copyProperties(oauthCodeDTO, iotOauthCode);
        iotOauthCode.setCreateOperatorId(RoleCode.CREATE_OPERATOR_ID);
        iotOauthCode.setInputDate(new Date());
        iotOauthCode.setIsDeleted(false);
        iotAuthorizationCodeService.saveOauthCode(iotOauthCode);

        return ResultDTO.newSuccess(null);
    }

    @Override
    public ResultDTO<String> getAuthorizationByCode(String code) {
        IotOauthCodeDto iotOauthCode = iotAuthorizationCodeService.getOauthCodeByCode(code);
        if (iotOauthCode == null) {
            LOGGER.warn("find authorization error ,code : {}", code);
            return ResultDTO.newSuccess(null);
        }

        return ResultDTO.newSuccess(iotOauthCode.getAuthentication());
    }

    @Override
    public ResultDTO<?> deleteAuthorizationByCode(String code) {
        iotAuthorizationCodeService.deleteOauthCodeByCode(code);
        return ResultDTO.newSuccess(null);
    }
}
