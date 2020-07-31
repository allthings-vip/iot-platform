package allthings.iot.dos.validate;

import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotFactorDTO;
import allthings.iot.dos.exception.IllegalArgumentException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author :  luhao
 * @FileName :  IotFactorValidate
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
public class IotFactorValidate {

    /**
     * 校验保存因子
     *
     * @param iotFactorDTO
     */
    public static void validateSave(IotFactorDTO iotFactorDTO) {
        String factorCode = iotFactorDTO.getFactorCode();
        if (StringUtils.isEmpty(factorCode)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_4006.getCode(), ErrorCode.ERROR_4006.getMessage());
        }

        String regexFactorCode = "\\w{1,100}";
        if (!Pattern.matches(regexFactorCode, factorCode)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_4007.getCode(), String.format(ErrorCode
                    .ERROR_4007.getMessage
                            (), 100));
        }

        String factorName = iotFactorDTO.getFactorName();
        if (StringUtils.isEmpty(factorName)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_4008.getCode(), ErrorCode.ERROR_4008.getMessage());
        }

        int factorNameMaxLen = 32;
        if (StringUtils.length(factorName) > factorNameMaxLen) {
            throw new IllegalArgumentException(ErrorCode.ERROR_4009.getCode(), String.format(ErrorCode
                    .ERROR_4009.getMessage
                            (), factorNameMaxLen));
        }

        Integer iotDataAggTypeId = iotFactorDTO.getIotDataAggTypeId();
        if (iotDataAggTypeId == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_4010.getCode(), ErrorCode.ERROR_4010.getMessage());
        }

        String unitName = iotFactorDTO.getUnitName();
        if (StringUtils.isNotEmpty(unitName)) {
            int unitNameMaxLen = 10;
            if (StringUtils.length(unitName) > unitNameMaxLen) {
                throw new IllegalArgumentException(ErrorCode.ERROR_4011.getCode(), String.format(ErrorCode
                        .ERROR_4011.getMessage
                                (), unitNameMaxLen));
            }
        }

        String unitSymbol = iotFactorDTO.getUnitSymbol();
        if (StringUtils.isNotEmpty(unitSymbol)) {
            int unitSymbolMaxLen = 10;
            if (StringUtils.length(unitSymbol) > unitSymbolMaxLen) {
                throw new IllegalArgumentException(ErrorCode.ERROR_4012.getCode(), String.format(ErrorCode
                        .ERROR_4012.getMessage
                                (), unitSymbolMaxLen));
            }
        }
    }

    /**
     * 校验修改因子
     *
     * @param iotFactorDTO
     */
    public static void validateUpdate(IotFactorDTO iotFactorDTO) {
        Long iotFactorId = iotFactorDTO.getIotFactorId();
        if (iotFactorId == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_4004.getCode(), ErrorCode.ERROR_4004.getMessage());
        }

        validateSave(iotFactorDTO);
    }

}
