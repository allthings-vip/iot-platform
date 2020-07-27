package allthings.iot.dms.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sylar on 16/5/6.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Commit
@Transactional
public class DeviceLocationControllerTest {
    final static String DeviceId = "123456789012345";
    final static String CoorType = "2";

    @Autowired
    private WebApplicationContext context;

    @Value("${server.port}")
    private int port;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).alwaysExpect(status().isOk()).build();
    }

    @Test
    public void getDeviceLocation() throws Exception {
        String url = "/allthings/iot/dms/getLocation";
        mockMvc.perform(get(url).param("deviceId", DeviceId).param("coorType", CoorType)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void bindLocation() throws Exception {
        String url = "/allthings/iot/dms/bindLocation";
        String json = "{\n" +
                "    \"userId\": \"AAAAAA\",\n" +
                "    \"deviceType\": \"TRCAN\",\n" +
                "    \"deviceId\": \"123456789012345\",\n" +
                "    \"coorType\": 2,\n" +
                "    \"lon\": 130.1234,\n" +
                "    \"lat\": 30.5678,\n" +
                "    \"locationDesc\": \"ZZZZZZZZZZZZ\"\n" +
                "}";

        mockMvc.perform(post(url, "json")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.getBytes()))
                .andDo(print());

    }
}
