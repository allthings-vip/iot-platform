package allthings.iot.util.sms;

/**
 * @author tyf
 * @date 2020/08/10 10:47:06
 */
public enum ErrorCodeEnum {
    OK("OK", "OK"), // 接口调用成功
    /**
     * 原因：签名的适用场景与短信类型不匹配。
     * 解决方案：请选择合适的签名和模版进行短信发送。
     * -适用场景为验证码的签名可与验证码模版匹配发送。
     * -适用场景为通用的签名可与验证码、短信通知、推广短信、国际/港澳台短信模版匹配发送。
     */
    SMS_SIGNATURE_SCENE_ILLEGAL("isv.SMS_SIGNATURE_SCENE_ILLEGAL", "短信所使用签名场景非法"),
    /**
     * 原因：发送短信时不同签名的短信使用了相同扩展码。
     * 解决方案：在调用短信发送接口时，不同的短信签名使用不同的扩展码。
     */
    EXTEND_CODE_ERROR("isv.EXTEND_CODE_ERROR", "扩展码使用错误，相同的扩展码不可用于多个签名"),
    /**
     * 原因：国际/港澳台消息模板仅支持发送国际、港澳台地区的号码。
     * 解决方案：如果想发送境内短信，请申请国内短信模版。
     */
    DOMESTIC_NUMBER_NOT_SUPPORTED("isv.DOMESTIC_NUMBER_NOT_SUPPORTED", "国际/港澳台消息模板不支持发送境内号码"),
    /**
     * 原因：被系统检测到源IP属于非中国大陆地区。
     * 解决方案：请将源IP地址修改为中国大陆地区的IP地址。港澳台、及海外地区的IP地址禁止发送国内短信业务。
     */
    DENY_IP_RANGE("isv.DENY_IP_RANGE", "源IP地址所在的地区被禁用"),
    /**
     * 因：已经达到您在控制台设置的短信日发送量限额值。
     * 解决方案：如需修改限额，请在短信服务控制台左侧导航栏中单击国内消息设置 > 安全设置，修改发送总量阈值。
     */
    DAY_LIMIT_CONTROL("isv.DAY_LIMIT_CONTROL", "触发日发送限额"),
    /**
     * 原因：短信内容包含禁止发送内容。
     * 解决方案：修改短信文案。
     */
    SMS_CONTENT_ILLEGAL("isv.SMS_CONTENT_ILLEGAL", "短信内容包含禁止发送内容"),
    /**
     * 原因：签名禁止使用。
     * 解决方案：请在短信服务控制台中申请符合规定的签名。
     */
    SMS_SIGN_ILLEGAL("isv.SMS_SIGN_ILLEGAL", "签名禁止使用"),
    /**
     * 原因：RAM权限不足。
     * 解决方案：请为当前使用的AK对应子账号进行授权：AliyunDysmsFullAccess（管理权限）。
     * 具体操作请参考：https://help.aliyun.com/document_detail/55764.html?spm=a2c4g.11186623.2.7.5c7166faCRjGJP。
     */
    RAM_PERMISSION_DENY("isp.RAM_PERMISSION_DENY", "RAM权限DENY"),
    /**
     * 原因：余额不足。余额不足时，套餐包中即使有短信额度也无法发送短信。
     * 解决方案：请及时充值。
     * <p>
     * 如果余额大于零仍报此错误，请通过工单联系工程师处理。
     */
    OUT_OF_SERVICE("isv.OUT_OF_SERVICE", "业务停机"),
    /**
     * 原因： 该AK所属的账号尚未开通云通信的服务，包括短信、语音、流量等服务。
     * 解决方案：当出现此类提示报错需要检查当前AK是否已经开通阿里云云通信短信服务，如已开通消息服务，则参照消息服务文档调用接口。
     */
    PRODUCT_UN_SUBSCRIPT("isv.PRODUCT_UN_SUBSCRIPT", "未开通云通信产品的阿里云客户"),
    /**
     * 原因： 该AK所属的账号尚未开通当前接口的产品，例如仅开通了短信服务的用户调用语音接口时会产生此报错信息。
     * 解决方案：检查AK对应账号是否已开通调用接口对应的服务。
     * 开通短信服务请单击短信服务产品介绍。https://www.aliyun.com/product/sms?spm=a2c4g.11186623.2.8.5c7166faCRjGJP
     */
    PRODUCT_UNSUBSCRIBE("isv.PRODUCT_UNSUBSCRIBE", "产品未开通"),
    /**
     * 原因： 使用了错误的账户名称或AK。
     * 解决方案：请确认账号信息。
     */
    ACCOUNT_NOT_EXISTS("isv.ACCOUNT_NOT_EXISTS", "账户不存在"),
    /**
     * 原因：账户异常。
     * 解决方案： 请确认账号信息。
     */
    ACCOUNT_ABNORMAL("isv.ACCOUNT_ABNORMAL", "账户异常"),
    /**
     * 原因： 短信模板不存在，或未经审核通过。
     * 解决方案： 参数TemplateCode请传入审核通过的模版ID，
     * 模版ID请在控制台模板管理页面中查看。https://dysms.console.aliyun.com/dysms.htm?spm=a2c4g.11186623.2.9.5c7166faCRjGJP#/template
     */
    SMS_TEMPLATE_ILLEGAL("isv.SMS_TEMPLATE_ILLEGAL", "短信模版不合法"),
    /**
     * 原因： 签名不存在，或未经审核通过。
     * 解决方案：参数SignName请传入审核通过的签名名称，
     * 签名请在控制台签名管理页面中查看。https://dysms.console.aliyun.com/dysms.htm?spm=a2c4g.11186623.2.10.5c7166faCRjGJP#/sign
     */
    SMS_SIGNATURE_ILLEGAL("isv.SMS_SIGNATURE_ILLEGAL", "短信签名不合法"),
    /**
     * 原因： 参数格式不正确。
     * 解决方案：请根据对应的API文档检查参数格式。
     * <p>
     * 例如，短信查询接口QuerySendDetails的参数SendDate日期格式为yyyyMMdd，正确格式为20170101，错误格式为2017-01-01。
     */
    INVALID_PARAMETERS("isv.INVALID_PARAMETERS", "参数异常"),
    /**
     * 原因： 系统错误。
     * 解决方案：请重新调用接口，如仍存在此情况请创建工单反馈工程师查看。
     */
    SYSTEM_ERROR("isp.SYSTEM_ERROR", "系统错误"),
    /**
     * 原因：手机号码格式错误。
     * 解决方案：参数PhoneNumbers请传入正确的格式。
     * -国内短信：11位手机号码，例如15951955195。
     * -国际/港澳台消息：国际区号+号码，例如85200000000。
     */
    MOBILE_NUMBER_ILLEGAL("isv.MOBILE_NUMBER_ILLEGAL", "非法手机号"),
    /**
     * 原因：参数PhoneNumbers中指定的手机号码数量超出限制。
     * 解决方案：请将手机号码数量限制在1000个以内。
     */
    MOBILE_COUNT_OVER_LIMIT("isv.MOBILE_COUNT_OVER_LIMIT", "手机号码数量超过限制"),
    /**
     * 原因： 参数TemplateParam中，变量未全部赋值。
     * 解决方案： 请JSON格式字符串为模板变量赋值。
     * <p>
     * 例如，模版为您好${name}，验证码${code}，则参数TemplateParam可以指定为{"name":"Tom","code":"123"}。
     */
    TEMPLATE_MISSING_PARAMETERS("isv.TEMPLATE_MISSING_PARAMETERS", "模版缺少变量"),
    /**
     * 原因： 短信发送频率超限。
     * 解决方案： 请将短信发送频率限制在正常的业务流控范围内。默认流控：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天。
     */
    BUSINESS_LIMIT_CONTROL("isv.BUSINESS_LIMIT_CONTROL", "业务限流"),
    /**
     * 原因：参数格式错误，不是合法的JSON格式。
     * 解决方案： 请在参数TemplateParam中指定正确的JSON格式字符串，例如{"code":"123"}。
     */
    INVALID_JSON_PARAM("isv.INVALID_JSON_PARAM", "JSON参数不合法，只接受字符串值"),
    /**
     * 原因： 黑名单管控是指变量内容含有限制发送的内容，例如变量中不允许透传URL。
     * 解决方案： 请检查通过变量是否透传了一些敏感信息。
     */
    BLACK_KEY_CONTROL_LIMIT("isv.BLACK_KEY_CONTROL_LIMIT", "黑名单管控"),
    /**
     * 原因：参数超出长度限制。
     * 解决方案：每个变量的长度限制为1~20字符。请修改参数长度。
     */
    PARAM_LENGTH_LIMIT("isv.PARAM_LENGTH_LIMIT", "参数超出长度限制"),
    /**
     * 原因： 黑名单管控是指变量内容含有限制发送的内容，例如变量中不允许透传URL。
     * 解决方案： 请检查通过变量是否透传了URL或敏感信息。
     */
    PARAM_NOT_SUPPORT_URL("isv.PARAM_NOT_SUPPORT_URL", "不支持URL"),
    /**
     * 原因： 当前账户余额不足。
     * 解决方案：请及时充值。调用接口前请确认当前账户余额是否足以支付预计发送的短信量。
     */
    AMOUNT_NOT_ENOUGH("isv.AMOUNT_NOT_ENOUGH", "账户余额不足"),
    /**
     * 原因：变量内容含有限制发送的内容，例如变量中不允许透传URL。
     * 解决方案： 请检查通过变量是否透传了URL或敏感信息。
     */
    TEMPLATE_PARAMS_ILLEGAL("isv.TEMPLATE_PARAMS_ILLEGAL", "模版变量里包含非法关键字"),
    /**
     * 原因： 签名（Signature）加密错误。
     * 解决方案：
     * -如果使用SDK调用接口，请注意accessKeyId和accessKeySecret字符串赋值正确。
     * -如果自行加密签名（Signature），请对照文档检查加密逻辑。
     */
    SIGNATURE_DOES_NOT_MATCH("SignatureDoesNotMatch", "签名（Signature）加密错误。"),
    /**
     * 原因： 一般由于时区差异造成时间戳错误，发出请求的时间和服务器接收到请求的时间不在15分钟内。
     * 阿里云网关使用的时间是GMT时间。
     * <p>
     * 解决方案：请使用GMT时间。
     */
    INVALID_TIMESTAMP_EXPIRED("InvalidTimeStamp.Expired", "指定的时间戳或日期值已过期。"),
    /**
     * 原因： 唯一随机数重复，SignatureNonce为唯一随机数，用于防止网络重放攻击。
     * 解决方案： 不同请求请使用不同的随机数值。
     */
    SIGNATURE_NONCE_USED("SignatureNonceUsed", "指定的签名随机数已被使用"),
    /**
     * 原因： 版本号（Version）错误。
     * 解决方案：请确认接口的版本号，短信服务的API版本号（Version）为2017-05-25。
     */
    INVALID_VERSION("InvalidVersion", "Specified parameter Version is not valid."),
    /**
     * 原因： 参数Action中指定的接口名错误。
     * 解决方案： 请在参数Action中使用正确的接口地址和接口名。
     */
    INVALID_ACTION_NOTFOUND("InvalidAction.NotFound", "Specified api is not found, please check your url and method"),
    /**
     * 原因：一个自然日中申请签名数量超过限制。
     * 解决方案：合理安排每天的签名申请数量，次日重新申请。
     */
    SIGN_COUNT_OVER_LIMIT("isv.SIGN_COUNT_OVER_LIMIT", "一个自然日中申请签名数量超过限制。"),
    /**
     * 原因：一个自然日中申请模板数量超过限制。
     * 解决方案：合理安排每天的模板申请数量，次日重新申请。
     */
    TEMPLATE_COUNT_OVER_LIMIT("isv.TEMPLATE_COUNT_OVER_LIMIT", "一个自然日中申请模板数量超过限制。"),
    /**
     * 原因：签名名称不符合规范。
     * 解决方案：参考个人用户签名规范或企业用户签名规范重新申请签名。
     * https://help.aliyun.com/document_detail/108076.html?spm=a2c4g.11186623.2.12.5c7166faCRjGJP
     * https://help.aliyun.com/document_detail/108077.html?spm=a2c4g.11186623.2.13.5c7166faCRjGJP
     */
    SIGN_NAME_ILLEGAL("isv.SIGN_NAME_ILLEGAL", "签名名称不符合规范。"),
    /**
     * 原因：签名认证材料附件大小超过限制。
     * 解决方案：压缩签名认证材料至2 MB以下。
     */
    SIGN_FILE_LIMIT("isv.SIGN_FILE_LIMIT", "签名认证材料附件大小超过限制。"),
    /**
     * 原因：签名的名称或申请说明的字数超过限制。
     * 解决方案：修改签名名称或申请说明，并重新提交审核。
     */
    SIGN_OVER_LIMIT("isv.SIGN_OVER_LIMIT", "签名字符数量超过限制。"),
    /**
     * 原因：模板的名称、内容或申请说明的字数超过限制。
     * 解决方案：修改模板的名称、内容或申请说明，并重新提交审核。
     */
    TEMPLATE_OVER_LIMIT("isv.TEMPLATE_OVER_LIMIT", "签名字符数量超过限制。"),
    /**
     * 原因：签名内容涉及违规信息，详情请参见个人用户签名规范或企业用户签名规范重新申请签名。
     * https://help.aliyun.com/document_detail/108076.html?spm=a2c4g.11186623.2.14.5c7166faCRjGJP
     * https://help.aliyun.com/document_detail/108077.html?spm=a2c4g.11186623.2.15.5c7166faCRjGJP
     * 解决方案:修改签名内容。
     */
    SIGNATURE_BLACKLIST("SIGNATURE_BLACKLIST", "签名黑名单");

    private String code;
    private String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
