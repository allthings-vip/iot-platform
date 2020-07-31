package allthings.iot.dos.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import tf56.iot.common.dto.ResultDTO;

/**
 * @author tyf
 * @date 2019/07/02 11:19:42
 */
@RequestMapping
public class IotDosBaseController {

    protected <T> ResultDTO<T> getResult(BizReturn<T> bizReturn) {
        if (bizReturn.IsError()) {
            if (bizReturn.error.getErrObj() == null) {
                return ResultDTO.newFail(bizReturn.error.getCode(), bizReturn.error.getMsg());
            } else {
                ResultDTO resultDTO = ResultDTO.newFail(bizReturn.error.getCode(), bizReturn.error.getMsg());
                resultDTO.setData(bizReturn.error.getErrObj());
                return resultDTO;
            }
        }
        return ResultDTO.newSuccess(bizReturn.R);
    }
}
