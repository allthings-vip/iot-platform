package allthings.iot.dos.validate;

import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceErrorMsgDTO;
import allthings.iot.dos.exception.IllegalArgumentException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author :  luhao
 * @FileName :  IotDeviceValidate
 * @CreateDate :  2018/5/7
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
public class IotDeviceValidate {

    /**
     * 保存校验
     *
     * @param iotDeviceDTO
     */
    public static IotDeviceErrorMsgDTO validateSave(IotDeviceDTO iotDeviceDTO, boolean isBatch) {
        Long iotProjectId = iotDeviceDTO.getIotProjectId();
        IotDeviceErrorMsgDTO iotDeviceErrorMsgDTO = new IotDeviceErrorMsgDTO();
        if (iotDeviceDTO.getRowNum() != null) {
            iotDeviceErrorMsgDTO.setRowNum(iotDeviceDTO.getRowNum());
        }
        if (iotProjectId == null) {
            if (isBatch) {
                iotDeviceErrorMsgDTO.getErrorMsgList().add(ErrorCode.ERROR_3011.getMessage());
            } else {
                throw new IllegalArgumentException(ErrorCode.ERROR_3011.getCode(), ErrorCode.ERROR_3011.getMessage());
            }
        }

        String deviceCode = iotDeviceDTO.getDeviceCode();
        if (StringUtils.isEmpty(deviceCode)) {
            if (isBatch) {
                iotDeviceErrorMsgDTO.getErrorMsgList().add(ErrorCode.ERROR_3007.getMessage());
            } else {
                throw new IllegalArgumentException(ErrorCode.ERROR_3007.getCode(), ErrorCode.ERROR_3007.getMessage());
            }
        }

        int deviceCodeMaxLen = 64;
        if (StringUtils.length(deviceCode) > deviceCodeMaxLen) {
            if (isBatch) {
                iotDeviceErrorMsgDTO.getErrorMsgList().add(String.format(ErrorCode.ERROR_3012.getMessage(),
                        deviceCodeMaxLen));
            } else {
                throw new IllegalArgumentException(ErrorCode.ERROR_3012.getCode(), String.format(ErrorCode
                                .ERROR_3012.getMessage(),
                        deviceCodeMaxLen));
            }
        }

        String bizCode = iotDeviceDTO.getBizCode();

        if (StringUtils.isNotEmpty(bizCode)) {
            int bizCodeMaxLen = 64;
            if (StringUtils.length(bizCode) > bizCodeMaxLen) {
                if (isBatch) {
                    iotDeviceErrorMsgDTO.getErrorMsgList().add(String.format(ErrorCode.ERROR_3014.getMessage(),
                            bizCodeMaxLen));
                } else {
                    throw new IllegalArgumentException(ErrorCode.ERROR_3014.getCode(), String.format(ErrorCode
                                    .ERROR_3014.getMessage(),
                            bizCodeMaxLen));
                }
            }
        }

        String deviceName = iotDeviceDTO.getDeviceName();
        if (StringUtils.isNotEmpty(deviceName)) {
            int deviceNameMaxLen = 64;
            if (StringUtils.length(deviceName) > deviceNameMaxLen) {
                if (isBatch) {
                    iotDeviceErrorMsgDTO.getErrorMsgList().add(String.format(ErrorCode.ERROR_3015.getMessage(),
                            deviceNameMaxLen));
                } else {
                    throw new IllegalArgumentException(ErrorCode.ERROR_3015.getCode(), String.format(ErrorCode
                                    .ERROR_3015.getMessage(),
                            deviceNameMaxLen));
                }
            }
        }

        Long iotDeviceTypeId = iotDeviceDTO.getIotDeviceTypeId();
        if (!isBatch && iotDeviceTypeId == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_3009.getCode(), ErrorCode.ERROR_3009.getMessage());
        }

        String mac = iotDeviceDTO.getMac();
        if (StringUtils.isNotEmpty(mac)) {
            int macMaxLen = 64;
            if (StringUtils.length(mac) > macMaxLen) {
                if (isBatch) {
                    iotDeviceErrorMsgDTO.getErrorMsgList().add(String.format(ErrorCode.ERROR_3016.getMessage(),
                            macMaxLen));
                } else {
                    throw new IllegalArgumentException(ErrorCode.ERROR_3016.getCode(), String.format(ErrorCode
                            .ERROR_3016.getMessage(), macMaxLen));
                }
            }
        }

        String firmwareVersion = iotDeviceDTO.getFirmwareVersion();
        if (StringUtils.isNotEmpty(firmwareVersion)) {
            int firmwareVersionMaxLen = 64;
            if (StringUtils.length(firmwareVersion) > firmwareVersionMaxLen) {
                if (isBatch) {
                    iotDeviceErrorMsgDTO.getErrorMsgList().add(String.format(ErrorCode.ERROR_3017.getMessage(),
                            firmwareVersionMaxLen));
                } else {
                    throw new IllegalArgumentException(ErrorCode.ERROR_3017.getCode(), String.format(ErrorCode
                                    .ERROR_3017.getMessage(),
                            firmwareVersionMaxLen));
                }
            }
        }

        String firmwareModel = iotDeviceDTO.getFirmwareModel();
        if (StringUtils.isNotEmpty(firmwareModel)) {
            int firmwareModelMaxLen = 64;
            if (StringUtils.length(firmwareModel) > firmwareModelMaxLen) {
                if (isBatch) {
                    iotDeviceErrorMsgDTO.getErrorMsgList().add(String.format(ErrorCode.ERROR_3018.getMessage(),
                            firmwareModelMaxLen));
                } else {
                    throw new IllegalArgumentException(ErrorCode.ERROR_3018.getCode(), String.format(ErrorCode
                                    .ERROR_3018.getMessage(),
                            firmwareModelMaxLen));
                }
            }
        }

        String desc = iotDeviceDTO.getDescription();
        if (StringUtils.isNotEmpty(desc)) {
            int descMaxLen = 200;
            if (StringUtils.length(desc) > descMaxLen) {
                if (isBatch) {
                    iotDeviceErrorMsgDTO.getErrorMsgList().add(String.format(ErrorCode.ERROR_3037.getMessage(),
                            descMaxLen));
                } else {
                    throw new IllegalArgumentException(ErrorCode.ERROR_3037.getCode(), String.format(ErrorCode
                                    .ERROR_3037.getMessage(),
                            descMaxLen));
                }
            }
        }

        if (iotDeviceErrorMsgDTO.getErrorMsgList().size() <= 0) {
            return null;
        } else {
            return iotDeviceErrorMsgDTO;
        }

    }

    /**
     * 更新校验
     *
     * @param iotDeviceDTO
     */
    public static IotDeviceErrorMsgDTO validateUpdate(IotDeviceDTO iotDeviceDTO, boolean isBatch) {
        IotDeviceErrorMsgDTO iotDeviceErrorMsgDTO = new IotDeviceErrorMsgDTO();
        if (iotDeviceDTO.getRowNum() != null) {
            iotDeviceErrorMsgDTO.setRowNum(iotDeviceDTO.getRowNum());
        }
        Long iotDeviceId = iotDeviceDTO.getIotDeviceId();
        if (iotDeviceId == null) {
            if (isBatch) {
                iotDeviceErrorMsgDTO.getErrorMsgList().add(ErrorCode.ERROR_3004.getMessage());
            } else {
                throw new IllegalArgumentException(ErrorCode.ERROR_3004.getCode(), ErrorCode.ERROR_3004.getMessage());
            }
        }

        IotDeviceErrorMsgDTO dto = validateSave(iotDeviceDTO, isBatch);
        if (dto != null) {
            iotDeviceErrorMsgDTO.getErrorMsgList().addAll(dto.getErrorMsgList());
            return iotDeviceErrorMsgDTO;
        } else {
            if (iotDeviceErrorMsgDTO.getErrorMsgList().size() <= 0) {
                return null;
            } else {
                return iotDeviceErrorMsgDTO;
            }
        }
    }

}
