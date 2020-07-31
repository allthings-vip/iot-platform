package allthings.iot.dos.client.api;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.IotAuthorityDTO;
import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author luhao
 * @date 2020/2/17 16:01
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotUserApi {

    @GetMapping("/dos/service/getUserByUsername")
    ResultDTO<IotUserDTO> getUserByUsername(@RequestParam("username") String username);

    @PostMapping("/dos/service/saveUser")
    ResultDTO<Long> saveUser(@RequestBody IotUserDTO iotUserDTO);

    @PostMapping("/dos/service/updateUser")
    ResultDTO<Integer> updateUser(@RequestBody IotUserDTO iotUserDTO);

    @PostMapping("/dos/service/updateUserByAdmin")
    ResultDTO<Integer> updateUserByAdmin(@RequestBody IotUserDTO iotUserDTO);

    @PostMapping("/dos/service/deleteUser")
    ResultDTO<Integer> deleteUser(@RequestBody IotUserDTO iotUserDTO);

    @PostMapping("/dos/service/getUserList")
    ResultDTO<PageResult<IotUserDTO>> getUserList(@RequestBody IotUserQueryDTO iotUserQueryDTO);

    @PostMapping("/dos/service/getCollaborators")
    ResultDTO<IotUserDTO> getCollaborators(@RequestBody IotUserQueryDTO iotUserQueryDTO);

    @PostMapping("/dos/service/getUserById")
    ResultDTO<IotUserDTO> getUserById(@RequestBody IotUserQueryDTO iotUserQueryDTO);

    @PostMapping("/dos/service/getUserByAdminAndIotUserId")
    ResultDTO<IotUserDTO> getUserByAdminAndIotUserId(@RequestBody IotUserQueryDTO iotUserQueryDTO);

    @PostMapping("/dos/service/updatePassword")
    ResultDTO<Integer> updatePassword(@RequestBody IotUserDTO iotUserDTO);

    @PostMapping("/dos/service/updateUserStatus")
    ResultDTO<Integer> updateUserStatus(@RequestBody IotUserDTO iotUserDTO);

    @PostMapping("/dos/service/getAdminAuthority")
    ResultDTO<Integer> getAdminAuthority(@RequestBody IotAuthorityDTO iotAuthorityDTO);
}
