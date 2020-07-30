package allthings.iot.des.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author tyf
 * @date 2019/03/04 11:03
 */
public interface IotDesMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
