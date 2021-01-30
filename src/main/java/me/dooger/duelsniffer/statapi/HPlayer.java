package me.dooger.duelsniffer.statapi;

import me.dooger.duelsniffer.Main;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.List;

public class HPlayer {

    private HypixelBase game;

    public HPlayer(HypixelBase game) {
        this.game = game;
        System.out.println("Adding HPlayer to List");
        Main.getInstance().statHud.addHPlayer(getPlayerName(), this);
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

    public int getRankColor() {
        return game.getRankColor();
    }

    public String getPlayerName() {
        return game.getPlayerName();
    }

    public HypixelBase getGame() {
        return game;
    }

}
