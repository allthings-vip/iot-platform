package allthings.iot.dms.service;

import com.google.common.collect.Lists;
import allthings.iot.dms.IDeviceOwnerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author :  luhao
 * @FileName :  DeviceOwnerServiceTest
 * @CreateDate :  2016/6/28
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) www.XXXXX.com   All Rights Reserved
 * *******************************************************************************************
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceOwnerServiceImplTest {

    @Autowired
    private IDeviceOwnerService deviceOwnerService;

    @Test
    public void bindDevice() throws Exception {
        deviceOwnerService.bindDevice("123", "2222");
    }

    @Test
    public void bindDevice1() throws Exception {
        deviceOwnerService.bindDevice("123", Lists.newArrayList("2222", "3333", "4444"));
    }

    @Test
    public void unBindDevice() throws Exception {
        deviceOwnerService.unBindDevice("123");
    }

    @Test
    public void unBindDevice1() throws Exception {
        deviceOwnerService.unBindDevice("123", Lists.newArrayList("2222", "3333"));
    }

    @Test
    public void getDeviceInfoByOwnerId() throws Exception {
//        deviceOwnerService.getDeviceInfoByOwnerId("123", 1, 20);
    }

    @Test
    public void getOwnerIdByDeviceId() throws Exception {
        System.out.println(deviceOwnerService.getOwnerIdByDeviceId("4444"));
    }

}