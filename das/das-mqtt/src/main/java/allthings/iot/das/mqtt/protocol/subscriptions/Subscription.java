package allthings.iot.das.mqtt.protocol.subscriptions;

import allthings.iot.das.mqtt.protocol.message.AbstractMessage;

import java.io.Serializable;

/**
 * @author :  sylar
 * @FileName :  MqttConst
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Subscription implements Serializable {
    private static final long serialVersionUID = 3312213897949373977L;
    /**
     * max QoS acceptable
     */
    private AbstractMessage.QOSType requestedQos;
    private String clientId;
    private String topicFilter;
    private boolean cleanSession;
    private boolean active = true;

    public Subscription(String clientId, String topicFilter, AbstractMessage.QOSType requestedQos, boolean
            cleanSession) {
        this.requestedQos = requestedQos;
        this.clientId = clientId;
        this.topicFilter = topicFilter;
        this.cleanSession = cleanSession;
    }

    public String getClientId() {
        return clientId;
    }

    public AbstractMessage.QOSType getRequestedQos() {
        return requestedQos;
    }

    public String getTopicFilter() {
        return topicFilter;
    }

    public boolean isCleanSession() {
        return this.cleanSession;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Subscription other = (Subscription) obj;
        if (this.requestedQos != other.requestedQos) {
            return false;
        }
        if ((this.clientId == null) ? (other.clientId != null) : !this.clientId.equals(other.clientId)) {
            return false;
        }
        if ((this.topicFilter == null) ? (other.topicFilter != null) : !this.topicFilter.equals(other.topicFilter)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.requestedQos != null ? this.requestedQos.hashCode() : 0);
        hash = 37 * hash + (this.clientId != null ? this.clientId.hashCode() : 0);
        hash = 37 * hash + (this.topicFilter != null ? this.topicFilter.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("[filter:%s, cliID: %s, qos: %s, active: %s]", this.topicFilter, this.clientId, this
                .requestedQos, this.active);
    }
}
