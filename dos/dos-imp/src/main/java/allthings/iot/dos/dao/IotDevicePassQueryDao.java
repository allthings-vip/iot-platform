package allthings.iot.dos.dao;

/**
 * @author tyf
 * @date 2019/03/14 19:43
 */
public interface IotDevicePassQueryDao {

    /**
     * 根据通道编码查询通道类型编码
     *
     * @param passCode
     * @return
     */
    String getPassTypeCodeByPassCode(String passCode);


}
