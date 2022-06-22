package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocal.Packet;
import protocal.PacketCodeC;
import protocal.request.LoginRequestPacket;
import protocal.response.LoginResponsePacket;
import protocal.response.MessageResponsePacket;
import util.LoginUtil;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println(new Date() + ": 客户端开始登录");

    // 创建登录对象
    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
    loginRequestPacket.setUserName("ssx");
    loginRequestPacket.setPassword("pwd");
    loginRequestPacket.setUserId(UUID.randomUUID().toString());

    // 编码
    ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

    // 写数据
    ctx.channel().writeAndFlush(buffer);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf byteBuf = (ByteBuf) msg;

    Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

    if (packet instanceof LoginResponsePacket) {
      LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
      if (loginResponsePacket.isSuccess()) {
        System.out.println(new Date() + ": 客户端登陆成功");
        LoginUtil.markAsLogin(ctx.channel());
      } else {
        System.out.println(new Date() + ": 客户端登陆失败 原因是: " + loginResponsePacket.getReason());
      }
    } else if (packet instanceof MessageResponsePacket) {
      MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
      System.out.println(new Date() + ": 客户端收到消息【" + messageResponsePacket.getMasssage() + "】");
    }
  }
}
