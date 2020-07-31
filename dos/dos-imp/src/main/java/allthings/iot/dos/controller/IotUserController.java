package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotUserBiz;
import allthings.iot.dos.client.api.IotUserApi;
import allthings.iot.dos.dto.IotAuthorityDTO;
import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.PageResult;
import tf56.iot.common.dto.ResultDTO;

/**
 * @author tyf
 * @date 2019/07/04 16:47:14
 */
@RestController
public class IotUserController extends IotDosBaseController implements IotUserApi {

    @Autowired
    private IotUserBiz userBiz;

    @Override
    public ResultDTO<IotUserDTO> getUserByUsername(@RequestParam("username") String username) {
        return getResult(userBiz.getUserByUsername(username));
    }

    @Override
    public ResultDTO<Long> saveUser(@RequestBody IotUserDTO iotUserDTO) {
        return getResult(userBiz.saveUser(iotUserDTO));
    }

    @Override
    public ResultDTO<Integer> updateUser(@RequestBody IotUserDTO iotUserDTO) {
        return getResult(userBiz.updateUser(iotUserDTO));
    }

    @Override
    public ResultDTO<Integer> updateUserByAdmin(@RequestBody IotUserDTO iotUserDTO) {
        return getResult(userBiz.updateUserByAdmin(iotUserDTO));
    }

    @Override
    public ResultDTO<Integer> deleteUser(@RequestBody IotUserDTO iotUserDTO) {
        return getResult(userBiz.deleteUser(iotUserDTO));
    }

    @Override
    public ResultDTO<PageResult<IotUserDTO>> getUserList(@RequestBody IotUserQueryDTO iotUserQueryDTO) {
        return getResult(userBiz.getUserList(iotUserQueryDTO));
    }

    @Override
    public ResultDTO<IotUserDTO> getCollaborators(@RequestBody IotUserQueryDTO iotUserQueryDTO) {
        return getResult(userBiz.getCollaborators(iotUserQueryDTO));
    }

    @Override
    public ResultDTO<IotUserDTO> getUserById(@RequestBody IotUserQueryDTO iotUserQueryDTO) {
        return getResult(userBiz.getUserById(iotUserQueryDTO));
    }

    @Override
    public ResultDTO<IotUserDTO> getUserByAdminAndIotUserId(@RequestBody IotUserQueryDTO iotUserQueryDTO) {
        return getResult(userBiz.getUserByAdminAndIotUserId(iotUserQueryDTO));
    }

    @Override
    public ResultDTO<Integer> updatePassword(@RequestBody IotUserDTO iotUserDTO) {
        return getResult(userBiz.updatePassword(iotUserDTO));
    }

    @Override
    public ResultDTO<Integer> updateUserStatus(@RequestBody IotUserDTO iotUserDTO) {
        return getResult(userBiz.updateUserStatus(iotUserDTO));
    }

    @Override
    public ResultDTO<Integer> getAdminAuthority(@RequestBody IotAuthorityDTO iotAuthorityDTO) {
        return getResult(userBiz.getAdminAuthority(iotAuthorityDTO));
    }

}
