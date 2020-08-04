package allthings.iot.dos.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotUserService;
import allthings.iot.dos.client.api.IotUserApi;
import allthings.iot.dos.dto.IotAuthorityDTO;
import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tyf
 * @date 2019/07/04 16:47:14
 */
@RestController
public class IotUserController implements IotUserApi {

    @Autowired
    private IotUserService userService;

    @Override
    public ResultDTO<IotUserDTO> getUserByUsername(@RequestParam("username") String username) {
        return userService.getUserByUsername(username);
    }

    @Override
    public ResultDTO<Long> saveUser(@RequestBody IotUserDTO iotUserDTO) {
        return userService.saveUser(iotUserDTO);
    }

    @Override
    public ResultDTO<Integer> updateUser(@RequestBody IotUserDTO iotUserDTO) {
        return userService.updateUser(iotUserDTO);
    }

    @Override
    public ResultDTO<Integer> updateUserByAdmin(@RequestBody IotUserDTO iotUserDTO) {
        return userService.updateUserByAdmin(iotUserDTO);
    }

    @Override
    public ResultDTO<Integer> deleteUser(@RequestBody IotUserDTO iotUserDTO) {
        return userService.deleteUser(iotUserDTO);
    }

    @Override
    public ResultDTO<PageResult<IotUserDTO>> getUserList(@RequestBody IotUserQueryDTO iotUserQueryDTO) {
        return userService.getUserList(iotUserQueryDTO);
    }

    @Override
    public ResultDTO<IotUserDTO> getCollaborators(@RequestBody IotUserQueryDTO iotUserQueryDTO) {
        return userService.getCollaborators(iotUserQueryDTO);
    }

    @Override
    public ResultDTO<IotUserDTO> getUserById(@RequestBody IotUserQueryDTO iotUserQueryDTO) {
        return userService.getUserById(iotUserQueryDTO);
    }

    @Override
    public ResultDTO<IotUserDTO> getUserByAdminAndIotUserId(@RequestBody IotUserQueryDTO iotUserQueryDTO) {
        return userService.getUserByAdminAndIotUserId(iotUserQueryDTO);
    }

    @Override
    public ResultDTO<Integer> updatePassword(@RequestBody IotUserDTO iotUserDTO) {
        return userService.updatePassword(iotUserDTO);
    }

    @Override
    public ResultDTO<Integer> updateUserStatus(@RequestBody IotUserDTO iotUserDTO) {
        return userService.updateUserStatus(iotUserDTO);
    }

    @Override
    public ResultDTO<Integer> getAdminAuthority(@RequestBody IotAuthorityDTO iotAuthorityDTO) {
        return userService.getAdminAuthority(iotAuthorityDTO);
    }

}
