package allthings.iot.dos.service;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.Result;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.des.client.IotDesFeignClient;
import allthings.iot.des.dto.query.IotDesDeviceEventListQueryDto;
import allthings.iot.des.dto.query.IotDesEventDetailDto;
import allthings.iot.dos.DosConstant;
import allthings.iot.dos.api.IotDeviceSaveMessageService;
import allthings.iot.dos.api.IotDeviceService;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.constant.RoleCode;
import allthings.iot.dos.consumer.IotLoggerProducer;
import allthings.iot.dos.dao.IotDeviceCountDao;
import allthings.iot.dos.dao.IotDeviceCountTagDao;
import allthings.iot.dos.dao.IotDeviceDao;
import allthings.iot.dos.dao.IotDevicePassDao;
import allthings.iot.dos.dao.IotDevicePassQueryDao;
import allthings.iot.dos.dao.IotDeviceQueryDao;
import allthings.iot.dos.dao.IotDeviceStatusDao;
import allthings.iot.dos.dao.IotDeviceTagDao;
import allthings.iot.dos.dao.IotDeviceTypeDao;
import allthings.iot.dos.dao.IotDeviceTypeTagDao;
import allthings.iot.dos.dao.IotLoggerTypeDao;
import allthings.iot.dos.dao.IotProjectDao;
import allthings.iot.dos.dao.IotProjectDeviceTypeDao;
import allthings.iot.dos.dao.IotProtocolDao;
import allthings.iot.dos.dao.IotUserDao;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceDetailDTO;
import allthings.iot.dos.dto.IotDeviceErrorMsgDTO;
import allthings.iot.dos.dto.IotDeviceRegisterBatchDTO;
import allthings.iot.dos.dto.IotDeviceSaveBatchDTO;
import allthings.iot.dos.dto.IotDeviceSimpleDTO;
import allthings.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import allthings.iot.dos.dto.IotDeviceStatusDTO;
import allthings.iot.dos.dto.IotOpenApiResponseDeviceDTO;
import allthings.iot.dos.dto.IotUserProjectDTO;
import allthings.iot.dos.dto.query.IotDeviceCountQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceCountTitleDTO;
import allthings.iot.dos.dto.query.IotDeviceDetailQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceEventDetailQueryDto;
import allthings.iot.dos.dto.query.IotDeviceHistoryQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceListBaseQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceLocationQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceLoggerQueryListDto;
import allthings.iot.dos.dto.query.IotDeviceMonitorQueryListDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryByCodeDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryListDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusCountDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusQueryDTO;
import allthings.iot.dos.dto.query.IotDosDeviceEventQueryListDto;
import allthings.iot.dos.dto.query.IotDosQueryDTO;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotLoggerSaveDto;
import allthings.iot.dos.dto.query.IotProjectDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotProtocolQueryDTO;
import allthings.iot.dos.exception.IllegalArgumentException;
import allthings.iot.dos.model.IotDevice;
import allthings.iot.dos.model.IotDeviceStatus;
import allthings.iot.dos.model.IotDeviceTag;
import allthings.iot.dos.model.IotDeviceType;
import allthings.iot.dos.model.IotDeviceTypeTag;
import allthings.iot.dos.model.IotProject;
import allthings.iot.dos.model.IotProtocol;
import allthings.iot.dos.validate.IotDeviceValidate;
import allthings.iot.ktv.client.KtvDataClient;
import allthings.iot.ktv.common.Constant;
import allthings.iot.ktv.common.dto.IotKtvFactorReportTimeDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;
import allthings.iot.util.gps.enums.CoorType;
import allthings.iot.util.gps.util.GpsUtil;
import allthings.iot.vehicle.client.VehicleDataApi;
import allthings.iot.vehicle.common.dto.GpsDto;
import allthings.iot.vehicle.common.dto.GpsQueryDto;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author :  luhao
 * @FileName :  IotDeviceBizImpl
 * @CreateDate :  2018/5/4
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
@Service(value = "iotDeviceService")
public class IotDeviceServiceImpl implements IotDeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotDeviceServiceImpl.class);
    @Autowired
    private IotDeviceDao iotDeviceDao;
    @Autowired
    private IotDeviceQueryDao iotDeviceQueryDao;
    @Autowired
    private IotDeviceCountDao iotDeviceCountDao;
    @Autowired
    private IotDeviceCountTagDao iotDeviceCountTagDao;
    @Autowired
    private IotDeviceTypeTagDao iotDeviceTypeTagDao;
    @Autowired
    private IotDeviceStatusDao iotDeviceStatusDao;
    @Autowired
    private IotProjectDeviceTypeDao iotProjectDeviceTypeDao;
    @Autowired
    private VehicleDataApi vehicleDataService;
    @Autowired
    private IotDeviceTagDao iotDeviceTagDao;
    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;
    @Autowired
    private KtvDataClient ktvDataClient;
    @Autowired
    private IotUserDao iotUserDao;
    @Autowired
    private IotDeviceTypeDao iotDeviceTypeDao;
    @Autowired
    private IotProtocolDao iotProtocolDao;
    @Autowired
    private IotDeviceSaveMessageService iotDeviceSaveMessageService;
    @Autowired
    private IotDesFeignClient desFeignClient;
    @Autowired
    private IotDevicePassDao iotDevicePassDao;
    @Autowired
    private IotLoggerProducer producer;
    @Autowired
    private IotLoggerTypeDao iotLoggerTypeDao;
    @Autowired
    private IotDevicePassQueryDao iotDevicePassQueryDao;
    @Autowired
    private IotProjectDao iotProjectDao;

    @Value("${zookeeper.connectString}")
    private String zkString;

    public static String getMD5String(String str) {
        return DigestUtils.md5Hex(str);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveDeviceStatus(DeviceConnectionMsg msg) {
        try {
            List<IotDevice> iotDevices = iotDeviceDao.getIotDeviceByDeviceCode(msg.getSourceDeviceId());
            if (CollectionUtils.isEmpty(iotDevices)) {
                iotDevices = iotDeviceDao.getIotDeviceByDeviceCode(msg.getSourceDeviceType() + msg.getSourceDeviceId());
                if (CollectionUtils.isEmpty(iotDevices)) {
                    IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();

                    iotDeviceDTO.setIotDeviceTypeId(Constants.LONG_OF_NULL);
                    iotDeviceDTO.setIotProjectId(Constants.LONG_OF_NULL);
                    iotDeviceDTO.setRegisterStatus(false);
                    iotDeviceDTO.setDeviceCode(msg.getSourceDeviceId());
                    iotDeviceDTO.setCreateOperatorId(0L);
                    iotDeviceDTO.setDeviceName("");
                    iotDeviceDTO.setDeviceId(getMD5String(msg.getSourceDeviceType() + iotDeviceDTO.getDeviceCode()));
                    iotDeviceDTO.setBizCode("");
                    dealIotDeviceDTO(iotDeviceDTO);
                    iotDevices.add(iotDeviceDao.saveAndFlush(getIotDevice(iotDeviceDTO)));

                    IotDeviceStatus iotDeviceStatus = new IotDeviceStatus();
                    iotDeviceStatus.setCreateOperatorId(1L);
                    iotDeviceStatus.setModifyOperatorId(1L);
                    iotDeviceStatus.setConnected(false);
                    iotDeviceStatus.setIotDeviceId(iotDevices.get(0).getIotDeviceId());
                    iotDeviceStatus.setNodeId(StringUtils.stripToEmpty(msg.getDasNodeId()));
                    iotDeviceStatus.setTerminalIp(StringUtils.stripToEmpty(msg.getTerminalIp()));
                    iotDeviceStatusDao.save(iotDeviceStatus);

                }
            }

            for (IotDevice iotDevice : iotDevices) {
                if (msg.isConnected()) {
                    iotDeviceStatusDao.updateIotDeviceOnlineStatus(iotDevice.getIotDeviceId(),
                            StringUtils.stripToEmpty(msg.getDasNodeId()),
                            StringUtils.stripToEmpty(msg.getTerminalIp()),
                            true, new Date(msg.getOccurTime()), false);
                } else {
                    iotDeviceStatusDao.updateIotDeviceOfflineStatus(iotDevice.getIotDeviceId(),
                            StringUtils.stripToEmpty(msg.getDasNodeId()),
                            StringUtils.stripToEmpty(msg.getTerminalIp()),
                            false, new Date(msg.getOccurTime()), false);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Long> saveIotDevice(IotDeviceDTO iotDeviceDTO) {
        try {

            ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotDeviceDTO.getIotProjectId(),
                    iotDeviceDTO.getCreateOperatorId(), iotDeviceDTO.getRoleCode());
            if (!result.isSuccess()) {
                return ResultDTO.newFail(result.getCode(), result.getMsg());
            }

            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client = CuratorFrameworkFactory.newClient(zkString, retryPolicy);
            client.start();
            //项目与设备设置成分布式锁
            InterProcessMutex lock = new InterProcessMutex(client, "/curator/lock/" + iotDeviceDTO.getDeviceCode() + "/" + iotDeviceDTO.getIotProjectId());
            try {
                lock.acquire();
                IotDeviceValidate.validateSave(iotDeviceDTO, false);
                Long iotDeviceId = iotDeviceDao.getIotDeviceIdByDeviceCode(iotDeviceDTO.getDeviceCode(), iotDeviceDTO.getIotProjectId());
                if (iotDeviceId != null) {
                    LOGGER.warn("device code {} duplicate", iotDeviceDTO.getDeviceCode());
                    return ResultDTO.newFail(ErrorCode.ERROR_3010.getCode(), ErrorCode.ERROR_3010.getMessage());
                }

                if (iotDeviceDTO.getRegisterStatus() == null) {
                    iotDeviceDTO.setRegisterDate(new Date());
                    iotDeviceDTO.setRegisterStatus(true);
                }

                iotDeviceId = iotDeviceDao.getIotDeviceIdByDeviceCode(iotDeviceDTO.getDeviceCode(), Constants.LONG_OF_NULL);
                if (iotDeviceId != null) {
                    iotDeviceDTO.setIotDeviceId(iotDeviceId);
                    updateIotDevice(iotDeviceDTO);
                } else {
                    List<IotProtocolQueryDTO> protocols = iotProtocolDao.getAllByIsDeleted(false);
                    batchSaveSetDeviceId(protocols, iotDeviceDTO);

                    dealIotDeviceDTO(iotDeviceDTO);
                    iotDeviceId = iotDeviceDao.saveAndFlush(getIotDevice(iotDeviceDTO)).getIotDeviceId();
                    iotDeviceDTO.setIotDeviceId(iotDeviceId);

                    //新增设备状态信息
                    IotDeviceStatus iotDeviceStatus = new IotDeviceStatus();
                    iotDeviceStatus.setCreateOperatorId(1L);
                    iotDeviceStatus.setModifyOperatorId(1L);
                    iotDeviceStatus.setConnected(false);
                    iotDeviceStatus.setIotDeviceId(iotDeviceId);
                    iotDeviceStatus.setTerminalIp("");
                    iotDeviceStatus.setNodeId("");
                    iotDeviceStatusDao.save(iotDeviceStatus);

                    iotDeviceSaveMessageService.sendMessage(Lists.newArrayList(iotDeviceDTO.getDeviceId()), iotDeviceDTO.getIotProjectId());
                }

                List<IotDeviceTag> iotDeviceTags = new ArrayList<>();
                iotDeviceTagDao.deleteAllByDeviceId(iotDeviceId, iotDeviceDTO.getCreateOperatorId());
                if (!CollectionUtils.isEmpty(iotDeviceDTO.getIotTagIds())) {
                    for (Long tagId : iotDeviceDTO.getIotTagIds()) {
                        IotDeviceTag iotDeviceTag = new IotDeviceTag();
                        iotDeviceTag.setIotTagId(tagId);
                        iotDeviceTag.setIotDeviceId(iotDeviceId);
                        iotDeviceTag.setCreateOperatorId(iotDeviceDTO.getCreateOperatorId());
                        iotDeviceTags.add(iotDeviceTag);
                    }

                    if (!CollectionUtils.isEmpty(iotDeviceTags)) {
                        iotDeviceTagDao.saveAll(iotDeviceTags);
                    }
                }

//                GpsDto gpsDto = getGpsDto(getIotDevice(iotDeviceDTO));
//                if (gpsDto != null) {
//                    vehicleDataService.saveGps(Lists.newArrayList(gpsDto));
//                }
                // 添加设备新增日志
                saveDeviceInfoLogger(iotDeviceDTO, true);
                return ResultDTO.newSuccess(iotDeviceId);
            } catch (InterruptedException e) {
                LOGGER.error("保存设备异常，参数 {}, 异常信息 {}", JSON.toJSONString(iotDeviceDTO), e.toString());
                return ResultDTO.newFail(ErrorCode.ERROR_3000.getCode(), ErrorCode.ERROR_3000.getMessage());
            } finally {
                lock.release();
                client.close();
            }
        } catch (IllegalArgumentException ie) {
            LOGGER.error("parameters validate illegal:{}", ie);
            return ResultDTO.newFail(ie.getErrorCode(), ie.toString());
        } catch (Exception ee) {
            LOGGER.error("save device error:{}", ee);
            return ResultDTO.newFail(ErrorCode.ERROR_3000.getCode(), ErrorCode.ERROR_3000.getMessage());
        }
    }

    private void dealIotDeviceDTO(IotDeviceDTO iotDeviceDTO) {
        if (iotDeviceDTO.getBizCode() == null) {
            iotDeviceDTO.setBizCode("");
        }
        if (iotDeviceDTO.getDescription() == null) {
            iotDeviceDTO.setDescription("");
        }
        if (iotDeviceDTO.getDeviceName() == null) {
            iotDeviceDTO.setDeviceName("");
        }
        if (iotDeviceDTO.getFirmwareModel() == null) {
            iotDeviceDTO.setFirmwareModel("");
        }
        if (iotDeviceDTO.getFirmwareVersion() == null) {
            iotDeviceDTO.setFirmwareVersion("");
        }
        if (iotDeviceDTO.getMac() == null) {
            iotDeviceDTO.setMac("");
        }
        if (iotDeviceDTO.getCreateOperatorId() == null) {
            iotDeviceDTO.setCreateOperatorId(0L);
        }
        if (iotDeviceDTO.getModifyOperatorId() == null) {
            iotDeviceDTO.setModifyOperatorId(0L);
        }
        if (iotDeviceDTO.getAgencyName() == null) {
            iotDeviceDTO.setAgencyName(StringUtils.EMPTY);
        }
    }

    /**
     * 处理gps数据
     *
     * @param iotDevice
     */
    private GpsDto getGpsDto(IotDevice iotDevice) {
        Double latitude = iotDevice.getLatitude();
        Double longitude = iotDevice.getLongitude();
        if (latitude != null && longitude != null) {
            double[] coor = GpsUtil.gpsConvertor(longitude, latitude, CoorType.BD09LL, CoorType.WGS84);

            GpsDto gpsDto = new GpsDto();
            gpsDto.setLatitude(String.valueOf(coor[1]));
            gpsDto.setLongitude(String.valueOf(coor[0]));
            gpsDto.setDeviceId(iotDevice.getDeviceCode());
            gpsDto.setRealtime(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(System.currentTimeMillis()));
            gpsDto.setPartyid(Constant.CONST_PARTY_ID);

            return gpsDto;
        }

        return null;
    }

    /**
     * 批量导入设备
     *
     * @param iotDeviceSaveBatchDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<List<IotDeviceErrorMsgDTO>> saveIotDevice(IotDeviceSaveBatchDTO iotDeviceSaveBatchDTO) {
        try {
            Long iotProjectId = iotDeviceSaveBatchDTO.getIotProjectId();

            ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotProjectId,
                    iotDeviceSaveBatchDTO.getCreateOperatorId(), iotDeviceSaveBatchDTO.getRoleCode());
            if (!result.isSuccess()) {
                return ResultDTO.newFail(result.getCode(), result.getMsg());
            }

            List<IotProjectDeviceTypeDTO> deviceTypeList = iotProjectDeviceTypeDao.getIotDeviceTypeByIotProjectIdAndDeleted(iotProjectId);

            List<IotDevice> deviceList = Lists.newArrayList();
            List<IotDeviceErrorMsgDTO> errorMsgDTOList = Lists.newArrayList();
            List<IotDeviceDTO> updateDeviceList = new ArrayList<>();
            List<IotProtocolQueryDTO> protocols = iotProtocolDao.getAllByIsDeleted(false);
            for (IotDeviceDTO iotDeviceDTO : iotDeviceSaveBatchDTO.getIotDeviceList()) {
                iotDeviceDTO.setEnabled(true);
                iotDeviceDTO.setIotProjectId(iotProjectId);
                IotDeviceErrorMsgDTO dto = IotDeviceValidate.validateSave(iotDeviceDTO, true);

                if (dto == null) {
                    dto = new IotDeviceErrorMsgDTO();
                    dto.setRowNum(iotDeviceDTO.getRowNum());
                }
                dto.setDeviceCode(iotDeviceDTO.getDeviceCode());
                IotProjectDeviceTypeDTO deviceTypeDTO = matchDeviceType(iotDeviceDTO.getDeviceTypeName(), deviceTypeList);
                if (deviceTypeDTO != null) {
                    iotDeviceDTO.setIotDeviceTypeId(deviceTypeDTO.getIotDeviceTypeId());
                } else {
                    dto.getErrorMsgList().add(String.format(ErrorCode.ERROR_3019.getMessage(), iotDeviceDTO.getDeviceTypeName()));
                    errorMsgDTOList.add(dto);
                    continue;
                }

                Long iotDeviceId = iotDeviceDao.getIotDeviceIdByDeviceCode(iotDeviceDTO.getDeviceCode(), iotProjectId);
                batchSaveSetDeviceId(protocols, iotDeviceDTO);

                if (CollectionUtils.isEmpty(dto.getErrorMsgList()) && iotDeviceId == null) {
                    iotDeviceId = iotDeviceDao.getIotDeviceIdByDeviceCode(iotDeviceDTO.getDeviceCode(), Constants.LONG_OF_NULL);
                    if (iotDeviceId == null) {
                        dealIotDeviceDTO(iotDeviceDTO);
                        iotDeviceDTO.setRegisterStatus(true);
                        iotDeviceDTO.setRegisterDate(new Date());
                        deviceList.add(getIotDevice(iotDeviceDTO));
                    } else {
                        iotDeviceDTO.setIotDeviceId(iotDeviceId);
                        iotDeviceDTO.setRegisterStatus(true);
                        iotDeviceDTO.setRegisterDate(new Date());
                        updateDeviceList.add(iotDeviceDTO);
                    }
                } else if (CollectionUtils.isEmpty(dto.getErrorMsgList()) && iotDeviceId != null) {
                    iotDeviceDTO.setIotDeviceId(iotDeviceId);
                    updateDeviceList.add(iotDeviceDTO);
                } else {
                    errorMsgDTOList.add(dto);
                }
            }

            if (errorMsgDTOList.size() > 0) {
                ResultDTO<List<IotDeviceErrorMsgDTO>> resultDTO = ResultDTO.newFail(ErrorCode.ERROR_3036.getCode(), ErrorCode.ERROR_3036.getMessage());
                resultDTO.setData(errorMsgDTOList);
                return resultDTO;
            }

            if (deviceList.size() > 0) {
                dealSaveBatch(deviceList);
                iotDeviceDao.saveAll(deviceList);

//                List<GpsDto> gpsDtoList = Lists.newArrayList();
//                for (IotDevice iotDevice : deviceList) {
//                    GpsDto gpsDto = getGpsDto(iotDevice);
//                    if (gpsDto != null) {
//                        gpsDtoList.add(gpsDto);
//                    }
//                }
//                if (!CollectionUtils.isEmpty(gpsDtoList)) {
//                    vehicleDataService.saveGps(gpsDtoList);
//                }
            }

            if (updateDeviceList.size() > 0) {
                for (IotDeviceDTO dto : updateDeviceList) {
                    updateIotDevice(dto);
                }
            }

            // 保存日志
            for (IotDevice device : deviceList) {
                IotDeviceDTO deviceDTO = new IotDeviceDTO();
                BeanUtils.copyProperties(device, deviceDTO);
                saveDeviceInfoLogger(deviceDTO, true);

                IotDeviceStatus iotDeviceStatus = new IotDeviceStatus();
                iotDeviceStatus.setCreateOperatorId(1L);
                iotDeviceStatus.setModifyOperatorId(1L);
                iotDeviceStatus.setConnected(false);
                iotDeviceStatus.setIotDeviceId(deviceDTO.getIotDeviceId());
                iotDeviceStatus.setNodeId("");
                iotDeviceStatus.setTerminalIp("");

                iotDeviceStatusDao.save(iotDeviceStatus);
            }

            return ResultDTO.newSuccess(Lists.newArrayList());
        } catch (Exception ie) {
            LOGGER.error("save device error:", ie);
            return ResultDTO.newFail(ErrorCode.ERROR_3000.getCode(), ErrorCode.ERROR_3000.getMessage());
        }
    }

    /**
     * 开放平台批量新增设备
     *
     * @param iotDeviceList
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<List<IotOpenApiResponseDeviceDTO>> saveOpenIotDevice(Long iotProjectId, List<IotDeviceDTO> iotDeviceList) throws Exception {
        List<IotDevice> deviceList = Lists.newArrayList();
        List<IotDeviceErrorMsgDTO> errorMsgDTOList = Lists.newArrayList();
        List<IotDeviceDTO> updateDeviceList = new ArrayList<>();
        List<IotProtocolQueryDTO> protocols = iotProtocolDao.getAllByIsDeleted(false);
        for (IotDeviceDTO iotDeviceDTO : iotDeviceList) {
            iotDeviceDTO.setEnabled(true);
            iotDeviceDTO.setIotProjectId(iotProjectId);
            IotDeviceErrorMsgDTO dto = IotDeviceValidate.validateSave(iotDeviceDTO, true);

            if (dto == null) {
                dto = new IotDeviceErrorMsgDTO();
            }
            if (StringUtils.isEmpty(iotDeviceDTO.getDeviceCode())) {
                errorMsgDTOList.add(dto);
                continue;
            }
            dto.setDeviceCode(iotDeviceDTO.getDeviceCode());

            Long iotDeviceId = iotDeviceDao.getIotDeviceIdByDeviceCode(iotDeviceDTO.getDeviceCode(), iotProjectId);
            batchSaveSetDeviceId(protocols, iotDeviceDTO);

            if (CollectionUtils.isEmpty(dto.getErrorMsgList()) && iotDeviceId == null) {
                iotDeviceId = iotDeviceDao.getIotDeviceIdByDeviceCode(iotDeviceDTO.getDeviceCode(),
                        Constants.LONG_OF_NULL);
                if (iotDeviceId == null) {

                    dealIotDeviceDTO(iotDeviceDTO);
                    iotDeviceDTO.setRegisterStatus(true);
                    iotDeviceDTO.setRegisterDate(new Date());
                    deviceList.add(getIotDevice(iotDeviceDTO));
                } else {
                    iotDeviceDTO.setRegisterStatus(true);
                    iotDeviceDTO.setRegisterDate(new Date());
                    iotDeviceDTO.setIotDeviceId(iotDeviceId);
                    updateDeviceList.add(iotDeviceDTO);
                }
            } else if (CollectionUtils.isEmpty(dto.getErrorMsgList()) && iotDeviceId != null) {
                iotDeviceDTO.setIotDeviceId(iotDeviceId);
                updateDeviceList.add(iotDeviceDTO);
            } else {
                errorMsgDTOList.add(dto);
            }
        }

        if (errorMsgDTOList.size() > 0) {
            ResultDTO resultDTO = ResultDTO.newFail(ErrorCode.ERROR_3036.getCode(), ErrorCode.ERROR_3036.getMessage());
            resultDTO.setData(errorMsgDTOList);
            return resultDTO;
        }

        if (deviceList.size() > 0) {
            dealSaveBatch(deviceList);
            iotDeviceDao.saveAll(deviceList);
//            List<GpsDto> gpsDtoList = Lists.newArrayList();
//            for (IotDevice iotDevice : deviceList) {
//                GpsDto gpsDto = getGpsDto(iotDevice);
//                if (gpsDto != null) {
//                    gpsDtoList.add(gpsDto);
//                }
//            }
//            if (!CollectionUtils.isEmpty(gpsDtoList)) {
//                vehicleDataService.saveGps(gpsDtoList);
//            }
        }

        if (updateDeviceList.size() > 0) {
            for (IotDeviceDTO dto : updateDeviceList) {
                updateIotDevice(dto);
            }
        }

        // 保存日志
        for (IotDevice device : deviceList) {
            IotDeviceDTO deviceDTO = new IotDeviceDTO();
            BeanUtils.copyProperties(device, deviceDTO);
            saveDeviceInfoLogger(deviceDTO, true);

            IotDeviceStatus iotDeviceStatus = new IotDeviceStatus();
            iotDeviceStatus.setCreateOperatorId(1L);
            iotDeviceStatus.setModifyOperatorId(1L);
            iotDeviceStatus.setConnected(false);
            iotDeviceStatus.setIotDeviceId(deviceDTO.getIotDeviceId());
            iotDeviceStatus.setNodeId("");
            iotDeviceStatus.setTerminalIp("");
            iotDeviceStatusDao.save(iotDeviceStatus);
        }
        return ResultDTO.newSuccess(null);
    }

    private void batchSaveSetDeviceId(List<IotProtocolQueryDTO> protocols, IotDeviceDTO iotDeviceDTO) {
        for (IotProtocolQueryDTO iotProtocolQueryDTO : protocols) {
            if (iotDeviceDTO.getDeviceCode().startsWith(iotProtocolQueryDTO.getProtocolCode())) {
                iotDeviceDTO.setDeviceId(getMD5String(iotDeviceDTO.getDeviceCode()));
                break;
            }
        }

        if (StringUtils.isEmpty(iotDeviceDTO.getDeviceId())) {
            IotDeviceType deviceType = iotDeviceTypeDao.getIotDeviceTypeByIotDeviceTypeId(iotDeviceDTO.getIotDeviceTypeId(), iotDeviceDTO.getIotProjectId());
            IotProtocol protocol = iotProtocolDao.getByIotProtocolId(deviceType.getIotProtocolId());
            iotDeviceDTO.setDeviceId(getMD5String(protocol.getProtocolCode() + iotDeviceDTO.getDeviceCode()));
        }
    }

    private void dealSaveBatch(List<IotDevice> saveDeviceList) {
        List<IotDevice> addDevices = new ArrayList<>();
        List<IotDevice> removeDevices = new ArrayList<>();
        Set<String> sameDevcieCode = new LinkedHashSet<>();
        for (int i = 0; i < saveDeviceList.size() - 1; i++) {
            IotDevice temp = new IotDevice();
            if (!sameDevcieCode.contains(saveDeviceList.get(i).getDeviceCode())) {
                for (int j = i + 1; j < saveDeviceList.size(); j++) {
                    if (saveDeviceList.get(j).getDeviceCode().equals(saveDeviceList.get(i).getDeviceCode())) {
                        removeDevices.add(saveDeviceList.get(i));
                        removeDevices.add(saveDeviceList.get(j));
                        BeanUtils.copyProperties(saveDeviceList.get(j), temp);
                        sameDevcieCode.add(temp.getDeviceCode());
                    }
                }
                if (!StringUtils.isEmpty(temp.getDeviceCode())) {
                    addDevices.add(temp);
                }
            }
        }
        if (!CollectionUtils.isEmpty(addDevices)) {
            saveDeviceList.removeAll(removeDevices);
            saveDeviceList.addAll(addDevices);
        }
    }

    /**
     * 判断项目是否包含该设备类型
     *
     * @param deviceTypeName
     * @param dtoList
     * @return
     */
    private IotProjectDeviceTypeDTO matchDeviceType(String deviceTypeName, List<IotProjectDeviceTypeDTO> dtoList) {
        for (IotProjectDeviceTypeDTO deviceTypeDTO : dtoList) {
            if (deviceTypeName.equals(deviceTypeDTO.getDeviceTypeName())) {
                return deviceTypeDTO;
            }
        }

        return null;
    }

    /**
     * 组装设备模型
     *
     * @param iotDeviceDTO
     * @return
     */
    private IotDevice getIotDevice(IotDeviceDTO iotDeviceDTO) {
        IotDevice iotDevice = new IotDevice();
        BeanUtils.copyProperties(iotDeviceDTO, iotDevice);
        if (iotDeviceDTO.getRegisterDate() != null) {
            iotDevice.setRegisterDate(new Date(iotDeviceDTO.getRegisterDate()));
        }

        iotDevice.setEnabled(true);
        String extendProperties = iotDeviceDTO.getExtendProperties() ==
                null ? null : JSON.toJSONString(iotDeviceDTO.getExtendProperties());
        iotDevice.setExtendProperties(extendProperties);
        return iotDevice;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Long> updateIotDevice(IotDeviceDTO iotDeviceDTO) {
        try {
            ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotDeviceDTO.getIotProjectId(),
                    iotDeviceDTO.getCreateOperatorId(), iotDeviceDTO.getRoleCode());
            if (!result.isSuccess()) {
                return ResultDTO.newFail(result.getCode(), result.getMsg());
            }
            IotDeviceValidate.validateUpdate(iotDeviceDTO, false);

            Long iotProjectId = iotDeviceDTO.getIotProjectId();
            Long iotDeviceTypeId = iotDeviceDTO.getIotDeviceTypeId();
            Long iotDeviceId = iotDeviceDTO.getIotDeviceId();
            List<Long> iotTagIds = iotDeviceDTO.getIotTagIds();

            IotDeviceValidate.validateSave(iotDeviceDTO, false);
            dealIotDeviceDTO(iotDeviceDTO);
            String extendProperties = iotDeviceDTO.getExtendProperties() == null ? null : JSON.toJSONString(iotDeviceDTO.getExtendProperties());
            if (iotDeviceDTO.getRegisterStatus() != null) {
                iotDeviceDao.updateIotDeviceRegister(iotDeviceId, iotDeviceDTO.getDeviceName(),
                        iotDeviceDTO.getBizCode(), iotProjectId, iotDeviceTypeId, iotDeviceDTO.getFirmwareModel(),
                        iotDeviceDTO.getFirmwareVersion(), iotDeviceDTO.getMac(), iotDeviceDTO.getLatitude(),
                        iotDeviceDTO.getLongitude(), iotDeviceDTO.getDescription(), iotDeviceDTO.getCreateOperatorId());

                iotDeviceDao.updateIotDeviceRegisterBatch(Lists.newArrayList(iotDeviceId), iotProjectId, iotDeviceTypeId,
                        iotDeviceDTO.getCreateOperatorId());
                ResultDTO<String> bizReturn = getDeviceIdByDeviceCode(iotDeviceDTO.getDeviceCode());
                if (bizReturn.isSuccess()) {
                    String deviceId = bizReturn.getData();
                    iotDeviceSaveMessageService.sendMessage(Lists.newArrayList(deviceId), iotProjectId);
                }
            } else {
                iotDeviceDao.updateIotDevice(iotDeviceId, iotDeviceDTO.getDeviceName(),
                        iotDeviceDTO.getBizCode(), iotProjectId, iotDeviceTypeId, iotDeviceDTO.getFirmwareModel(),
                        iotDeviceDTO.getFirmwareVersion(), iotDeviceDTO.getMac(), iotDeviceDTO.getLatitude(),
                        iotDeviceDTO.getLongitude(), iotDeviceDTO.getDescription(), iotDeviceDTO.getCreateOperatorId(),
                        extendProperties,
                        iotDeviceDTO.getAgencyName());
            }

            IotProtocol protocol = iotProtocolDao.getByDeviceTypeId(iotDeviceTypeId);
            IotDevice device = iotDeviceDao.getByIotDeviceId(iotDeviceId);
            iotDeviceDao.updateDeviceId(iotDeviceId, getMD5String(protocol.getProtocolCode() + device.getDeviceCode()));
            if (iotDeviceDTO.getBatch() == null || iotDeviceDTO.getBatch() == false) {
                List<IotDeviceTag> iotDeviceTags = new ArrayList<>();
                iotDeviceTagDao.deleteAllByDeviceId(iotDeviceId, iotDeviceDTO.getCreateOperatorId());
                if (!CollectionUtils.isEmpty(iotTagIds)) {
                    for (Long tagId : iotTagIds) {
                        IotDeviceTag iotDeviceTag = new IotDeviceTag();
                        iotDeviceTag.setIotTagId(tagId);
                        iotDeviceTag.setIotDeviceId(iotDeviceId);
                        iotDeviceTag.setCreateOperatorId(iotDeviceDTO.getCreateOperatorId());
                        iotDeviceTags.add(iotDeviceTag);
                    }
                    if (!CollectionUtils.isEmpty(iotDeviceTags)) {
                        iotDeviceTagDao.saveAll(iotDeviceTags);
                    }
                }
            }

//            GpsDto gpsDto = getGpsDto(getIotDevice(iotDeviceDTO));
//            if (gpsDto != null) {
//                //更新
//                vehicleDataService.saveGps(Lists.newArrayList(gpsDto));
//            }

            // 保存设备信息更新日志
            saveDeviceInfoLogger(iotDeviceDTO, false);

            return ResultDTO.newSuccess(iotDeviceDTO.getIotDeviceId());
        } catch (IllegalArgumentException ie) {
            LOGGER.error("parameters validate illegal:{}", ie);
            return ResultDTO.newFail(ie.getErrorCode(), ie.toString());
        } catch (Exception ee) {
            LOGGER.error("update device error:{}", ee);
            return ResultDTO.newFail(ErrorCode.ERROR_3002.getCode(),
                    ErrorCode.ERROR_3002.getMessage());
        }
    }

    private void saveDeviceInfoLogger(IotDeviceDTO deviceDTO, boolean isAdd) {
        Long iotLoggerTypeId = iotLoggerTypeDao.getIotLoggerTypeIdByLoggerTypeCode(Constants.DEVICE_LOGGER_TYPE);
        IotLoggerSaveDto logger = new IotLoggerSaveDto(deviceDTO.getCreateOperatorId(), deviceDTO.getModifyOperatorId(),
                System.currentTimeMillis(), deviceDTO.getCreateOperatorId(), iotLoggerTypeId,
                IotDevice.class.getSimpleName(),
                deviceDTO.getIotDeviceId(), deviceDTO.getIotProjectId());
        IotLogDTO logDTO = new IotLogDTO();
        logDTO.setDeviceCode(deviceDTO.getDeviceCode());
        logDTO.setDeviceId(deviceDTO.getIotDeviceId());
        logDTO.setLoggerTime(System.currentTimeMillis());
        logDTO.setAssociationId(deviceDTO.getIotDeviceId());
        logDTO.setIotProjectId(deviceDTO.getIotProjectId());
        logDTO.setCreateOperatorId(deviceDTO.getCreateOperatorId());
        logDTO.setModifyOperatorId(deviceDTO.getModifyOperatorId());
        logDTO.setUserId(deviceDTO.getCreateOperatorId());
        logDTO.setLogTypeCode(Constants.DEVICE_LOGGER_TYPE);
        logDTO.setAssociationType(IotDevice.class.getSimpleName());
        if (isAdd) {
            logDTO.setLogContent("新增设备【" + deviceDTO.getDeviceCode() + "】成功");
        } else {
            logDTO.setLogContent("修改设备【" + deviceDTO.getDeviceCode() + "】信息成功");
        }
        producer.sendToQueue(logDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> deleteIotDevice(Long[] iotDeviceIds, Long modifyOperatorId) {
        if (ArrayUtils.isEmpty(iotDeviceIds)) {
            LOGGER.warn(ErrorCode.ERROR_3004.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_3004.getCode(),
                    ErrorCode.ERROR_3004.getMessage());
        }

        try {
            for (Long iotDeviceId : iotDeviceIds) {
                iotDeviceDao.deleteIotDeviceByIotDeviceId(iotDeviceId, modifyOperatorId);
            }

            return ResultDTO.newSuccess();
        } catch (Exception ee) {
            LOGGER.error("delete device error:{}", ee.toString());
            return ResultDTO.newFail(ErrorCode.ERROR_3001.getCode(),
                    ErrorCode.ERROR_3001.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updateIotDeviceStatus(IotDeviceStatusDTO iotDeviceStatusDTO) {
        try {
            Long userId = iotDeviceStatusDTO.getModifyOperatorId();

            ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotDeviceStatusDTO.getIotProjectId(),
                    userId, iotDeviceStatusDTO.getRoleCode());
            if (!result.isSuccess()) {
                return ResultDTO.newFail(result.getCode(), result.getMsg());
            }

            Long[] iotDeviceIds = iotDeviceStatusDTO.getIotDeviceIds();

            if (ArrayUtils.isEmpty(iotDeviceIds)) {
                LOGGER.warn("device status update error:{}", ErrorCode.ERROR_3004.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_3004.getCode(),
                        ErrorCode.ERROR_3004.getMessage());
            }

            if (iotDeviceStatusDTO.getEnabled() == null) {
                LOGGER.warn("device status update error:{}", ErrorCode.ERROR_3013.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_3013.getCode(),
                        ErrorCode.ERROR_3013.getMessage());
            }

            boolean isEnabled = iotDeviceStatusDTO.getEnabled();

            for (Long iotDeviceId : iotDeviceIds) {
                iotDeviceDao.updateIotDeviceStatus(iotDeviceId, !isEnabled, isEnabled, userId);
            }
            // 添加日志
            addDeviceStatusLogger(iotDeviceStatusDTO);
            return ResultDTO.newSuccess();
        } catch (Exception ee) {
            LOGGER.error(" device status update error:{}", ee.toString());
            return ResultDTO.newFail(ErrorCode.ERROR_3006.getCode(),
                    ErrorCode.ERROR_3006.getMessage());
        }
    }

    private void addDeviceStatusLogger(IotDeviceStatusDTO iotDeviceStatusDTO) {
        IotLogDTO iotLogDTO = new IotLogDTO();
        iotLogDTO.setIotProjectId(iotDeviceStatusDTO.getIotProjectId());
        iotLogDTO.setCreateOperatorId(iotDeviceStatusDTO.getCreateOperatorId());
        iotLogDTO.setModifyOperatorId(iotDeviceStatusDTO.getModifyOperatorId());
        iotLogDTO.setLogTypeCode(Constants.DEVICE_LOGGER_TYPE);
        iotLogDTO.setUserId(iotDeviceStatusDTO.getCreateOperatorId());
        iotLogDTO.setLoggerTime(System.currentTimeMillis());
        iotLogDTO.setAssociationType(IotDevice.class.getSimpleName());
        for (Long iotDeviceId : iotDeviceStatusDTO.getIotDeviceIds()) {
            IotDeviceDTO device = getIotDeviceByIotDeviceId(iotDeviceId);
            if (device == null) {
                continue;
            }
            if (iotDeviceStatusDTO.getEnabled()) {
                iotLogDTO.setLogContent("设备【" + device.getDeviceCode() + "】启用");
            } else {
                iotLogDTO.setLogContent("设备【" + device.getDeviceCode() + "】停用");
            }
            iotLogDTO.setAssociationId(iotDeviceId);

            producer.sendToQueue(iotLogDTO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updateIotDeviceStatus(List<String> deviceCodes, Integer status, Long iotProjectId) {
        try {
            if (CollectionUtils.isEmpty(deviceCodes)) {
                LOGGER.warn("device status update error:{}", ErrorCode.ERROR_3004.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_3004.getMessage());
            }

            if (status == null) {
                LOGGER.warn("device status update error:{}", ErrorCode.ERROR_3013.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_3013.getMessage());
            }

            boolean isEnabled;
            if (status == 0) {
                isEnabled = false;
            } else {
                isEnabled = true;
            }

            iotDeviceDao.updateIotDeviceStatus(deviceCodes, !isEnabled, isEnabled, iotProjectId);

            // 保存设备操作日志
            saveLogger(iotProjectId, deviceCodes, status);

            return ResultDTO.newSuccess();
        } catch (Exception ee) {
            LOGGER.error(" device status update error, deviceCodes:{}, status:{}, iotProjectId:{}, error:{}",
                    JSON.toJSONString(deviceCodes), status, iotProjectId, ee.toString());
            return ResultDTO.newFail(ErrorCode.ERROR_3006.getMessage());
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotDeviceStatusCountDTO> getDeviceCountByOnlineStatus(IotDeviceQueryListDTO iotDeviceQueryListDTO) {
        try {

            ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotDeviceQueryListDTO.getIotProjectId(),
                    iotDeviceQueryListDTO.getCreateOperatorId(), iotDeviceQueryListDTO.getRoleCode());
            if (!result.isSuccess()) {
                return ResultDTO.newFail(result.getCode(), result.getMsg());
            }

            if (iotDeviceQueryListDTO.getIotProjectId() == null) {
                LOGGER.warn(ErrorCode.ERROR_2004.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_2004.getCode(),
                        ErrorCode.ERROR_2004.getMessage());
            }

            IotDeviceStatusCountDTO iotDeviceStatusCountDTO = iotDeviceQueryDao.getDeviceCountByOnlineStatus(
                    iotDeviceQueryListDTO.getConnected(), iotDeviceQueryListDTO.getIotDeviceTypeId(),
                    iotDeviceQueryListDTO.getIotProjectId(), iotDeviceQueryListDTO.getIotTagId(),
                    iotDeviceQueryListDTO.getKeywords());

            return ResultDTO.newSuccess(iotDeviceStatusCountDTO);
        } catch (Exception ee) {
            LOGGER.error("getDeviceCountByOnlineStatus error:", ee);
            return ResultDTO.newFail("");
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<IotDeviceCountQueryDTO>> getDeviceCountByType(IotDosQueryDTO iotDosQueryDTO) {
        Long startDatetime = iotDosQueryDTO.getStartDatetime();
        Long endDatetime = iotDosQueryDTO.getEndDatetime();
        String type = iotDosQueryDTO.getType();
        Long iotUserId = iotDosQueryDTO.getModifyOperatorId();
        Long iotProjectId = iotDosQueryDTO.getIotProjectId();
        String roleCode = iotDosQueryDTO.getRoleCode();

        IotUserProjectDTO iotUserProjectDTO = new IotUserProjectDTO();
        iotUserProjectDTO.setIotProjectId(iotProjectId);
        iotUserProjectDTO.setIotUserId(iotUserId);
        iotUserProjectDTO.setRoleCode(roleCode);
        ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotProjectId, iotUserId, roleCode);
        if (!result.isSuccess()) {
            LOGGER.warn("通过设备类型获取设备数量失败：{}", JSON.toJSONString(iotDosQueryDTO));
            return ResultDTO.newFail(result.getCode(), result.getMsg());
        }
        List<IotDeviceCountQueryDTO> resultList;
        if (DosConstant.TOTAL.equals(type)) {
            resultList = iotDeviceCountDao.getDayBeforeQuantityByDateRange(new Date(startDatetime),
                    new Date(endDatetime), iotProjectId);
        } else if (DosConstant.ADDED.equals(type)) {
            resultList = iotDeviceCountDao.getDayQuantityByDateRange(new Date(startDatetime),
                    new Date(endDatetime), iotProjectId);
        } else {
            LOGGER.warn("query type is error ,type={}", type);
            return ResultDTO.newFail(ErrorCode.ERROR_3003.getCode(),
                    ErrorCode.ERROR_3003.getMessage());
        }

        return ResultDTO.newSuccess(resultList);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<QueryResult<IotDeviceCountTitleDTO>> getDeviceCountTopByType(Long startDatetime,
                                                                                  Long endDatetime, Integer top,
                                                                                  String type) {
        Page<IotDeviceCountTitleDTO> page;
        if (DosConstant.PROJECT.equals(type)) {
            page = iotDeviceCountDao.getTopGroupByIotProjectId(PageRequest.of(0, top));
        } else if (DosConstant.DEVICE_TYPE.equals(type)) {
            page = iotDeviceCountDao.getTopGroupByIotDeviceTypeId(PageRequest.of(0, top));
        } else if (DosConstant.TAG.equals(type)) {
            page = iotDeviceCountTagDao.getTopGroupByByIotTagId(PageRequest.of(0, top));
        } else {
            LOGGER.warn("query type is error ,type={}", type);
            return ResultDTO.newFail(ErrorCode.ERROR_3035.getCode(),
                    ErrorCode.ERROR_3035.getMessage());
        }

        QueryResult<IotDeviceCountTitleDTO> queryResult = new QueryResult<>(page.getContent(), page.getTotalElements());
        return ResultDTO.newSuccess(queryResult);

    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotDeviceDetailQueryDTO> getIotDeviceDetail(IotDeviceDTO iotDeviceDTO) {
        try {

            ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotDeviceDTO.getIotProjectId(),
                    iotDeviceDTO.getCreateOperatorId(), iotDeviceDTO.getRoleCode());
            if (!result.isSuccess()) {
                return ResultDTO.newFail(result.getCode(), result.getMsg());
            }
            Long iotDeviceId = iotDeviceDTO.getIotDeviceId();
            if (iotDeviceId == null) {
                LOGGER.warn(ErrorCode.ERROR_3004.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_3004.getCode(),
                        ErrorCode.ERROR_3004.getMessage());
            }

            IotDeviceDetailQueryDTO iotDeviceQueryDTO = iotDeviceQueryDao.getIotDeviceByIotDeviceId(iotDeviceId);
            if (iotDeviceQueryDTO == null) {
                LOGGER.warn(ErrorCode.ERROR_3005.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_3005.getCode(),
                        ErrorCode.ERROR_3005.getMessage());
            }

            iotDeviceQueryDTO.setIotTagIds(iotDeviceTagDao.getTagIdByDeviceId(iotDeviceId));

            getGps(iotDeviceQueryDTO);

            String userName = iotUserDao.getIotUserByIotUserId(iotDeviceQueryDTO.getCreateOperatorId());

            iotDeviceQueryDTO.setCreateOperatorName(userName);
            try {
                //TODO:deviceId
                //String deviceId = (String) getDeviceIdByDeviceCode(iotDeviceQueryDTO.getDeviceCode()).R;
                List<IotKtvFactorReportTimeDto> ret =
                        ktvDataClient.batchQueryDeviceLastReportTime(new String[]{iotDeviceQueryDTO.getDeviceCode()}).getRet();
                if (!CollectionUtils.isEmpty(ret)) {
                    long latestTime = 0;
                    for (IotKtvFactorReportTimeDto temp : ret) {
                        if (iotDeviceQueryDTO.getDeviceCode().equals(temp.getDeviceId())) {
                            if (latestTime < temp.getReportTime()) {
                                latestTime = temp.getReportTime();
                            }
                        }
                    }
                    iotDeviceQueryDTO.setLatestUploadDatetime(latestTime);
                }
            } catch (Exception e) {
                LOGGER.error("ktv查询失败,iotDeviceId:{}, error:{}", iotDeviceId, e.toString());
            }

            return ResultDTO.newSuccess(iotDeviceQueryDTO);
        } catch (Exception ee) {
            LOGGER.error("getIotDeviceDetail error:", ee);
            return ResultDTO.newFail("");
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotDeviceDetailQueryDTO> getIotDeviceDetail(IotDeviceQueryByCodeDTO iotDeviceQueryByCodeDTO) {
        try {
            Long iotProjectId = iotDeviceQueryByCodeDTO.getIotProjectId();
            String deviceCode = iotDeviceQueryByCodeDTO.getDeviceCode();
            Long iotUserId = iotDeviceQueryByCodeDTO.getCreateOperatorId();
            String roleCode = iotDeviceQueryByCodeDTO.getRoleCode();
            ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotProjectId, iotUserId, roleCode);
            if (!result.isSuccess()) {
                return ResultDTO.newFail(result.getCode(), result.getMsg());
            }

            if (StringUtils.isEmpty(deviceCode)) {
                LOGGER.warn(ErrorCode.ERROR_3004.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_3004.getCode(),
                        ErrorCode.ERROR_3004.getMessage());
            }

            IotDeviceDetailQueryDTO iotDeviceQueryDTO = iotDeviceQueryDao.getIotDeviceByDeviceCode(deviceCode,
                    iotProjectId);
            if (iotDeviceQueryDTO == null) {
                LOGGER.warn(ErrorCode.ERROR_3005.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_3005.getCode(),
                        ErrorCode.ERROR_3005.getMessage());
            }
            getGps(iotDeviceQueryDTO);

            return ResultDTO.newSuccess(iotDeviceQueryDTO);
        } catch (Exception e) {
            LOGGER.error("getIotDeviceDetail error:{}", e);
            return ResultDTO.newFail("");
        }
    }

    /**
     * 获取Gps信息
     *
     * @param iotDeviceQueryDTO
     */
    private void getGps(IotDeviceQueryDTO iotDeviceQueryDTO) {

        QueryDto queryDto = new QueryDto();
        String[] factors = new String[]{GpsMsgParam.ATTR_GPS_BD09_LONGITUDE, GpsMsgParam.ATTR_GPS_BD09_LATITUDE,
                GpsMsgParam.ATTR_GPS_ADDRESS};
        queryDto.setFactors(factors);
        //TODO:deviceId
        //String deviceId = (String) getDeviceIdByDeviceCode(iotDeviceQueryDTO.getDeviceCode()).R;
        queryDto.setDeviceId(iotDeviceQueryDTO.getDeviceCode());
        queryDto.setPartyId("-100");
        try {
            Result<Map.Entry<String, List<Map<String, Object>>>> result = vehicleDataService.queryLatest(queryDto);
            Map.Entry<String, List<Map<String, Object>>> ret1 = result.getRet();
            List<Map<String, Object>> returnList = ret1.getValue();
            for (Map<String, Object> ret : returnList) {
                String factor = (String) ret.get("factor");
                String value = (String) ret.get("value");
                if (GpsMsgParam.ATTR_GPS_BD09_LONGITUDE.equals(factor) && !StringUtils.isEmpty(value)) {
                    iotDeviceQueryDTO.setLongitude(Double.parseDouble(value));
                }
                if (GpsMsgParam.ATTR_GPS_ADDRESS.equals(factor) && !StringUtils.isEmpty(value)) {
                    iotDeviceQueryDTO.setAddress(value);
                }
                if (GpsMsgParam.ATTR_GPS_BD09_LATITUDE.equals(factor) && !StringUtils.isEmpty(value)) {
                    iotDeviceQueryDTO.setLatitude(Double.parseDouble(value));
                }
            }
        } catch (Exception e) {
            LOGGER.error(String.format("vehicle查询异常, 参数:%s, error:", JSON.toJSONString(iotDeviceQueryDTO)), e);
        }
    }

    /**
     * 批量获取GPS信息
     *
     * @param deviceCodes
     * @param queryDTOList
     */
    private List<IotDeviceQueryDTO> getGpsBatch(List<String> deviceCodes, List<IotDeviceQueryDTO> queryDTOList) {
        QueryListCriteriaDto queryListCriteriaDto = new QueryListCriteriaDto();
        String[] factors = new String[]{GpsMsgParam.ATTR_GPS_BD09_LONGITUDE, GpsMsgParam.ATTR_GPS_BD09_LATITUDE,
                GpsMsgParam.ATTR_GPS_ADDRESS};
        queryListCriteriaDto.setDeviceIds(deviceCodes);
        queryListCriteriaDto.setFactors(factors);
        queryListCriteriaDto.setPartyId(-100);
        try {
            Result<Map.Entry<String, List<Map<String, Object>>>> result =
                    vehicleDataService.queryBathLast(queryListCriteriaDto);
            Map.Entry<String, List<Map<String, Object>>> ret1 = result.getRet();
            if (ret1.getValue() == null) {
                return null;
            }
            List<Map<String, Object>> returnList = ret1.getValue();
            for (Map<String, Object> ret : returnList) {
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) ret.get("datas");

                for (IotDeviceQueryDTO iotDeviceQueryDTO : queryDTOList) {
                    if (iotDeviceQueryDTO.getDeviceCode().equals(ret.get("deviceId"))) {
                        for (Map<String, Object> map : dataList) {
                            String value = (String) map.get("value");
                            if (GpsMsgParam.ATTR_GPS_BD09_LONGITUDE.equals(map.get("factor")) &&
                                    !StringUtils.isEmpty(value)) {
                                iotDeviceQueryDTO.setLongitude(Double.parseDouble(value));
                            }
                            if (GpsMsgParam.ATTR_GPS_ADDRESS.equals(map.get("factor")) && !StringUtils.isEmpty(value)) {
                                iotDeviceQueryDTO.setAddress(value);
                            }
                            if (GpsMsgParam.ATTR_GPS_BD09_LATITUDE.equals(map.get("factor")) &&
                                    !StringUtils.isEmpty(value)) {
                                iotDeviceQueryDTO.setLatitude(Double.parseDouble(value));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("批量获取GPS信息异常, 参数:{}, error:{}", JSON.toJSONString(deviceCodes), e.toString());
        }
        return queryDTOList;
    }

    /**
     * 查询设备
     *
     * @param deviceQueryListDTO
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<PageResult<IotDeviceQueryDTO>> getIotDeviceByIotProjectId(IotDeviceQueryListDTO deviceQueryListDTO) {
        try {
            Long iotProjectId = deviceQueryListDTO.getIotProjectId();

            Long iotUserId = deviceQueryListDTO.getCreateOperatorId();
            String roleCode = deviceQueryListDTO.getRoleCode();
            ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotProjectId, iotUserId, roleCode);
            if (!result.isSuccess()) {
                return ResultDTO.newFail(result.getCode(), result.getMsg());
            }

            QueryResult<IotDeviceQueryDTO> queryResult = iotDeviceQueryDao.getIotDeviceByIotProjectId(deviceQueryListDTO.getConnected(),
                    deviceQueryListDTO.getIotDeviceTypeId(), iotProjectId, deviceQueryListDTO.getIotTagId(),
                    deviceQueryListDTO.getKeywords(), deviceQueryListDTO.getPageIndex(),
                    deviceQueryListDTO.getPageSize());
            List<IotDeviceQueryDTO> dtoList = queryResult.getItems();

            if (CollectionUtils.isEmpty(dtoList)) {
                PageResult<IotDeviceQueryDTO> pageResult = new PageResult<>(0, Lists.newArrayList());
                return ResultDTO.newSuccess(pageResult);
            }
            PageResult<IotDeviceQueryDTO> pageResult = new PageResult<>((int) queryResult.getRowCount(), dtoList);
            getReportTime(queryResult, dtoList);
            return ResultDTO.newSuccess(pageResult);

        } catch (Exception e) {
            LOGGER.error("getIotDeviceByIotProjectId error:", e);
            return ResultDTO.newFail("");
        }

    }

    private ResultDTO<?> getReportTime(QueryResult<IotDeviceQueryDTO> result, List<IotDeviceQueryDTO> dtoList) {
        List<String> deviceCodes = new ArrayList<>();
        for (IotDeviceQueryDTO queryDTO : dtoList) {
            //TODO:deviceID
            //String deviceId = (String) getDeviceIdByDeviceCode(queryDTO.getDeviceCode()).R;
            deviceCodes.add(queryDTO.getDeviceCode());
        }
        List<IotKtvFactorReportTimeDto> ret =
                ktvDataClient.batchQueryDeviceLastReportTime(deviceCodes.toArray(new String[deviceCodes.size()])).getRet();
        if (CollectionUtils.isEmpty(ret)) {
            PageResult<IotDeviceQueryDTO> pageResult = new PageResult<>((int) result.getRowCount(), result.getItems());
            return ResultDTO.newSuccess(pageResult);
        }

        for (IotDeviceQueryDTO queryDTO : dtoList) {
            for (IotKtvFactorReportTimeDto temp : ret) {
                if (queryDTO.getDeviceCode().equals(temp.getDeviceId())) {
                    long reportTime =
                            queryDTO.getLatestUploadDatetime() == null ? 0 : queryDTO.getLatestUploadDatetime();
                    if (reportTime < temp.getReportTime()) {
                        queryDTO.setLatestUploadDatetime(temp.getReportTime());
                    }
                }
            }

        }
        return null;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<PageResult<IotDeviceQueryDTO>> getIotOpenDeviceList(Boolean connected, Long iotProjectId,
                                                                         String keywords, Integer pageIndex,
                                                                         Integer pageSize, Boolean enabled) {
        QueryResult<IotDeviceQueryDTO> result = iotDeviceQueryDao.getIotOpenDeviceList(connected, iotProjectId,
                keywords, pageIndex, pageSize, enabled);
        List<IotDeviceQueryDTO> dtoList = result.getItems();
        if (CollectionUtils.isEmpty(dtoList)) {
            return ResultDTO.newFail(ErrorCode.ERROR_3044.getCode(),
                    ErrorCode.ERROR_3044.getMessage());
        }
        PageResult<IotDeviceQueryDTO> pageResult = new PageResult<>((int) result.getRowCount(), dtoList);
        getReportTime(result, dtoList);
        return ResultDTO.newSuccess(pageResult);
    }

    @Override
    public ResultDTO<PageResult<IotDeviceQueryDTO>> getIotOpenDeviceList(Long iotProjectId, List<String> deviceCodes,
                                                                         Boolean enabled) {
        QueryResult<IotDeviceQueryDTO> result = iotDeviceQueryDao.getIotOpenDeviceList(iotProjectId, deviceCodes,
                enabled);
        List<IotDeviceQueryDTO> dtoList = result.getItems();
        if (CollectionUtils.isEmpty(dtoList)) {
            return ResultDTO.newFail(ErrorCode.ERROR_3044.getCode(),
                    ErrorCode.ERROR_3044.getMessage());
        }
        PageResult<IotDeviceQueryDTO> pageResult = new PageResult<>((int) result.getRowCount(), dtoList);
        getReportTime(result, dtoList);
        return ResultDTO.newSuccess(pageResult);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<QueryResult<IotDeviceCountTitleDTO>> getDeviceCountByTag(Long iotTagId, Long startDatetime,
                                                                              Long endDatetime) {
        List<IotDeviceTypeTag> deviceTypeTagList = iotDeviceTypeTagDao.getByIotTagId(iotTagId);
        List<Long> iotDeviceTypes = Lists.newArrayList();
        for (IotDeviceTypeTag iotDeviceTypeTag : deviceTypeTagList) {
            if (!iotDeviceTypes.contains(iotDeviceTypeTag.getIotDeviceTypeId())) {
                iotDeviceTypes.add(iotDeviceTypeTag.getIotDeviceTypeId());
            }
        }

        if (iotDeviceTypes.size() <= 0) {
            QueryResult<IotDeviceCountTitleDTO> queryResult = new QueryResult<>(Lists.newArrayList(), 0);
            return ResultDTO.newSuccess(queryResult);
        }

        List<IotDeviceCountTitleDTO> dtoList = iotDeviceCountDao.getTopGroupByIotDeviceTypeId(iotDeviceTypes.toArray
                (new Long[iotDeviceTypes.size()]));

        QueryResult<IotDeviceCountTitleDTO> queryResult = new QueryResult<>(dtoList, dtoList.size());
        return ResultDTO.newSuccess(queryResult);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<IotDeviceStatusQueryDTO>> getIotDeviceStatus(IotDeviceStatusBatchQueryDTO iotDeviceStatusBatchQueryDTO) {

        try {
            List<String> deviceCodes = iotDeviceStatusBatchQueryDTO.getDeviceCodes();
            Long iotProjectId = iotDeviceStatusBatchQueryDTO.getIotProjectId();
            Long iotUserId = iotDeviceStatusBatchQueryDTO.getCreateOperatorId();
            String roleCode = iotDeviceStatusBatchQueryDTO.getRoleCode();
            ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotProjectId, iotUserId, roleCode);
            if (!result.isSuccess()) {
                return ResultDTO.newFail(result.getCode(), result.getMsg());
            }

            if (deviceCodes == null || deviceCodes.isEmpty()) {
                return ResultDTO.newFail(ErrorCode.ERROR_3007.getMessage());
            }
            List<IotDeviceStatusQueryDTO> iotDeviceStatusDTOList = iotDeviceQueryDao.getIotDeviceStatus(deviceCodes,
                    iotProjectId);

            return ResultDTO.newSuccess(iotDeviceStatusDTOList);

        } catch (Exception e) {
            LOGGER.error("getIotDeviceStatus error:", e);
            return ResultDTO.newFail("");
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(String deviceCode) {
//        LOGGER.info("--------------getProtocolByDeviceCode:" + deviceCode);
        if (Strings.isNullOrEmpty(deviceCode)) {
            LOGGER.error("query protocol code failed: code: {}, message: {}",
                    ErrorCode.ERROR_3007.getCode(), ErrorCode.ERROR_3007.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                    ErrorCode.ERROR_3007.getMessage());
        }
        List<IotIovProtocolCodeQueryDto> protocolByDeviceCodes = iotDeviceQueryDao.getProtocolByDeviceCode(deviceCode);
        if (!CollectionUtils.isEmpty(protocolByDeviceCodes)) {
            return ResultDTO.newSuccess(protocolByDeviceCodes.get(0));
        }
        List<String> persistDeviceCode = iotDeviceDao.getDeviceCodeByDeviceId(getMD5String(deviceCode));
        if (!CollectionUtils.isEmpty(persistDeviceCode)) {
            protocolByDeviceCodes = iotDeviceQueryDao.getProtocolByDeviceCode(persistDeviceCode.get(0));
        }
        if (!CollectionUtils.isEmpty(protocolByDeviceCodes)) {
            return ResultDTO.newSuccess(protocolByDeviceCodes.get(0));
        }

        String passTypeCode = iotDevicePassQueryDao.getPassTypeCodeByPassCode(deviceCode);
        if (StringUtils.isNotBlank(passTypeCode)) {
            IotIovProtocolCodeQueryDto protocolByDeviceCode = new IotIovProtocolCodeQueryDto();
            protocolByDeviceCode.setProtocolCode(passTypeCode);
            return ResultDTO.newSuccess(protocolByDeviceCode);
        }
        return ResultDTO.newFail("无该设备信息");
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ResultDTO<List<IotIovProtocolCodeQueryDto>> getProtocolByDeviceCodes(List<String> deviceCodes) {
        if (CollectionUtils.isEmpty(deviceCodes)) {
            LOGGER.error("query protocol code failed: code: {}, message: {}",
                    ErrorCode.ERROR_3007.getCode(), ErrorCode.ERROR_3007.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                    ErrorCode.ERROR_3007.getMessage());
        }
        List<IotIovProtocolCodeQueryDto> protocolByDeviceCodes = iotDeviceQueryDao.getProtocolByDeviceCodes(deviceCodes);
        if (!CollectionUtils.isEmpty(protocolByDeviceCodes)) {
            for (IotIovProtocolCodeQueryDto dto : protocolByDeviceCodes) {
                deviceCodes.remove(dto.getDeviceCode());
            }
        }
        if (CollectionUtils.isEmpty(deviceCodes)) {
            return ResultDTO.newSuccess(protocolByDeviceCodes);
        }
        List<String> deviceIds = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        for (String code : deviceCodes) {
            String deviceId = getMD5String(code);
            deviceIds.add(deviceId);
            map.put(deviceId, code);
        }
        List<IotIovProtocolCodeQueryDto> protocolByDeviceIds = iotDeviceQueryDao.getProtocolByDeviceIds(deviceIds);
        if (!CollectionUtils.isEmpty(protocolByDeviceIds)) {
            protocolByDeviceIds.forEach((deviceId) -> {
                String deviceCode = map.get(deviceId.getDeviceId());
                deviceId.setDeviceCode(deviceCode);
                deviceCodes.remove(deviceCode);
            });
            protocolByDeviceCodes.addAll(protocolByDeviceIds);
        }
        return ResultDTO.newSuccess(protocolByDeviceCodes);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotOpenApiResponseDeviceDTO> getProtocolByIotDeviceId(List<Long> iotDeviceIds) {
        List<IotOpenApiResponseDeviceDTO> list = iotDeviceQueryDao.getProtocolByIotDeviceId(iotDeviceIds);
        if (CollectionUtils.isEmpty(list)) {
            return ResultDTO.newSuccess(null);
        }
        return ResultDTO.newSuccess(list.get(0));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<PageResult<IotDeviceDTO>> getIotDeviceByRegister(IotDeviceMonitorQueryListDTO queryListDTO) {
        if (!RoleCode.ADMIN.equals(queryListDTO.getRoleCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_8014.getCode(),
                    ErrorCode.ERROR_8014.getMessage());
        }
        try {
            if (queryListDTO.getPageIndex() == null || queryListDTO.getPageSize() == null) {
                return ResultDTO.newFail(ErrorCode.ERROR_5053.getCode(),
                        ErrorCode.ERROR_5053.getMessage());
            }
            List<IotDeviceDTO> result = iotDeviceQueryDao.getIotDeviceByRegister(queryListDTO.getIotProtocolId(), queryListDTO.getDeviceCode(),
                    queryListDTO.getRegisterStatus(), queryListDTO.getPageIndex(), queryListDTO.getPageSize());
            Integer total = iotDeviceQueryDao.getIotDeviceByRegister(queryListDTO.getIotProtocolId(), queryListDTO.getDeviceCode(),
                    queryListDTO.getRegisterStatus(), 1, Integer.MAX_VALUE).size();
            PageResult<IotDeviceDTO> pageResult = new PageResult<>(total, result);
            return ResultDTO.newSuccess(pageResult);
        } catch (Exception e) {
            LOGGER.error("getIotDeviceByRegister error:", e);
            return ResultDTO.newFail("");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updateIotDeviceRegisterBatch(IotDeviceRegisterBatchDTO registerBatchDTO) {
        try {
            Long iotProjectId = registerBatchDTO.getIotProjectId();
            Long iotDeviceTypeId = registerBatchDTO.getIotDeviceTypeId();
            Long operatorId = registerBatchDTO.getCreateOperatorId();
            ResultDTO<Integer> judge = iotDeviceTypeService.judgeProject(iotProjectId, operatorId, registerBatchDTO.getRoleCode());
            if (!judge.isSuccess()) {
                return ResultDTO.newFail(judge.getCode(), judge.getMsg());
            }

            if (iotDeviceTypeId == null) {
                LOGGER.error("query protocol code failed: code: {}, message: {}",
                        ErrorCode.ERROR_3009.getCode(), ErrorCode.ERROR_3009.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_3009.getCode(),
                        ErrorCode.ERROR_3009.getMessage());
            }
            if (iotProjectId == null) {
                LOGGER.error("query protocol code failed: code: {}, message: {}",
                        ErrorCode.ERROR_3011.getCode(), ErrorCode.ERROR_3011.getMessage());
                return ResultDTO.newFail(ErrorCode.ERROR_3011.getCode(),
                        ErrorCode.ERROR_3011.getMessage());
            }
            List<Long> iotDeviceIds = registerBatchDTO.getIotDeviceIds();
            List<Long> existIds = new ArrayList<>();
            for (Long id : iotDeviceIds) {
                Integer exist = iotDeviceDao.getIsRegister(id);
                if (exist == 1) {
                    existIds.add(id);
                }
            }
            iotDeviceIds.removeAll(existIds);
            if (iotDeviceIds.size() != 0) {
                iotDeviceDao.updateIotDeviceRegisterBatch(iotDeviceIds, iotProjectId, iotDeviceTypeId, operatorId);
                List<String> deviceIds = iotDeviceDao.getDeviceIdsByIotDeviceIds(iotDeviceIds);
                iotDeviceSaveMessageService.sendMessage(deviceIds, iotProjectId);
                for (Long iotDeviceId : iotDeviceIds) {
                    IotDevice device = iotDeviceDao.getByIotDeviceId(iotDeviceId);
                    IotDeviceDTO deviceDTO = new IotDeviceDTO();
                    BeanUtils.copyProperties(device, deviceDTO);
                    saveDeviceInfoLogger(deviceDTO, false);
                }
            }
            return ResultDTO.newSuccess();
        } catch (Exception e) {
            LOGGER.error("updateIotDeviceRegisterBatch error:", e);
            return ResultDTO.newFail("");
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<IotDeviceQueryDTO>> getDeviceLocation(IotDeviceLocationQueryDTO queryDTO) {
        try {
            Long iotDeviceTypeId = queryDTO.getIotDeviceTypeId();
            Long iotProjectId = queryDTO.getIotProjectId();
            Long iotTagId = queryDTO.getIotTagId();
            Boolean connected = queryDTO.getConnected();
            String keywords = queryDTO.getKeywords();
            QueryResult<IotDeviceQueryDTO> queryResult = iotDeviceQueryDao.getIotDeviceByIotProjectId(connected,
                    iotDeviceTypeId, iotProjectId, iotTagId, keywords, 1, Integer.MAX_VALUE);
            if (queryResult.getItems().size() == 0) {
                return ResultDTO.newSuccess(queryResult.getItems());
            }

            List<String> deviceCodes = new ArrayList<>();
            List<IotDeviceQueryDTO> devices = queryResult.getItems();

            for (IotDeviceQueryDTO iotDeviceQueryDTO : devices) {
                //TODO:deviceId
                //String deviceId = (String) getDeviceIdByDeviceCode(iotDeviceQueryDTO.getDeviceCode()).R;
                deviceCodes.add(iotDeviceQueryDTO.getDeviceCode());
            }

            getGpsBatch(deviceCodes, devices);
            try {
                List<IotKtvFactorReportTimeDto> ret =
                        ktvDataClient.batchQueryDeviceLastReportTime(deviceCodes.toArray(new String[deviceCodes.size()])).getRet();
                if (CollectionUtils.isEmpty(ret)) {
                    return ResultDTO.newSuccess(devices);
                }
                for (IotKtvFactorReportTimeDto temp : ret) {
                    for (IotDeviceQueryDTO deviceQueryDTO : devices) {
                        if (deviceQueryDTO.getDeviceCode().equals(temp.getDeviceId())) {
                            deviceQueryDTO.setLatestUploadDatetime(temp.getReportTime());
                        }
                    }

                }
            } catch (Exception e) {
                LOGGER.error("ktv异常" + e.toString());
            }

            return ResultDTO.newSuccess(devices);
        } catch (Exception e) {
            LOGGER.error("getDeviceLocation error:", e);
            return ResultDTO.newFail("");
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<Map<String, String>>> history(IotDeviceHistoryQueryDTO historyQueryDTO) {
        try {
            Long iotProjectId = historyQueryDTO.getIotProjectId();
            Long iotUserId = historyQueryDTO.getModifyOperatorId();
            String roleCode = historyQueryDTO.getRoleCode();
            String deviceCode = historyQueryDTO.getDeviceCode();
            Long startTime = historyQueryDTO.getStartTime();
            Long endTime = historyQueryDTO.getEndTime();
            ResultDTO<Integer> judge = iotDeviceTypeService.judgeProject(iotProjectId, iotUserId, roleCode);
            if (judge.isSuccess()) {
                return ResultDTO.newFail(judge.getCode(), judge.getMsg());
            }
            if (deviceCode == null) {
                return ResultDTO.newFail("deviceCode为空");
            }

            if (iotProjectId == null) {
                return ResultDTO.newFail("ProjectId is null");
            }
            if (startTime == null && endTime == null) {
                return ResultDTO.newFail("time is null");
            }
            ResultDTO<String> result = getDeviceIdByDeviceCode(deviceCode, iotProjectId);
            if (result.getData() == null) {
                return ResultDTO.newFail("无该设备权限");
            }

            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            GpsQueryDto gpsQueryDto = new GpsQueryDto();
            gpsQueryDto.setPartyid(-100);
            gpsQueryDto.setDeviceid(deviceCode);
            gpsQueryDto.setStarttime(sd.format(new Date(startTime)));
            gpsQueryDto.setEndtime(sd.format(new Date(endTime)));
            gpsQueryDto.setFormat("BD09");
            Result<QueryResult<Map<String, String>>> vehicleResult = vehicleDataService.queryGpsList(gpsQueryDto);
            System.out.println(JSON.toJSONString(vehicleResult));
            if (vehicleResult.getRc() != 0) {
                return ResultDTO.newFail(vehicleResult.getRc(), vehicleResult.getErr());
            }
            QueryResult<Map<String, String>> queryResult = vehicleResult.getRet();
            return ResultDTO.newSuccess(queryResult.getItems());
        } catch (Exception e) {
            LOGGER.error("get history error:", e);
            return ResultDTO.newFail("");
        }
    }

    @Override
    public ResultDTO<String> getDeviceIdByDeviceCode(String deviceCode, Long iotProjectId) {
        try {
            if (StringUtils.isEmpty(deviceCode)) {
                return ResultDTO.newFail("deviceCode is null");
            }
            if (iotProjectId == null) {
                return ResultDTO.newFail("ProjectId为空");
            }
            return ResultDTO.newSuccess(iotDeviceDao.getDeviceIdByDeviceCode(deviceCode, iotProjectId));
        } catch (Exception e) {
            LOGGER.error("getDeviceIdByDeviceCode error:", e);
            return ResultDTO.newFail("");
        }
    }

    @Override
    public ResultDTO<String> getDeviceIdByDeviceCode(String deviceCode) {
        try {
            if (StringUtils.isEmpty(deviceCode)) {
                return ResultDTO.newFail("deviceCode is null");
            }
            List<IotProtocolQueryDTO> protocols = iotProtocolDao.getAllByIsDeleted(false);
            List<String> deviceIds = null;

            for (IotProtocolQueryDTO iotProtocolQueryDTO : protocols) {
                if (deviceCode.startsWith(iotProtocolQueryDTO.getProtocolCode())) {
                    deviceIds = iotDeviceDao.getDeviceIdByDeviceCode(deviceCode);
                    if (CollectionUtils.isEmpty(deviceIds)) {
                        deviceIds =
                                iotDeviceDao.getDeviceIdByDeviceCode(deviceCode.substring(iotProtocolQueryDTO.getProtocolCode().length()));
                    }
                    if (CollectionUtils.isEmpty(deviceIds)) {
                        return ResultDTO.newFail("查询不到该设备或无deviceId");
                    }
                    return ResultDTO.newSuccess(deviceIds.get(0));
                }
            }

            deviceIds = iotDeviceDao.getDeviceIdByDeviceCode(deviceCode);
            if (!CollectionUtils.isEmpty(deviceIds)) {
                return ResultDTO.newSuccess(deviceIds.get(0));
            }

            List<String> protocolAndDeviceCodes = new ArrayList<>();
            for (IotProtocolQueryDTO iotProtocolQueryDTO : protocols) {
                protocolAndDeviceCodes.add(iotProtocolQueryDTO.getProtocolCode() + deviceCode);
            }

            for (String str : protocolAndDeviceCodes) {
                deviceIds = iotDeviceDao.getDeviceIdByDeviceCode(str);
                if (!CollectionUtils.isEmpty(deviceIds)) {
                    return ResultDTO.newSuccess(deviceIds.get(0));
                }
            }

            // 通道id
            String deviceId = iotDevicePassDao.getPassIdByPassCode(deviceCode);
            if (StringUtils.isNotBlank(deviceId)) {
                return ResultDTO.newSuccess(deviceId);
            }

            return ResultDTO.newFail("查询不到该设备或无deviceId");
        } catch (Exception e) {
            LOGGER.error("getDeviceIdByDeviceCode error:", e);
            return ResultDTO.newFail("");
        }
    }

    @Override
    public ResultDTO<Long> getIotDeviceIdByDeviceCode(String deviceCode, Long iotProjectId) {
        if (StringUtils.isEmpty(deviceCode)) {
            return ResultDTO.newFail("deviceCode is null");
        }
        if (iotProjectId == null) {
            return ResultDTO.newFail("项目id为空");
        }
        return ResultDTO.newSuccess(iotDeviceDao.getIotDeviceIdByDeviceCode(deviceCode, iotProjectId));
    }

    @Override
    public ResultDTO<List<Long>> getIotProjectIdByDeviceCode(String deviceCode) {
        if (StringUtils.isEmpty(deviceCode)) {
            return ResultDTO.newFail("deviceCode 为空");
        }
        List<Long> projectIds = iotDeviceDao.getIotProjectIdByDeviceCode(deviceCode);
        if (!CollectionUtils.isEmpty(projectIds)) {
            return ResultDTO.newSuccess(projectIds);
        }
        return ResultDTO.newFail("找不到对应项目");
    }

    @Override
    public ResultDTO<List<IotDeviceSimpleDTO>> getSimpleDevices() {
        List<IotDeviceSimpleDTO> devices = iotDeviceDao.getAll();
        return ResultDTO.newSuccess(devices);
    }

    @Override
    public ResultDTO<List<Long>> getIotDeviceIdByDeviceCode(String deviceCode) {
        List<Long> iotDeviceId = iotDeviceDao.getIdByDeviceCode(deviceCode);
        return ResultDTO.newSuccess(iotDeviceId);
    }

    @Override
    public ResultDTO<PageResult<IotDesDeviceEventListQueryDto>> getDeviceEventList(IotDosDeviceEventQueryListDto deviceDTO) {

        ResultDTO<Integer> bizReturn = iotDeviceTypeService.judgeProject(deviceDTO.getIotProjectId(),
                deviceDTO.getCreateOperatorId(), deviceDTO.getRoleCode());
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }

        Integer pageIndex = deviceDTO.getPageIndex();
        Integer pageSize = deviceDTO.getPageSize();

        if (pageIndex == null || pageSize == null || pageIndex <= 0 || pageSize <= 0) {
            return ResultDTO.newFail("分页参数异常");
        }

        Long iotDeviceId = deviceDTO.getIotDeviceId();
        IotDevice device = iotDeviceDao.getByIotDeviceId(iotDeviceId);
        if (device == null || !device.getIotProjectId().equals(deviceDTO.getIotProjectId())) {
            return ResultDTO.newFail("设备与项目不符");
        }

        Long startTime = deviceDTO.getStartTime();
        Long endTime = deviceDTO.getEndTime();
        if (startTime == null || endTime == null || startTime > endTime) {
            return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(), "时间参数异常");
        }

        return ResultDTO.newSuccess(desFeignClient.getDeviceEventListByIotDeviceId(iotDeviceId, startTime, endTime,
                pageIndex, pageSize).getRet());
    }

    @Override
    public ResultDTO<IotDesEventDetailDto> getDeviceEventDetail(IotDeviceEventDetailQueryDto deviceEventDetailQueryDto) {

        ResultDTO<Integer> bizReturn = iotDeviceTypeService.judgeProject(deviceEventDetailQueryDto.getIotProjectId(),
                deviceEventDetailQueryDto.getCreateOperatorId(), deviceEventDetailQueryDto.getRoleCode());
        if (!bizReturn.isSuccess()) {
            LOGGER.warn("该用户没有项目操作权限，用户id：{}， 项目id：{}， 用户角色编码：{}", deviceEventDetailQueryDto.getCreateOperatorId(),
                    deviceEventDetailQueryDto.getIotProjectId(), deviceEventDetailQueryDto.getRoleCode());
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }

        Long iotDesDeviceEventId = deviceEventDetailQueryDto.getIotDesDeviceEventId();
        return ResultDTO.newSuccess(desFeignClient.getEventDetailByIotDesDeviceEventId(iotDesDeviceEventId).getRet());
    }

    @Override
    public ResultDTO<?> getDeviceLoggerListByIotDeviceId(IotDeviceLoggerQueryListDto deviceLoggerQueryListDto) {
        return null;
    }

    @Override
    public IotDeviceDTO getIotDeviceByIotDeviceId(Long iotDeviceId) {
        IotDevice device = iotDeviceDao.getByIotDeviceId(iotDeviceId);
        IotDeviceDTO deviceDTO = null;
        if (device != null) {
            deviceDTO = new IotDeviceDTO();
            BeanUtils.copyProperties(device, deviceDTO);
        }
        return deviceDTO;
    }

//    @Override
//    public List<IotDevice> getIotDeviceByDeviceCode(String deviceCode) {
//        return iotDeviceDao.getIotDeviceByDeviceCode(deviceCode);
//    }

    @Override
    public ResultDTO<String> getDeviceIdByBizCode(String bizCode) {
        return ResultDTO.newSuccess(iotDeviceDao.getDeviceIdByBizCode(bizCode));
    }

    @Override
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByBizCode(String bizCode) {
        return ResultDTO.newSuccess(iotDeviceQueryDao.getProtocolByBizCode(bizCode));
    }

    @Override
    public ResultDTO<List<Long>> getIotProjectIdByDeviceId(String deviceId) {
        if (StringUtils.isEmpty(deviceId)) {
            return ResultDTO.newFail("deviceId 为空");
        }
        List<Long> projectIds = iotDeviceDao.getIotProjectIdByDeviceId(deviceId);
        if (CollectionUtils.isEmpty(projectIds)) {
            return ResultDTO.newSuccess(projectIds);
        }
        return ResultDTO.newFail("找不到对应项目");
    }

    @Override
    public ResultDTO<IotDeviceDetailDTO> getIotDeviceBasicByDeviceCode(String deviceCode, Long iotProjectId) {
        IotDeviceDetailDTO iotDeviceDetailDTO = iotDeviceDao.getDeviceDetail(deviceCode, iotProjectId);
        if (iotDeviceDetailDTO == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_3005.getMessage());
        }

        return ResultDTO.newSuccess(iotDeviceDetailDTO);
    }

    @Override
    public ResultDTO<List<IotDeviceDetailDTO>> getDeviceListByProjectId(Long iotProjectId) {
        IotProject iotProject = iotProjectDao.findByIotProjectIdAndIsDeleted(iotProjectId, false);
        if (iotProject == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_2005.getCode(),
                    ErrorCode.ERROR_2005.getMessage());
        }
        if (!iotProject.getReview()) {
            return ResultDTO.newFail(ErrorCode.ERROR_2015.getCode(),
                    ErrorCode.ERROR_2015.getMessage());
        }
        List<IotDeviceDetailDTO> iotDeviceDetailDTOS = iotDeviceDao.getDeviceListByProjectId(iotProjectId);
        return ResultDTO.newSuccess(iotDeviceDetailDTOS);
    }

    @Override
    public ResultDTO<List<IotDeviceQueryDTO>> getDeviceListCustom(IotDeviceListBaseQueryDTO iotDeviceListBaseQueryDTO) {
        List<IotDeviceQueryDTO> list = iotDeviceQueryDao.getDeviceListCustom(iotDeviceListBaseQueryDTO);
        return ResultDTO.newSuccess(list);
    }

    @Override
    public ResultDTO<List<IotDeviceDetailDTO>> getIotDevicesByProjectIdAndProtocolCode(Long iotProjectId,
                                                                                       String protocolCode) {
        if (iotProjectId == null) {
            return ResultDTO.newFail("项目id不能为空");
        }
        if (StringUtils.isBlank(protocolCode)) {
            return ResultDTO.newFail("协议编码不能为空");
        }
        return ResultDTO.newSuccess(iotDeviceDao.getIotDevicesByProjectIdAndProtocolCode(iotProjectId, protocolCode));
    }

    /**
     * 保存设备操作日志
     *
     * @param iotProjectId
     * @param deviceCodes
     * @param status
     */
    private void saveLogger(long iotProjectId, List<String> deviceCodes, int status) {
        IotLogDTO logDTO = new IotLogDTO();
        logDTO.setIotProjectId(iotProjectId);
        logDTO.setCreateOperatorId(-1L);
        logDTO.setModifyOperatorId(-1L);
        logDTO.setLogTypeCode(Constants.DEVICE_LOGGER_TYPE);
        logDTO.setUserId(-1L);
        logDTO.setLoggerTime(System.currentTimeMillis());
        logDTO.setAssociationType(IotDevice.class.getSimpleName());
        for (String deviceCode : deviceCodes) {
            logDTO.setDeviceCode(deviceCode);
            if (status == 1) {
                logDTO.setLogContent("设备【" + deviceCode + "】启用");
            } else {
                logDTO.setLogContent("设备【" + deviceCode + "】停用");
            }
            producer.sendToQueue(logDTO);
        }
    }

}
