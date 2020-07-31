package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dao.IotDeviceDao;
import allthings.iot.dos.dao.IotFactorDao;
import allthings.iot.dos.dao.IotProtocolDao;
import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import allthings.iot.dos.dto.query.IotFactorRangeValueQueryDTO;
import allthings.iot.dos.dto.query.IotFactorValueQueryDTO;
import allthings.iot.dos.api.IotKvDataService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import allthings.iot.ktv.client.KtvDataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import allthings.iot.common.dto.Result;
import allthings.iot.common.usual.DeviceTypes;
import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.ktv.common.dto.QueryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  IotKvDataBizImpl
 * @CreateDate :  2018-5-18
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
@Service("iotKvDataService")
public class IotKvDataServiceImpl implements IotKvDataService {

    @Autowired
    private KtvDataClient ktvDataClient;
    @Autowired
    private IotDeviceDao iotDeviceDao;

    @Autowired
    private IotFactorDao iotFactorDao;

    @Autowired
    private IotProtocolDao iotProtocolDao;

    @Override
    public ResultDTO<List<IotFactorValueQueryDTO>> getKVLatest(String deviceCode, String partyId, Long iotProjectId) {
        List<IotFactorValueQueryDTO> dtoList = Lists.newArrayList();

        List<String> factorCodes = new ArrayList<>();
        List<IotFactorQueryDTO> factorList = Lists.newArrayList();
        Long iotDeviceId = iotDeviceDao.getIotDeviceIdByDeviceCode(deviceCode, iotProjectId);
        if (iotDeviceId != null) {
            Long protocolId = iotProtocolDao.getIdByIotDeviceId(iotDeviceId);
            factorList = iotFactorDao.getAll(protocolId);
        } else {
            Long protocolId = iotProtocolDao.getIdByCode(DeviceTypes.DEVICE_TYPE_PASS);
            factorList = iotFactorDao.getAll(protocolId);
        }
        for (IotFactorQueryDTO iotFactorQueryDTO : factorList) {
            factorCodes.add(iotFactorQueryDTO.getFactorCode());
        }

        if (CollectionUtils.isEmpty(factorCodes)) {
            return ResultDTO.newSuccess(dtoList);
        }
        QueryDto queryDto = new QueryDto();
        queryDto.setDeviceId(deviceCode);
        queryDto.setFactors(factorCodes.toArray(new String[factorCodes.size()]));
        queryDto.setPartyId(partyId);

        Result<List<Map<String, String>>> result = ktvDataClient.queryKvLatest(queryDto);
        if (!Result.isSuccess(result)) {
            return ResultDTO.newFail(result.getRc(), result.getErr());
        }

        List<Map<String, String>> mapResult = result.getRet();

        for (Map<String, String> map : mapResult) {
            Long inputDate = Long.valueOf(map.get(GpsMsgParam.ATTR_GPS_DATETIME));
            for (String factorCode : factorCodes) {
                IotFactorValueQueryDTO iotFactorValueQueryDTO = new IotFactorValueQueryDTO();
                iotFactorValueQueryDTO.setFactorCode(factorCode);
                iotFactorValueQueryDTO.setValue(map.get(factorCode));
                iotFactorValueQueryDTO.setInputDate(inputDate);
                for (IotFactorQueryDTO iotFactorQueryDTO : factorList) {
                    if (iotFactorQueryDTO.getFactorCode().equals(factorCode)) {
                        iotFactorValueQueryDTO.setUnit_symbol(iotFactorQueryDTO.getUnitSymbol());
                        iotFactorValueQueryDTO.setFactorName(iotFactorQueryDTO.getFactorName());
                    }
                }
                dtoList.add(iotFactorValueQueryDTO);
            }
        }

        return ResultDTO.newSuccess(dtoList);
    }

    @Override
    public ResultDTO<List<IotFactorRangeValueQueryDTO>> getKVRange(String deviceCode, String partyId,
                                                                   String[] factorCodes, Long startDatetime, Long
            endDatetime, Long iotProjectId) {
        Long iotDeviceId = iotDeviceDao.getIotDeviceIdByDeviceCode(deviceCode, iotProjectId);
        Long protocolId = iotProtocolDao.getIdByIotDeviceId(iotDeviceId);
        if (protocolId == null) {
            protocolId = iotProtocolDao.getIdByCode(DeviceTypes.DEVICE_TYPE_PASS);
        }
        List<IotFactorQueryDTO> factorList = iotFactorDao.getAll(protocolId);
//        IotDeviceSimpleDTO simpleDTO = iotDeviceDao.getIotDeviceSimpleByDeviceCode(deviceCode);
//        if (simpleDTO == null) {
//            return ResultDTO.newSuccess(null);
//        }
//
//        if (!deviceCode.startsWith(simpleDTO.getProtocolCode())) {
//            deviceCode = simpleDTO.getProtocolCode() + deviceCode;
//        }
        //TODO:deviceId
        //String deviceId = (String) iotDeviceService.getDeviceIdByDeviceCode(deviceCode).R;

        QueryDto queryDto = new QueryDto();
        queryDto.setDeviceId(deviceCode);
        queryDto.setFactors(factorCodes);
        queryDto.setPartyId(partyId);
        queryDto.setStartDatetime(startDatetime);
        queryDto.setEndDatetime(endDatetime);

        Result<List<Map<String, String>>> result = ktvDataClient.queryKv(queryDto);
        if (!Result.isSuccess(result)) {
            return ResultDTO.newFail(result.getRc(), result.getErr());
        }
        List<Map<String, String>> mapResult = result.getRet();
        Map<String, List<IotFactorValueQueryDTO>> resultMap = Maps.newHashMap();

        for (String factorCode : factorCodes) {
            resultMap.put(factorCode, Lists.newArrayList());
        }

        for (Map<String, String> map : mapResult) {
            Long inputDate = Long.valueOf(map.get(GpsMsgParam.ATTR_GPS_DATETIME));

            for (String factorCode : factorCodes) {
                IotFactorValueQueryDTO iotFactorValueQueryDTO = new IotFactorValueQueryDTO();
                iotFactorValueQueryDTO.setFactorCode(factorCode);
                if (map.get(factorCode) == null) {
                    continue;
                }

                iotFactorValueQueryDTO.setValue(map.get(factorCode));
                iotFactorValueQueryDTO.setInputDate(inputDate);

                List<IotFactorValueQueryDTO> list = resultMap.get(iotFactorValueQueryDTO.getFactorCode());
                list.add(iotFactorValueQueryDTO);
            }
        }

        List<IotFactorRangeValueQueryDTO> factorValueList = Lists.newArrayList();
        for (String factorCode : factorCodes) {
            IotFactorRangeValueQueryDTO queryDTO = new IotFactorRangeValueQueryDTO();
            queryDTO.setFactorCode(factorCode);
            List<IotFactorValueQueryDTO> list = resultMap.get(factorCode);
            for (IotFactorQueryDTO iotFactorQueryDTO : factorList) {
                if (iotFactorQueryDTO.getFactorCode().equals(factorCode)) {
                    queryDTO.setFactorName(iotFactorQueryDTO.getFactorName());
                }
            }
            if (list == null) {
                queryDTO.setData(Lists.newArrayList());
            } else {
                queryDTO.setData(list);
            }

            factorValueList.add(queryDTO);
        }
        return ResultDTO.newSuccess(factorValueList);
    }
}
