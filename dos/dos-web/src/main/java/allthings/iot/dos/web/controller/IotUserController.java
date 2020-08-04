package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotUserApi;
import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-10-30 18:16
 */
@RestController
public class IotUserController extends BaseController {
    @Autowired
    private IotUserApi iotUserApi;

    @PostMapping("/user/save")
    public ResultDTO<Long> saveUser(@RequestBody IotUserDTO iotUserDTO) {
        return iotUserApi.saveUser(iotUserDTO);
    }

    /**
     * 用户修改自身信息
     *
     * @param iotUserDTO
     * @return
     */
    @PostMapping("/user/update")
    public ResultDTO<Integer> updateUser(@RequestBody IotUserDTO iotUserDTO) {
        setDto(iotUserDTO);
        return iotUserApi.updateUser(iotUserDTO);
    }

    /**
     * 管理员修改用户信息
     *
     * @param iotUserDTO
     * @return
     */
    @PostMapping("/user/admin/update")
    public ResultDTO<Integer> updateUserByAdmin(@RequestBody IotUserDTO iotUserDTO) {
        setDto(iotUserDTO);
        return iotUserApi.updateUserByAdmin(iotUserDTO);
    }

    @GetMapping("/user/list")
    public ResultDTO<PageResult<IotUserDTO>> getUserList(@RequestParam("pageIndex") Integer pageIndex,
                                                         @RequestParam("pageSize") Integer pageSize,
                                                         @RequestParam(value = "keywords", required = false) String keywords) {
        IotUserQueryDTO iotUserQueryDTO = new IotUserQueryDTO();
        iotUserQueryDTO.setKeywords(keywords);
        iotUserQueryDTO.setPageSize(pageSize);
        iotUserQueryDTO.setPageIndex(pageIndex);
        setDto(iotUserQueryDTO);
        return iotUserApi.getUserList(iotUserQueryDTO);
    }

    @GetMapping("/user/collaborators")
    public ResultDTO<IotUserDTO> getCollaborators(@RequestParam(value = "keywords", required = false) String keywords) {
        IotUserQueryDTO iotUserQueryDTO = new IotUserQueryDTO();
        iotUserQueryDTO.setKeywords(keywords);
        setDto(iotUserQueryDTO);
        return iotUserApi.getCollaborators(iotUserQueryDTO);
    }

    @GetMapping("/user/get")
    public ResultDTO<IotUserDTO> getUserById() {

        return ResultDTO.newSuccess(getUserInfo());
    }

    @GetMapping("/user/get/{iotUserId}")
    public ResultDTO<IotUserDTO> getUserByAdminByIotUserId(@PathVariable("iotUserId") Long iotUserId) {
        IotUserQueryDTO iotUserQueryDTO = new IotUserQueryDTO();
        iotUserQueryDTO.setIotUserId(iotUserId);
        setDto(iotUserQueryDTO);
        return iotUserApi.getUserByAdminAndIotUserId(iotUserQueryDTO);
    }

    @PostMapping("/user/update/password")
    public ResultDTO<Integer> updatePassword(@RequestBody IotUserDTO iotUserDTO) {

        setDto(iotUserDTO);
        return iotUserApi.updatePassword(iotUserDTO);
    }

    @PostMapping("/user/update/status")
    public ResultDTO<Integer> updateUserStatus(@RequestBody IotUserDTO iotUserDTO) {

        setDto(iotUserDTO);
        return iotUserApi.updateUserStatus(iotUserDTO);
    }
}
