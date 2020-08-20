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

    private static final List<String> FILE_SUFFIX_NAME = Lists.newArrayList(".bmp", ".jpg", ".png", ".jpeg", ".gif");

    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final String WINDOWS_FILE_PATH = "E:/upload/image/";
    private static final String LINUX_FILE_PATH = "/upload/image/";

    @Override
    public String upload(MultipartFile file) {
        try {
            // 获取原始名字
            String fullName = file.getOriginalFilename();
            // 获取文件后缀名
            String suffixName = fullName.substring(fullName.lastIndexOf("."));
            // 保存文件路径，文件名
            String fileFormat = DateTimeFormat.forPattern("yyyy-MM-dd").print(System.currentTimeMillis()) + "/" + fullName;
            String filePath = OS.contains("windows") ? WINDOWS_FILE_PATH : LINUX_FILE_PATH;
            if (FILE_SUFFIX_NAME.contains(suffixName.toLowerCase())) {
                fullName = filePath + fileFormat;
            } else {
                fullName = filePath + fileFormat;
            }
            // 文件对象
            File dest = new File(fullName);
            // 判断路径是否存在，如果不存在则创建
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            // 保存文件到本地
            file.transferTo(dest);
            return fileFormat;
        } catch (Exception e) {
            log.error("文件上传异常", e);
            return null;
        }
    }

}
