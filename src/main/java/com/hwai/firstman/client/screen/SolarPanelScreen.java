package com.hwai.firstman.client.screen;

import com.hwai.firstman.Firstman;
import com.hwai.firstman.container.SolarPanelContainer;
import com.hwai.firstman.init.ItemInit;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SolarPanelScreen extends AbstractContainerScreen<SolarPanelContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Firstman.MODID,
            "textures/gui/solar_panel_screen.png");

    public SolarPanelScreen(SolarPanelContainer container, Inventory playerInv, Component title) {
        super(container, playerInv, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 97;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);

    }

    @Override
    protected void renderBg(GuiGraphics graphics, float mouseX, int mouseY, int partialTicks) {
        renderBackground(graphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.renderFakeItem(ItemInit.SOLAR_PANEL.get().getDefaultInstance(), this.imageWidth / 2 - 8, 20);
        graphics.drawString(Minecraft.getInstance().font, "Power (dE/dt): " + getMenu().getData().get(0), this.imageWidth / 3, 55, 0xffffff);
        graphics.drawString(Minecraft.getInstance().font, "SunLight: " + getMenu().getData().get(1), this.imageWidth / 3, 65, 0xffffff);
    }

}
