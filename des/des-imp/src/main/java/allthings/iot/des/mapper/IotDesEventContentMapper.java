package allthings.iot.des.mapper;

import allthings.iot.des.model.IotDesEventContent;
import allthings.iot.des.util.IotDesMapper;

/**
 * @author tyf
 * @date 2019/03/04 14:35
 */
public interface IotDesEventContentMapper extends IotDesMapper<IotDesEventContent> {

    /**
     * 保存事件内容
     * @param iotDesEventContentModel
     * @return
     */
    Long saveIotDesEventContent(IotDesEventContent iotDesEventContentModel);



}
