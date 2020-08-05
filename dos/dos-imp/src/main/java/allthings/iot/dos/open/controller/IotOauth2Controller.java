package allthings.iot.dos.open.controller;

import allthings.iot.dos.client.open.IotOauth2Api;
import allthings.iot.dos.dto.oauth2.AccessTokenDTO;
import allthings.iot.dos.dto.oauth2.BaseClientDTO;
import allthings.iot.dos.dto.oauth2.OauthCodeDTO;
import allthings.iot.dos.dto.oauth2.RefreshTokenDTO;
import allthings.iot.dos.oauth2.api.RpcOauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/11 18:10:16
 */
@RestController
public class IotOauth2Controller implements IotOauth2Api {

    @Autowired
    private RpcOauth2Service rpcOauth2Biz;

    @Override
    public ResultDTO<BaseClientDTO> loadClientByClientId(@RequestParam("clientId") String clientId) {
        return rpcOauth2Biz.loadClientByClientId(clientId);
    }

    @Override
    public ResultDTO<String> getAccessTokenByAuthenticationId(@RequestParam("authenticationId") String authenticationId) {
        return rpcOauth2Biz.getAccessTokenByAuthenticationId(authenticationId);
    }

    @Override
    public ResultDTO<String> getAccessTokenByTokenId(@RequestParam("tokenId") String tokenId) {
        return rpcOauth2Biz.getAccessTokenByTokenId(tokenId);
    }

    @Override
    public ResultDTO<List<String>> getAccessTokenByClientId(@RequestParam("clientId") String clientId) {
        return rpcOauth2Biz.getAccessTokenByClientId(clientId);
    }

    @Override
    public ResultDTO<List<String>> getAccessTokenByUsername(@RequestParam("username") String username) {
        return rpcOauth2Biz.getAccessTokenByUsername(username);
    }

    @Override
    public ResultDTO<List<String>> getAccessTokenByClientIdAndUsername(@RequestParam("clientId") String clientId,
                                                                       @RequestParam("username") String username) {
        return rpcOauth2Biz.getAccessTokenByClientIdAndUsername(clientId, username);
    }

    @Override
    public ResultDTO<?> saveAccessToken(@RequestBody AccessTokenDTO accessTokenDTO) {
        return rpcOauth2Biz.saveAccessToken(accessTokenDTO);
    }

    @Override
    public ResultDTO<?> saveRefreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        return rpcOauth2Biz.saveRefreshToken(refreshTokenDTO);
    }

    @Override
    public ResultDTO<String> getRefreshTokenByTokenId(@RequestParam("tokenId") String tokenId) {
        return rpcOauth2Biz.getRefreshTokenByTokenId(tokenId);
    }

    @Override
    public ResultDTO<?> deleteAccessTokenByTokenId(@RequestParam("tokenId") String tokenId) {
        return rpcOauth2Biz.deleteAccessTokenByTokenId(tokenId);
    }

    @Override
    public ResultDTO<String> getAuthenticationByTokenId(@RequestParam("tokenId") String tokenId) {
        return rpcOauth2Biz.getAuthenticationByTokenId(tokenId);
    }

    @Override
    public ResultDTO<?> deleteRefreshTokenByTokenId(@RequestParam("tokenId") String tokenId) {
        return rpcOauth2Biz.deleteRefreshTokenByTokenId(tokenId);
    }

    @Override
    public ResultDTO<String> getAuthenticationByTokenIdForRefreshToken(@RequestParam("tokenId") String tokenId) {
        return rpcOauth2Biz.getAuthenticationByTokenIdForRefreshToken(tokenId);
    }

    @Override
    public ResultDTO<?> deleteAccessTokenByRefreshToken(@RequestParam("refreshToken") String refreshToken) {
        return rpcOauth2Biz.deleteAccessTokenByRefreshToken(refreshToken);
    }

    @Override
    public ResultDTO<?> saveAuthorization(@RequestBody OauthCodeDTO oauthCodeDTO) {
        return rpcOauth2Biz.saveAuthorization(oauthCodeDTO);
    }

    @Override
    public ResultDTO<String> getAuthorizationByCode(@RequestParam("code") String code) {
        return rpcOauth2Biz.getAuthorizationByCode(code);
    }

    @Override
    public ResultDTO<?> deleteAuthorizationByCode(@RequestParam("code") String code) {
        return rpcOauth2Biz.deleteAuthorizationByCode(code);
    }

}
