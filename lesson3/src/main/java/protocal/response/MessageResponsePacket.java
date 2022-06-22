package protocal.response;

import lombok.Data;
import protocal.Packet;
import protocal.command.Command;

@Data
public class MessageResponsePacket extends Packet {
  private String masssage;

  @Override
  public Byte getCommand() {
    return Command.MESSAGE_RESPONSE;
  }
}
