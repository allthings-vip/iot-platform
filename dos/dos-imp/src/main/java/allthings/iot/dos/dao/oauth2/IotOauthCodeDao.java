package allthings.iot.dos.dao.oauth2;

import allthings.iot.dos.model.oauth2.IotOauthCode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-14 17:44
 */

public interface IotOauthCodeDao extends BaseRepository<IotOauthCode, Long> {
    IotOauthCode findByCodeAndIsDeleted(String code, Boolean isDeleted);

    @Modifying
    @Query("delete from IotOauthCode where code=:code and isDeleted=false ")
    Long deleteIotOauthCodeByCode(@Param("code") String code);
}
