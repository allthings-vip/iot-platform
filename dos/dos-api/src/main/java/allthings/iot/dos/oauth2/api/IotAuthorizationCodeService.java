package allthings.iot.dos.oauth2.api;

import allthings.iot.dos.dto.oauth2.IotOauthCodeDto;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-14 17:37
 */

public interface IotAuthorizationCodeService {
    /**
     * 新增
     *
     * @param iotOauthCode
     * @return
     */
    Long saveOauthCode(IotOauthCodeDto iotOauthCode);

    /**
     * 查询
     *
     * @param code
     * @return
     */
    IotOauthCodeDto getOauthCodeByCode(String code);

    /**
     * 删除
     *
     * @param code
     * @return
     */
    void deleteOauthCodeByCode(String code);
}
