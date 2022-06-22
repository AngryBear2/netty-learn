package util;

import attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/** 登录工具类 */
public class LoginUtil {
  /**
   * 登录标记设置位true
   *
   * @param channel
   */
  public static void markAsLogin(Channel channel) {
    channel.attr(Attributes.LOGIN).set(true);
  }

  public static boolean hasLogin(Channel channel) {
    Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
    return loginAttr != null;
  }
}
