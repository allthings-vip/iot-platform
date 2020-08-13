package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.web.service.IFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author :  luhao
 * @FileName :  IotImageUploadController
 * @CreateDate :  2018-5-11
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
@RestController
public class IotImageUploadController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotImageUploadController.class);

    private final static Long FILE_MAX_SIZE = 5L * 1024 * 1024;

    @Autowired
    private IFileStorageService fileStorageService;

    @PostMapping("/upload/image")
    public ResultDTO<?> uploadImage(@RequestParam("file") MultipartFile imageFile) {
        String fileName = imageFile.getOriginalFilename();
        try {
            Long size = imageFile.getSize();
            if (size > FILE_MAX_SIZE) {
                LOGGER.error("file is to big ,size {}", size);
                return ResultDTO.newFail(ErrorCode.ERROR_5051.getCode(),
                        ErrorCode.ERROR_5051.getMessage());
            }
            return ResultDTO.newSuccess(fileStorageService.upload(imageFile));
        } catch (Exception e) {
            LOGGER.error("upload image error, image name: {}", fileName, e);
            return ResultDTO.newFail(ErrorCode.ERROR_5050.getCode(),
                    ErrorCode.ERROR_5050.getMessage() + "," + e
                            .getMessage());
        }
    }

//    @GetMapping(value="/download")
//    public ResponseEntity<?> downloadFile(@RequestParam(name="fileId") String fileId){
//        if(StringUtils.isBlank(fileId)){
//            return new ResponseEntity<>(JSON.toJSON(ResultDTO.newFail("file id is empty")), HttpStatus.BAD_REQUEST);
//        }
//        return (ResponseEntity<?>) fss.getFileWithDownload(fileId,false).getData();
//    }


}
