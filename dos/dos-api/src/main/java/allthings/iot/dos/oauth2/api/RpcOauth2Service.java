package allthings.iot.dos.oauth2.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.oauth2.AccessTokenDTO;
import allthings.iot.dos.dto.oauth2.BaseClientDTO;
import allthings.iot.dos.dto.oauth2.OauthCodeDTO;
import allthings.iot.dos.dto.oauth2.RefreshTokenDTO;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  com.allthings.iot.dos.oauth2.api.RpcOauth2Biz
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
public interface RpcOauth2Service {
    /**
     * 加载client 信息
     *
     * @param clientId
     * @return
     */
    ResultDTO<BaseClientDTO> loadClientByClientId(String clientId);

    /**
     * 根据认证Id获取token
     *
     * @param authenticationId
     * @return
     */
    ResultDTO<String> getAccessTokenByAuthenticationId(String authenticationId);


    /**
     * 根据认证Id获取token
     *
     * @param tokenId
     * @return
     */
    ResultDTO<String> getAccessTokenByTokenId(String tokenId);

    /**
     * 根据clientId获取token
     *
     * @param clientId
     * @return
     */
    ResultDTO<List<String>> getAccessTokenByClientId(String clientId);

    /**
     * 根据username获取token
     *
     * @param username
     * @return
     */
    ResultDTO<List<String>> getAccessTokenByUsername(String username);


    /**
     * 根据clientId和username获取token
     *
     * @param username
     * @return
     */
    ResultDTO<List<String>> getAccessTokenByClientIdAndUsername(String clientId, String username);


    /**
     * 保存accessToken
     *
     * @param accessTokenDTO
     * @return
     */
    ResultDTO<?> saveAccessToken(AccessTokenDTO accessTokenDTO);


    /**
     * 保存RefreshToken
     *
     * @param refreshTokenDTO
     * @return
     */
    ResultDTO<?> saveRefreshToken(RefreshTokenDTO refreshTokenDTO);

    /**
     * 根据tokenid获取refresh token
     *
     * @param tokenId
     * @return
     */
    ResultDTO<String> getRefreshTokenByTokenId(String tokenId);

    /**
     * 根据tokenId删除token
     *
     * @param tokenId
     * @return
     */
    ResultDTO<?> deleteAccessTokenByTokenId(String tokenId);

    /**
     * 根据tokenId获取认证信息
     *
     * @param tokenId
     * @return
     */
    ResultDTO<String> getAuthenticationByTokenId(String tokenId);


    /**
     * 根据tokenId删除refreshtoken
     *
     * @param tokenId
     * @return
     */
    ResultDTO<?> deleteRefreshTokenByTokenId(String tokenId);

    /**
     * 根据tokenId获取认证信息
     *
     * @param tokenId
     * @return
     */
    ResultDTO<String> getAuthenticationByTokenIdForRefreshToken(String tokenId);


    /**
     * 根据refreshToken删除token
     *
     * @param refreshToken
     * @return
     */
    ResultDTO<?> deleteAccessTokenByRefreshToken(String refreshToken);

    /**
     * 保存认证信息
     *
     * @param oauthCodeDTO
     * @return
     */
    ResultDTO<?> saveAuthorization(OauthCodeDTO oauthCodeDTO);

    /**
     * 根据code查询认证信息
     *
     * @param code
     * @return
     */
    ResultDTO<String> getAuthorizationByCode(String code);

    /**
     * 根据code删除认证信息
     *
     * @param code
     * @return
     */
    ResultDTO<?> deleteAuthorizationByCode(String code);
}
