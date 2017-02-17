package net.spellcraftgaming.rpghud.gui.hud.element.hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.spellcraftgaming.rpghud.gui.GuiIngameRPGHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;

public class HudElementHotbarHotbar extends HudElement {

	protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

	public HudElementHotbarHotbar() {
		super(HudElementType.HOTBAR, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		if (this.mc.playerController.isSpectator()) {
			((GuiIngameRPGHud) gui).getSpectatorGui().renderTooltip(res, partialTicks);
		}
		if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
			EntityPlayer entityplayer = (EntityPlayer) this.mc.getRenderViewEntity();
			ItemStack itemstack = entityplayer.getHeldItemOffhand();
			EnumHandSide enumhandside = entityplayer.getPrimaryHand().opposite();
			int i = res.getScaledWidth() / 2;
			float f = zLevel;
			zLevel = -90.0F;

			gui.drawTexturedModalRect(49, res.getScaledHeight() - 47, 0, 0, 182, 22);
			gui.drawTexturedModalRect(48 + 1 + entityplayer.inventory.currentItem * 20, res.getScaledHeight() - 47 - 1, 0, 22, 24, 22);

			gui.drawTexturedModalRect(49 + 181, res.getScaledHeight() - 47, 60, 23, 22, 22);

			zLevel = f;
			GlStateManager.enableRescaleNormal();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderHelper.enableGUIStandardItemLighting();

			for (int l = 0; l < 9; ++l) {
				int i1 = 50 + l * 20 + 2;
				int j1 = res.getScaledHeight() - 16 - 19 - 9;
				this.renderHotbarItem(i1, j1, partialTicks, entityplayer, entityplayer.inventory.mainInventory[l]);
			}

			int l1 = res.getScaledHeight() - 47 + 3;
			this.renderHotbarItem(49 + 184, l1, partialTicks, entityplayer, itemstack);

			if (this.mc.gameSettings.attackIndicator == 2) {
				float f1 = this.mc.thePlayer.getCooledAttackStrength(0.0F);

				if (f1 < 1.0F) {
					int i2 = res.getScaledHeight() - 20;
					int j2 = i + 91 + 6;

					if (enumhandside == EnumHandSide.RIGHT) {
						j2 = i - 91 - 22;
					}

					this.mc.getTextureManager().bindTexture(Gui.ICONS);
					int k1 = (int) (f1 * 19.0F);
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					gui.drawTexturedModalRect(j2, i2 - 9, 0, 94, 18, 18);
					gui.drawTexturedModalRect(j2, i2 - 9 + 18 - k1, 18, 112 - k1, 18, k1);
				}
			}

			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
		}
	}

	/**
	 * Renders an item on the screen
	 * 
	 * @param xPos
	 *            the x position on the screen
	 * @param yPos
	 *            the y position on the screen
	 * @param partialTicks
	 *            the partial ticks (used for animation)
	 * @param player
	 *            the player who should get the item rendered
	 * @param item
	 *            the item (via ItemStack)
	 */
	protected void renderHotbarItem(int xPos, int yPos, float partialTicks, EntityPlayer player, ItemStack item) {
		if (item != null) {
			float f = item.animationsToGo - partialTicks;

			if (f > 0.0F) {
				GlStateManager.pushMatrix();
				float f1 = 1.0F + f / 5.0F;
				GlStateManager.translate(xPos + 8, yPos + 12, 0.0F);
				GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
				GlStateManager.translate((-(xPos + 8)), (-(yPos + 12)), 0.0F);
			}

			this.mc.getRenderItem().renderItemAndEffectIntoGUI(player, item, xPos, yPos);

			if (f > 0.0F) {
				GlStateManager.popMatrix();
			}

			this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, item, xPos, yPos);
		}
	}

}