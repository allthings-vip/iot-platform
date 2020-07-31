package allthings.iot.dos.dto;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-23 14:47
 */

public class IotAuthorityDTO extends AbstractIotDosDTO {
    /**
     * 页面路由
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 标题
     */
    private String urlName;

    /**
     * 顺序
     */
    private Long order;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}
