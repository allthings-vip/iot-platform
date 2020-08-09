package allthings.iot.sms.dot;

public class ShortMessageDto {
    /**
     * 接收短信的手机号码。
     * <p>
     * 格式：
     * <p>
     * 国内短信：11位手机号码，例如15951955195。
     * 国际/港澳台消息：国际区号+号码，例如85200000000。
     * 支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
     * <p>
     * 验证码类型短信，建议使用单独发送的方式。
     */
    private String phoneNumbers;

    /**
     * 短信签名名称。请在控制台签名管理页面签名名称一列查看。
     * <p>
     * 必须是已添加、并通过审核的短信签名。
     */
    private String signName;

    /**
     * 短信模板ID。请在控制台模板管理页面模板CODE一列查看。
     * <p>
     * 必须是已添加、并通过审核的短信签名；且发送国际/港澳台消息时，请使用国际/港澳台短信模版。
     */
    private String templateCode;

    /**
     * 短信模板变量对应的实际值，JSON格式。
     * <p>
     * 如果JSON中需要带换行符，请参照标准的JSON协议处理。
     */
    private String templateParam;

    /**
     * 上行短信扩展码，无特殊需要此字段的用户请忽略此字段。
     */
    private String smsUpExtendCode;

    /**
     * 外部流水扩展字段。
     */
    private String outId;

    /**
     *
     */
    private String action;

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }
}
