package me.dooger.duelsniffer.hud;

import me.dooger.duelsniffer.statapi.HPlayer;
import me.dooger.duelsniffer.utils.ChatColor;
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

    public StatHud() {
        hPlayers = new HashMap<>();
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

        int height = (hPlayers.size() * (fontRenderer.FONT_HEIGHT + 3)) + fontRenderer.FONT_HEIGHT + 10;
        drawRect(0, 0, 350, height + 3, new Color(15, 15, 15, 100).getRGB());

        fontRenderer.drawString("NAME", 5, 5, ChatColor.RED.getRGB());
        fontRenderer.drawString("CWS", 105, 5, ChatColor.RED.getRGB());
        fontRenderer.drawString("BWS", 155, 5, ChatColor.RED.getRGB());
        fontRenderer.drawString("WLR", 205, 5, ChatColor.RED.getRGB());
        fontRenderer.drawString("KDR", 255, 5, ChatColor.RED.getRGB());
        fontRenderer.drawString("AIM", 305, 5, ChatColor.RED.getRGB());

        int spacer = fontRenderer.FONT_HEIGHT + 10;
        for (Map.Entry<String,HPlayer> entry : hPlayers.entrySet()) {
            drawPlayerStats(entry.getValue(), fontRenderer, spacer);
            spacer += fontRenderer.FONT_HEIGHT + 3;
        }


    }

    private void drawPlayerStats(HPlayer player, FontRenderer font, int y) {
        int spacer = 5;
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

    public HashMap<String,HPlayer> getHPlayers() {
        return hPlayers;
    }

    public void removeHPlayer(String name) {
        hPlayers.remove(name);
    }

    public void addHPlayer(String name, HPlayer hPlayer) {
        hPlayers.put(name, hPlayer);
        System.out.println(name + " " + (hPlayer == null));
        System.out.println(hPlayers.size());
    }

    public void clearHPlayerList() {
        hPlayers.clear();
    }

}
