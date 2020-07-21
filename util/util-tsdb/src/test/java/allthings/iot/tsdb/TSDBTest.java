package allthings.iot.tsdb;

import com.baidubce.BceClientConfiguration;
import com.baidubce.Protocol;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.tsdb.TsdbClient;
import com.baidubce.services.tsdb.model.*;
import com.baidubce.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by luhao on 2017/6/21.
 */
public class TSDBTest {

    public TsdbClient getInstance() {
        BceClientConfiguration config = new BceClientConfiguration()
                .withProtocol(Protocol.HTTPS)   //使用HTTPS协议;不设置，默认使用HTTP协议
                .withCredentials(new DefaultBceCredentials(Const.ACCESS_KEY, Const.ACCESS_SECRET))
                .withEndpoint(Const.ENDPOINT);

        TsdbClient tsdbClient = new TsdbClient(config);

        return tsdbClient;
    }


    @Test
    public void testGetMetric() {
        TsdbClient tsdbClient = getInstance();

        GetMetricsResponse response = tsdbClient.getMetrics();
        for (String metric : response.getMetrics()) {
            System.out.println(metric);
        }

    }

    @Test
    public void testGetTag() {
        TsdbClient tsdbClient = getInstance();

        GetTagsResponse response = tsdbClient.getTags("mxd_000003");

        for (Map.Entry<String, List<String>> entry : response.getTags().entrySet()) {
            System.out.println(entry.getKey() + "|||||||");
            System.out.println(entry.getValue());
        }

    }

    @Test
    public void testGetData() {
        Map<String, List<String>> map = Maps.newHashMap();
        map.put("baseCode", Lists.newArrayList("AJ01"));
        TsdbClient tsdbClient = getInstance();
        List<Query> queries = Arrays.asList(new Query()                          // 创建Query对象
                .withMetric("mxd_000003").withFilters(new Filters().withAbsoluteStart(1495555200000L).withTags(map))
                .withLimit(100));

        QueryDatapointsResponse response = tsdbClient.queryDatapoints(queries);

        try {
            System.out.println(JsonUtils.toJsonPrettyString(response.getResults().get(0)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
