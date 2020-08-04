package allthings.iot.dos.open.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.open.IotVehicleOpenApi;
import allthings.iot.dos.dto.open.IotGpsDTO;
import allthings.iot.dos.dto.open.IotSaveDeviceDTO;
import allthings.iot.dos.dto.open.IotSimpleQueryDTO;
import allthings.iot.dos.dto.open.IotTrackPageQueryDto;
import allthings.iot.dos.dto.open.IotTrackQueryListDTO;
import allthings.iot.dos.dto.open.IotVehicleHistoryQueryDTO;
import allthings.iot.dos.dto.open.IotVehicleLastestBatchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author :  txw
 * @FileName :  IotOpenVehicleController
 * @CreateDate :  2018/11/14
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@RestController
public class IotOpenVehicleController extends IotOpenBaseController {

    @Autowired
    private IotVehicleOpenApi iotVehicleOpenApi;

    /**
     * 查询设备轨迹
     *
     * @param iotTrackQueryListDTO
     * @return
     */
    @PostMapping("/vechile/track/list")
    public ResultDTO getTrackList(@RequestBody IotTrackQueryListDTO iotTrackQueryListDTO) {
        String clientId = getClientId();
        iotTrackQueryListDTO.setClientId(clientId);
        ResultDTO<PageResult<IotGpsDTO>> bizReturn = iotVehicleOpenApi.getTrackList(iotTrackQueryListDTO);
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }
        PageResult<IotGpsDTO> pageResult = bizReturn.getData();

        return ResultDTO.newSuccess(pageResult.getData(), pageResult.getTotal());

    }

    /**
     * 查询设备轨迹(处理停车时间)
     *
     * @param iotTrackQueryListDTO
     * @return
     */
    @PostMapping("/vechile/track/duplicate/list")
    public ResultDTO getTrackDuplicateList(@RequestBody IotTrackQueryListDTO iotTrackQueryListDTO) {
        String clientId = getClientId();
        iotTrackQueryListDTO.setClientId(clientId);
        ResultDTO<PageResult<Map<String, String>>> bizReturn =
                iotVehicleOpenApi.getTrackDuplicateList(iotTrackQueryListDTO);
        if (!bizReturn.isSuccess()) {
            return bizReturn;
        }
        PageResult<Map<String, String>> pageResult = bizReturn.getData();

        return ResultDTO.newSuccess(pageResult.getData(), pageResult.getTotal());
    }

    /**
     * 查询设备最新位置
     *
     * @param iotTrackQueryListDTO
     * @return
     */
    @PostMapping("/vechile/track/latest")
    public ResultDTO getGpsLatest(@RequestBody IotTrackQueryListDTO iotTrackQueryListDTO) {
        String clientId = getClientId();
        iotTrackQueryListDTO.setClientId(clientId);
        return iotVehicleOpenApi.getGpsLatest(iotTrackQueryListDTO);
    }

    /**
     * 保存车机数据
     *
     * @param iotSaveDeviceDTO
     * @return
     */
    @PostMapping("/vechile/save")
    public ResultDTO saveVehicle(@RequestBody IotSaveDeviceDTO iotSaveDeviceDTO) {
        String clientId = getClientId();
        iotSaveDeviceDTO.setClientId(clientId);
        return iotVehicleOpenApi.saveVehicle(iotSaveDeviceDTO);
    }

    /**
     * 查询车辆历史数据
     *
     * @param iotVehicleListQueryDTO
     * @return
     */
    @PostMapping("/vechile/list")
    public ResultDTO vehicleList(@RequestBody IotVehicleHistoryQueryDTO iotVehicleListQueryDTO) {
        String clientId = getClientId();
        iotVehicleListQueryDTO.setClientId(clientId);
        return iotVehicleOpenApi.vehicleList(iotVehicleListQueryDTO);
    }

    /**
     * 批量查询因子最新数据
     *
     * @param iotVehicleLastestBatchDTO
     * @return
     */
    @PostMapping("/vechile/batch/last")
    public ResultDTO vehicleBatchList(@RequestBody IotVehicleLastestBatchDTO iotVehicleLastestBatchDTO) {
        String clientId = getClientId();
        iotVehicleLastestBatchDTO.setClientId(clientId);
        return iotVehicleOpenApi.vehicleBatchList(iotVehicleLastestBatchDTO);
    }

    /**
     * 车辆时间段内开始结束油量
     *
     * @param iotSimpleQueryDTO
     * @return
     */
    @PostMapping("/vechile/oil")
    public ResultDTO queryOil(@RequestBody IotSimpleQueryDTO iotSimpleQueryDTO) {
        String clientId = getClientId();
        iotSimpleQueryDTO.setClientId(clientId);
        return iotVehicleOpenApi.queryOil(iotSimpleQueryDTO);
    }

    /**
     * 油耗里程查询
     *
     * @param iotSimpleQueryDTO
     * @return
     */
    @PostMapping("/vechile/fuel")
    public ResultDTO queryOilFuel(@RequestBody IotSimpleQueryDTO iotSimpleQueryDTO) {
        String clientId = getClientId();
        iotSimpleQueryDTO.setClientId(clientId);
        return iotVehicleOpenApi.queryOilFuel(iotSimpleQueryDTO);
    }

    /**
     * 查询时间段内里程
     *
     * @param iotSimpleQueryDTO
     * @return
     */
    @PostMapping("/vechile/mileage")
    public ResultDTO queryMileage(@RequestBody IotSimpleQueryDTO iotSimpleQueryDTO) {
        String clientId = getClientId();
        iotSimpleQueryDTO.setClientId(clientId);
        return iotVehicleOpenApi.queryMileage(iotSimpleQueryDTO);
    }

    @PostMapping("/vechile/track/getGpsListPage")
    public ResultDTO queryTrackPage(@RequestBody IotTrackPageQueryDto queryDto) {
        String clientId = getClientId();
        queryDto.setClientId(clientId);
        return iotVehicleOpenApi.queryKvPage(queryDto);
    }

}
