package allthings.iot.dos.oauth2.api;


import allthings.iot.dos.dto.IotOauthRefreshTokenDto;

/**
 * @author tyf
 * @date 2018/11/15 9:03
 */
public interface IotOauthRefreshTokenService {

    /**
     * 新增
     *
     * @param iotOauthRefreshToken
     * @return
     */
    IotOauthRefreshTokenDto saveIotOauthRefreshToken(IotOauthRefreshTokenDto iotOauthRefreshToken);


    /**
     * 根据tokenId查询
     *
     * @param tokenId
     * @return
     */
    IotOauthRefreshTokenDto findIotOauthRefreshTokenByTokenId(String tokenId);


    /**
     * 根据tokenId删除
     *
     * @param tokenId
     */
    void deletedIotOauthRefreshTokenByTokenId(String tokenId);

}
