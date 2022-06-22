package demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf byteBuf = (ByteBuf) msg;
    System.out.println(new Date() + ": 服务端读取到数据->" + byteBuf.toString(Charset.forName("utf-8")));
    System.out.println(new Date() + " :服务器端写出数据");
    ctx.channel().writeAndFlush(getByteBuf(ctx));
  }

  private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
    ByteBuf buffer = ctx.alloc().buffer();
    byte[] bytes = "你好，客户端".getBytes(Charset.forName("utf-8"));
    buffer.writeBytes(bytes);
    return buffer;
  }
}
