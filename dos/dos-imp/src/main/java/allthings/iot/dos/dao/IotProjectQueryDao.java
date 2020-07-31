package allthings.iot.dos.dao;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dos.dto.query.IotProjectQueryDTO;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-27 17:24
 */

public interface IotProjectQueryDao {
    QueryResult<IotProjectQueryDTO> getIotProjectList(Long iotUserId, String keywords, Integer pageIndex,
                                                      Integer pageSize);
}
