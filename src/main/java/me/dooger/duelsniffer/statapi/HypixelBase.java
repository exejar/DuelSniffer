package me.dooger.duelsniffer.statapi;

import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public abstract class HypixelBase extends HypixelAPI {
    private String playerName, playerUUID, gameType;
    private EntityPlayer player;

    public HypixelBase(String playerName, String playerUUID, String gameType) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.gameType = gameType;
    }

    public HypixelBase(EntityPlayer player, String gameType) {
        this.playerName = player.getName();
        this.playerUUID = player.getUniqueID().toString();
        this.gameType = gameType;
    }

    public abstract String getNameStats();

    public abstract String getFormattedStatsString();

    public abstract HashMap<String,Integer> getFormattedStatColorMap();

    public String getPlayerName() { return this.playerName; }

    public String getPlayerUUID() { return this.playerUUID; }

    public EntityPlayer getPlayerEntity() { return this.player; }

    public int getRankColor() { return this.rankColor; }

}
