package allthings.iot.dos.client.open;

import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.open.IotGpsDTO;
import allthings.iot.dos.dto.open.IotGpsPageDto;
import allthings.iot.dos.dto.open.IotSaveDeviceDTO;
import allthings.iot.dos.dto.open.IotSimpleQueryDTO;
import allthings.iot.dos.dto.open.IotTrackPageQueryDto;
import allthings.iot.dos.dto.open.IotTrackQueryListDTO;
import allthings.iot.dos.dto.open.IotVehicleHistoryQueryDTO;
import allthings.iot.dos.dto.open.IotVehicleLastestBatchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;
import java.util.Map;

/**
 * @author luhao
 * @date 2020/2/17 17:59
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotVehicleOpenApi {
    @PostMapping("/dos/service/getTrackList")
    ResultDTO<PageResult<IotGpsDTO>> getTrackList(@RequestBody IotTrackQueryListDTO iotTrackQueryListDTO);

    @PostMapping("/dos/service/getTrackDuplicateList")
    ResultDTO<PageResult<Map<String, String>>> getTrackDuplicateList(@RequestBody IotTrackQueryListDTO iotTrackQueryListDTO);

    @PostMapping("/dos/service/getGpsLatest")
    ResultDTO<List<IotGpsDTO>> getGpsLatest(@RequestBody IotTrackQueryListDTO iotTrackQueryListDTO);

    @PostMapping("/dos/service/saveVehicle")
    ResultDTO<Integer> saveVehicle(@RequestBody IotSaveDeviceDTO iotSaveDeviceDTO);

    @PostMapping("/dos/service/vehicleList")
    ResultDTO<List<Map<String, Object>>> vehicleList(@RequestBody IotVehicleHistoryQueryDTO iotVehicleListQueryDTO);

    @PostMapping("/dos/service/vehicleBatchList")
    ResultDTO<List<Map<String, Object>>> vehicleBatchList(@RequestBody IotVehicleLastestBatchDTO iotVehicleLastestBatchDTO);

    @PostMapping("/dos/service/queryOil")
    ResultDTO<Map<String, Double>> queryOil(@RequestBody IotSimpleQueryDTO iotSimpleQueryDTO);

    @PostMapping("/dos/service/queryOilFuel")
    ResultDTO<Map<String, Object>> queryOilFuel(@RequestBody IotSimpleQueryDTO iotSimpleQueryDTO);

    @PostMapping("/dos/service/queryMileage")
    ResultDTO<Map<String, Double>> queryMileage(@RequestBody IotSimpleQueryDTO iotSimpleQueryDTO);

    @PostMapping("/dos/service/getTrackPage")
    ResultDTO<IotGpsPageDto> queryKvPage(@RequestBody IotTrackPageQueryDto queryDto);


}
