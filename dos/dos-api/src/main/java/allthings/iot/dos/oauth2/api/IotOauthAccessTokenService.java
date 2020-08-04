package allthings.iot.dos.oauth2.api;

import allthings.iot.dos.dto.IotOauthAccessTokenDto;

/**
 * @author tyf
 * @date 2018/11/14 17:38
 */
public interface IotOauthAccessTokenService {

    /**
     * 保存
     *
     * @param IotOauthAccessTokenDto
     * @return
     */
    IotOauthAccessTokenDto saveIotOauthAccessToken(IotOauthAccessTokenDto IotOauthAccessTokenDto);

    /**
     * 根据tokenId查询
     *
     * @param tokenId
     * @return
     */
    IotOauthAccessTokenDto findIotOauthAccessTokenByTokenId(String tokenId);

    /**
     * 根据authenticationId查询
     *
     * @param authenticationId
     * @return
     */
    IotOauthAccessTokenDto findIotOauthAccessTokenByAuthenticationId(String authenticationId);

    /**
     * 根据用户名和客户端id查询
     *
     * @param username
     * @param clientId
     * @return
     */
    IotOauthAccessTokenDto findByUserNameAndClientId(String username, String clientId);

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    IotOauthAccessTokenDto findByUserName(String username);

    /**
     * 根据客户端id查询
     *
     * @param clientId
     * @return
     */
    IotOauthAccessTokenDto findByClientId(String clientId);

    /**
     * 根据tokenId 删除token信息
     *
     * @param tokenId
     */
    void deletedByTokenId(String tokenId);


    /**
     * 根据refreshToken删除
     *
     * @param refreshToken
     */
    void deletedByRefreshToken(String refreshToken);

}
