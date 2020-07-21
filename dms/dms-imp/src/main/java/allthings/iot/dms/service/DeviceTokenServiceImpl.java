package allthings.iot.dms.service;

import com.google.common.collect.Lists;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.pojo.DeviceGuid;
import allthings.iot.dms.IDeviceTokenService;
import allthings.iot.dms.dao.DeviceTokenDao;
import allthings.iot.dms.dto.DeviceTokenDto;
import allthings.iot.dms.entity.IotDeviceToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  DeviceTokenServiceImpl
 * @CreateDate :  2017/11/08
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
@Service
public class DeviceTokenServiceImpl implements IDeviceTokenService {

    @Autowired
    DeviceTokenDao dao;

    @Override
    public long countOfDeviceToken() {
        return dao.count();
    }

    @Override
    public String generateDeviceId(String deviceType, String token) {
        IotDeviceToken pojo = dao.getByToken(token);
        if (pojo == null) {
            String guid = null;
            while (guid == null || dao.getByDeviceId(guid) != null) {
                guid = DeviceGuid.generateGuid(deviceType);
            }

            pojo = new IotDeviceToken();
            pojo.setDeviceType(deviceType);
            pojo.setToken(token);
            pojo.setDeviceId(guid);
            dao.saveAndFlush(pojo);

            return guid;
        } else {
            return pojo.getDeviceId();
        }
    }

    @Override
    public DeviceTokenDto getDeviceTokenByToken(String token) {
        DeviceTokenDto deviceTokenDto = new DeviceTokenDto();
        BeanUtils.copyProperties(dao.getByToken(token), deviceTokenDto);
        return deviceTokenDto;
    }

    @Override
    public DeviceTokenDto getDeviceTokenByDeviceId(String deviceId) {
        DeviceTokenDto deviceTokenDto = new DeviceTokenDto();
        BeanUtils.copyProperties(dao.getByDeviceId(deviceId), deviceTokenDto);
        return deviceTokenDto;
    }

    @Override
    public QueryResult<DeviceTokenDto> getDeviceTokensByDeviceType(String deviceType, int pageIndex, int pageSize) {
        Page<IotDeviceToken> page = dao.findAll(new Specification<IotDeviceToken>() {
            @Override
            public Predicate toPredicate(Root<IotDeviceToken> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                return criteriaBuilder.equal(root.get("deviceType").as(String.class), deviceType);
            }
        }, new PageRequest(pageIndex - 1, pageSize));

        List<DeviceTokenDto> deviceTokenDtoList = Lists.newArrayList();
        PredicateUtil.entity2Dto(page.getContent(), deviceTokenDtoList, DeviceTokenDto.class);
        return new QueryResult<>(deviceTokenDtoList, page.getTotalElements());
    }

}
