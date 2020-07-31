package allthings.iot.dos.validate;

import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.exception.IllegalArgumentException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-10-31 08:52
 */

public class IotUserValidate {
    private static final String MOBILE = "^(13[0-9]|14[5-9]|15[0-35-9]|166|17[0-8]|18[0-9]|19[89])\\d{8}";

    private static final String EMAIL = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";

    private static final String PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";

    public static void validateSave(IotUserDTO iotUserDTO) {
        int length_32 = 32;
        if (StringUtils.isBlank(iotUserDTO.getUsername()) || StringUtils.length(iotUserDTO.getUsername()) > length_32) {
            throw new IllegalArgumentException(ErrorCode.ERROR_8003.getCode(),
                    String.format(ErrorCode.ERROR_8003.getMessage(), length_32));
        }
        validatePassword(iotUserDTO);


        if (!Pattern.matches(PASSWORD, iotUserDTO.getPassword())) {
            throw new IllegalArgumentException(ErrorCode.ERROR_8029.getCode(), ErrorCode.ERROR_8029.getMessage());
        }

        validateUpdate(iotUserDTO);
    }

    public static void validatePassword(IotUserDTO iotUserDTO) {
        if (StringUtils.isBlank(iotUserDTO.getPassword())) {
            throw new IllegalArgumentException(ErrorCode.ERROR_8004.getCode(), ErrorCode.ERROR_8004.getMessage());
        }

        if (StringUtils.isBlank(iotUserDTO.getPassword2())) {
            throw new IllegalArgumentException(ErrorCode.ERROR_8028.getCode(), ErrorCode.ERROR_8028.getMessage());
        }

        if (!Pattern.matches(PASSWORD, iotUserDTO.getPassword2())) {
            throw new IllegalArgumentException(ErrorCode.ERROR_8030.getCode(), ErrorCode.ERROR_8030.getMessage());
        }

        if (StringUtils.isNotBlank(iotUserDTO.getNewPassword()) &&
                !Pattern.matches(PASSWORD, iotUserDTO.getNewPassword())) {
            throw new IllegalArgumentException(ErrorCode.ERROR_8031.getCode(), ErrorCode.ERROR_8031.getMessage());
        }
    }

    public static void validateUpdate(IotUserDTO iotUserDTO) {
        int length_32 = 32;
        if (StringUtils.isBlank(iotUserDTO.getRealName()) || StringUtils.length(iotUserDTO.getRealName()) > length_32) {
            throw new IllegalArgumentException(ErrorCode.ERROR_8005.getCode(),
                    String.format(ErrorCode.ERROR_8005.getMessage(), length_32));
        }

        if (StringUtils.isBlank(iotUserDTO.getMobile()) || !Pattern.matches(MOBILE, iotUserDTO.getMobile())) {
            throw new IllegalArgumentException(ErrorCode.ERROR_8006.getCode(), ErrorCode.ERROR_8006.getMessage());
        }

        if (StringUtils.isNotEmpty(iotUserDTO.getEmail()) && !Pattern.matches(EMAIL, iotUserDTO.getEmail())) {
            throw new IllegalArgumentException(ErrorCode.ERROR_8008.getCode(), ErrorCode.ERROR_8008.getMessage());
        }

        int length_64 = 64;
        if (StringUtils.isBlank(iotUserDTO.getCompanyName()) ||
                StringUtils.length(iotUserDTO.getCompanyName()) > length_64) {
            throw new IllegalArgumentException(ErrorCode.ERROR_8009.getCode(),
                    String.format(ErrorCode.ERROR_8009.getMessage(), length_64));
        }
    }
}
