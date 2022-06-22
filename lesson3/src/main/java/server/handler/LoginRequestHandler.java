package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.request.LoginRequestPacket;
import protocal.response.LoginResponsePacket;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
    System.out.println(new Date() + ": 收到客户端登陆请求");

    LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
    loginResponsePacket.setVersion(msg.getVersion());
    if (valid(msg)) {
      loginResponsePacket.setSuccess(true);
      System.out.println(new Date() + ": 登陆成功！");
    } else {
      loginResponsePacket.setReason("账号密码校验失败");
      loginResponsePacket.setSuccess(false);
      System.out.println(new Date() + ": 登陆失败！");
    }
    ctx.channel().writeAndFlush(loginResponsePacket);
  }

  private boolean valid(LoginRequestPacket loginRequestPacket) {
    return true;
  }
}
