package protocal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import protocal.command.Command;
import protocal.request.LoginRequestPacket;
import protocal.request.MessageRequestPacket;
import protocal.response.LoginResponsePacket;
import protocal.response.MessageResponsePacket;
import serialize.Serializer;
import serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {
  // 魔数 协议校验
  private static final int MAGIC_NUMBER = 0x12345678;
  // 实例
  public static final PacketCodeC INSTANCE = new PacketCodeC();
  // 包指令类型 map
  private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
  // 序列化类型
  private static final Map<Byte, Serializer> serializerMap;

  static {
    packetTypeMap = new HashMap<>();
    packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
    packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
    packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
    packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

    serializerMap = new HashMap<>();
    Serializer serializer = new JSONSerializer();
    serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
  }

  /**
   * 编码
   *
   * @param packet
   * @return
   */
  public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
    // 1.创建ByteBuf对象
    ByteBuf byteBuf = byteBufAllocator.ioBuffer();
    // 2.序列化java对象
    byte[] bytes = Serializer.DEFALUT.serializer(packet);

    // 3.实际编码过程
    byteBuf.writeInt(MAGIC_NUMBER);
    byteBuf.writeByte(packet.getVersion());
    byteBuf.writeByte(Serializer.DEFALUT.getSerializerAlgorithm());
    byteBuf.writeByte(packet.getCommand());
    byteBuf.writeInt(bytes.length);
    byteBuf.writeBytes(bytes);

    return byteBuf;
  }

  public Packet decode(ByteBuf byteBuf) {
    // 跳过 magic number
    byteBuf.skipBytes(4);
    // 跳过版本号
    byteBuf.skipBytes(1);
    // 序列化算法
    byte serializeAlgorethm = byteBuf.readByte();
    // 指令
    byte command = byteBuf.readByte();
    // 数据包长度
    int length = byteBuf.readInt();

    byte[] bytes = new byte[length];
    // 读取数据包数据
    byteBuf.readBytes(bytes);
    Class<? extends Packet> requestType = getRequestType(command);
    Serializer serializer = getSerializer(serializeAlgorethm);

    if (requestType != null && serializer != null) {
      return serializer.deserializer(requestType, bytes);
    }
    return null;
  }
  // 获取序列化方式
  private Serializer getSerializer(byte serializeAlgorithm) {
    return serializerMap.get(serializeAlgorithm);
  }

  // 获取请求类型对应类
  private Class<? extends Packet> getRequestType(byte command) {
    return packetTypeMap.get(command);
  }
}
