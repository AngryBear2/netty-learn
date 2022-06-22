package server;

import codec.PacketDecoder;
import codec.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import server.handler.LoginRequestHandler;
import server.handler.MessageRequestHandler;

import java.util.Date;

public class NettyServer {

  private static final int PORT = 8888;

  public static void main(String[] args) {
    final ServerBootstrap serverBootstrap = new ServerBootstrap();
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    serverBootstrap
        .group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .handler(
            new ChannelInboundHandlerAdapter() {
              @Override
              public void channelActive(ChannelHandlerContext ctx) throws Exception {
                super.channelActive(ctx);
              }
            })
        // 表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
        .option(ChannelOption.SO_BACKLOG, 1024)
        // 表示是否开启TCP底层心跳机制，true为开启
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        // 表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
        .childOption(ChannelOption.TCP_NODELAY, true)
        .childHandler(
            new ChannelInitializer<NioSocketChannel>() {
              @Override
              protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                nioSocketChannel.pipeline().addLast(new PacketDecoder());
                nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                nioSocketChannel.pipeline().addLast(new PacketEncoder());
              }
            });
    bind(serverBootstrap, PORT);
  }

  private static void bind(final ServerBootstrap serverBootstrap, final int port) {
    serverBootstrap
        .bind(port)
        .addListener(
            new GenericFutureListener<Future<? super Void>>() {
              @Override
              public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                  System.out.println(new Date() + ":端口[" + port + "]绑定成功");
                } else {
                  System.err.println("端口[" + port + "]绑定失败！");
                }
              }
            });
  }
}
