package allthings.iot.dos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author tyf
 * @date 2019/02/26 19:22
 */
public class IotDevicePassDto extends AbstractIotDosDTO {

    private Long iotDevicePassId;

    private String passCode;

    private String passName;

    private Long iotDeviceId;

    private String passId;

    private List<Map<String, String>> extendProperties;

    @JsonIgnore
    private String extendPropertiesAlias;

    private Long iotPassTypeId;

    private String passTypeCode;

    public IotDevicePassDto() {
    }

    public IotDevicePassDto(Long iotDevicePassId, String passCode, String passName, String extendProperties) {
        this.iotDevicePassId = iotDevicePassId;
        this.passCode = passCode;
        this.passName = passName;
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.extendProperties = mapper.readValue(extendProperties, new TypeReference<List<Map<String, String>>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Long getIotDevicePassId() {
        return iotDevicePassId;
    }

    public void setIotDevicePassId(Long iotDevicePassId) {
        this.iotDevicePassId = iotDevicePassId;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getPassName() {
        return passName;
    }

    public void setPassName(String passName) {
        this.passName = passName;
    }

    public Long getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(Long iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public List<Map<String, String>> getExtendProperties() {
        return extendProperties;
    }

    @JsonIgnore
    public void setExtendProperties(List<Map<String, String>> extendProperties) {
        this.extendProperties = extendProperties;
    }

    @JsonIgnore
    public String getExtendPropertiesAlias() {
        return extendPropertiesAlias;
    }

    public void setExtendPropertiesAlias(String extendPropertiesAlias) {
        try {
            List<Map<String, String>> mapList = Lists.newLinkedList();
            if (!StringUtils.isBlank(extendPropertiesAlias)) {
                ObjectMapper mapper = new ObjectMapper();
                mapList = mapper.readValue(extendPropertiesAlias, new TypeReference<List<Map<String, String>>>() {
                });
            }
            this.extendProperties = mapList;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Long getIotPassTypeId() {
        return iotPassTypeId;
    }

    public void setIotPassTypeId(Long iotPassTypeId) {
        this.iotPassTypeId = iotPassTypeId;
    }

    public String getPassTypeCode() {
        return passTypeCode;
    }

    public void setPassTypeCode(String passTypeCode) {
        this.passTypeCode = passTypeCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(passCode, iotDeviceId, iotPassTypeId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof IotDevicePassDto) {
            IotDevicePassDto iotDevicePassDto = (IotDevicePassDto) obj;
            if (iotDevicePassDto.getPassCode().equals(this.passCode)
                    && iotDevicePassDto.getIotDeviceId().equals(this.iotDeviceId)
                    && iotDevicePassDto.getIotPassTypeId().equals(this.iotPassTypeId)) {
                return true;
            }
        }
        return false;
    }
}
