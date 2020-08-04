package allthings.iot.dos.open.security.oauth2;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.open.IotOauth2Api;
import allthings.iot.dos.dto.oauth2.AccessTokenDTO;
import allthings.iot.dos.dto.oauth2.RefreshTokenDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  com.tf56.iot.dos.security.oauth2.RpcTokenStore
 * @CreateDate :  2018/11/15
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
public class RpcTokenStore implements TokenStore {
    private static final Log LOG = LogFactory.getLog(RpcTokenStore.class);

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    private IotOauth2Api iotOauth2Api;

    public RpcTokenStore(IotOauth2Api iotOauth2Api) {
        Assert.notNull(iotOauth2Api, "RpcOauth2Biz required");
        this.iotOauth2Api = iotOauth2Api;
    }

    public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
        this.authenticationKeyGenerator = authenticationKeyGenerator;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String key = authenticationKeyGenerator.extractKey(authentication);
        OAuth2AccessToken accessToken = null;
        ResultDTO<String> bizReturn = iotOauth2Api.getAccessTokenByAuthenticationId(key);
        if (!bizReturn.isSuccess()) {
            LOG.info("Failed to find access token for authentication " + authentication);
        }


        try {
            String accessTokenString = bizReturn.getData();
            if (StringUtils.isEmpty(accessTokenString)) {
                LOG.info("Failed to find access token for authentication " + authentication);
            } else {
                accessToken = SerializationUtils.deserialize(accessTokenString.getBytes("ISO-8859-1"));
            }
        } catch (IOException e) {
            LOG.info("Failed to find access token for authentication " + authentication);
        }

        if (accessToken != null
                && !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
            removeAccessToken(accessToken.getValue());
            // Keep the store consistent (maybe the same user is represented by this authentication but the details have
            // changed)
            storeAccessToken(accessToken, authentication);
        }
        return accessToken;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }

        if (readAccessToken(token.getValue()) != null) {
            removeAccessToken(token.getValue());
        }

        try {
            AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
            accessTokenDTO.setClientId(authentication.getOAuth2Request().getClientId());
            accessTokenDTO.setUsername(authentication.isClientOnly() ? null : authentication.getName());
            accessTokenDTO.setTokenId(extractTokenKey(token.getValue()));
            accessTokenDTO.setToken(new String(SerializationUtils.serialize(token), "ISO-8859-1"));
            accessTokenDTO.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
            accessTokenDTO.setAuthentication(new String(SerializationUtils.serialize(authentication), "ISO-8859-1"));
            accessTokenDTO.setRefreshToken(extractTokenKey(refreshToken));

            iotOauth2Api.saveAccessToken(accessTokenDTO);
        } catch (Exception ee) {
            LOG.info("Failed to save access token for token " + token);
        }
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;

        try {
            ResultDTO<String> bizReturn = iotOauth2Api.getAccessTokenByTokenId(extractTokenKey(tokenValue));
            if (!bizReturn.isSuccess()) {
                LOG.warn("Failed to find access token for token " + tokenValue);
            }

            String accessTokenString = bizReturn.getData();
            if (StringUtils.isEmpty(accessTokenString)) {
                LOG.info("Failed to find access token for token " + tokenValue);
            } else {
                accessToken = SerializationUtils.deserialize(accessTokenString.getBytes("ISO-8859-1"));
            }
        } catch (IOException e) {
            LOG.error("Failed to deserialize access token for " + tokenValue, e);
            removeAccessToken(tokenValue);
        }

        return accessToken;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        removeAccessToken(token.getValue());
    }

    public void removeAccessToken(String tokenValue) {
        iotOauth2Api.deleteAccessTokenByTokenId(extractTokenKey(tokenValue));
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication authentication = null;

        try {
            ResultDTO<String> bizReturn = iotOauth2Api.getAuthenticationByTokenId(extractTokenKey(token));
            if (!bizReturn.isSuccess()) {
                LOG.info("Failed to find access token for token " + token);
            }

            String authenticationString = bizReturn.getData();
            if (StringUtils.isEmpty(authenticationString)) {
                LOG.info("Failed to find access token for token " + token);
            } else {
                authentication = SerializationUtils.deserialize(authenticationString.getBytes("ISO-8859-1"));
            }
        } catch (Exception e) {
            LOG.error("Failed to deserialize authentication for " + token, e);
            removeAccessToken(token);
        }

        return authentication;
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        try {
            RefreshTokenDTO accessTokenDTO = new RefreshTokenDTO();
            accessTokenDTO.setTokenId(extractTokenKey(refreshToken.getValue()));
            accessTokenDTO.setToken(new String(SerializationUtils.serialize(refreshToken), "ISO-8859-1"));
            accessTokenDTO.setAuthentication(new String(SerializationUtils.serialize(authentication), "ISO-8859-1"));

            iotOauth2Api.saveRefreshToken(accessTokenDTO);
        } catch (Exception ee) {
            LOG.error("Failed to save access token for token " + refreshToken, ee);
        }
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String token) {
        OAuth2RefreshToken refreshToken = null;

        try {
            ResultDTO<String> bizReturn = iotOauth2Api.getRefreshTokenByTokenId(extractTokenKey(token));
            if (!bizReturn.isSuccess()) {
                LOG.info("Failed to find refresh token for token " + token);
            }

            String refreshTokenString = bizReturn.getData();
            if (StringUtils.isEmpty(refreshTokenString)) {
                LOG.info("Failed to find refresh token for token " + token);
            } else {
                refreshToken = SerializationUtils.deserialize(refreshTokenString.getBytes("ISO-8859-1"));
            }
        } catch (IOException e) {
            LOG.warn("Failed to deserialize refresh token for token " + token, e);
            removeRefreshToken(token);
        }

        return refreshToken;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        removeRefreshToken(token.getValue());
    }

    public void removeRefreshToken(String token) {
        iotOauth2Api.deleteRefreshTokenByTokenId(extractTokenKey(token));
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String value) {
        OAuth2Authentication authentication = null;

        try {
            ResultDTO<String> bizReturn = iotOauth2Api.getAuthenticationByTokenIdForRefreshToken(extractTokenKey(value));
            if (!bizReturn.isSuccess()) {
                LOG.info("Failed to find access token for token " + value);
            }

            String authenticationString = bizReturn.getData();
            if (StringUtils.isEmpty(authenticationString)) {
                LOG.info("Failed to find access token for token " + value);
            } else {
                authentication = SerializationUtils.deserialize(authenticationString.getBytes("ISO-8859-1"));
            }
        } catch (IOException e) {
            LOG.warn("Failed to deserialize access token for " + value, e);
            removeRefreshToken(value);
        }

        return authentication;
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
        iotOauth2Api.deleteAccessTokenByRefreshToken(extractTokenKey(refreshToken));
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<>();


        ResultDTO<List<String>> bizReturn = iotOauth2Api.getAccessTokenByClientId(clientId);
        if (!bizReturn.isSuccess()) {
            LOG.info("Failed to find access token for clientId " + clientId);
        }

        List<String> accessTokenStrings = bizReturn.getData();
        if (StringUtils.isEmpty(accessTokenStrings)) {
            LOG.info("Failed to find access token for clientId " + clientId);
        } else {
            for (String accessTokenString : accessTokenStrings) {
                try {
                    OAuth2AccessToken accessToken =
                            SerializationUtils.deserialize(accessTokenString.getBytes("ISO-8859-1"));
                    accessTokens.add(accessToken);
                } catch (Exception ee) {
                    LOG.info("Failed to find access token for clientId " + clientId);
                    continue;
                }
            }
        }

        accessTokens = removeNulls(accessTokens);

        return accessTokens;
    }

    public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<>();

        ResultDTO<List<String>> bizReturn = iotOauth2Api.getAccessTokenByUsername(userName);
        if (!bizReturn.isSuccess()) {
            LOG.info("Failed to find access token for userName " + userName);
        }

        List<String> accessTokenStrings = bizReturn.getData();
        if (StringUtils.isEmpty(accessTokenStrings)) {
            LOG.info("Failed to find access token for userName " + userName);
        } else {
            for (String accessTokenString : accessTokenStrings) {
                try {
                    OAuth2AccessToken accessToken =
                            SerializationUtils.deserialize(accessTokenString.getBytes("ISO-8859-1"));
                    accessTokens.add(accessToken);
                } catch (Exception ee) {
                    LOG.info("Failed to find access token for userName " + userName);
                    continue;
                }
            }
        }

        accessTokens = removeNulls(accessTokens);

        return accessTokens;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<>();


        ResultDTO<List<String>> bizReturn = iotOauth2Api.getAccessTokenByClientIdAndUsername(clientId, userName);
        if (!bizReturn.isSuccess()) {
            LOG.info("Failed to find access token for clientId " + clientId + " and userName " + userName);
        }

        List<String> accessTokenStrings = bizReturn.getData();
        if (StringUtils.isEmpty(accessTokenStrings)) {
            LOG.info("Failed to find access token for clientId " + clientId + " and userName " + userName);
        } else {
            for (String accessTokenString : accessTokenStrings) {
                try {
                    OAuth2AccessToken accessToken = SerializationUtils.deserialize(accessTokenString.getBytes("ISO-8859-1"));
                    accessTokens.add(accessToken);
                } catch (Exception ee) {
                    LOG.info("Failed to find access token for clientId " + clientId + " and userName " + userName, ee);
                    continue;
                }
            }
        }

        accessTokens = removeNulls(accessTokens);

        return accessTokens;
    }

    private List<OAuth2AccessToken> removeNulls(List<OAuth2AccessToken> accessTokens) {
        List<OAuth2AccessToken> tokens = new ArrayList<>();
        for (OAuth2AccessToken token : accessTokens) {
            if (token != null) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }
}
