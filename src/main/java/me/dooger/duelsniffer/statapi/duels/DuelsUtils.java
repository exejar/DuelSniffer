package me.dooger.duelsniffer.statapi.duels;

import me.dooger.duelsniffer.statapi.HypixelBase;
import me.dooger.duelsniffer.utils.ChatColor;
import me.dooger.duelsniffer.utils.MathUtils;
import net.minecraft.entity.player.EntityPlayer;

public abstract class DuelsUtils extends HypixelBase {

    public DuelsUtils(String player, String playerUUID, String gameType) {
        super(player, playerUUID, gameType);
    }

    public DuelsUtils(EntityPlayer player, String gameType) {
        super(player, gameType);
    }

    public double getKDR(Duels d) {
        return MathUtils.formatDouble(d.getKills(), d.getDeaths());
    }

    public double getWLR(Duels d) {
        return MathUtils.formatDouble(d.getWins(), d.getLosses());
    }

    public double getMeleeAim(Duels d) {
        return MathUtils.formatDouble(d.getMeleeHits(), d.getMeleeSwings());
    }

    public ChatColor kdrColor(double kdr) {
        ChatColor rgb;
        if (kdr < 5) {
            rgb = ChatColor.GRAY;
        } else if (kdr < 10) {
            rgb = ChatColor.WHITE;
        } else if (kdr < 20) {
            rgb = ChatColor.GOLD;
        } else if (kdr < 25) {
            rgb = ChatColor.DARK_GREEN;
        } else if (kdr < 30) {
            rgb = ChatColor.RED;
        } else if (kdr < 50) {
            rgb = ChatColor.DARK_RED;
        } else if (kdr < 100) {
            rgb = ChatColor.LIGHT_PURPLE;
        } else {
            rgb = ChatColor.DARK_PURPLE;
        }
        return rgb;
    }

    public ChatColor wlrColor(double wlr) {
        ChatColor color;
        if (wlr < 5) {
            color = ChatColor.GRAY;
        } else if (wlr < 10) {
            color = ChatColor.WHITE;
        } else if (wlr < 20) {
            color = ChatColor.GOLD;
        } else if (wlr < 25) {
            color = ChatColor.DARK_GREEN;
        } else if (wlr < 30) {
            color = ChatColor.RED;
        } else if (wlr < 50) {
            color = ChatColor.DARK_RED;
        } else if (wlr < 100) {
            color = ChatColor.LIGHT_PURPLE;
        } else {
            color = ChatColor.DARK_PURPLE;
        }
        return color;
    }

    public ChatColor bestWSColor(int bestws) {
        ChatColor color;
        if (bestws < 50) {
            color = ChatColor.GRAY;
        } else if (bestws < 75) {
            color = ChatColor.WHITE;
        } else if (bestws < 100) {
            color = ChatColor.GOLD;
        } else if (bestws < 150) {
            color = ChatColor.DARK_GREEN;
        } else if (bestws < 200) {
            color = ChatColor.RED;
        } else if (bestws < 250) {
            color = ChatColor.DARK_RED;
        } else if (bestws < 500) {
            color = ChatColor.LIGHT_PURPLE;
        } else {
            color = ChatColor.DARK_PURPLE;
        }
        return color;
    }

    public ChatColor currentWSColor(int currws) {
        ChatColor color;
        if (currws < 50) {
            color = ChatColor.GRAY;
        } else if (currws < 75) {
            color = ChatColor.WHITE;
        } else if (currws < 100) {
            color = ChatColor.GOLD;
        } else if (currws < 150) {
            color = ChatColor.DARK_GREEN;
        } else if (currws < 200) {
            color = ChatColor.RED;
        } else if (currws < 250) {
            color = ChatColor.DARK_RED;
        } else if (currws < 500) {
            color = ChatColor.LIGHT_PURPLE;
        } else {
            color = ChatColor.DARK_PURPLE;
        }
        return color;
    }

    public ChatColor meleeAimColor(double maim) {
        ChatColor color;
        if (maim < 0.25) {
            color = ChatColor.GRAY;
        } else if (maim < 0.30) {
            color = ChatColor.WHITE;
        } else if (maim < 0.35) {
            color = ChatColor.GOLD;
        } else if (maim < 0.40) {
            color = ChatColor.DARK_GREEN;
        } else if (maim < 0.50) {
            color = ChatColor.RED;
        } else if (maim < 0.75) {
            color = ChatColor.DARK_RED;
        } else if (maim < 0.85) {
            color = ChatColor.LIGHT_PURPLE;
        } else {
            color = ChatColor.DARK_PURPLE;
        }
        return color;
    }

}
