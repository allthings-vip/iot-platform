package allthings.iot.dos.open.oauth2;

import allthings.iot.dos.dao.oauth2.IotOauthAccessTokenDao;
import allthings.iot.dos.dto.IotOauthAccessTokenDto;
import allthings.iot.dos.model.oauth2.IotOauthAccessToken;
import allthings.iot.dos.oauth2.api.IotOauthAccessTokenService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

/**
 * @author tyf
 * @date 2018/11/15 9:32
 */
@Service("iotOauthAccessTokenService")
public class IotOauthAccessTokenServiceImpl implements IotOauthAccessTokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotOauthAccessTokenServiceImpl.class);

    @Autowired
    private IotOauthAccessTokenDao iotOauthAccessTokenDao;

    @Override
    public IotOauthAccessTokenDto saveIotOauthAccessToken(IotOauthAccessTokenDto oauthAccessTokenDto) {
        try {

            IotOauthAccessToken accessToken = new IotOauthAccessToken();
            BeanUtils.copyProperties(oauthAccessTokenDto, accessToken);
            accessToken = iotOauthAccessTokenDao.saveAndFlush(accessToken);
            oauthAccessTokenDto.setIotOauthAccessTokenId(accessToken.getIotOauthAccessTokenId());
            return oauthAccessTokenDto;
        } catch (Exception ee) {
            LOGGER.warn("access token生成失败", ee);
            return null;
        }
    }

    @Override
    public IotOauthAccessTokenDto findIotOauthAccessTokenByTokenId(String tokenId) {
        Optional<IotOauthAccessToken> optional = iotOauthAccessTokenDao.findOne((root, query, cb) ->
                cb.equal(root.get("tokenId").as(String.class), tokenId));
        if (optional.isPresent()) {
            IotOauthAccessToken oauthAccessToken = optional.get();
            IotOauthAccessTokenDto accessTokenDto = new IotOauthAccessTokenDto();
            BeanUtils.copyProperties(oauthAccessToken, accessTokenDto);
            return accessTokenDto;
        }
        return null;
    }

    @Override
    public IotOauthAccessTokenDto findIotOauthAccessTokenByAuthenticationId(String authenticationId) {
        Optional<IotOauthAccessToken> optional = iotOauthAccessTokenDao.findOne((root, query, cb) ->
                cb.equal(root.get("authenticationId").as(String.class), authenticationId));
        if (optional.isPresent()) {
            IotOauthAccessToken oauthAccessToken = optional.get();
            IotOauthAccessTokenDto accessTokenDto = new IotOauthAccessTokenDto();
            BeanUtils.copyProperties(oauthAccessToken, accessTokenDto);
            return accessTokenDto;
        }
        return null;
    }

    @Override
    public IotOauthAccessTokenDto findByUserNameAndClientId(String username, String clientId) {
        Optional<IotOauthAccessToken> optional = iotOauthAccessTokenDao.findOne((root, query, cb) -> {
            List<Predicate> list = Lists.newArrayList();
            list.add(cb.equal(root.get("username").as(String.class), username));
            list.add(cb.equal(root.get("clientId").as(String.class), clientId));
            Predicate[] predicates = new Predicate[list.size()];
            return cb.and(list.toArray(predicates));
        });
        if (optional.isPresent()) {
            IotOauthAccessToken oauthAccessToken = optional.get();
            IotOauthAccessTokenDto accessTokenDto = new IotOauthAccessTokenDto();
            BeanUtils.copyProperties(oauthAccessToken, accessTokenDto);
            return accessTokenDto;
        }
        return null;
    }

    @Override
    public IotOauthAccessTokenDto findByUserName(String username) {
        Optional<IotOauthAccessToken> optional = iotOauthAccessTokenDao.findOne((root, query, cb) ->
                cb.equal(root.get("username").as(String.class), username));
        if (optional.isPresent()) {
            IotOauthAccessToken oauthAccessToken = optional.get();
            IotOauthAccessTokenDto accessTokenDto = new IotOauthAccessTokenDto();
            BeanUtils.copyProperties(oauthAccessToken, accessTokenDto);
            return accessTokenDto;
        }
        return null;
    }

    @Override
    public IotOauthAccessTokenDto findByClientId(String clientId) {
        Optional<IotOauthAccessToken> optional = iotOauthAccessTokenDao.findOne((root, query, cb) ->
                cb.equal(root.get("clientId").as(String.class), clientId));
        if (optional.isPresent()) {
            IotOauthAccessToken oauthAccessToken = optional.get();
            IotOauthAccessTokenDto accessTokenDto = new IotOauthAccessTokenDto();
            BeanUtils.copyProperties(oauthAccessToken, accessTokenDto);
            return accessTokenDto;
        }

        return null;
    }

    @Override
    public void deletedByTokenId(String tokenId) {
        List<IotOauthAccessToken> list = iotOauthAccessTokenDao.findAll((root, query, cb) ->
                cb.equal(root.get("tokenId").as(String.class), tokenId));
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (IotOauthAccessToken accessToken : list) {
            iotOauthAccessTokenDao.delete(accessToken);
        }
    }

    @Override
    public void deletedByRefreshToken(String refreshToken) {
        Optional<IotOauthAccessToken> optional = iotOauthAccessTokenDao.findOne((root, query, cb) ->
                cb.equal(root.get("refreshToken").as(String.class), refreshToken));
        if (optional.isPresent()) {
            iotOauthAccessTokenDao.delete(optional.get());
        }
    }
}
