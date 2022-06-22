package protocal.request;

import lombok.Data;
import protocal.Packet;
import protocal.command.Command;

@Data
public class LoginRequestPacket extends Packet {

  private String userId;

  private String userName;

  private String password;

  @Override
  public Byte getCommand() {
    return Command.LOGIN_REQUEST;
  }
}
