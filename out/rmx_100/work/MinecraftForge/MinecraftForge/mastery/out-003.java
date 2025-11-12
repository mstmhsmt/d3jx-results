package cpw.mods.fml.client;

import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import java.util.Iterator;

public class GuiModItemsMissing extends GuiScreen {

    private List<String> missingItems;

    public GuiModItemsMissing(List<String> items) {
        this.missingItems = items;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 38, I18n.format("gui.done")));
    }

    @Override
    protected void actionPerformed(GuiButton p_73875_1_) {
        if (p_73875_1_.enabled && p_73875_1_.id == 1) {
            FMLClientHandler.instance().showGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.drawDefaultBackground();
        int spaceAvailable = this.field_146295_m - 38 - 20;
        
<<<<<<< commits-rmx_100/MinecraftForge/MinecraftForge/6fd3c12a4a15a5cf38c421a94576a5cacd3fb7c1/GuiModItemsMissing-096c3e9.java
int spaceRequired = Math.min(spaceAvailable, 10 + 6 * 10 + missingItems.size());
=======
this.drawCenteredString(this.fontRendererObj, "Forge Mod Loader could load this save", this.width / 2, offset, 0xFFFFFF);
>>>>>>> commits-rmx_100/MinecraftForge/MinecraftForge/58a4095f56930cb27f7a06db5409ca7d570761cd/GuiModItemsMissing-4335bfc.java

        int offset = 10 + (spaceAvailable - spaceRequired) / 2;
        offset += 20;
        
<<<<<<< commits-rmx_100/MinecraftForge/MinecraftForge/6fd3c12a4a15a5cf38c421a94576a5cacd3fb7c1/GuiModItemsMissing-096c3e9.java
this.func_73732_a(this.field_146289_q, String.format("There are %d unassigned blocks and items in this save", missingItems.size()), this.field_146294_l / 2, offset, 0xFFFFFF);offset += 10;offset += 20;this.func_73732_a(this.field_146289_q, "Missing Blocks/Items:", this.field_146294_l / 2, offset, 0xFFFFFF);offset += 10;Iterator<String> it = missingItems.iterator();
=======
offset += 10;this.drawCenteredString(this.fontRendererObj, String.format("There are %d unassigned blocks and items in this save", missingItems.size()), this.width / 2, offset, 0xFFFFFF);offset += 10;this.drawCenteredString(this.fontRendererObj, "You will not be able to load until they are present again", this.width / 2, offset, 0xFFFFFF);
>>>>>>> commits-rmx_100/MinecraftForge/MinecraftForge/58a4095f56930cb27f7a06db5409ca7d570761cd/GuiModItemsMissing-4335bfc.java

        while (it.hasNext()) {
            String item = it.next();
            this.func_73732_a(this.field_146289_q, item, this.field_146294_l / 2, offset, 0xFFFFFF);
            offset += 10;
            if (offset >= spaceAvailable)
                break;
        }
        if (it.hasNext()) {
            this.func_73732_a(this.field_146289_q, "...", this.field_146294_l / 2, offset, 0xFFFFFF);
        }
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
