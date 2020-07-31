package allthings.iot.dos.constant;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-04-16 16:12
 */

public interface DevicePass {
    /**
     * 视频设备通道编码
     */
    String PASS_TYPE_CODE = "CAMERA";

    /**
     * 拓展属性编码参数名
     */
    String EXTEND_CODE = "extendCode";

    /**
     * 拓展属性名参数名
     */
    String EXTEND_NAME = "extendName";

    /**
     * 拓展属性值参数名
     */
    String EXTEND_VALUE = "extendValue";

    /**
     * 云台控制命令
     */
    List<Integer> COMMAND = Lists.newArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

    /**
     * 速度
     */
    List<Integer> SPEED = Lists.newArrayList(0, 1, 2);
}
