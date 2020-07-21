package allthings.iot.dms.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author :  luhao
 * @FileName :  DeviceOwnerControllerTest
 * @CreateDate :  2016/6/29
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
public class DeviceOwnerControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).alwaysExpect(status().isOk()).build();
    }

    @Test
    public void bindDevice() throws Exception {
        String url = "/dms/bindDevice";
        String json = "{\"ownerId\":\"123\",\"deviceId\":\"vehic31FFDF054D59383124582151FFC0F0FF\"}";
        mockMvc.perform(post(url).param("ownerId", "123").param("deviceId", "vehic31FFDF054D59383124582151FFC0F0FF")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void bindDeviceList() throws Exception {
        String url = "/dms/bindDeviceList";
        String json = "{\n" +
                "    \"ownerId\": \"11111\", \n" +
                "    \"deviceIdList\": [\"vehic31FFDF054D59383124582151FFC0F0FF\"] \n" +
                "}";

        mockMvc.perform(post(url, "json")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.getBytes()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void unbindDevice() throws Exception {
        String url = "/dms/unBindDevice";
        String json = "{\"ownerId\":\"123\",\"deviceId\":\"vehic31FFDF054D59383124582151FFC0F0FF\"}";
        mockMvc.perform(post(url).param("ownerId", "123").param("deviceArray", "vehic31FFDF054D59383124582151FFC0F0FF")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getDeviceInfoByOwnerId() throws Exception {
        String url = "/dms/getDeviceInfoByOwnerId";
        String json = "{\"ownerId\":\"123\",\"deviceId\":\"vehic31FFDF054D59383124582151FFC0F0FF\"}";
        mockMvc.perform(get(url).param("ownerId", "123").param("pageIndex", "1").param("pageSize", "20")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}