package me.exejar.duelsniffer.statapi;

import me.exejar.duelsniffer.utils.ChatColor;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public class HPlayer {

    private HypixelBase game;

    public HPlayer(HypixelBase game) {
        this.game = game;
    }

    public String getPlayerUUID() {
        return game.getPlayerUUID();
    }

    public String getNameStats() {
        return game.getNameStats();
    }

    public String getFormattedStatsString() {
        return game.getFormattedStatsString();
    }

    public HashMap<String,Integer> getFormattedStatColorMap() {
        return game.getFormattedStatColorMap();
    }

    public EntityPlayer getPlayerEntity() {
        return game.getPlayerEntity();
    }

    public ChatColor getRankColor() {
        return game.getRankColor();
    }

    public String getPlayerName() {
        return game.getPlayerName();
    }

    public HypixelBase getGame() {
        return game;
    }

}
