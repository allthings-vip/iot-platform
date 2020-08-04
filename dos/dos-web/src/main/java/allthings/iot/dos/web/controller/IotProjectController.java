package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotEventPushUrlApi;
import allthings.iot.dos.client.api.IotProjectApi;
import allthings.iot.dos.dto.IotEventPushUrlDto;
import allthings.iot.dos.dto.IotProjectDTO;
import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.dto.query.IotAppSecretQueryDTO;
import allthings.iot.dos.dto.query.IotProjectDeleteQueryDTO;
import allthings.iot.dos.dto.query.IotProjectQueryDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProjectController
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
@RestController
public class IotProjectController extends BaseController {
    @Autowired
    private IotProjectApi iotProjectApi;

    @Autowired
    private IotEventPushUrlApi iotEventPushUrlApi;

    @PostMapping(value = "/projects/save")
    public ResultDTO<Long> saveProject(@RequestBody IotProjectDTO iotProjectDTO) {
        setDto(iotProjectDTO);
        return iotProjectApi.saveIotProject(iotProjectDTO);
    }

    @PostMapping(value = "/projects/update")
    public ResultDTO<Integer> updateProject(@RequestBody IotProjectDTO iotProjectDTO) {
        setDto(iotProjectDTO);
        return iotProjectApi.updateIotProject(iotProjectDTO);
    }

    @PostMapping(value = "/projects/delete")
    public ResultDTO<Integer> deleteProject(Long[] iotProjectIds) {
        IotProjectDeleteQueryDTO iotProjectDeleteQueryDTO = new IotProjectDeleteQueryDTO();
        iotProjectDeleteQueryDTO.setIotProjectIds(iotProjectIds);
        setDto(iotProjectDeleteQueryDTO);
        return iotProjectApi.deleteIotProject(iotProjectDeleteQueryDTO);
    }

    @GetMapping(value = "/projects/{iotProjectId}")
    public ResultDTO<IotProjectDTO> getProject(@PathVariable("iotProjectId") Long iotProjectId) {
        IotProjectSimpleDTO iotProjectSimpleDTO = new IotProjectSimpleDTO();
        setDto(iotProjectSimpleDTO);
        iotProjectSimpleDTO.setIotProjectId(iotProjectId);
        return iotProjectApi.getIotProjectDetail(iotProjectSimpleDTO);
    }


    @GetMapping(value = "/projects/name")
    public ResultDTO<List<IotProjectSimpleDTO>> getProjectName() {
        IotProjectSimpleDTO iotProjectSimpleDTO = new IotProjectSimpleDTO();
        setDto(iotProjectSimpleDTO);
        return iotProjectApi.getIotProjectNameList(iotProjectSimpleDTO);
    }

    @GetMapping(value = "/projects/list")
    public ResultDTO<PageResult<IotProjectQueryDTO>> getProject(@RequestParam(value = "projectName",
            required = false) String projectName,
                                                                @RequestParam("pageIndex") Integer pageIndex,
                                                                @RequestParam("pageSize") Integer pageSize) {
        IotProjectSimpleDTO iotProjectSimpleDTO = new IotProjectSimpleDTO();
        iotProjectSimpleDTO.setProjectName(projectName);
        iotProjectSimpleDTO.setPageIndex(pageIndex);
        iotProjectSimpleDTO.setPageSize(pageSize);
        setDto(iotProjectSimpleDTO);
        return iotProjectApi.getIotProjectList(iotProjectSimpleDTO);
    }

    @PostMapping("/projects/review")
    public ResultDTO<Integer> reviewProject(@RequestParam("iotProjectId") Long iotProjectId) {
        IotProjectDTO iotProjectDTO = new IotProjectDTO();
        iotProjectDTO.setIotProjectId(iotProjectId);
        setDto(iotProjectDTO);
        return iotProjectApi.reviewProject(iotProjectDTO);
    }

    @PostMapping("/projects/appsecret")
    public ResultDTO<String> getAppSecret(@RequestBody IotAppSecretQueryDTO iotAppSecretQueryDTO) {
        IotUserDTO iotUserDTO = getUser();
        iotAppSecretQueryDTO.setModifyOperatorId(iotUserDTO.getIotUserId());
        iotAppSecretQueryDTO.setCreateOperatorId(iotUserDTO.getIotUserId());
        iotAppSecretQueryDTO.setRoleCode(iotUserDTO.getRoleCode());
        iotAppSecretQueryDTO.setMobile(iotUserDTO.getMobile());
        return iotProjectApi.getAppSecret(iotAppSecretQueryDTO);
    }

    @PostMapping("/project/push/url")
    public ResultDTO<Long> saveOrUpdatePushUrl(@RequestBody IotEventPushUrlDto iotEventPushUrlDto) {
        setDto(iotEventPushUrlDto);
        return iotEventPushUrlApi.saveIotEventPushUrl(iotEventPushUrlDto);
    }

    @GetMapping("/project/get/push/url")
    public ResultDTO<IotEventPushUrlDto> getProjectPushUrl(@RequestParam("iotProjectId") Long iotProjectId) {
        IotEventPushUrlDto eventPushUrlDto = new IotEventPushUrlDto();
        eventPushUrlDto.setIotProjectId(iotProjectId);
        setDto(eventPushUrlDto);
        return iotEventPushUrlApi.getEventPushUrlByIotProjectId(eventPushUrlDto);
    }
}
