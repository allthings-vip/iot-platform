package allthings.iot.bb808.das.packet;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import allthings.iot.bb808.common.MsgParam;

import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x8103
 * @CreateDate :  2017/12/21
 * @Description : 设置终端参数
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8103 extends AbstractPacket {
    private static final Logger logger = LoggerFactory.getLogger(Packet0x8103.class);
    private short paramCount;

    public Packet0x8103() {
        super("8103");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(paramCount);
        Map<String, Object> paramMap = super.get(MsgParam.TERMINAL_PARAMETERS_MAP);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            packParameter(Integer.parseInt(entry.getKey()), entry.getValue(), buf);
        }
        buf.markWriterIndex();
        buf.writerIndex(0);
        buf.writeByte(paramCount);
        buf.resetWriterIndex();

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }

    private void packParameter(Integer paramId, Object value, ByteBuf buf) {
        if (value == null) {
            return;
        }
        switch (paramId) {
            case 0x0001:    //终端心跳发送间隔，单位为秒（s）
            case 0x0002:    //TCP 消息应答超时时间，单位为秒（s）
            case 0x0003:    //TCP 消息重传次数
            case 0x0004:    //UDP 消息应答超时时间，单位为秒（s）
            case 0x0005:    //UDP 消息重传次数
            case 0x0006:    //SMS 消息应答超时时间，单位为秒（s）
            case 0x0007:    //SMS 消息重传次数
            case 0x0018:    //服务器TCP 端口
            case 0x0019:    //服务器UDP 端口
            case 0x001B:    //道路运输证IC 卡认证主服务器TCP 端口
            case 0x001C:    //道路运输证IC 卡认证主服务器UDP 端口
            case 0x0020:    //位置汇报策略，0：定时汇报；1：定距汇报；2：定时和定距汇报
            case 0x0021:    //位置汇报方案，0：根据ACC 状态； 1：根据登录状态和ACC 状态，先判断登录状态，若登录再根据ACC 状态
            case 0x0022:    //驾驶员未登录汇报时间间隔，单位为秒（s），>0
            case 0x0027:    //休眠时汇报时间间隔，单位为秒（s），>0
            case 0x0028:    //紧急报警时汇报时间间隔，单位为秒（s），>0
            case 0x0029:    //缺省时间汇报间隔，单位为秒（s），>0
            case 0x002C:    //缺省距离汇报间隔，单位为米（m），>0
            case 0x002D:    //驾驶员未登录汇报距离间隔，单位为米（m），>0
            case 0x002E:    //休眠时汇报距离间隔，单位为米（m），>0
            case 0x002F:    //紧急报警时汇报距离间隔，单位为米（m），>0
            case 0x0030:    //拐点补传角度，<180
            case 0x0045:    //终端电话接听策略，0：自动接听；1：ACC ON 时自动接听，OFF 时手动接听
            case 0x0046:    //每次最长通话时间，单位为秒（s），0 为不允许通话，0xFFFFFFFF 为不限制
            case 0x0047:    //当月最长通话时间，单位为秒（s），0 为不允许通话，0xFFFFFFFF 为不限制
            case 0x0050:    //报警屏蔽字，与位置信息汇报消息中的报警标志相对应，相应位为1则相应报警被屏蔽
            case 0x0051:    //报警发送文本SMS 开关，与位置信息汇报消息中的报警标志相对应，相应位为1 则相应报警时发送文本SMS
            case 0x0052:    //报警拍摄开关，与位置信息汇报消息中的报警标志相对应，相应位为1 则相应报警时摄像头拍摄
            case 0x0053:    //报警拍摄存储标志，与位置信息汇报消息中的报警标志相对应，相应位为1 则对相应报警时拍的照片进行存储，否则实时上传
            case 0x0054:    //关键标志，与位置信息汇报消息中的报警标志相对应，相应位为1 则对相应报警为关键报警
            case 0x0055:    //最高速度，单位为公里每小时（km/h）
            case 0x0056:    //超速持续时间，单位为秒（s）
            case 0x0057:    //连续驾驶时间门限，单位为秒（s）
            case 0x0058:    //当天累计驾驶时间门限，单位为秒（s）
            case 0x0059:    //最小休息时间，单位为秒（s）
            case 0x005A:    //最长停车时间，单位为秒（s）
            case 0x0064:    //定时拍照控制
            case 0x0065:    //定距拍照控制
            case 0x0070:    //图像/视频质量，1-10，1 最好
            case 0x0071:    //亮度，0-255
            case 0x0072:    //对比度，0-127
            case 0x0073:    //饱和度，0-127
            case 0x0074:    //色度，0-255
            case 0x0093:    //GNSS 模块详细定位数据采集频率，单位为秒，默认为1
            case 0x0095:    //GNSS 模块详细定位数据上传设置
            case 0x0100:    //CAN 总线通道1 采集时间间隔(ms)，0 表示不采集
            case 0x0102:    //CAN 总线通道2 采集时间间隔(ms)，0 表示不采集
                buf.writeInt(paramId);
                buf.writeByte(4);
                int i = (int) Long.parseLong(String.valueOf(value));
                buf.writeInt((int) Long.parseLong(String.valueOf(value)));
                break;
            case 0x0010:    //主服务器APN，无线通信拨号访问点。若网络制式为CDMA，则该处为PPP 拨号号码
            case 0x0011:    //主服务器无线通信拨号用户名
            case 0x0012:    //主服务器无线通信拨号密码
            case 0x0013:    //主服务器地址,IP 或域名
            case 0x0014:    //备份服务器APN，无线通信拨号访问点
            case 0x0015:    //备份服务器无线通信拨号用户名
            case 0x0016:    //备份服务器无线通信拨号密码
            case 0x0017:    //备份服务器地址,IP 或域名
            case 0x001A:    //道路运输证IC 卡认证主服务器IP 地址或域名
            case 0x001D:    //道路运输证IC 卡认证备份服务器IP 地址或域名，端口同主服务器
            case 0x0040:    //监控平台电话号码
            case 0x0041:    //复位电话号码，可采用此电话号码拨打终端电话让终端复位
            case 0x0042:    //恢复出厂设置电话号码，可采用此电话号码拨打终端电话让终端恢复
            case 0x0043:    //监控平台SMS 电话号码
            case 0x0044:    //接收终端SMS 文本报警号码
            case 0x0048:    //监听电话号码
            case 0x0049:    //监管平台特权短信号码
            case 0x0083:    //公安交通管理部门颁发的机动车号牌
                buf.writeInt(paramId);
                String tmp = value.toString();
                byte[] bytes = tmp.getBytes(Charsets.UTF_8);
                buf.writeByte(bytes.length);
                buf.writeBytes(bytes);
                break;
            case 0x0031:    //电子围栏半径（非法位移阈值），单位为米
            case 0x005C:    //疲劳驾驶预警差值，单位为秒（s），>0
            case 0x005D:    //碰撞报警参数设置：  b7-b0： 碰撞时间，单位4ms；
                //  b15-b8：碰撞加速度，单位0.1g，设置范围在：0-79 之间，默认为10。
            case 0x005E:    //侧翻报警参数设置：侧翻角度，单位1 度，默认为30 度。
            case 0x0081:    //车辆所在的省域ID
            case 0x0082:    //车辆所在的市域ID
            case 0x0101:    //CAN 总线通道1 上传时间间隔(s)，0 表示不上传
            case 0x0103:    //CAN 总线通道2 上传时间间隔(s)，0 表示不上传
                buf.writeInt(paramId);
                buf.writeByte(2);
                buf.writeShort(Integer.parseInt(String.valueOf(value)));
                break;
            case 0x005B:    //超速报警预警差值，单位为1/10Km/h
                buf.writeInt(paramId);
                buf.writeByte(2);
                buf.writeShort((int) (Double.parseDouble(String.valueOf(value)) * 10));
                break;
            case 0x0080:    //车辆里程表读数，1/10km
                buf.writeInt(paramId);
                buf.writeByte(4);
                buf.writeInt((int) (Double.parseDouble(String.valueOf(value)) * 10));
                break;
            case 0x0084:    //车牌颜色，按照JT/T415-2006 的5.4.12
            case 0x0090:    //GNSS 定位模式
            case 0x0091:    //GNSS 波特率
            case 0x0092:    //GNSS 模块详细定位数据输出频率
            case 0x0094:    //GNSS 模块详细定位数据采集频频率
                buf.writeInt(paramId);
                buf.writeByte(1);
                buf.writeByte(Integer.parseInt(String.valueOf(value)));
                break;
            case 0x0110:    //CAN 总线ID 单独采集设置：
                buf.writeInt(paramId);
                buf.writeByte(8);
                buf.writeLong(Long.parseLong(String.valueOf(value)));
                break;
            default:
                if (paramId >= 0x0111 && paramId <= 0x01FF) { //用于其他CAN 总线ID 单独采集设置
                    buf.writeInt(paramId);
                    buf.writeByte(8);
                    buf.writeLong(Long.parseLong(String.valueOf(value)));
                } else if (paramId >= 0xF000 && paramId <= 0xFFFF) {   //用户自定义
                    logger.info("0x0813 unsupport ExternalId[" + paramId + "]");
                    paramCount--;
                } else {
                    logger.info("0x0813 unsupport ExternalId[" + paramId + "]");
                    paramCount--;
                }
        }
        paramCount++;
    }
}
