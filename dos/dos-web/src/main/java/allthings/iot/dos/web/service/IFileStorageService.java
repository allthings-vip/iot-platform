package allthings.iot.dos.web.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author tyf
 * @date 2020/08/10 09:30:17
 */
public interface IFileStorageService {
    /**
     * 文件上传
     *
     * @param item
     * @return
     */
    String upload(MultipartFile item);
}
