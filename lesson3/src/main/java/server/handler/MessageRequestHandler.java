package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.request.MessageRequestPacket;
import protocal.response.MessageResponsePacket;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg)
      throws Exception {
    MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
    System.out.println(new Date() + " :收到客户端消息: " + msg.getMessage());
    messageResponsePacket.setMasssage("服务器回复谢谢你");
    ctx.channel().writeAndFlush(messageResponsePacket);
  }
}
