package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.response.MessageResponsePacket;

import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
  @Override
  protected void channelRead0(
      ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
    System.out.println(new Date() + " :客户端收到消息: " + messageResponsePacket.getMasssage());
  }
}
