package tf56.cloud.iot.store.dustbin.data.controller;

import com.google.common.base.Strings;
import tf56.cloud.iot.store.dustbin.data.dto.DustbinDeviceDataDto;
import tf56.cloud.iot.store.dustbin.data.dto.DustbinParamDto;
import tf56.cloud.iot.store.dustbin.data.service.IDustbinService;
import allthings.iot.common.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  DustbinDataController
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@RestController
@RequestMapping("/device/data/dustbin")
public class DustbinDataController {
    @Autowired
    IDustbinService dustbinParamService;

    /**
     * 保存（新增/更新） 垃圾桶的参数。
     *
     * @param dustbinParamDto
     * @return
     */
    @RequestMapping(value = "/saveParams", method = RequestMethod.POST)
    public Result<?> saveParams(@RequestBody DustbinParamDto dustbinParamDto) {
        Result<?> result = this.validateOnSave(dustbinParamDto);
        if (Result.FAILD == result.getRc()) {
            return result;
        }

        dustbinParamService.addOrUpdate(dustbinParamDto);
        return Result.newSuccess();
    }

    private Result<?> validateOnSave(DustbinParamDto dustbinParamDto) {
        String deviceId = dustbinParamDto.getDeviceId();
        Integer height = dustbinParamDto.getHeight();
        Integer fullThreshold = dustbinParamDto.getFullThreshold();
        Integer halfFullThreshold = dustbinParamDto.getHalfFullThreshold();

        StringBuffer errMsg = new StringBuffer();
        if (Strings.isNullOrEmpty(deviceId)) {
            errMsg.append("设备编码不能为空！");
        }

        if (height == null) {
            errMsg.append("垃圾桶高度不能为空！");
        }

        if (fullThreshold == null) {
            errMsg.append("全满阈值不能为空！");
        }

        if (halfFullThreshold == null) {
            errMsg.append("半满阈值不能为空！");
        }

        if (errMsg.length() > 0) {
            return Result.newFaild("参数错误：" + errMsg.toString());
        }

        return Result.newSuccess();
    }

    /**
     * 获取垃圾桶的参数设置
     *
     * @param deviceId 要求格式为：deviceType + deviceNumber。如TRCAN867587029315130
     * @return
     */
    @RequestMapping(value = "/getParams", method = RequestMethod.GET)
    public Result<?> getByDeviceId(String deviceId) {
        if (Strings.isNullOrEmpty(deviceId)) {
            return Result.newFaild("参数错误：设备编码不能为空！");
        }

        DustbinParamDto dustbinParamDto = dustbinParamService.getByDeviceId(deviceId);
        if (dustbinParamDto == null) {
            return Result.newFaild(Result.FAILD, "no record found for deviceId[" + deviceId + "]");
        }

        return Result.newSuccess(dustbinParamDto);
    }

    /**
     * 获取垃圾桶的参数设置
     *
     * @param deviceId 要求格式为：deviceType + deviceNumber。如TRCAN867587029315130
     * @return
     */
    @RequestMapping(value = "/getDustbinData", method = RequestMethod.GET)
    public Result<?> getDustbinData(String deviceId, Long startDatetime, Long endDatetime, List<String> factorCodes,
                                    Integer pageIndex, Integer pageSize) {
        if (Strings.isNullOrEmpty(deviceId)) {
            return Result.newFaild("参数错误：设备编码不能为空！");
        }

        List<DustbinDeviceDataDto> dustbinDeviceDataDtos = dustbinParamService.getDustbinDeviceDataList(deviceId,
                startDatetime, endDatetime, factorCodes, pageIndex, pageSize);
        if (CollectionUtils.isEmpty(dustbinDeviceDataDtos)) {
            return Result.newFaild(Result.FAILD, "no record found for deviceId[" + deviceId + "]");
        }

        return Result.newSuccess(dustbinDeviceDataDtos);
    }
}
