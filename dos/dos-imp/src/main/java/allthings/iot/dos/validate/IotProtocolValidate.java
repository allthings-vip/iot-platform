package allthings.iot.dos.validate;

import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotProtocolDTO;
import allthings.iot.dos.exception.IllegalArgumentException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author :  luhao
 * @FileName :  IotProtocolValidate
 * @CreateDate :  2018-6-3
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
public class IotProtocolValidate {

    private static final String REGEX_IP =
            "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\." +
                    "(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";

    private static final String REGEX_DOMAIN = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?";

    private static final int MAX_PORT = 65535;

    public static void validateSave(IotProtocolDTO iotProtocolDTO) {
        String protocolName = iotProtocolDTO.getProtocolName();
        if (StringUtils.isBlank(protocolName)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7006.getCode(), ErrorCode.ERROR_7006.getMessage());
        }

        int protocolNameMaxLen = 32;
        if (StringUtils.length(protocolName) > protocolNameMaxLen) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7014.getCode(), String.format(ErrorCode
                    .ERROR_7014.getMessage(), protocolName));
        }

        String protocolCode = iotProtocolDTO.getProtocolCode();
        if (StringUtils.isBlank(protocolCode)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7007.getCode(), ErrorCode.ERROR_7007.getMessage());
        }

        int protocolCodeMaxLen = 64;
        if (StringUtils.length(protocolCode) > protocolCodeMaxLen) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7015.getCode(), String.format(ErrorCode
                    .ERROR_7015.getMessage(), protocolCodeMaxLen));
        }

        String serverDomain = iotProtocolDTO.getServerDomain();
        if (StringUtils.isBlank(serverDomain)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7008.getCode(), ErrorCode.ERROR_7008.getMessage());
        }

        if (!Pattern.matches(REGEX_DOMAIN, serverDomain)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7016.getCode(), ErrorCode.ERROR_7016.getMessage());
        }

        String serverIp = iotProtocolDTO.getServerIp();
        if (StringUtils.isBlank(serverIp)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7009.getCode(), ErrorCode.ERROR_7009.getMessage());
        }

        if (!Pattern.matches(REGEX_IP, serverIp)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7017.getCode(), ErrorCode.ERROR_7017.getMessage());
        }

        Integer serverPort = iotProtocolDTO.getServerPort();
        if (serverPort == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7010.getCode(), ErrorCode.ERROR_7010.getMessage());
        }

        if (serverPort > MAX_PORT || serverPort <= 0) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7018.getCode(), ErrorCode.ERROR_7018.getMessage());
        }

        String testServerDomain = iotProtocolDTO.getTestServerDomain();
        if (StringUtils.isBlank(testServerDomain)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7011.getCode(), ErrorCode.ERROR_7011.getMessage());
        }

        if (!Pattern.matches(REGEX_DOMAIN, testServerDomain)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7019.getCode(), ErrorCode.ERROR_7019.getMessage());
        }

        String testServerIp = iotProtocolDTO.getTestServerIp();
        if (StringUtils.isBlank(testServerIp)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7012.getCode(), ErrorCode.ERROR_7012.getMessage());
        }

        if (!Pattern.matches(REGEX_IP, testServerIp)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7020.getCode(), ErrorCode.ERROR_7020.getMessage());
        }

        Integer testServerPort = iotProtocolDTO.getTestServerPort();
        if (testServerPort == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7013.getCode(), ErrorCode.ERROR_7013.getMessage());
        }

        if (testServerPort > MAX_PORT || testServerPort <= 0) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7021.getCode(), ErrorCode.ERROR_7021.getMessage());
        }

        Long[] factorIds = iotProtocolDTO.getIotFactorIds();
        if (factorIds == null || factorIds.length <= 0) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7023.getCode(), ErrorCode.ERROR_7023.getMessage());
        }

        String description = iotProtocolDTO.getDescription();
        if (StringUtils.isNotEmpty(description)) {
            int descriptionMaxLen = 500;
            if (StringUtils.length(description) > descriptionMaxLen) {
                throw new IllegalArgumentException(ErrorCode.ERROR_7022.getCode(), String.format(ErrorCode
                        .ERROR_7022.getMessage
                                (), descriptionMaxLen));
            }
        }

    }


    public static void validateUpdate(IotProtocolDTO iotProtocolDTO) {
        Long iotProtocolId = iotProtocolDTO.getIotProtocolId();
        if (iotProtocolId == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_7004.getCode(), ErrorCode.ERROR_7004.getMessage());
        }

        validateSave(iotProtocolDTO);
    }

}
