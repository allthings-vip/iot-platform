package allthings.iot.dos.open.oauth2;

import allthings.iot.dos.dao.oauth2.IotOauthRefreshTokenDao;
import allthings.iot.dos.dto.IotOauthRefreshTokenDto;
import allthings.iot.dos.model.oauth2.IotOauthRefreshToken;
import allthings.iot.dos.oauth2.api.IotOauthRefreshTokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tyf
 * @date 2018/11/15 9:06
 */
@Service("iotOauthRefreshTokenService")
public class IotOauthRefreshTokenServiceImpl implements IotOauthRefreshTokenService {

    @Autowired
    private IotOauthRefreshTokenDao iotOauthRefreshTokenDao;

    @Override
    public IotOauthRefreshTokenDto saveIotOauthRefreshToken(IotOauthRefreshTokenDto iotOauthRefreshToken) {
        IotOauthRefreshToken oauthRefreshToken = new IotOauthRefreshToken();
        BeanUtils.copyProperties(iotOauthRefreshToken, oauthRefreshToken);
        oauthRefreshToken = iotOauthRefreshTokenDao.saveAndFlush(oauthRefreshToken);
        iotOauthRefreshToken.setIotOauthRefreshTokenId(oauthRefreshToken.getIotOauthRefreshTokenId());
        return iotOauthRefreshToken;
    }

    @Override
    public IotOauthRefreshTokenDto findIotOauthRefreshTokenByTokenId(String tokenId) {
        IotOauthRefreshToken iotOauthRefreshToken = iotOauthRefreshTokenDao.findOne(((root, query, cb) ->
                cb.equal(root.get("tokenId").as(String.class), tokenId)
        )).get();
        IotOauthRefreshTokenDto oauthRefreshTokenDto = new IotOauthRefreshTokenDto();
        BeanUtils.copyProperties(iotOauthRefreshToken, oauthRefreshTokenDto);
        return oauthRefreshTokenDto;
    }

    @Override
    public void deletedIotOauthRefreshTokenByTokenId(String tokenId) {
        iotOauthRefreshTokenDao.delete(iotOauthRefreshTokenDao.findOne(((root, query, cb) ->
                cb.equal(root.get("tokenId").as(String.class), tokenId)
        )).get());
    }
}
