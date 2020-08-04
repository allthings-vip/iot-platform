package allthings.iot.dos.open.oauth2;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dao.oauth2.IotOauthApprovalsDao;
import allthings.iot.dos.dto.oauth2.ApprovalsDTO;
import allthings.iot.dos.model.oauth2.IotOauthApprovals;
import allthings.iot.dos.oauth2.api.IotOauthApprovalsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-14 18:53
 */
@Service("iotOauthApprovalsService")
public class IotOauthApprovalsServiceImpl implements IotOauthApprovalsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotOauthApprovalsServiceImpl.class);

    @Autowired
    private IotOauthApprovalsDao iotOauthApprovalsDao;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<?> saveIotOauthApprovals(ApprovalsDTO approvalsDTO) {
        IotOauthApprovals iotOauthApprovals = toIotOauthApprovals(approvalsDTO);
        iotOauthApprovals = iotOauthApprovalsDao.saveAndFlush(iotOauthApprovals);
        return ResultDTO.newSuccess(iotOauthApprovals.getIotOauthApprovalsId());
    }

    @Override
    public ResultDTO<?> getIotOauthApprovals(ApprovalsDTO approvalsDTO) {
        String userId = approvalsDTO.getUserId();
        String clientId = approvalsDTO.getClientId();
        if (userId == null) {
            return ResultDTO.newFail("");
        }
        if (clientId == null) {
            return ResultDTO.newFail("");
        }
        List<IotOauthApprovals> iotOauthApprovals =
                iotOauthApprovalsDao.getIotOauthApprovalsByUserIdAndClientId(userId, clientId);

        return ResultDTO.newSuccess(iotOauthApprovals);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<?> updateIotOauthApprovals(ApprovalsDTO approvalsDTO) {
        String userId = approvalsDTO.getUserId();
        String clientId = approvalsDTO.getClientId();
        String scope = approvalsDTO.getScope();
        String status = approvalsDTO.getStatus().name();
        Date expiresAt = approvalsDTO.getExpiresAt();
        Date lastModifiedAt = approvalsDTO.getLastUpdatedAt();
        iotOauthApprovalsDao.updateIotOauthApprovals(expiresAt, status, lastModifiedAt, userId, clientId, scope);
        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<?> updateIotOauthApprovalsExpiresAt(ApprovalsDTO approvalsDTO) {
        Date expiresAt = approvalsDTO.getExpiresAt();
        String userId = approvalsDTO.getUserId();
        String clientId = approvalsDTO.getClientId();
        String scope = approvalsDTO.getScope();
        iotOauthApprovalsDao.updateIotOauthApprovalsExpiresAt(expiresAt, userId, clientId, scope);
        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<?> deleteIotOauthApprovals(ApprovalsDTO approvalsDTO) {
        String userId = approvalsDTO.getUserId();
        String clientId = approvalsDTO.getClientId();
        String scope = approvalsDTO.getScope();
        Date expiresAt = approvalsDTO.getExpiresAt();
        iotOauthApprovalsDao.deleteIotOauthApprovals(userId, clientId, scope, expiresAt);
        return ResultDTO.newSuccess();
    }

    private IotOauthApprovals toIotOauthApprovals(ApprovalsDTO approvalsDTO) {
        IotOauthApprovals iotOauthApprovals = new IotOauthApprovals();
        iotOauthApprovals.setClientId(approvalsDTO.getClientId());
        iotOauthApprovals.setUserId(approvalsDTO.getUserId());
        iotOauthApprovals.setScope(approvalsDTO.getScope());
        iotOauthApprovals.setStatus(approvalsDTO.getStatus().name());
        if (approvalsDTO.getExpiresAt() != null) {
            iotOauthApprovals.setExpiresAt(approvalsDTO.getExpiresAt());
        }
        if (approvalsDTO.getLastUpdatedAt() != null) {
            iotOauthApprovals.setLastModifiedAt(approvalsDTO.getLastUpdatedAt());
        }
        return iotOauthApprovals;
    }
}
