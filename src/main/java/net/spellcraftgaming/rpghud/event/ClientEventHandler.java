package net.spellcraftgaming.rpghud.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.spellcraftgaming.rpghud.gui.GuiSettingsMod;
import net.spellcraftgaming.rpghud.main.ModRPGHud;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @SubscribeEvent
    public void onGuiInit(InitGuiEvent event) {
        if(event.getGui() instanceof GuiMainMenu || event.getGui() instanceof GuiMainMenu) {
            Minecraft mc = Minecraft.getInstance();
            String s = I18n.format("name.rpghud", new Object[0]);
            event.addButton(new GuiButton(142, event.getGui().width - (mc.fontRenderer.getStringWidth(s) + 8), 0, mc.fontRenderer.getStringWidth(s) + 8, 20, s) {
                @Override
                public void onClick(double mouseX, double mouseY) {
                    mc.displayGuiScreen(new GuiSettingsMod(event.getGui(), I18n.format("gui.settings.rpghud", new Object[0])));
                }
            });
        }
    }
    
    @SubscribeEvent
    public void onPlayerCloseContainer(PlayerContainerEvent.Close event) {
        ModRPGHud.renderDetailsAgain[0] = true;
        ModRPGHud.renderDetailsAgain[1] = true;
        ModRPGHud.renderDetailsAgain[2] = true;
    }
}
