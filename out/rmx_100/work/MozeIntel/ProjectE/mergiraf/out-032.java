package moze_intel.projecte.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StepHeightPKT implements IMessage
{
	private float value;
<<<<<<< commits-rmx_100/MozeIntel/ProjectE/460c0e172a99b769065c76d8dbac443d7ef3acbf/StepHeightPKT-adaf87d.java
||||||| commits-rmx_100/MozeIntel/ProjectE/bc42ad5edf3c63afb233eb89016a75bc3cc057ef/StepHeightPKT-5672986.java
	
	//Needs empty constructor
=======
>>>>>>> commits-rmx_100/MozeIntel/ProjectE/6190506feaa36d6fcd0e0102e801d508a9e474fd/StepHeightPKT-498e902.java
	public StepHeightPKT() {}

	public StepHeightPKT(float value)
	{
		this.value = value;
<<<<<<< commits-rmx_100/MozeIntel/ProjectE/460c0e172a99b769065c76d8dbac443d7ef3acbf/StepHeightPKT-adaf87d.java
	}

	@Override
	public void fromBytes(ByteBuf buf) 
||||||| commits-rmx_100/MozeIntel/ProjectE/bc42ad5edf3c63afb233eb89016a75bc3cc057ef/StepHeightPKT-5672986.java
	}

	@Override
	public IMessage onMessage(StepHeightPKT message, MessageContext ctx) 
	{
		Minecraft.getMinecraft().thePlayer.stepHeight = message.value;
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
=======
	}

	@Override
	public void fromBytes(ByteBuf buf)
>>>>>>> commits-rmx_100/MozeIntel/ProjectE/6190506feaa36d6fcd0e0102e801d508a9e474fd/StepHeightPKT-498e902.java
	{
		value = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeFloat(value);
<<<<<<< commits-rmx_100/MozeIntel/ProjectE/460c0e172a99b769065c76d8dbac443d7ef3acbf/StepHeightPKT-adaf87d.java
	}

	public static class Handler implements IMessageHandler<StepHeightPKT, IMessage>
	{
		@Override
		public IMessage onMessage(final StepHeightPKT message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				public void run() {
					Minecraft.getMinecraft().thePlayer.stepHeight = message.value;
				}
			});
			return null;
		}
||||||| commits-rmx_100/MozeIntel/ProjectE/bc42ad5edf3c63afb233eb89016a75bc3cc057ef/StepHeightPKT-5672986.java
=======
	}

	public static class Handler implements IMessageHandler<StepHeightPKT, IMessage>
	{
		@Override
		public IMessage onMessage(final StepHeightPKT message, MessageContext ctx)
		{
			Minecraft.getMinecraft().thePlayer.stepHeight = message.value;
			return null;
		}
>>>>>>> commits-rmx_100/MozeIntel/ProjectE/6190506feaa36d6fcd0e0102e801d508a9e474fd/StepHeightPKT-498e902.java
	}
}
