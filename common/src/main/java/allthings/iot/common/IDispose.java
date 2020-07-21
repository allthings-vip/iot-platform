package allthings.iot.common;

import java.io.Serializable;

/**
 * 资源释放接口
 *
 * @author sylar
 */
public interface IDispose extends Serializable {
    /**
     * 释放资源
     */
    void dispose();
}
