package allthings.iot.dos.web.kapthcha;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.ErrorCode;
import com.cuisongliu.kaptcha.autoconfigure.util.KaptchaUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-22 16:46
 */
@Service("kaptchaService")
public class KaptchaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KaptchaService.class);

    @Autowired
    private DefaultKaptcha iotDosKaptcha;

    public ResultDTO<?> dosKaptchaGenerator(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String capText = iotDosKaptcha.createText();
        session.setAttribute(KaptchaUtil.kaptchaKeyGenerator(""), capText);
        BufferedImage bi = iotDosKaptcha.createImage(capText);
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", out);
            byte[] bytes = out.toByteArray();
            String img = Base64Utils.encodeToString(bytes);
            return ResultDTO.newSuccess(img);
        } catch (IOException e) {
            LOGGER.error("kaptcha error:{}", e.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_5055.getCode(), ErrorCode.ERROR_5055.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                LOGGER.error("IO close error:{}", e.getMessage());
            }
        }
    }
}
