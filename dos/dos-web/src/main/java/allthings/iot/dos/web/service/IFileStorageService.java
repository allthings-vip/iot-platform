package allthings.iot.dos.web.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author tyf
 * @date 2020/08/10 09:30:17
 */
public interface IFileStorageService {
    String upload(MultipartFile item);
}
