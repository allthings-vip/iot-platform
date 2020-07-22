package allthings.iot.bb808.das;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 * @author :  luhao
 * @FileName :  Bb808SplitDecoder
 * @CreateDate :  2017/12/21
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
public class Bb808SplitDecoder extends ByteToMessageDecoder {

    private static final int MIN_FRAME_LENGTH = 1 + 12 + 0 + 1 + 1;
    private static final int MAX_BUF_LENGTH = 1024;
    private byte start = 0x7e;
    private byte end = 0x7e;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < MIN_FRAME_LENGTH) {
            return;
        }

        in.markReaderIndex();
        if (in.readByte() == start) {
            // 寻找结束符，从开始符下一个位置(索引1)开始搜索
            int endIndex = in.indexOf(in.readerIndex(), in.writerIndex(), end);
            if (endIndex >= 0) {
                in.resetReaderIndex();
                byte[] messageBytes = new byte[endIndex + 1 - in.readerIndex()];
                in.readBytes(messageBytes);

                byte[] decodeBuff = decodeBuff(messageBytes);
                ByteBuf frame = Unpooled.wrappedBuffer(decodeBuff);
                out.add(frame);
            } else {
                // 没有结束，等待下一个粘包
                in.resetReaderIndex();

                byte[] buf = new byte[in.writerIndex() - in.readerIndex()];
                in.getBytes(in.readerIndex(), buf);
            }
        } else {
            // 如果开头不是start的这段数据都丢弃
            in.resetReaderIndex();

            byte[] buf = new byte[in.writerIndex() - in.readerIndex()];
            in.readBytes(buf);
        }
    }

    private byte[] decodeBuff(byte[] oriBuff) {
        ByteBuf buffer = Unpooled.buffer(oriBuff.length);
        int tmpByte = oriBuff[0] & 0xff;
        for (int i = 0; i < oriBuff.length; i++) {
            tmpByte = oriBuff[i] & 0xff;
            if ((oriBuff[i] & 0xff) == 0x7d && (i < oriBuff.length - 1)) {
                if ((oriBuff[i + 1] & 0xff) == 0x02) {
                    tmpByte = 0x7E;
                    i++;
                } else if ((oriBuff[i + 1] & 0xff) == 0x01) {
                    tmpByte = 0x7D;
                    i++;
                }
            }
            buffer.writeByte(tmpByte);
        }

        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        return bytes;
    }
}
