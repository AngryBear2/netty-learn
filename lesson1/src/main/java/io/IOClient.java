package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

/** 传统IO客户端 */
public class IOClient {
  public static void main(String[] args) {
    new Thread(
            () -> {
              try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(8888));
                while (true) {
                  socket.getOutputStream().write((new Date() + ":hello world").getBytes());
                  Thread.sleep(2000);
                }
              } catch (IOException e) {
                e.printStackTrace();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            })
        .start();
  }
}
