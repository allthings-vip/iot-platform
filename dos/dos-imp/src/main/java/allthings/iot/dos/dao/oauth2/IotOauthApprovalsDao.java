package allthings.iot.dos.dao.oauth2;

import allthings.iot.dos.model.oauth2.IotOauthApprovals;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-14 18:51
 */

public interface IotOauthApprovalsDao extends BaseRepository<IotOauthApprovals, Long> {
    @Query("from IotOauthApprovals where userId=:userId and clientId=:clientId and isDeleted=false ")
    List<IotOauthApprovals> getIotOauthApprovalsByUserIdAndClientId(@Param("userId") String userId,
                                                                    @Param("clientId") String clientId);

    @Modifying
    @Query("update IotOauthApprovals set expiresAt=:expiresAt, status=:status, lastModifiedAt=:lastModifiedAt" +
            " where userId=:userId and clientId=:clientId and scope=:scope and isDeleted=false ")
    Long updateIotOauthApprovals(@Param("expiresAt") Date expiresAt,
                                 @Param("status") String status,
                                 @Param("lastModifiedAt") Date lastModifiedAt,
                                 @Param("userId") String userId,
                                 @Param("clientId") String clientId,
                                 @Param("scope") String scope);

    @Modifying
    @Query("update IotOauthApprovals set expiresAt=:expiresAt where userId=:userId and clientId=:clientId and " +
            "scope=:scope")
    Long updateIotOauthApprovalsExpiresAt(@Param("expiresAt") Date expiresAt, @Param("userId") String userId,
                                          @Param("clientId") String clientId, @Param("scope") String scope);

    @Modifying
    @Query("delete from IotOauthApprovals where userId=:userId and clientId=:clientId and scope=:scope and " +
            "expiresAt<=:expiresAt")
    Long deleteIotOauthApprovals(@Param("userId") String userId, @Param("clientId") String clientId,
                                 @Param("scope") String scope, @Param("expiresAt") Date expiresAt);
}
