package allthings.iot.bsj.data.service.imp;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import allthings.iot.bsj.common.PoMsgParam;
import allthings.iot.bsj.data.service.IBsjDataService;
import allthings.iot.bsj.data.service.dto.BsjCmdDto;

import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  BsjDataServiceImplTest
 * @CreateDate :  2018/1/12
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
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BsjDataServiceImplTest {
    @Autowired
    private IBsjDataService bsjDataService;

    @Test
    public void sendCmd() throws Exception {
        BsjCmdDto bsjCmdDto = new BsjCmdDto();
        bsjCmdDto.setDeviceId("A811DDCB");
        //
        bsjCmdDto.setMsgCode("48");

        Map<String, Object> params = Maps.newHashMap();
        params.put(PoMsgParam.ATTR_SETTING_INTERVAL, 1);

        bsjCmdDto.setParams(params);

        bsjDataService.sendCmd(bsjCmdDto);
    }
}