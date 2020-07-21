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

import java.util.ArrayList;
import java.util.List;

/**
 * @author andrea
 */
public class SubscribeMessage extends MessageIDMessage {

    private List<Couple> mSubscriptions = new ArrayList<Couple>();

    public SubscribeMessage() {
        //Subscribe has always QoS 1
        mMessageType = SUBSCRIBE;
        mQos = AbstractMessage.QOSType.LEAST_ONE;
    }

    public List<Couple> subscriptions() {
        return mSubscriptions;
    }

    public void addSubscription(Couple subscription) {
        mSubscriptions.add(subscription);
    }

    public static class Couple {

        private byte mQos;
        private String mTopicfilter;

        public Couple(byte qos, String topic) {
            mQos = qos;
            mTopicfilter = topic;
        }

        public byte getQos() {
            return mQos;
        }

        public String getTopicFilter() {
            return mTopicfilter;
        }
    }
}
