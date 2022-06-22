package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocal.Packet;
import protocal.PacketCodeC;
import protocal.request.LoginRequestPacket;
import protocal.request.MessageRequestPacket;
import protocal.response.LoginResponsePacket;
import protocal.response.MessageResponsePacket;

import java.util.Date;

/**
 * pipeline与ChannelHandler 责任链模式 pipeline 双向链表存储channelHandlerContext
 * channelHandlerContext中获取channelHandler
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf byteBuf = (ByteBuf) msg;

    // 解码
    Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

    // 判断是否有登录请求包
    if (packet instanceof LoginRequestPacket) {
      LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
      LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
      loginResponsePacket.setVersion(packet.getVersion());

      // 登录校验
      if (valid(loginRequestPacket)) {
        // 校验成功
        loginResponsePacket.setSuccess(true);
        System.out.println(new Date() + ": 客户端登陆成功");
      } else {
        // 校验失败
        loginResponsePacket.setReason("账号密码校验失败");
        loginResponsePacket.setSuccess(false);
        System.out.println(new Date() + ": 账号密码校验失败");
      }
      ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
      ctx.channel().writeAndFlush(responseByteBuf);
    } else if (packet instanceof MessageRequestPacket) {
      MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
      System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

      MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
      messageResponsePacket.setMasssage(
          new Date() + "服务端回复【" + messageRequestPacket.getMessage() + "】");
      ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
      ctx.channel().writeAndFlush(responseByteBuf);
    }
  }

  private boolean valid(LoginRequestPacket loginRequestPacket) {
    return true;
  }
}
