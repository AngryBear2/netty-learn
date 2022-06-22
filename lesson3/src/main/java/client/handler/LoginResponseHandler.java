package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.request.LoginRequestPacket;
import protocal.response.LoginResponsePacket;
import util.LoginUtil;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
    loginRequestPacket.setUserName("ssx");
    loginRequestPacket.setPassword("pwd");
    loginRequestPacket.setUserId(UUID.randomUUID().toString());

    ctx.channel().writeAndFlush(loginRequestPacket);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket)
      throws Exception {
    if (loginResponsePacket.isSuccess()) {
      System.out.println(new Date() + ": 客户端登陆成功");
      LoginUtil.markAsLogin(ctx.channel());
    } else {
      System.out.println(new Date() + " :客户端登陆失败，原因 :" + loginResponsePacket.getReason());
    }
  }
}
