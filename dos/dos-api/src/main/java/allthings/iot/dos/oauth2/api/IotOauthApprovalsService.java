package allthings.iot.dos.oauth2.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.oauth2.ApprovalsDTO;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-14 18:52
 */

public interface IotOauthApprovalsService {
    /**
     * 新增
     *
     * @param approvalsDTO
     * @return
     */
    ResultDTO<?> saveIotOauthApprovals(ApprovalsDTO approvalsDTO);

    /**
     * 通过userId，clientId查询
     *
     * @param approvalsDTO
     * @return
     */
    ResultDTO<?> getIotOauthApprovals(ApprovalsDTO approvalsDTO);

    /**
     * 修改
     *
     * @param approvalsDTO
     * @return
     */
    ResultDTO<?> updateIotOauthApprovals(ApprovalsDTO approvalsDTO);

    /**
     * 修改expiresAt
     *
     * @param approvalsDTO
     * @return
     */
    ResultDTO<?> updateIotOauthApprovalsExpiresAt(ApprovalsDTO approvalsDTO);

    /**
     * 删除
     *
     * @param approvalsDTO
     * @return
     */
    ResultDTO<?> deleteIotOauthApprovals(ApprovalsDTO approvalsDTO);
}
