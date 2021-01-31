package me.dooger.duelsniffer.hud;

import me.dooger.duelsniffer.Main;
import me.dooger.duelsniffer.config.ModConfig;
import me.dooger.duelsniffer.statapi.duels.DuelsModes;
import me.dooger.duelsniffer.utils.ChatColor;
import me.dooger.duelsniffer.utils.MathUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.apache.commons.lang3.EnumUtils;

import java.awt.*;

public class Gui extends GuiScreen {
    private boolean dragging;
    private int lastMouseX;
    private int lastMouseY;
    private int lastAddX;
    private int lastAddY;
    private String buttonName = "Click Me to Cycle Mode!";
    private final int CHANGEMODEID = 0;
    private DuelsModes duelMode;

    GuiButton changeMode;

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

        drawString(fontRendererObj, duelMode.toGameString(), centerX - (fontRendererObj.getStringWidth(duelMode.toGameString()) / 2), centerY + 30, 0xFFFFFF);
        changeMode.drawButton(mc, mouseX, mouseY);
    }

    @Override
    public void initGui() {
        if (!EnumUtils.isValidEnum(DuelsModes.class, config.getHudMode())) {
            duelMode = DuelsModes.ALL;
        } else {
            duelMode = DuelsModes.valueOf(config.getHudMode());
        }

        buttonList.clear();

        final int centerX = width/2;
        final int centerY = height/2;

        buttonList.add(changeMode = new GuiButton(CHANGEMODEID, centerX - 75, centerY, 150, 20, buttonName));
    }

    @Override
    public void actionPerformed(GuiButton guiButton) {
        if (guiButton.id == CHANGEMODEID) {
            int index = MathUtils.findIndexInArray(DuelsModes.values(), duelMode);
            if (index + 1 == DuelsModes.values().length) {
                index = -1;
            }

            duelMode = DuelsModes.values()[index + 1];
        }
    }

    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            this.dragging = false;
            if (hud.getMinX() < 0) {
                hud.setAddX(0);
            }
            if (hud.getMinY() < 0) {
                hud.setAddY(0);
            }
            if (hud.getMaxX() > this.width) {
                hud.setAddX(this.width - (hud.getMaxX() - hud.getMinX()));
            }
            if (hud.getMaxY() > this.height) {
                hud.setAddY(this.height - (hud.getMaxY() - hud.getMinY()));
            }

            hud.updatePos();
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
        config.setHudMode(duelMode.name());
        config.setHudX(hud.getAddX());
        config.setHudY(hud.getAddY());
        config.save();
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }

    private ModConfig config = ModConfig.getInstance();
    private StatHud hud = Main.getInstance().statHud;

}
