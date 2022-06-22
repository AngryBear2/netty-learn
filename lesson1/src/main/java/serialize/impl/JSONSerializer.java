package serialize.impl;

import com.alibaba.fastjson.JSON;
import serialize.Serializer;
import serialize.SerializerAlgorithm;

public class JSONSerializer implements Serializer {
  @Override
  public byte getSerializerAlgorithm() {
    return SerializerAlgorithm.JSON;
  }

  @Override
  public byte[] serializer(Object object) {
    return JSON.toJSONBytes(object);
  }

  @Override
  public <T> T deserializer(Class<T> clazz, byte[] bytes) {
    return JSON.parseObject(bytes, clazz);
  }
}
