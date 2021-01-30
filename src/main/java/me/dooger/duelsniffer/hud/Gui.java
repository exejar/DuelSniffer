package me.dooger.duelsniffer.hud;

import me.dooger.duelsniffer.Main;
import me.dooger.duelsniffer.config.ModConfig;
import me.dooger.duelsniffer.utils.ChatColor;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class Gui extends GuiScreen {
    private boolean dragging;
    private int lastMouseX;
    private int lastMouseY;
    private int lastAddX;
    private int lastAddY;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        final int centerX = width/2;
        final int centerY = height/2;

        final int minX = hud.getMinX();
        final int minY = hud.getMinY();
        final int maxX = hud.getMaxX();
        final int maxY = hud.getMaxY();

        drawRect(minX, minY, maxX, maxY, new Color(15, 15, 15, 100).getRGB());

        fontRendererObj.drawString("NAME", minX + 5, minY + 5, ChatColor.RED.getRGB());
        fontRendererObj.drawString("CWS", minX + 105, minY + 5, ChatColor.RED.getRGB());
        fontRendererObj.drawString("BWS", minX + 155, minY + 5, ChatColor.RED.getRGB());
        fontRendererObj.drawString("WLR", minX + 205, minY + 5, ChatColor.RED.getRGB());
        fontRendererObj.drawString("KDR", minX + 255, minY + 5, ChatColor.RED.getRGB());
        fontRendererObj.drawString("AIM", minX + 305, minY + 5, ChatColor.RED.getRGB());
    }

    @Override
    public void initGui() {
        buttonList.clear();
    }

    @Override
    public void actionPerformed(GuiButton guiButton) {

    }

    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            this.dragging = false;
        }
    }

    protected void mouseClickMove(final int mouseX, final int mouseY, final int lastButtonClicked, final long timeSinceMouseclick) {
        if (!this.dragging && mouseX > hud.getMinX() && mouseX < hud.getMaxX() && mouseY > hud.getMinY() && mouseY < hud.getMaxY()) {
            this.dragging = true;
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.lastAddX = hud.getAddX();
            this.lastAddY = hud.getAddY();
        } else if (this.dragging) {
            hud.setAddX(this.lastAddX + (mouseX - this.lastMouseX));
            hud.setAddY(this.lastAddY + (mouseY - this.lastMouseY));
            if (hud.getHPlayers().isEmpty()) {
                hud.updatePos();
            }
        }
    }

    @Override
    public void onGuiClosed() {
        config.save();
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }

    private ModConfig config = ModConfig.getInstance();
    private StatHud hud = Main.getInstance().statHud;

}
