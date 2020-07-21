package allthings.iot.das.mqtt.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.Attribute;
import io.netty.util.AttributeMap;
import allthings.iot.das.mqtt.MqttConst;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author :  sylar
 * @FileName :  CodecUtil
 * @CreateDate :  2017/11/08
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
public class CodecUtil {

    private static final int TWO = 2;

    static byte readMessageType(ByteBuf in) {
        byte h1 = in.readByte();
        byte messageType = (byte) ((h1 & 0x00F0) >> 4);
        return messageType;
    }

    static boolean checkHeaderAvailability(ByteBuf in) {
        if (in.readableBytes() < 1) {
            return false;
        }

        //skip the messageType byte
        in.skipBytes(1);

        int remainingLength = CodecUtil.decodeRemainingLenght(in);
        if (remainingLength == -1) {
            return false;
        }

        //check remaining length
        if (in.readableBytes() < remainingLength) {
            return false;
        }

        //return messageType == type ? MessageDecoderResult.OK : MessageDecoderResult.NOT_OK;
        return true;
    }

    /**
     * Decode the variable remaining length as defined in MQTT v3.1 specification
     * (section 2.1).
     *
     * @return the decoded length or -1 if needed more data to decodePublishMessage the length field.
     */
    static int decodeRemainingLenght(ByteBuf in) {
        int multiplier = 1;
        int value = 0;
        byte digit;
        do {
            if (in.readableBytes() < 1) {
                return -1;
            }
            digit = in.readByte();
            value += (digit & 0x7F) * multiplier;
            multiplier *= 128;
        } while ((digit & 0x80) != 0);
        return value;
    }

    /**
     * Encode the value in the format defined in specification as variable length
     * array.
     *
     * @throws IllegalArgumentException if the value is not in the specification bounds
     *                                  [0..268435455].
     */
    static ByteBuf encodeRemainingLength(int value) throws CorruptedFrameException {
        if (value > MqttConst.MAX_LENGTH_LIMIT || value < 0) {
            throw new CorruptedFrameException("Value should in range 0.." + MqttConst.MAX_LENGTH_LIMIT + " found " +
                    value);
        }

        ByteBuf encoded = Unpooled.buffer(4);
        byte digit;
        do {
            digit = (byte) (value % 128);
            value = value / 128;
            // if there are more digits to encode, set the top bit of this digit
            if (value > 0) {
                digit = (byte) (digit | 0x80);
            }
            encoded.writeByte(digit);
        } while (value > 0);
        return encoded;
    }

    /**
     * Load a string from the given buffer, reading first the two bytes of len
     * and then the UTF-8 bytes of the string.
     *
     * @return the decoded string or null if NEED_DATA
     */
    static String decodeString(ByteBuf in) throws UnsupportedEncodingException {
        if (in.readableBytes() < TWO) {
            return null;
        }

        int strLen = in.readUnsignedShort();
        if (in.readableBytes() < strLen) {
            return null;
        }
        byte[] strRaw = new byte[strLen];
        in.readBytes(strRaw);

        return new String(strRaw, "UTF-8");
    }


    /**
     * Return the IoBuffer with string encoded as MSB, LSB and UTF-8 encoded
     * string content.
     */
    static ByteBuf encodeString(String str) {
        ByteBuf out = Unpooled.buffer(2);
        byte[] raw;
        try {
            raw = str.getBytes("UTF-8");
            //NB every Java platform has got UTF-8 encoding by default, so this 
            //exception are never raised.
        } catch (UnsupportedEncodingException ex) {
            LoggerFactory.getLogger(CodecUtil.class).error(null, ex);
            return null;
        }

        out.writeShort(raw.length);
        out.writeBytes(raw);
        return out;
    }

    /**
     * Return the number of bytes to encode the given remaining length value
     */
    static int numBytesToEncode(int len) {
        int lenLimit1 = TWO << 7;
        if (0 <= len && len < lenLimit1) {
            return 1;
        }
        int lenLimit2 = TWO << 14;
        if (lenLimit1 <= len && len < lenLimit2) {
            return 2;
        }
        int lenLimit3 = TWO << 21;
        if (lenLimit2 <= len && len < lenLimit3) {
            return 3;
        }
        int lenLimit4 = TWO << 28;
        if (lenLimit3 <= len && len < lenLimit4) {
            return 4;
        }
        throw new IllegalArgumentException("value shoul be in the range [0..268435455]");
    }

    static byte encodeFlags(AbstractMessage message) {
        byte flags = 0;
        if (message.isDupFlag()) {
            flags |= 0x08;
        }

        if (message.isRetainFlag()) {
            flags |= 0x01;
        }

        flags |= ((message.getQos().ordinal() & 0x03) << 1);
        return flags;
    }

    static boolean isMQTT311(AttributeMap attrsMap) {
        Attribute<Integer> versionAttr = attrsMap.attr(MqttDecoder.PROTOCOL_VERSION);
        Integer protocolVersion = versionAttr.get();
        if (protocolVersion == null) {
            return true;
        }
        return protocolVersion == MqttConst.VERSION_3_1_1;
    }
}
