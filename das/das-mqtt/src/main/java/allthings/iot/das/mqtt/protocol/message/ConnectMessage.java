/*
 * Copyright (c) 2012-2014 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package allthings.iot.das.mqtt.protocol.message;

/**
 * The attributes Qos, Dup and Retain aren't used for Connect message
 *
 * @author andrea
 */
public class ConnectMessage extends AbstractMessage {
    String mProtocolName;
    byte mProtocolVersion;

    /**
     * Connection flags
     */
    boolean mCleanSession;
    boolean mWillFlag;
    byte mWillQos;
    boolean mWillRetain;
    boolean mPasswordFlag;
    boolean mUserFlag;
    int mKeepAlive;

    /**
     * Variable part
     */
    String mUsername;
    String mPassword;
    String mClientID;
    String mWilltopic;
    String mWillMessage;

    public ConnectMessage() {
        mMessageType = CONNECT;
    }

    public boolean isCleanSession() {
        return mCleanSession;
    }

    public void setCleanSession(boolean cleanSession) {
        this.mCleanSession = cleanSession;
    }

    public int getKeepAlive() {
        return mKeepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.mKeepAlive = keepAlive;
    }

    public boolean isPasswordFlag() {
        return mPasswordFlag;
    }

    public void setPasswordFlag(boolean passwordFlag) {
        this.mPasswordFlag = passwordFlag;
    }

    public byte getProcotolVersion() {
        return mProtocolVersion;
    }

    public void setProcotolVersion(byte procotolVersion) {
        this.mProtocolVersion = procotolVersion;
    }

    public String getProtocolName() {
        return mProtocolName;
    }

    public void setProtocolName(String protocolName) {
        this.mProtocolName = protocolName;
    }

    public boolean isUserFlag() {
        return mUserFlag;
    }

    public void setUserFlag(boolean userFlag) {
        this.mUserFlag = userFlag;
    }

    public boolean isWillFlag() {
        return mWillFlag;
    }

    public void setWillFlag(boolean willFlag) {
        this.mWillFlag = willFlag;
    }

    public byte getWillQos() {
        return mWillQos;
    }

    public void setWillQos(byte willQos) {
        this.mWillQos = willQos;
    }

    public boolean isWillRetain() {
        return mWillRetain;
    }

    public void setWillRetain(boolean willRetain) {
        this.mWillRetain = willRetain;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getClientID() {
        return mClientID;
    }

    public void setClientID(String clientID) {
        this.mClientID = clientID;
    }

    public String getWillTopic() {
        return mWilltopic;
    }

    public void setWillTopic(String topic) {
        this.mWilltopic = topic;
    }

    public String getWillMessage() {
        return mWillMessage;
    }

    public void setWillMessage(String willMessage) {
        this.mWillMessage = willMessage;
    }

    @Override
    public String toString() {
        String base = String.format("Connect [clientID: %s, prot: %s, ver: %02X, clean: %b]", mClientID,
                mProtocolName, mProtocolVersion, mCleanSession);
        if (mWillFlag) {
            base += String.format(" Will [QoS: %d, retain: %b]", mWillQos, mWillRetain);
        }
        return base;
    }
}
