/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.gbt32960.das.record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;


/**
 * 类Record0x82.java的实现描述：TODO 类实现描述 
 * @author mingyuan.miao 2018年4月26日 上午11:17:15
 */
public class RecordCustom extends AbstractRecord {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordCustom.class);

    public RecordCustom(){
        super("0x80_FE");
    }

    /* (non-Javadoc)
     * @see allthings.iot.gbt32960.das.record.AbstractRecord#unpack(io.netty.buffer.ByteBuf)
     */
    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        LOGGER.warn("RecordCustom unpack. byteBuf={}", byteBuf.array());
        int dataLength = byteBuf.readUnsignedShort();
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
    }

}
