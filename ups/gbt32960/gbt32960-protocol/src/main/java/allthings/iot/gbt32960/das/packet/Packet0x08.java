/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.gbt32960.das.packet;

import java.util.Map;

import io.netty.buffer.ByteBuf;


/**
 * 类Packet0x08.java的实现描述：终端校时
 * 
 * @author mingyuan.miao 2018年4月18日 下午3:54:54
 */
public class Packet0x08 extends AbstractPacket {

    public Packet0x08(){
        super("0x08");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        // 数据单元为空

    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.gbt32960.das.packet.AbstractPacket#packBody(java.util.Map)
     */
    @Override
    protected byte[] packBody(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return new byte[0];
    }

}
