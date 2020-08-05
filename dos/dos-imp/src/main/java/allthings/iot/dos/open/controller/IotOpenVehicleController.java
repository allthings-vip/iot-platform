package allthings.iot.dos.open.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.open.IotVehicleOpenApi;
import allthings.iot.dos.dto.open.IotGpsDTO;
import allthings.iot.dos.dto.open.IotGpsPageDto;
import allthings.iot.dos.dto.open.IotSaveDeviceDTO;
import allthings.iot.dos.dto.open.IotSimpleQueryDTO;
import allthings.iot.dos.dto.open.IotTrackPageQueryDto;
import allthings.iot.dos.dto.open.IotTrackQueryListDTO;
import allthings.iot.dos.dto.open.IotVehicleHistoryQueryDTO;
import allthings.iot.dos.dto.open.IotVehicleLastestBatchDTO;
import allthings.iot.dos.open.api.IotOpenVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author tyf
 * @date 2019/07/04 17:49:21
 */
@RestController
public class IotOpenVehicleController implements IotVehicleOpenApi {

    @Autowired
    private IotOpenVehicleService openVehicleBiz;

    @Override
    public ResultDTO<PageResult<IotGpsDTO>> getTrackList(@RequestBody IotTrackQueryListDTO iotTrackQueryListDTO) {
        return openVehicleBiz.getTrackList(iotTrackQueryListDTO);
    }

    @Override
    public ResultDTO<PageResult<Map<String, String>>> getTrackDuplicateList(@RequestBody IotTrackQueryListDTO iotTrackQueryListDTO) {
        return openVehicleBiz.getTrackDuplicateList(iotTrackQueryListDTO);
    }

    @Override
    public ResultDTO<List<IotGpsDTO>> getGpsLatest(@RequestBody IotTrackQueryListDTO iotTrackQueryListDTO) {
        return openVehicleBiz.getGpsLatest(iotTrackQueryListDTO);
    }

    @Override
    public ResultDTO<Integer> saveVehicle(@RequestBody IotSaveDeviceDTO iotSaveDeviceDTO) {
        return openVehicleBiz.saveVehicle(iotSaveDeviceDTO);
    }

    @Override
    public ResultDTO<List<Map<String, Object>>> vehicleList(@RequestBody IotVehicleHistoryQueryDTO iotVehicleListQueryDTO) {
        return openVehicleBiz.vehicleList(iotVehicleListQueryDTO);
    }

    @Override
    public ResultDTO<List<Map<String, Object>>> vehicleBatchList(@RequestBody IotVehicleLastestBatchDTO iotVehicleLastestBatchDTO) {
        return openVehicleBiz.vehicleBatchList(iotVehicleLastestBatchDTO);
    }

    @Override
    public ResultDTO<Map<String, Double>> queryOil(@RequestBody IotSimpleQueryDTO iotSimpleQueryDTO) {
        return openVehicleBiz.queryOil(iotSimpleQueryDTO);
    }

    @Override
    public ResultDTO<Map<String, Object>> queryOilFuel(@RequestBody IotSimpleQueryDTO iotSimpleQueryDTO) {
        return openVehicleBiz.queryOilFuel(iotSimpleQueryDTO);
    }

    @Override
    public ResultDTO<Map<String, Double>> queryMileage(@RequestBody IotSimpleQueryDTO iotSimpleQueryDTO) {
        return openVehicleBiz.queryMileage(iotSimpleQueryDTO);
    }

    @Override
    public ResultDTO<IotGpsPageDto> queryKvPage(@RequestBody IotTrackPageQueryDto queryDto) {
        return openVehicleBiz.queryKvPage(queryDto);
    }
}
