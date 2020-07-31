package allthings.iot.dos.dao.oauth2;

import allthings.iot.dos.model.oauth2.IotOauthClientDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotOauthClientDetailsDao
 * @CreateDate :  2018/11/15
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface IotOauthClientDetailsDao extends BaseRepository<IotOauthClientDetails, Long> {

    @Modifying
    @Query("delete from IotOauthClientDetails where clientId =:clientId")
    Long deleteClientDetails(@Param("clientId") String clientId);

    @Query("select clientId, clientSecret, resourceIds, scope, authorizedGrantTypes, webServerRedirectUri, " +
            "authorities," +
            " accessTokenValidity, refreshTokenValidity, additionalInformation, autoApprove " +
            " from IotOauthClientDetails order by clientId ")
    List<IotOauthClientDetails> findClientDetails();

    @Modifying
    @Query("update IotOauthClientDetails set resourceIds=:resourceIds, scope=:scope, " +
            "authorizedGrantTypes=:authorizedGrantTypes, " +
            " webServerRedirectUri=:webServerRedirectUri, authorities=:authorities, " +
            "accessTokenValidity=:accessTokenValidity, " +
            " refreshTokenValidity=:refreshTokenValidity, additionalInformation=:additionalInformation, " +
            " autoApprove=:autoApprove where clientId =:clientId ")
    Long updateClientDetails(@Param("resourceIds") String resourceIds, @Param("scope") String scope,
                             @Param("authorizedGrantTypes") String authorizedGrantTypes,
                             @Param("webServerRedirectUri") String webServerRedirectUri,
                             @Param("authorities") String authorities,
                             @Param("accessTokenValidity") Long accessTokenValidity,
                             @Param("refreshTokenValidity") Long refreshTokenValidity,
                             @Param("additionalInformation") String additionalInformation,
                             @Param("autoApprove") String autoApprove,
                             @Param("clientId") String clientId);

    @Modifying
    @Query("update IotOauthClientDetails set clientSecret =:clientSecret where clientId =:clientId")
    Long updateClientSecret(@Param("clientSecret") String clientSecret, @Param("clientId") String clientId);

    @Query(" from IotOauthClientDetails where clientId =:clientId ")
    IotOauthClientDetails selectClientDetails(@Param("clientId") String clientId);
}
