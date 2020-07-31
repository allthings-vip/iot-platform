package allthings.iot.dos.client.open;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.oauth2.AccessTokenDTO;
import allthings.iot.dos.dto.oauth2.BaseClientDTO;
import allthings.iot.dos.dto.oauth2.OauthCodeDTO;
import allthings.iot.dos.dto.oauth2.RefreshTokenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 18:49
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotOauth2Api {
    @GetMapping("/dos/service/loadClientByClientId")
    ResultDTO<BaseClientDTO> loadClientByClientId(@RequestParam("clientId") String clientId);

    @GetMapping("/dos/service/getAccessTokenByAuthenticationId")
    ResultDTO<String> getAccessTokenByAuthenticationId(@RequestParam("authenticationId") String authenticationId);

    @GetMapping("/dos/service/getAccessTokenByTokenId")
    ResultDTO<String> getAccessTokenByTokenId(@RequestParam("tokenId") String tokenId);

    @GetMapping("/dos/service/getAccessTokenByClientId")
    ResultDTO<List<String>> getAccessTokenByClientId(@RequestParam("clientId") String clientId);

    @GetMapping("/dos/service/getAccessTokenByUsername")
    ResultDTO<List<String>> getAccessTokenByUsername(@RequestParam("username") String username);

    @GetMapping("/dos/service/getAccessTokenByClientIdAndUsername")
    ResultDTO<List<String>> getAccessTokenByClientIdAndUsername(@RequestParam("clientId") String clientId,
                                                                @RequestParam("username") String username);

    @PostMapping("/dos/service/saveAccessToken")
    ResultDTO<?> saveAccessToken(@RequestBody AccessTokenDTO accessTokenDTO);

    @PostMapping("/dos/service/saveRefreshToken")
    ResultDTO<?> saveRefreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO);

    @GetMapping("/dos/service/getRefreshTokenByTokenId")
    ResultDTO<String> getRefreshTokenByTokenId(@RequestParam("tokenId") String tokenId);

    @PostMapping("/dos/service/deleteAccessTokenByTokenId")
    ResultDTO<?> deleteAccessTokenByTokenId(@RequestParam("tokenId") String tokenId);

    @GetMapping("/dos/service/getAuthenticationByTokenId")
    ResultDTO<String> getAuthenticationByTokenId(@RequestParam("tokenId") String tokenId);

    @PostMapping("/dos/service/deleteRefreshTokenByTokenId")
    ResultDTO<?> deleteRefreshTokenByTokenId(@RequestParam("tokenId") String tokenId);

    @GetMapping("/dos/service/getAuthenticationByTokenIdForRefreshToken")
    ResultDTO<String> getAuthenticationByTokenIdForRefreshToken(@RequestParam("tokenId") String tokenId);

    @PostMapping("/dos/service/deleteAccessTokenByRefreshToken")
    ResultDTO<?> deleteAccessTokenByRefreshToken(@RequestParam("refreshToken") String refreshToken);

    @PostMapping("/dos/service/saveAuthorization")
    ResultDTO<?> saveAuthorization(@RequestBody OauthCodeDTO oauthCodeDTO);

    @GetMapping("/dos/service/getAuthorizationByCode")
    ResultDTO<String> getAuthorizationByCode(@RequestParam("code") String code);

    @PostMapping("/dos/service/deleteAuthorizationByCode")
    ResultDTO<?> deleteAuthorizationByCode(@RequestParam("code") String code);
}
