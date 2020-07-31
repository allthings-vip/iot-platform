package allthings.iot.dos.excel;

import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceErrorMsgDTO;
import allthings.iot.dos.exception.IllegalDeviceArgumentException;
import allthings.iot.dos.exception.IllegalTemplateException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author :  luhao
 * @FileName :  IotDeviceExcelImport
 * @CreateDate :  2018-5-10
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class IotDeviceExcelImport {

    public static final String DEVICE_INFO = "deviceInfo";
    public static final String VALIDATE_ERROR = "validateError";
    private static final Logger LOGGER = LoggerFactory.getLogger(IotDeviceExcelImport.class);
    private static final String[] HEADER = {"*设备编码", "业务编码", "设备名称", "*设备类型", "所属机构",
            "mac地址", "固件型号", "固件版本号", "经度", "纬度", "备注"};
    private static final Integer CELL_SIZE = 11;
    private static final String NUMBER_REGEX = "^(\\-|\\+)?\\d+(\\.\\d+)?$";

    /**
     * 设备Excel解析
     *
     * @param iotProjectId
     * @param operatorId
     * @param multipartFile
     * @return
     */
    public Map<String, List<?>> importDeviceExcel(Long iotProjectId, Long operatorId, MultipartFile
            multipartFile)
            throws IllegalTemplateException, IOException {
        InputStream inputStream = multipartFile.getInputStream();
        String fileName = multipartFile.getOriginalFilename();
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception ee) {
            workbook = new HSSFWorkbook(inputStream);
        }

        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            LOGGER.error("Excel sheet not found,file name {}", fileName);
            //解析出错
            throw new IllegalTemplateException(ErrorCode.ERROR_3030.getCode(), ErrorCode.ERROR_3030.getMessage());
        }

        int rowNum = sheet.getPhysicalNumberOfRows();
        List<IotDeviceDTO> deviceList = Lists.newArrayList();
        List<IotDeviceErrorMsgDTO> errorList = Lists.newArrayList();
        for (int index = 0; index < rowNum; index++) {
            Row row = sheet.getRow(index);
            try {
                if (index == 0) {
                    //校验头部
                    compareHeader(row);
                } else {
                    deviceList.add(parseIotDeviceDTO(iotProjectId, operatorId, row));
                }
            } catch (IllegalDeviceArgumentException ie) {
                if (ie.getIotDeviceErrorMsgDTO() == null) {
                    LOGGER.warn("line {} is blank", index);
                } else {
                    errorList.add(ie.getIotDeviceErrorMsgDTO());
                }
            } catch (IllegalTemplateException it) {
                LOGGER.error("parse excel error line {}", index, it);
                throw it;
            } catch (Exception e) {
                LOGGER.error("parse excel error line {}", index, e);
                throw new IllegalTemplateException(ErrorCode.ERROR_3032.getCode(), ErrorCode.ERROR_3032.getMessage());
            }
        }

        Map<String, List<?>> listMap = Maps.newHashMap();
        listMap.put(DEVICE_INFO, deviceList);
        if (errorList.size() > 0) {
            listMap.put(VALIDATE_ERROR, errorList);
        }

        return listMap;
    }

    /**
     * 比较头部，表示模板没有被篡改
     *
     * @param row
     * @return
     */
    private void compareHeader(Row row) {
        for (int index = 0; index < CELL_SIZE; index++) {
            Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
            if (cell == null) {
                throw new IllegalTemplateException(ErrorCode.ERROR_3031.getCode(), ErrorCode.ERROR_3031.getMessage());
            }

            String cellValue = cell.getStringCellValue();
            if (!HEADER[index].equals(cellValue)) {
                throw new IllegalTemplateException(ErrorCode.ERROR_3031.getCode(), ErrorCode.ERROR_3031.getMessage());
            }
        }
    }

    /**
     * 获取数据cell数据，将所有格式都转为string进行处理
     *
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell) {
        String cellValue = "";
        if (null != cell) {
            // 以下是判断数据的类型
            String enumName = cell.getCellType().name();
            if (enumName.equals(CellType.NUMERIC.name())) {
                DecimalFormat df = new DecimalFormat("###.##############");
                cellValue = df.format(cell.getNumericCellValue());
            } else if (enumName.equals(CellType.STRING.name())) {
                cellValue = cell.getStringCellValue();
            } else if (enumName.equals(CellType.BOOLEAN.name())) {
                cellValue = String.valueOf(cell.getBooleanCellValue());
            } else if (enumName.equals(CellType.FORMULA.name())) {
                cellValue = String.valueOf(cell.getCellFormula());
            }
        }

        return cellValue;
    }


    /**
     * 解析row为IotDeviceDTO
     *
     * @param iotProjectId
     * @param operatorId
     * @param row
     * @return
     */
    private IotDeviceDTO parseIotDeviceDTO(Long iotProjectId, Long operatorId, Row row) throws
            IllegalDeviceArgumentException {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        //组织
        IotDeviceErrorMsgDTO iotDeviceErrorMsgDTO = new IotDeviceErrorMsgDTO();
        iotDeviceErrorMsgDTO.setRowNum(row.getRowNum());
        List<String> errorList = Lists.newArrayList();

        boolean isAllBlank = true;

        //设备号
        Cell deviceCodeCell = row.getCell(0);
        if (deviceCodeCell == null) {
            errorList.add(ErrorCode.ERROR_3007.getMessage());
        } else {
            String deviceCode = getCellValue(deviceCodeCell);
            if (StringUtils.isEmpty(deviceCode)) {
                errorList.add(ErrorCode.ERROR_3007.getMessage());
            } else {
                isAllBlank = false;
                iotDeviceDTO.setDeviceCode(deviceCode);
            }
        }

        //业务编码
        Cell bizCodeCell = row.getCell(1);
        if (bizCodeCell != null) {
            String bizCode = getCellValue(bizCodeCell);
            if (StringUtils.isNotEmpty(bizCode)) {
                isAllBlank = false;
                iotDeviceDTO.setBizCode(bizCode);
            }
        }

        //设备名称
        Cell deviceNameCell = row.getCell(2);
        if (deviceCodeCell != null) {
            String deviceName = getCellValue(deviceNameCell);
            if (StringUtils.isNotEmpty(deviceName)) {
                isAllBlank = false;
                iotDeviceDTO.setDeviceName(deviceName);
            }
        }

        //设备类型
        Cell deviceTypeCell = row.getCell(3);
        if (deviceTypeCell == null) {
            errorList.add(ErrorCode.ERROR_1008.getMessage());
        } else {
            String deviceType = getCellValue(deviceTypeCell);
            if (StringUtils.isEmpty(deviceType)) {
                errorList.add(ErrorCode.ERROR_1008.getMessage());
            } else {
                isAllBlank = false;
                iotDeviceDTO.setDeviceTypeName(deviceType);
            }
        }

        //所属机构
        Cell agencyCell = row.getCell(4);
        if (agencyCell != null) {
            String agencyName = getCellValue(agencyCell);
            if (StringUtils.isNotEmpty(agencyName)) {
                isAllBlank = false;
                iotDeviceDTO.setAgencyName(agencyName);
            }
        }

        //mac地址
        Cell macCell = row.getCell(5);
        if (macCell != null) {
            String mac = getCellValue(macCell);
            if (StringUtils.isNotEmpty(mac)) {
                isAllBlank = false;
                iotDeviceDTO.setMac(mac);
            }
        }

        //固件型号
        Cell firmwareModelCell = row.getCell(6);
        if (firmwareModelCell != null) {
            String firmwareModel = getCellValue(firmwareModelCell);
            if (StringUtils.isNotEmpty(firmwareModel)) {
                isAllBlank = false;
                iotDeviceDTO.setFirmwareModel(firmwareModel);
            }
        }

        //固件版本
        Cell firmwareVersionCell = row.getCell(7);
        if (firmwareVersionCell != null) {
            String firmwareVersion = getCellValue(firmwareVersionCell);

            if (StringUtils.isNotEmpty(firmwareVersion)) {
                isAllBlank = false;
                iotDeviceDTO.setFirmwareVersion(firmwareVersion);
            }
        }

        //经度
        Cell longitudeCell = row.getCell(8);
        //纬度
        Cell latitudeCell = row.getCell(9);

        String longitudeStr = getCellValue(longitudeCell);
        String latitudeStr = getCellValue(latitudeCell);

        if (StringUtils.isNotBlank(longitudeStr)) {
            if (StringUtils.isBlank(latitudeStr)) {
                errorList.add(ErrorCode.ERROR_3040.getMessage());
            }
            isAllBlank = false;
            if (Pattern.matches(NUMBER_REGEX, longitudeStr)) {
                iotDeviceDTO.setLongitude(Double.valueOf(longitudeStr));
            } else {
                errorList.add(ErrorCode.ERROR_3033.getMessage());
            }
        }


        if (StringUtils.isNotBlank(latitudeStr)) {
            if (StringUtils.isBlank(longitudeStr)) {
                errorList.add(ErrorCode.ERROR_3040.getMessage());
            }
            isAllBlank = false;
            if (Pattern.matches(NUMBER_REGEX, latitudeStr)) {
                iotDeviceDTO.setLatitude(Double.valueOf(latitudeStr));
            } else {
                errorList.add(ErrorCode.ERROR_3034.getMessage());
            }
        }


        //备注
        Cell descriptionCell = row.getCell(10);
        if (descriptionCell != null) {
            String description = getCellValue(descriptionCell);
            if (StringUtils.isNotEmpty(description)) {
                isAllBlank = false;
                iotDeviceDTO.setDescription(description);
            }
        }

        if (errorList.size() > 0) {
            if (isAllBlank) {
                throw new IllegalDeviceArgumentException(null);
            }

            iotDeviceErrorMsgDTO.setErrorMsgList(errorList);
            throw new IllegalDeviceArgumentException(iotDeviceErrorMsgDTO);
        }

        iotDeviceDTO.setIotProjectId(iotProjectId);
        iotDeviceDTO.setCreateOperatorId(operatorId);
        iotDeviceDTO.setModifyOperatorId(operatorId);
        iotDeviceDTO.setRowNum(row.getRowNum());

        return iotDeviceDTO;
    }
}
