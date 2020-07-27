package allthings.iot.dms.service;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dms.DmsConfig;
import allthings.iot.dms.IDeviceOtaService;
import allthings.iot.dms.dao.DeviceOtaFileDao;
import allthings.iot.dms.dto.DeviceOtaFileDto;
import allthings.iot.dms.entity.IotDeviceConnectionLog;
import allthings.iot.dms.entity.IotDeviceInfo;
import allthings.iot.dms.entity.IotDeviceOtaFile;
import com.google.common.collect.Lists;
import com.google.common.io.BaseEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  DeviceOtaServiceImpl
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Service
public class DeviceOtaServiceImpl implements IDeviceOtaService {

    @Autowired
    DeviceOtaFileDao dao;
    @Autowired
    DmsConfig dmsConfig;

    @Override
    public QueryResult<DeviceOtaFileDto> getDeviceOtaFilesByDeviceType(String deviceType, int pageIndex, int pageSize) {
        Page<IotDeviceOtaFile> page = dao.findAll(new Specification<IotDeviceOtaFile>() {
            @Override
            public Predicate toPredicate(Root<IotDeviceOtaFile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                return PredicateUtil.newPredicateByDeviceType(root, criteriaBuilder, deviceType);
            }
        }, new PageRequest(pageIndex - 1, pageSize));

        List<DeviceOtaFileDto> deviceOtaFileDtoList = Lists.newArrayList();
        PredicateUtil.entity2Dto(page.getContent(), deviceOtaFileDtoList, DeviceOtaFileDto.class);
        return new QueryResult<>(deviceOtaFileDtoList, page.getTotalElements());
    }


    @Override
    public void uploadOtaFile(String otaFullName, String deviceType, int versionCode, String versionName, String
            description, String content) {
        //解码文件内容并获取文件决定路径
        byte[] bytes = BaseEncoding.base64().decode(content);
        String filePath = null;

        //保存上传文件信息
        IotDeviceOtaFile deviceOtaFile = new IotDeviceOtaFile();
        deviceOtaFile.setFileName(otaFullName);
        deviceOtaFile.setDeviceType(deviceType);
        deviceOtaFile.setVersionCode(versionCode);
        deviceOtaFile.setVersionName(versionName);
        deviceOtaFile.setDescription(description);
        deviceOtaFile.setFilePath(filePath);
        if (dao.getByDeviceTypeAndVersionCode(deviceType, versionCode) == null) {
            //如果不存在该条记录，则插入创建时间
            deviceOtaFile.setInputDate(new Date());
        }
        dao.save(deviceOtaFile);
    }

    /**
     * 根据参数查询设备升级文档信息
     *
     * @param deviceType   设备类型
     * @param connected    设备状态
     * @param deviceCode   设备编码
     * @param beginVersion 开始版本号
     * @param endVersion   结束版本号
     * @return 设备升级文档信息
     */
    @Override
    public QueryResult<DeviceOtaFileDto> findUpDocument(String deviceType, boolean connected, String deviceCode, int beginVersion,
                                                        int endVersion, int pageIndex, int pageSize) {
        Page<IotDeviceOtaFile> page = dao.findAll((root, query, cb) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            Root<IotDeviceInfo> diRoot = query.from(IotDeviceInfo.class);
            Root<IotDeviceConnectionLog> dclRoot = query.from(IotDeviceConnectionLog.class);
            predicateList.add(cb.equal(root.get("deviceType").as(String.class), diRoot.get("deviceType")));
            predicateList.add(cb.equal(diRoot.get("deviceId").as(String.class), dclRoot.get("deviceId")));
            predicateList.add(cb.equal(root.get("deviceType").as(String.class), deviceType));
            predicateList.add(cb.greaterThanOrEqualTo(root.get("versionCode").as(int.class), beginVersion));
            predicateList.add(cb.lessThan(root.get("versionCode").as(int.class), endVersion));
            if (deviceCode != null) {
                predicateList.add(cb.equal(diRoot.get("deviceId").as(String.class), deviceCode));
            }
            predicateList.add(cb.equal(dclRoot.get("connected").as(boolean.class), connected));
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        }, new PageRequest(pageIndex, pageSize));
        List<DeviceOtaFileDto> deviceOtaFileDtoList = Lists.newArrayList();
        PredicateUtil.entity2Dto(page.getContent(), deviceOtaFileDtoList, DeviceOtaFileDto.class);
        return new QueryResult<>(deviceOtaFileDtoList, page.getTotalElements());
    }
}
