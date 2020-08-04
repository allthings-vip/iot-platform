package allthings.iot.dos.open.oauth2;

import allthings.iot.dos.dao.oauth2.IotOauthCodeDao;
import allthings.iot.dos.dto.oauth2.IotOauthCodeDto;
import allthings.iot.dos.model.oauth2.IotOauthCode;
import allthings.iot.dos.oauth2.api.IotAuthorizationCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-14 17:37
 */
@Service("iotAuthorizationCodeService")
public class IotAuthorizationCodeServiceImpl implements IotAuthorizationCodeService {

    @Autowired
    private IotOauthCodeDao iotOauthCodeDao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Long saveOauthCode(IotOauthCodeDto iotOauthCodeDTO) {
        IotOauthCode iotOauthCode = new IotOauthCode();
        BeanUtils.copyProperties(iotOauthCodeDTO, iotOauthCode);
        iotOauthCode = iotOauthCodeDao.saveAndFlush(iotOauthCode);
        return iotOauthCode.getIotOauthCodeId();
    }

    @Override
    public IotOauthCodeDto getOauthCodeByCode(String code) {
        IotOauthCode iotOauthCode = iotOauthCodeDao.findByCodeAndIsDeleted(code, false);
        IotOauthCodeDto oauthCodeDto = new IotOauthCodeDto();
        BeanUtils.copyProperties(iotOauthCode, oauthCodeDto);
        return oauthCodeDto;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteOauthCodeByCode(String code) {
        iotOauthCodeDao.deleteIotOauthCodeByCode(code);
    }
}
