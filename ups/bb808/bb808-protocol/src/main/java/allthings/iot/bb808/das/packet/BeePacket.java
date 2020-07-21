package allthings.iot.bb808.das.packet;

/**
 * @author :  luhao
 * @FileName :  BeePacket
 * @CreateDate :  2017/12/21
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface BeePacket {

    /**
     * 获取消息的编码。 对于每一条消息都需要有一个标识，标识该命令式属于那种类型的消息，以便于Server可以针对不同的消息进行差异化处理。
     *
     * @return
     */
    String getPacketId();

    /**
     * 打包消息体
     */
    byte[] pack();

    /**
     * 解码byte数组流
     */
    void unpack(byte[] bytes);
}
