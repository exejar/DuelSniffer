package me.exejar.duelsniffer.hud;

import me.exejar.duelsniffer.config.ModConfig;
import me.exejar.duelsniffer.statapi.HPlayer;
import me.exejar.duelsniffer.utils.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StatHud extends RenderUtils {
    private HashMap<String,HPlayer> hPlayers;
    private Minecraft mc = Minecraft.getMinecraft();

    private int addX, addY, minX, maxX, minY, maxY;

    public StatHud() {
        hPlayers = new HashMap<>();

        ModConfig config = ModConfig.getInstance();

        if (config.getHudX() == null || config.getHudX().isEmpty()) {
            addX = 0;
        } else {
            addX = Integer.parseInt(config.getHudX());
        }

        if (config.getHudY() == null || config.getHudY().isEmpty()) {
            addY = 0;
        } else {
            addY = Integer.parseInt(config.getHudY());
        }

    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            if (!hPlayers.isEmpty()) {
                renderHudStats();
            }
        }
    }

    private void renderHudStats() {
        RenderManager renderManager = mc.getRenderManager();
        FontRenderer fontRenderer = renderManager.getFontRenderer();

        final int height = (hPlayers.size() * (fontRenderer.FONT_HEIGHT + 3)) + fontRenderer.FONT_HEIGHT + 10;
        final int minX = addX;
        final int minY = addY;
        final int maxX = addX + 350;
        final int maxY = addY + height;

        drawRect(minX, minY, maxX, maxY, new Color(15, 15, 15, 100).getRGB());

        fontRenderer.drawString("NAME", minX + 5, minY + 5, ChatColor.RED.getRGB());
        fontRenderer.drawString("CWS", minX + 105, minY + 5, ChatColor.RED.getRGB());
        fontRenderer.drawString("BWS", minX + 155, minY + 5, ChatColor.RED.getRGB());
        fontRenderer.drawString("WLR", minX + 205, minY + 5, ChatColor.RED.getRGB());
        fontRenderer.drawString("KDR", minX + 255, minY + 5, ChatColor.RED.getRGB());
        fontRenderer.drawString("AIM", minX + 305, minY + 5, ChatColor.RED.getRGB());

        int spacer = minY + fontRenderer.FONT_HEIGHT + 10;
        for (Map.Entry<String,HPlayer> entry : hPlayers.entrySet()) {
            drawPlayerStats(entry.getValue(), fontRenderer, minX, spacer);
            spacer += fontRenderer.FONT_HEIGHT + 3;
        }

        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    private void drawPlayerStats(HPlayer player, FontRenderer font, int x, int y) {
        int spacer = x + 5;
        if (!player.getGame().isNicked) {
            font.drawString(player.getPlayerName(), spacer, y, player.getRankColor().getRGB(), true);
            spacer += 100;

            for (Map.Entry<String,Integer> stat : player.getFormattedStatColorMap().entrySet()) {
                font.drawString(stat.getKey(), spacer, y, stat.getValue(), true);
                spacer += 50;
            }
        } else {
            font.drawString("[NICKED] " + player.getPlayerName(), spacer, y, ChatColor.RED.getRGB(), true);
        }
    }

    public void updatePos() {
        RenderManager renderManager = mc.getRenderManager();
        FontRenderer fontRenderer = renderManager.getFontRenderer();

        final int height = fontRenderer.FONT_HEIGHT + 10;

        this.minX = addX;
        this.minY = addY;
        this.maxX = addX + 350;
        this.maxY = addY + height;
    }

    public HashMap<String,HPlayer> getHPlayers() {
        return hPlayers;
    }

    public void removeHPlayer(String name) {
        hPlayers.remove(name);
    }

    public void addHPlayer(String name, HPlayer hPlayer) {
        hPlayers.put(name, hPlayer);
    }

    public void clearHPlayerList() {
        hPlayers.clear();
    }

    public int getAddX() { return this.addX; }

    public int getMinX() { return this.minX; }

    public int getMaxX() { return this.maxX; }

    public int getAddY() { return this.addY; }

    public int getMinY() { return this.minY; }

    public int getMaxY() { return this.maxY; }


    public void setAddX(int addX) { this.addX = addX; }

    public void setAddY(int addY) { this.addY = addY; }

}
