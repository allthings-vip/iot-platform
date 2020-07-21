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


import com.alibaba.fastjson.annotation.JSONField;

import java.nio.ByteBuffer;

/**
 * @author andrea
 */
public class PublishMessage extends MessageIDMessage {

    private String mTopicName;

    @JSONField(serialize = false, deserialize = false)
    private ByteBuffer mPayload;


    public PublishMessage() {
        mMessageType = PUBLISH;
    }

    public String getTopicName() {
        return mTopicName;
    }

    public void setTopicName(String topicName) {
        this.mTopicName = topicName;
    }

    @JSONField(serialize = false, deserialize = false)
    public ByteBuffer getPayload() {
        return mPayload;
    }

    @JSONField(serialize = false, deserialize = false)
    public void setPayload(ByteBuffer payload) {
        this.mPayload = payload;
    }

    public byte[] getPayloadBytes() {
        return mPayload.array();
    }

    public void setPayloadBytes(byte[] payloadBytes) {
        this.mPayload = ByteBuffer.wrap(payloadBytes);
    }
}
