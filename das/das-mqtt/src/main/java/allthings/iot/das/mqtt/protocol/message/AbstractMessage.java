/*
 * Copyright (c) 2012-2014 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http:/**www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http:/**www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package allthings.iot.das.mqtt.protocol.message;

import java.io.Serializable;

/**
 * Basic abstract message for all MQTT protocol messages.
 *
 * @author andrea
 */
public abstract class AbstractMessage implements Serializable {
    /**
     * Client request to connect to Server
     */
    public static final byte CONNECT = 1;
    /**
     * Connect Acknowledgment
     */
    public static final byte CONNACK = 2;
    /**
     * Publish message
     */
    public static final byte PUBLISH = 3;
    /**
     * Publish Acknowledgment
     */
    public static final byte PUBACK = 4;
    /**
     * Publish Received (assured delivery part 1)
     */
    public static final byte PUBREC = 5;
    /**
     * Publish Release (assured delivery part 2)
     */
    public static final byte PUBREL = 6;
    /**
     * Publish Complete (assured delivery part 3)
     */
    public static final byte PUBCOMP = 7;
    /**
     * Client Subscribe request
     */
    public static final byte SUBSCRIBE = 8;
    /**
     * Subscribe Acknowledgment
     */
    public static final byte SUBACK = 9;
    /**
     * Client Unsubscribe request
     */
    public static final byte UNSUBSCRIBE = 10;
    /**
     * Unsubscribe Acknowledgment
     */
    public static final byte UNSUBACK = 11;
    /**
     * PING Request
     */
    public static final byte PINGREQ = 12;
    /**
     * PING Response
     */
    public static final byte PINGRESP = 13;
    /**
     * Client is Disconnecting
     */
    public static final byte DISCONNECT = 14;
    /**
     * type
     */
    protected boolean mDupFlag;
    protected QOSType mQos;
    protected boolean mRetainFlag;
    protected int mRemainingLength;
    protected byte mMessageType;

    public byte getMessageType() {
        return mMessageType;
    }

    public void setMessageType(byte messageType) {
        this.mMessageType = messageType;
    }

    public boolean isDupFlag() {
        return mDupFlag;
    }

    public void setDupFlag(boolean dupFlag) {
        this.mDupFlag = dupFlag;
    }

    public QOSType getQos() {
        return mQos;
    }

    public void setQos(QOSType qos) {
        this.mQos = qos;
    }

    public boolean isRetainFlag() {
        return mRetainFlag;
    }

    public void setRetainFlag(boolean retainFlag) {
        this.mRetainFlag = retainFlag;
    }

    /**
     * TOBE used only internally
     */
    public int getRemainingLength() {
        return mRemainingLength;
    }

    /**
     * TOBE used only internally
     */
    public void setRemainingLength(int remainingLength) {
        this.mRemainingLength = remainingLength;
    }

    public enum QOSType {
        /**
         * 最多一次，有可能重复或丢失。
         */
        MOST_ONE,
        /**
         * 至少一次，有可能重复。
         */
        LEAST_ONE,
        /**
         * 只有一次，确保消息只到达一次
         */
        EXACTLY_ONCE,
        /**
         * 保留
         */
        RESERVED;

        public static String formatQoS(QOSType qos) {
            return String.format("%d - %s", qos.ordinal(), qos.name());
        }
    }

}
