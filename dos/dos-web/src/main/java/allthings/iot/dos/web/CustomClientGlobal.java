package allthings.iot.dos.web;

/**
 * @author :  luhao
 * @FileName :  CustomClientGlobal
 * @CreateDate :  2018-5-28
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
public class CustomClientGlobal {
    //kb
    public static final int DEFAULT_SECTION_SIZE = 1024;
    // second
    public static final int DEFAULT_CONNECT_TIMEOUT = 5;
    // second
    public static final int DEFAULT_NETWORK_TIMEOUT = 30;

    /**
     * load global variables
     *
     * @param inputStream 文件流
     */
//    public static void init(InputStream inputStream) throws FileNotFoundException, IOException, FastDFSException {
//        Properties properties = new Properties();
//        properties.load(inputStream);
//
//        String g_connect_timeout_str = properties.getProperty("connect_timeout");
//        int g_connect_timeout = StringUtils.isEmpty(g_connect_timeout_str) ? DEFAULT_CONNECT_TIMEOUT : Integer.parseInt
//                (g_connect_timeout_str);
//        if (g_connect_timeout < 0) {
//            g_connect_timeout = DEFAULT_CONNECT_TIMEOUT;
//        }
//        // millisecond
//        g_connect_timeout *= 1000;
//        ClientGlobal.setG_connect_timeout(g_connect_timeout);
//
//        String g_network_timeout_str = properties.getProperty("network_timeout");
//        int g_network_timeout = StringUtils.isEmpty(g_network_timeout_str) ? DEFAULT_NETWORK_TIMEOUT : Integer.parseInt
//                (g_network_timeout_str);
//        if (g_network_timeout < 0) {
//            g_network_timeout = DEFAULT_NETWORK_TIMEOUT;
//        }
//        // millisecond
//        g_network_timeout *= 1000;
//        ClientGlobal.setG_network_timeout(g_network_timeout);
//
//        String g_charset = properties.getProperty("charset");
//        if (g_charset == null || g_charset.length() == 0) {
//            g_charset = "ISO8859-1";
//        }
//
//        ClientGlobal.setG_charset(g_charset);
//
//        //配置文件传入的为kb，转化为字节数
//        String section_size_str = properties.getProperty("section_size");
//        int section_size = StringUtils.isEmpty(section_size_str) ? DEFAULT_SECTION_SIZE : Integer.parseInt
//                (section_size_str);
//        if (section_size < 0) {
//            section_size = DEFAULT_SECTION_SIZE;
//        }
//        section_size *= 1024;
//        ClientGlobal.section_size = section_size;
//
//        // 初始化tracker server节点信息
//        String[] szTrackerServers = properties.getProperty("tracker_server").split(",");
//        System.out.println(szTrackerServers[0] + "===============");
//        if (szTrackerServers == null) {
//            throw new FastDFSException("item \"tracker_server\" in  config not found");
//        }
//
//        TrackerGroup g_tracker_group = new TrackerGroup(ClientGlobal.initTrackerServers(szTrackerServers));
//        ClientGlobal.setG_tracker_group(g_tracker_group);
//
//        String g_tracker_http_port_str = properties.getProperty("http.tracker_http_port");
//        int g_tracker_http_port = StringUtils.isEmpty(g_tracker_http_port_str) ? 80 : Integer.parseInt
//                (g_tracker_http_port_str);
//        ClientGlobal.setG_tracker_http_port(g_tracker_http_port);
//
//        String g_anti_steal_token_str = properties.getProperty("http.anti_steal_token");
//        boolean g_anti_steal_token = StringUtils.isEmpty(g_anti_steal_token_str) ? false : Boolean.parseBoolean
//                (g_anti_steal_token_str);
//        ClientGlobal.setG_anti_steal_token(g_anti_steal_token);
//        if (g_anti_steal_token) {
//            String g_secret_key = properties.getProperty("http.secret_key");
//            ClientGlobal.setG_secret_key(g_secret_key);
//        }
//
//        String items = properties.getProperty("thumbnails");
//        ClientGlobal.initThumbnails(items);
//        ClientGlobal.initGroupName(properties.getProperty("fastdfs.groupname"));
//    }
}
