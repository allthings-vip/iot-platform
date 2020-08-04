package allthings.iot.dos.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotPointCountService;
import allthings.iot.dos.client.api.IotPointCountApi;
import allthings.iot.dos.dto.query.IotDosQueryDTO;
import allthings.iot.dos.dto.query.IotPointCountQueryDTO;
import allthings.iot.dos.dto.query.IotPointCountTitleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/04 16:22:38
 */
@RestController
public class IotPointCountController implements IotPointCountApi {

    @Autowired
    private IotPointCountService pointCountBiz;

    @Override
    public ResultDTO<List<IotPointCountQueryDTO>> getByDateRange(@RequestBody IotDosQueryDTO iotDosQueryDTO) {
        return pointCountBiz.getByDateRange(iotDosQueryDTO);
    }

    @Override
    public ResultDTO<QueryResult<IotPointCountTitleDTO>> getTopByDateRange(@RequestParam("startDatetime") Long startDatetime,
                                                                           @RequestParam("endDatetime") Long endDatetime,
                                                                           @RequestParam("type") String type,
                                                                           @RequestParam("top") Integer top) {
        return pointCountBiz.getTopByDateRange(startDatetime, endDatetime, type, top);
    }

}
