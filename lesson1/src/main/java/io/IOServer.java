package io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/** 传统IO服务端 */
public class IOServer {
  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(8888);
    // 接收新连接线程
    new Thread(
            () -> {
              while (true) {
                try {
                  // 获取客户端连接，阻塞
                  Socket socket = serverSocket.accept();
                  System.out.println("获取新连接");
                  // 每个连接创建一个线程
                  new Thread(
                          () -> {
                            try {
                              int len;
                              byte[] data = new byte[1024];
                              InputStream inputStream = socket.getInputStream();
                              while ((len = inputStream.read(data)) != -1) {
                                System.out.println(new String(data, 0, len));
                              }

                            } catch (IOException e) {
                              e.printStackTrace();
                            }
                          })
                      .start();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            })
        .start();
  }
}
