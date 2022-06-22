package protocal.response;

import lombok.Data;
import protocal.Packet;
import protocal.command.Command;

@Data
public class LoginResponsePacket extends Packet {

  private boolean success;

  private String reason;

  @Override
  public Byte getCommand() {
    return Command.LOGIN_RESPONSE;
  }
}
