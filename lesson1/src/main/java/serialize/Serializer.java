package serialize;

import serialize.impl.JSONSerializer;

public interface Serializer {
  Serializer DEFALUT = new JSONSerializer();
  /** 序列化算法 */
  byte getSerializerAlgorithm();
  // java对象转换成二进制
  byte[] serializer(Object object);
  // 二进制转换为java对象
  <T> T deserializer(Class<T> clazz, byte[] bytes);
}
