package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.api.IotOverviewService;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.RoleCode;
import allthings.iot.dos.dao.IotDeviceDao;
import allthings.iot.dos.dao.IotDeviceTypeDao;
import allthings.iot.dos.dao.IotProjectDao;
import allthings.iot.dos.dao.IotUserProjectDao;
import allthings.iot.dos.dto.query.IotDosOverviewDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import allthings.iot.util.redis.ICentralCacheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotOverviewBizImpl
 * @CreateDate :  2018-5-20
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Service("iotOverviewService")
public class IotOverviewServiceImpl implements IotOverviewService {
    @Autowired
    private IotDeviceDao iotDeviceDao;

    @Autowired
    private IotProjectDao iotProjectDao;

    @Autowired
    private IotDeviceTypeDao iotDeviceTypeDao;

    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;

    @Autowired
    private ICentralCacheService cache;

    @Autowired
    private IotUserProjectDao iotUserProjectDao;

    /**
     * 首页总览
     *
     * @param iotUserQueryDTO
     * @return
     */
    @Override
    public ResultDTO<IotDosOverviewDTO> overview(IotUserQueryDTO iotUserQueryDTO) {
        IotDosOverviewDTO iotDosOverviewDTO = new IotDosOverviewDTO();
        Long iotUserId = iotUserQueryDTO.getCreateOperatorId();
        String roleCode = iotUserQueryDTO.getRoleCode();
        Long projectCounts;
        Long deviceTypeCounts;
        Long deviceCounts;
        Long totalPointCounts;
        if (RoleCode.ADMIN.equals(roleCode)) {
            projectCounts = iotProjectDao.getIotProjectCount();
            deviceTypeCounts = iotDeviceTypeDao.getDeviceTypeCount();
            deviceCounts = iotDeviceDao.getDeviceCountByAdmin();
            List<IotProjectSimpleDTO> iotProjectSimpleDTOS = iotProjectDao.getProjectNameByDeleted(false);
            totalPointCounts = getProjectCount(iotProjectSimpleDTOS);
        } else {
            List<IotProjectSimpleDTO> iotProjectSimpleDTOS = iotUserProjectDao.getProjectListByIotUserId(iotUserId);
            totalPointCounts = getProjectCount(iotProjectSimpleDTOS);
            projectCounts = iotProjectDao.getIotProjectCountByIotUserId(iotUserId);
            deviceTypeCounts = iotDeviceTypeDao.getTotalCount(iotUserId);
            deviceCounts = iotDeviceDao.getDeviceCount(iotUserId);
        }
        iotDosOverviewDTO.setProjectCounts(projectCounts);
        iotDosOverviewDTO.setDeviceTypeCounts(deviceTypeCounts);
        iotDosOverviewDTO.setDeviceCounts(deviceCounts);
        iotDosOverviewDTO.setTotalPointCounts(totalPointCounts);
        return ResultDTO.newSuccess(iotDosOverviewDTO);
    }

    private Long getProjectCount(List<IotProjectSimpleDTO> iotProjectSimpleDTOS) {
        Long total = 0L;
        if (CollectionUtils.isEmpty(iotProjectSimpleDTOS)) {
            return total;
        }
        for (IotProjectSimpleDTO iotProjectSimpleDTO : iotProjectSimpleDTOS) {
            String projectPoint = cache.getMapField(Constants.DOS_PROJECT_POINT_TOTAL_COUNT_KEY,
                    String.valueOf(iotProjectSimpleDTO.getIotProjectId()), String.class);
            if (StringUtils.isNotBlank(projectPoint)) {
                total += Long.parseLong(projectPoint);
            }
        }
        return total;
    }

    /**
     * 项目总览
     *
     * @param iotUserQueryDTO
     * @return
     */
    @Override
    public ResultDTO<IotDosOverviewDTO> overviewByIotProjectId(IotUserQueryDTO iotUserQueryDTO) {
        Long iotUserId = iotUserQueryDTO.getCreateOperatorId();
        Long iotProjectId = iotUserQueryDTO.getIotProjectId();
        String roleCode = iotUserQueryDTO.getRoleCode();

        ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotProjectId, iotUserId, roleCode);
        if (!result.isSuccess()) {
            return ResultDTO.newFail(result.getCode(), result.getMsg());
        }

        Long totalPointCounts = 0L;
        Long addedTodayPointCounts = 0L;
        IotDosOverviewDTO iotDosOverviewDTO = new IotDosOverviewDTO();
        String total = cache.getMapField(Constants.DOS_PROJECT_POINT_TOTAL_COUNT_KEY, String.valueOf(iotProjectId), String.class);
        if (StringUtils.isNotBlank(total)) {
            totalPointCounts = Long.parseLong(total);
        }
        String added = cache.getMapField(Constants.DOS_PROJECT_POINT_TODAY_COUNT_KEY, String.valueOf(iotProjectId), String.class);
        if (StringUtils.isNotBlank(added)) {
            addedTodayPointCounts = Long.parseLong(added);
        }

        iotDosOverviewDTO.setTotalPointCounts(totalPointCounts);
        iotDosOverviewDTO.setDeviceTypeCounts(iotDeviceTypeDao.countByIotProjectIdAndIsDeleted(iotProjectId, false));
        iotDosOverviewDTO.setDeviceCounts(iotDeviceDao.countByIotProjectIdAndIsDeleted(iotProjectId, false));
        iotDosOverviewDTO.setOnlineDeviceCounts(iotDeviceDao.getOnlineCountByRange(iotProjectId));
        iotDosOverviewDTO.setAddedTodayPointCounts(addedTodayPointCounts);

        return ResultDTO.newSuccess(iotDosOverviewDTO);
    }
}
