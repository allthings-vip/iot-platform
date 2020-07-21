package allthings.iot.common;

import java.io.Serializable;

/**
 * 唯一标识符(ID)接口
 *
 * @author sylar
 */
public interface IKey<ID> extends Serializable {
    /**
     * 获取唯一标识符
     *
     * @return
     */
    ID getID();
}

