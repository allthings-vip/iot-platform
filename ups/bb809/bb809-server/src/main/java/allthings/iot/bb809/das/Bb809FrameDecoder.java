package allthings.iot.bb809.das;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import allthings.iot.util.misc.ByteUtils;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  Bb809FrameDecoder
 * @CreateDate :  2018/3/13
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
public class Bb809FrameDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bb809FrameDecoder.class);

    /**
     * 1头标识，22数据头，1消息体，2CRC，1尾标识.
     */
    private static final int MIN_FRAME_LENGTH = 1 + 22 + 1 + 2 + 1;

    private static final int MAX_FRAME_LENGTH = 1024 * 20;

    private static final byte START = 0x5b;
    private static final byte END = 0x5d;

    /**
     * 判断是否半包等待
     **/
    private boolean isWaitLeft = false;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();

        in.markReaderIndex();
        //长度小于min,等待;也有可能只剩半包数据
        if (readableBytes < MIN_FRAME_LENGTH && isWaitLeft) {
            byte[] tempBytes = new byte[readableBytes];
            in.readBytes(tempBytes);
            LOGGER.error("data[{}] less than min length[{}], wait", ByteUtils.bytesToHexString(tempBytes),
                    MIN_FRAME_LENGTH);
            in.resetReaderIndex();
            return;
        }

        //长度大于max，直接drop,原因：ByteMessageDecoder多次处理半包问题，已经超出规定长度
        if (readableBytes > MAX_FRAME_LENGTH) {
            byte[] tempBytes = new byte[readableBytes];
            in.readBytes(tempBytes);
            isWaitLeft = false;
            LOGGER.error("data[{}] more than max length[{}], drop", ByteUtils.bytesToHexString(tempBytes),
                    MAX_FRAME_LENGTH);
            return;
        }

        //找头标识
        int startIndex = in.indexOf(in.readerIndex(), in.writerIndex(), START);
        if (startIndex < 0) {
            byte[] tempBytes = new byte[in.writerIndex() - in.readerIndex()];
            in.readBytes(tempBytes);
            LOGGER.error("data[{}] can not find start, drop" + ByteUtils.bytesToHexString(tempBytes));
            return;
        }

        //虽然头标记在最后，但是剩余部分可能在下一个包中
        //找尾标识
        int endIndex = in.indexOf(startIndex + 1, in.writerIndex(), END);
        if (endIndex < 0) {
            byte[] tempBytes = new byte[in.writerIndex() - in.readerIndex()];
            in.readBytes(tempBytes);

            isWaitLeft = true;
            in.readerIndex(startIndex);
            LOGGER.error("data[{}] can not find end, wait" + ByteUtils.bytesToHexString(tempBytes));
            return;
        }

        //这里 + 1
        byte[] messageBytes = new byte[endIndex + 1 - startIndex];
        in.readBytes(messageBytes);

        byte[] outBytes = decodeBuf(messageBytes);
        ByteBuf outBuf = Unpooled.wrappedBuffer(outBytes);
        out.add(outBuf);
    }


    /**
     * 转义还原
     */
    private byte[] decodeBuf(byte[] sourceBytes) {
        ByteBuf resultBuf = Unpooled.buffer(sourceBytes.length * 2);
        for (int i = 0; i < sourceBytes.length; i++) {
            byte tempByte = (byte) 0xff;

            if (sourceBytes[i] == (byte) 0x5a) {
                //最后一个字节不会是0x5a,不需要判断
                if (sourceBytes[i + 1] == (byte) 0x01) {
                    tempByte = (byte) 0x5b;
                    i++;
                }

                if (sourceBytes[i + 1] == (byte) 0x02) {
                    tempByte = (byte) 0x5a;
                    i++;
                }

                resultBuf.writeByte(tempByte & 0xff);
                continue;
            }

            if (sourceBytes[i] == (byte) 0x5e) {
                //最后一个字节不会是0x5a,不需要判断
                if (sourceBytes[i + 1] == (byte) 0x01) {
                    tempByte = (byte) 0x5d;
                    i++;
                }

                if (sourceBytes[i + 1] == (byte) 0x02) {
                    tempByte = (byte) 0x5e;
                    i++;
                }


                resultBuf.writeByte(tempByte & 0xff);
                continue;
            }
            tempByte = sourceBytes[i];
            resultBuf.writeByte(tempByte & 0xff);
        }
        byte[] resultBytes = new byte[resultBuf.readableBytes()];
        resultBuf.readBytes(resultBytes);
        return resultBytes;
    }
}
