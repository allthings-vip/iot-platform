package allthings.iot.tsdb;

import com.baidubce.BceClientConfiguration;
import com.baidubce.Protocol;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.tsdb.TsdbClient;
import com.baidubce.services.tsdb.model.Datapoint;
import com.baidubce.services.tsdb.model.Query;
import com.baidubce.services.tsdb.model.QueryDatapointsRequest;
import com.baidubce.services.tsdb.model.QueryDatapointsResponse;
import com.fasterxml.jackson.databind.JsonNode;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  TsdbOps
 * @CreateDate :  2017/6/21
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
public final class TsdbOps implements IService {
    /**
     * tsdb的客户端.
     */
    private TsdbClient tsdbClient;

    /**
     * 配置.
     */
    private TsdbConfig tsdbConfig;

    public TsdbOps(TsdbConfig tsdbConfig) {
        this.tsdbConfig = tsdbConfig;
    }

    @PostConstruct
    @Override
    public void connect() {
        Protocol protocol = tsdbConfig.getProtocol();
        if (protocol == null) {
            protocol = Protocol.HTTP;
        }

        tsdbClient = getTsdbClient(tsdbConfig.getAccessKey(), tsdbConfig.getAccessSecret(), tsdbConfig.getEndpoint(),
                protocol);
    }

    /**
     * 如果需要较多的查询，则直接获取tsdbClient去定义.
     *
     * @return 返回TsdbClient
     */
    public TsdbClient getTsdbClient() {
        return tsdbClient;
    }

    /**
     * 获取TsdbClient.
     *
     * @param accessKey    用户的Access Key ID
     * @param accessSecret 用户的Secret Access Key
     * @param endpoint     用户的时序数据库域名，形式如databasename.tsdb.iot.gz.baidubce.com
     * @param protocol     不设置，默认使用HTTP协议
     * @return 返回TsdbClient
     */
    private TsdbClient getTsdbClient(String accessKey, String accessSecret, String endpoint, Protocol protocol) {
        if (protocol == null) {
            protocol = Protocol.HTTP;
        }

        BceClientConfiguration config = new BceClientConfiguration()
                .withProtocol(protocol) //不设置，默认使用HTTP协议
                .withCredentials(new DefaultBceCredentials(accessKey, accessSecret))
                .withEndpoint(endpoint);

        return new TsdbClient(config);
    }

    /**
     * 写入Tsdb.
     *
     * @param metric metric
     * @param tags   标签
     * @param values 值 示例： <pre class="code"> values.add(Lists.<JsonNode> newArrayList(new LongNode(time), new
     *               LongNode(value)));</pre> 主要考虑后面的value的类型，LongNode、DoubleNode、TextNode、BinaryNode
     * @throws Exception 抛出异常
     */
    public void writeDatapoints(String metric, Map<String, String> tags, List<List<JsonNode>> values) throws Exception {
        Datapoint datapoint = new Datapoint()
                .withMetric(metric);
        datapoint.setTags(tags);
        datapoint.setValues(values);

        tsdbClient.writeDatapoints(Arrays.asList(datapoint));
    }

    /**
     * 无需封装，直接使用tsdb本身的查询.
     *
     * @param queries queries
     * @return QueryDatapointsResponse
     */
    public QueryDatapointsResponse queryDatapoints(List<Query> queries) {
        return tsdbClient.queryDatapoints(queries);
    }

    /**
     * 无需封装，直接使用tsdb本身的查询.
     *
     * @param queryDatapointsRequest queryDatapointsRequest
     * @return QueryDatapointsResponse
     */
    public QueryDatapointsResponse queryDatapoints(QueryDatapointsRequest queryDatapointsRequest) {
        return tsdbClient.queryDatapoints(queryDatapointsRequest);
    }
}
