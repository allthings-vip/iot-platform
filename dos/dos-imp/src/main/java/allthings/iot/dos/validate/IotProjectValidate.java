package allthings.iot.dos.validate;

import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotProjectDTO;
import allthings.iot.dos.exception.IllegalArgumentException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author :  luhao
 * @FileName :  IotProjectValidate
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
public class IotProjectValidate {

    /**
     * 保存项目校验
     *
     * @param iotProjectDTO
     */
    public static void validateSave(IotProjectDTO iotProjectDTO) {
        if (iotProjectDTO == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_2012.getCode(), ErrorCode.ERROR_2012.getMessage());
        }

        String projectName = iotProjectDTO.getProjectName();
        if (StringUtils.isEmpty(projectName)) {
            throw new IllegalArgumentException(ErrorCode.ERROR_2006.getCode(), ErrorCode.ERROR_2006.getMessage());
        }

        int projectNameMaxLen = 32;
        if (StringUtils.length(projectName) > projectNameMaxLen) {
            throw new IllegalArgumentException(ErrorCode.ERROR_2007.getCode(), String.format(ErrorCode
                    .ERROR_2007.getMessage(), projectNameMaxLen));
        }

        String companyName = iotProjectDTO.getCompanyName();
        if (StringUtils.isNotEmpty(companyName)) {
            int companyNameMaxLen = 64;
            if (StringUtils.length(companyName) > companyNameMaxLen) {
                throw new IllegalArgumentException(ErrorCode.ERROR_2009.getCode(), String.format(ErrorCode
                        .ERROR_2009.getMessage(), companyNameMaxLen));
            }
        }

        String description = iotProjectDTO.getDescription();
        if (StringUtils.isNotEmpty(description)) {
            int descriptionMaxLen = 100;
            if (StringUtils.length(description) > descriptionMaxLen) {
                throw new IllegalArgumentException(ErrorCode.ERROR_2010.getCode(), String.format(ErrorCode
                        .ERROR_2010.getMessage
                                (), descriptionMaxLen));
            }
        }
    }

    /**
     * 修改项目校验
     *
     * @param iotProjectDTO
     */
    public static void validateUpdate(IotProjectDTO iotProjectDTO) {
        Long iotProjectId = iotProjectDTO.getIotProjectId();
        if (iotProjectId == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_2004.getCode(), ErrorCode.ERROR_2004.getMessage());
        }

        validateSave(iotProjectDTO);
    }

}
