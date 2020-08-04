package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotUserApi;
import allthings.iot.dos.dto.AbstractIotDosDTO;
import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.dto.query.AbstractIotDosQueryListDto;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * @author :  luhao
 * @FileName :  BaseController
 * @CreateDate :  2018-5-7
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
public class BaseController {

    @Autowired
    private IotUserApi iotUserApi;

    protected IotUserDTO getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = (User) auth.getPrincipal();
            ResultDTO<IotUserDTO> bizReturn = iotUserApi.getUserByUsername(user.getUsername());
            IotUserDTO iotUserDTO = bizReturn.getData();
            iotUserDTO.setPassword(null);
            return iotUserDTO;
        } else {
            return new IotUserDTO();
        }
    }

    public IotUserDTO getUserInfo() {
        IotUserDTO iotUserDTO = getUser();
        String mobile = iotUserDTO.getMobile();
        if (StringUtils.isNotBlank(mobile)) {
            iotUserDTO.setMobile(RegExUtils.replacePattern(mobile, "(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        }
        iotUserDTO.setSalt(null);
        return iotUserDTO;
    }

    public void setDto(AbstractIotDosQueryListDto abstractIotDosQueryListDto) {
        IotUserDTO iotUserDTO = getUser();

        abstractIotDosQueryListDto.setCreateOperatorId(iotUserDTO.getIotUserId());
        abstractIotDosQueryListDto.setRoleCode(iotUserDTO.getRoleCode());
        abstractIotDosQueryListDto.setModifyOperatorId(iotUserDTO.getIotUserId());
    }

    public void setDto(AbstractIotDosDTO AbstractIotDosDTO) {
        IotUserDTO iotUserDTO = getUser();
        AbstractIotDosDTO.setCreateOperatorId(iotUserDTO.getIotUserId());
        AbstractIotDosDTO.setRoleCode(iotUserDTO.getRoleCode());
        AbstractIotDosDTO.setModifyOperatorId(iotUserDTO.getIotUserId());
    }
}
