package allthings.iot.dos.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author tyf
 * @date 2019/07/15 18:42:40
 */
public class IotServiceListDto implements Serializable {

    private List<IotServiceDTO> services;

    public IotServiceListDto(List<IotServiceDTO> services) {
        this.services = services;
    }

    public IotServiceListDto() {
    }

    public List<IotServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<IotServiceDTO> services) {
        this.services = services;
    }
}
