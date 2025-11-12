package moze_intel.projecte.network.packets;

import io.netty.buffer.ByteBuf;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateGemModePKT implements IMessage
{
	private boolean mode;

	public UpdateGemModePKT() {}

	public UpdateGemModePKT(boolean mode)
	{
		this.mode = mode;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		mode = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(mode);
	}
<<<<<<< commits-rmx_100/MozeIntel/ProjectE/460c0e172a99b769065c76d8dbac443d7ef3acbf/UpdateGemModePKT-90900c0.java

	public static class Handler implements IMessageHandler<UpdateGemModePKT, IMessage>
	{
		@Override
		public IMessage onMessage(final UpdateGemModePKT pkt, final MessageContext ctx)
		{
			ctx.getServerHandler().playerEntity.mcServer.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					ItemStack stack = ctx.getServerHandler().playerEntity.getHeldItem();

					if (stack != null && stack.getItem() == ObjHandler.eternalDensity)
					{
						stack.getTagCompound().setBoolean("Whitelist", pkt.mode);
					}
				}
			});

			return null;
		}
	}
||||||| commits-rmx_100/MozeIntel/ProjectE/bc42ad5edf3c63afb233eb89016a75bc3cc057ef/UpdateGemModePKT-77f7a81.java
=======

	public static class Handler implements IMessageHandler<UpdateGemModePKT, IMessage>
	{
		@Override
		public IMessage onMessage(final UpdateGemModePKT pkt, final MessageContext ctx)
		{
			ItemStack stack = ctx.getServerHandler().playerEntity.getHeldItem();

			if (stack != null && stack.getItem() == ObjHandler.eternalDensity)
			{
				stack.getTagCompound().setBoolean("Whitelist", pkt.mode);
			}

			return null;
		}
	}
>>>>>>> commits-rmx_100/MozeIntel/ProjectE/6190506feaa36d6fcd0e0102e801d508a9e474fd/UpdateGemModePKT-3166dec.java
}
