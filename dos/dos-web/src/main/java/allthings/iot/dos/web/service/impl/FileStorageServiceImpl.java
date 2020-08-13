package allthings.iot.dos.web.service.impl;

import allthings.iot.dos.web.service.IFileStorageService;
import com.google.common.collect.Lists;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author tyf
 * @date 2020/08/10 09:30:48
 */
@Service("fileStorageService")
public class FileStorageServiceImpl implements IFileStorageService {

    private Logger log = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    private static final List<String> FILE_SUFFIX_NAME = Lists.newArrayList("bmp", "jpg", "png", "jpeg", "gif");


//    @Value("${fss.headPoint:http://localhost:${server.port}}")
//    private String filePath;

//    @Autowired
//    private FilePathConfigure fileStore;

    @Override
    public String upload(MultipartFile file) {
        try {
            // 获取原始名字
            String fullName = file.getOriginalFilename();
            // 获取文件后缀名
            String suffixName = fullName.substring(fullName.lastIndexOf("."));
            // 保存文件路径，文件名
            if (FILE_SUFFIX_NAME.contains(suffixName.toLowerCase())) {
                fullName = "E:\\upload\\image\\" + DateTimeFormat.forPattern("yyyy-MM-dd").print(System.currentTimeMillis()) + "\\" + fullName;
            } else {
                fullName = "E:\\upload\\file\\" + DateTimeFormat.forPattern("yyyy-MM-dd").print(System.currentTimeMillis()) + "\\" + fullName;
            }
            // 文件对象
            File dest = new File(fullName);
            // 判断路径是否存在，如果不存在则创建
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            // 保存文件到本地
            file.transferTo(dest);
            return fullName;
        } catch (Exception e) {
            log.error("文件上传异常", e);
            return null;
        }
    }

//    @Override
//    public ResultDTO<?> getFileWithDownload(String fileId, boolean inline) {
//        ResultDTO result = ResultDTO.newFail("");
//        if(StringUtils.isBlank(fileId)){
//            result.setMsg(new ResponseEntity<>(JSON.toJSON(ResultDTO.newFail("Arg-fileId lost")), HttpStatus.INTERNAL_SERVER_ERROR));
//            return result;
//        }
//
//        File file = new File(fileId);
//        if(file.exists()){
//            try{
//                HttpHeaders headers = new HttpHeaders();
//                String downloadFielName = new String(model.getFileName().getBytes("UTF-8"),"iso-8859-1");
//                if(inline){
//                    headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=" + downloadFielName);
//                    headers.set(HttpHeaders.CONTENT_TYPE, ContentType.getContentType(model.getExtendName()) + ";charset=utf-8");
//                }else{
//                    headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + downloadFielName);
//                    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString() + ";charset=utf-8");
//                }
//                return ResultDTO.newSuccess(new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
//                        headers, HttpStatus.CREATED));
//            } catch (UnsupportedEncodingException e) {
//                log.error("encode error not found code : UTF-8" ,e);
//                return ResultDTO.newSuccess(new ResponseEntity<>(JSON.toJSON(ResultDTO.newFail("encode error not found code : UTF-8")),HttpStatus.INTERNAL_SERVER_ERROR));
//            } catch (IOException e) {
//                log.error("File read error" ,e);
//                return ResultDTO.newSuccess(new ResponseEntity<>(JSON.toJSON(ResultDTO.newFail("File read error")),HttpStatus.INTERNAL_SERVER_ERROR));
//            }
//
//        }else{
//            return ResultDTO.newSuccess(new ResponseEntity<>(JSON.toJSON(ResultDTO.newFail("file lost")),HttpStatus.OK));
//        }
//    }


}
