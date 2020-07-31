package allthings.iot.dos.open.api;

import allthings.iot.dos.open.dto.IotGpsDTO;
import allthings.iot.dos.open.dto.IotGpsPageDto;
import allthings.iot.dos.open.dto.IotSaveDeviceDTO;
import allthings.iot.dos.open.dto.IotSimpleQueryDTO;
import allthings.iot.dos.open.dto.IotTrackPageQueryDto;
import allthings.iot.dos.open.dto.IotTrackQueryListDTO;
import allthings.iot.dos.open.dto.IotVehicleHistoryQueryDTO;
import allthings.iot.dos.open.dto.IotVehicleLastestBatchDTO;
import com.tf56.core.BizReturn;
import com.allthings.iot.dos.open.dto.*;
import allthings.iot.common.dto.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author :  txw
 * @FileName :  IotOpenVehicleBiz
 * @CreateDate :  2018/11/12
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
public interface IotOpenVehicleBiz {

    /**
     * 查询设备轨迹
     *
     * @param iotTrackQueryListDTO
     * @return
     */
    ResultDTO<PageResult<IotGpsDTO>> getTrackList(IotTrackQueryListDTO iotTrackQueryListDTO);

    /**
     * 查询设备轨迹(处理停车时间)
     *
     * @param iotTrackQueryListDTO
     * @return
     */
    ResultDTO<PageResult<Map<String, String>>> getTrackDuplicateList(IotTrackQueryListDTO iotTrackQueryListDTO);

    /**
     * 查询设备最新位置
     *
     * @param iotTrackQueryListDTO
     * @return
     */
    ResultDTO<List<IotGpsDTO>> getGpsLatest(IotTrackQueryListDTO iotTrackQueryListDTO);

    /**
     * 保存车机数据
     *
     * @param iotSaveDeviceDTO
     * @return
     */
    ResultDTO<Integer> saveVehicle(IotSaveDeviceDTO iotSaveDeviceDTO);

    /**
     * 查询车辆历史数据
     *
     * @param iotVehicleListQueryDTO
     * @return
     */
    ResultDTO<List<Map<String, Object>>> vehicleList(IotVehicleHistoryQueryDTO iotVehicleListQueryDTO);

    /**
     * 批量查询因子最新数据
     *
     * @param iotVehicleLastestBatchDTO
     * @return
     */
    ResultDTO<List<Map<String, Object>>> vehicleBatchList(IotVehicleLastestBatchDTO iotVehicleLastestBatchDTO);

    /**
     * 车辆时间段内开始结束油量
     *
     * @param iotSimpleQueryDTO
     * @return
     */
    ResultDTO<Map<String, Double>> queryOil(IotSimpleQueryDTO iotSimpleQueryDTO);

    /**
     * 油耗里程查询
     *
     * @param iotSimpleQueryDTO
     * @return
     */
    ResultDTO<Map<String, Object>> queryOilFuel(IotSimpleQueryDTO iotSimpleQueryDTO);

    /**
     * 查询时间段内里程
     *
     * @param iotSimpleQueryDTO
     * @return
     */
    ResultDTO<Map<String, Double>> queryMileage(IotSimpleQueryDTO iotSimpleQueryDTO);

    /**
     * 分页查询轨迹数据
     *
     * @param queryDto
     * @return
     */
    ResultDTO<IotGpsPageDto> queryKvPage(IotTrackPageQueryDto queryDto);
}
