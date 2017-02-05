package net.spellcraftgaming.rpghud.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementBarred;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementFoodDefault extends HudElementBarred {

	public HudElementFoodDefault() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}
	
	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int stamina = this.mc.thePlayer.getFoodStats().getFoodLevel();
		ItemStack itemMain = this.mc.thePlayer.getHeldItemMainhand();
		ItemStack itemSec = this.mc.thePlayer.getHeldItemOffhand();
		if ((itemMain != null && itemMain.getItem() instanceof ItemFood) || (itemSec != null && itemSec.getItem() instanceof ItemFood) && this.mc.thePlayer.getFoodStats().needFood() && this.settings.show_hunger_preview) {
			float value;
			if(itemMain.getItem() instanceof ItemFood) 
				value = ((ItemFood) itemMain.getItem()).getHealAmount(itemMain);
			else value = ((ItemFood) itemSec.getItem()).getHealAmount(itemSec);
			int bonusHunger = (int) (value + stamina);
			if (bonusHunger > 20)
				bonusHunger = 20;
			int colorPreview = offsetColor(this.settings.color_stamina, OFFSET_PREVIEW);
			drawCustomBar(49, 26, 110, 12, bonusHunger / 20.0D * 100.0D, -1, -1, colorPreview, offsetColorPercent(colorPreview, OFFSET_PERCENT));
		}
		if (this.mc.thePlayer.isPotionActive(MobEffects.HUNGER)) {
			drawCustomBar(49, 26, 110, 12, stamina / 20.0D * 100.0D, -1, -1, this.settings.color_hunger, offsetColorPercent(this.settings.color_hunger, OFFSET_PERCENT));
		} else {
			drawCustomBar(49, 26, 110, 12, stamina / 20.0D * 100.0D, -1, -1, this.settings.color_stamina, offsetColorPercent(this.settings.color_stamina, OFFSET_PERCENT));
		}
		String staminaString = stamina + "/" + "20";
		if (this.settings.show_numbers_stamina)
			gui.drawCenteredString(this.mc.fontRendererObj, staminaString, 49 + 55, 28, -1);
	}

}
