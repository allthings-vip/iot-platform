package allthings.iot.dos.validate;

import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotDeviceTypeDTO;
import allthings.iot.dos.exception.IllegalArgumentException;
import org.apache.commons.lang3.StringUtils;


/**
 * @author :  luhao
 * @FileName :  IotDeviceTypeValidate
 * @CreateDate :  2018/4/29
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
public class IotDeviceTypeValidate {

    private static final String REGEX_DEVICE_CODE = "^[A-Za-z0-9_]{1,32}$";

    /**
     * 校验保存
     *
     * @param iotDeviceTypeDTO
     */
    public static void validateSave(IotDeviceTypeDTO iotDeviceTypeDTO) {
        String deviceTypeCode = iotDeviceTypeDTO.getDeviceTypeCode();
        if (StringUtils.isBlank(deviceTypeCode)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_1006.getCode(), ErrorCode.ERROR_1006.getMessage());
        }
        //设备类型编码规则
        deviceTypeCode = StringUtils.trim(deviceTypeCode);
        int deviceTypeCodeLength = 32;
        if (StringUtils.length(deviceTypeCode) > deviceTypeCodeLength) {
            throw new IllegalArgumentException(ErrorCode.ERROR_1007.getCode(), String.format(ErrorCode
                    .ERROR_1007.getMessage
                            (), deviceTypeCodeLength));
        }

        Long iotProtocolId = iotDeviceTypeDTO.getIotProtocolId();
        if (iotProtocolId == null || iotProtocolId <= 0) {
            throw new IllegalArgumentException(ErrorCode.ERROR_1010.getCode(), ErrorCode.ERROR_1010.getMessage());
        }
        validate(iotDeviceTypeDTO);
    }

    /**
     * 校验设备类型修改
     *
     * @param iotDeviceTypeDTO
     */
    public static void validateUpdate(IotDeviceTypeDTO iotDeviceTypeDTO) {
        Long iotDeviceTypeId = iotDeviceTypeDTO.getIotDeviceTypeId();
        if (iotDeviceTypeId == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_1004.getCode(), ErrorCode.ERROR_1004.getMessage());
        }

        validate(iotDeviceTypeDTO);
    }

    private static void validate(IotDeviceTypeDTO iotDeviceTypeDTO) {
        String deviceTypeName = iotDeviceTypeDTO.getDeviceTypeName();
        if (StringUtils.isBlank(deviceTypeName)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_1008.getCode(), ErrorCode.ERROR_1008.getMessage());
        }

        //设备类型名称规则
        deviceTypeName = StringUtils.trim(deviceTypeName);
        int length = 32;
        if (deviceTypeName.length() > length) {
            throw new IllegalArgumentException(ErrorCode.ERROR_1009.getCode(), String.format(ErrorCode
                    .ERROR_1009.getMessage
                            (), length));
        }

        int descriptionMaxLen = 200;
        String description = iotDeviceTypeDTO.getDescription();
        if (StringUtils.isNotEmpty(description)) {
            if (StringUtils.length(description) > descriptionMaxLen) {
                throw new IllegalArgumentException(ErrorCode.ERROR_1021.getCode(), String.format(ErrorCode
                        .ERROR_1021.getMessage(), descriptionMaxLen));
            }
        }

        Long iotProjectId = iotDeviceTypeDTO.getIotProjectId();
        if (iotProjectId == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_2004.getCode(), ErrorCode.ERROR_2004.getMessage());
        }
    }
}
