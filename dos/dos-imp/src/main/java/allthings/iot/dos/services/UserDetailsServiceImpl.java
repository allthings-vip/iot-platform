package allthings.iot.dos.services;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotUserService;
import allthings.iot.dos.dto.IotUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author :  luhao
 * @FileName :  UserDetailsServiceImpl
 * @CreateDate :  2018-5-9
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
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IotUserService iotUserApi;

    @Override
    public UserDetails loadUserByUsername(String un) throws UsernameNotFoundException {
        ResultDTO<IotUserDTO> result = iotUserApi.getUserByUsername(un);
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (!result.isSuccess()) {
            throw new UsernameNotFoundException(String.format("username:【%s】 not found", un));
        }

        IotUserDTO iotUserDTO = result.getData();
        if (iotUserDTO == null) {
            throw new UsernameNotFoundException(String.format("username:【%s】 not found", un));
        }

        if (!iotUserDTO.getUsername().equals(un)) {
            throw new UsernameNotFoundException(String.format("username:【%s】 not found", un));
        }

        if (!iotUserDTO.getEnabled()) {
            throw new DisabledException(String.format("username:【%s】is disabled,", un));
        }
        //iotRedisFactory.set(un, iotUserDTO.getSalt());
        return new User(iotUserDTO.getUsername(),
                iotUserDTO.getPassword() + "@" + iotUserDTO.getSalt(), true, true, true, true, authorities);
    }
}
