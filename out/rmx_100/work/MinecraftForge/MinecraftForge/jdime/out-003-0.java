package cpw.mods.fml.client;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiModItemsMissing extends GuiScreen {
  private List<String> missingItems;

  public GuiModItemsMissing(List<String> items) {
    this.missingItems = items;
  }


  @SuppressWarnings(value = { "unchecked" }) @Override public void func_73866_w_() {
    this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m - 38, I18n.func_135052_a("gui.done")));
  }


  @SuppressWarnings(value = { "unchecked" }) @Override public void initGui() {
    this.buttonList.add(new GuiButton(1, this.width / 2 - 75, this.height - 38, I18n.format("gui.done")));
  }

  @Override protected void actionPerformed(GuiButton p_73875_1_) {
    if (p_73875_1_.enabled && p_73875_1_.id == 1) {
      FMLClientHandler.instance().showGuiScreen(null);
    }
  }


  @Override public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
    this.func_146276_q_();
    int spaceAvailable = this.field_146295_m - 38 - 20;
    int spaceRequired = Math.min(spaceAvailable, 10 + 6 * 10 + missingItems.size());
    int offset = 10 + (spaceAvailable - spaceRequired) / 2;
    this.func_73732_a(this.field_146289_q, "Forge Mod Loader could load this save", this.field_146294_l / 2, offset, 0xFFFFFF);
    offset += 20;
    this.func_73732_a(this.field_146289_q, String.format("There are %d unassigned blocks and items in this save", missingItems.size()), this.field_146294_l / 2, offset, 0xFFFFFF);
    offset += 10;
    this.func_73732_a(this.field_146289_q, "You will not be able to load until they are present again", this.field_146294_l / 2, offset, 0xFFFFFF);
    offset += 20;
    this.func_73732_a(this.field_146289_q, "Missing Blocks/Items:", this.field_146294_l / 2, offset, 0xFFFFFF);
    offset += 10;
    Iterator<String> it = missingItems.iterator();
    while (it.hasNext()) {
      String item = it.next();
      this.func_73732_a(this.field_146289_q, item, this.field_146294_l / 2, offset, 0xFFFFFF);
      offset += 10;
      if (offset >= spaceAvailable) {
        break;
      }
    }
    if (it.hasNext()) {
      this.func_73732_a(this.field_146289_q, "...", this.field_146294_l / 2, offset, 0xFFFFFF);
    }
    super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
  }


  @Override public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
    this.drawDefaultBackground();
    int offset = 85;
    this.drawCenteredString(this.fontRendererObj, "Forge Mod Loader could load this save", this.width / 2, offset, 0xFFFFFF);
    offset += 10;
    this.drawCenteredString(this.fontRendererObj, String.format("There are %d unassigned blocks and items in this save", missingItems.size()), this.width / 2, offset, 0xFFFFFF);
    offset += 10;
    this.drawCenteredString(this.fontRendererObj, "You will not be able to load until they are present again", this.width / 2, offset, 0xFFFFFF);
    super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
  }
}
