package moze_intel.projecte.network.packets;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StepHeightPKT implements IMessage {
  private float value;

  public StepHeightPKT() {
  }

  public StepHeightPKT(float value) {
    this.value = value;
  }

  @Override public void fromBytes(ByteBuf buf) {
    value = buf.readFloat();
  }

  @Override public void toBytes(ByteBuf buf) {
    buf.writeFloat(value);
  }

  public static class Handler implements IMessageHandler<StepHeightPKT, IMessage> {
    @Override public IMessage onMessage(final StepHeightPKT message, MessageContext ctx) {

<<<<<<< commits-rmx_100/MozeIntel/ProjectE/460c0e172a99b769065c76d8dbac443d7ef3acbf/StepHeightPKT-adaf87d.java
      Minecraft.getMinecraft().addScheduledTask(new Runnable() {
        @Override public void run() {
          Minecraft.getMinecraft().thePlayer.stepHeight = message.value;
        }
      });
=======
      Minecraft.getMinecraft().thePlayer.stepHeight = message.value;
>>>>>>> commits-rmx_100/MozeIntel/ProjectE/6190506feaa36d6fcd0e0102e801d508a9e474fd/StepHeightPKT-498e902.java

      return null;
    }
  }
}