package allthings.iot.dms.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sylar on 2017/11/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback
@Transactional
public class DasConnectionLogControllerTest {

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
    public void getDasConnectionLogsByNodeId() throws Exception {
        String url = "/dms/getDasConnectionLogsByNodeId";
        mockMvc.perform(get(url).param("nodeId", "TRCAN_1")
                .param("beginTime", "1486784660176")
                .param("endTime", "1486800338208")
                .param("pageIndex", "1")
                .param("pageSize", "100")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}