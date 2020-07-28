package com.tf56.iot.spring.boot.starter.hbase.boot;

import com.tf56.iot.spring.boot.starter.hbase.api.HbaseTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * JThink@JThink
 *
 * @author JThink
 * @version 0.0.1
 * desc： hbase auto configuration
 * date： 2016-11-16 11:11:27
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HbaseProperties.class)
@ConditionalOnClass(HbaseTemplate.class)
public class HbaseAutoConfiguration {

    private static final String HBASE_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_ROOTDIR = "hbase.rootdir";
    private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";
    public static final String HADOOP_USER_NAME = "hbase.user.name";
    public static final String HBASE_RPC_TIMEOUT = "hbase.rpc.timeout";
    public static final String HBASE_SCANNER_TIMEOUT_PERIOD = "hbase.client.scanner.timeout.period";
    public static final String HBASE_HTABLE_THREADS_MAX = "hbase.htable.threads.max";


    @Autowired
    private HbaseProperties hbaseProperties;

    @Bean
    @ConditionalOnMissingBean(HbaseTemplate.class)
    public HbaseTemplate hbaseTemplate() {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set(HBASE_RPC_TIMEOUT, "600000");
        configuration.set(HBASE_SCANNER_TIMEOUT_PERIOD, "600000");
        configuration.set(HBASE_QUORUM, this.hbaseProperties.getQuorum());
        configuration.set(HBASE_HTABLE_THREADS_MAX, "0");
        if (StringUtils.isNotBlank(hbaseProperties.getRootDir())) {
            configuration.set(HBASE_ROOTDIR, hbaseProperties.getRootDir());
        }

        if (StringUtils.isNotBlank(hbaseProperties.getNodeParent())) {
            configuration.set(HBASE_ZNODE_PARENT, hbaseProperties.getNodeParent());
        }

        if (StringUtils.isNotBlank(hbaseProperties.getUsername())) {
            configuration.set(HADOOP_USER_NAME, hbaseProperties.getUsername());
            System.setProperty("HADOOP_USER_NAME", hbaseProperties.getUsername());
        }

        return new HbaseTemplate(configuration);
    }
}
