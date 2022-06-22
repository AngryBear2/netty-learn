package protocal.request;

import lombok.Data;
import protocal.Packet;
import protocal.command.Command;

@Data
public class MessageRequestPacket extends Packet {

  private String message;

  @Override
  public Byte getCommand() {
    return Command.MESSAGE_REQUEST;
  }
}
