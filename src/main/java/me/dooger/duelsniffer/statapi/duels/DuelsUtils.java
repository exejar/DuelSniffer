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

    public int kdrColor(double kdr) {
        int rgb;
        if (kdr < 5) {
            rgb = ChatColor.GRAY.getRGB();
        } else if (kdr < 10) {
            rgb = ChatColor.WHITE.getRGB();
        } else if (kdr < 20) {
            rgb = ChatColor.GOLD.getRGB();
        } else if (kdr < 25) {
            rgb = ChatColor.DARK_GREEN.getRGB();
        } else if (kdr < 30) {
            rgb = ChatColor.RED.getRGB();
        } else if (kdr < 50) {
            rgb = ChatColor.DARK_RED.getRGB();
        } else if (kdr < 100) {
            rgb = ChatColor.LIGHT_PURPLE.getRGB();
        } else {
            rgb = ChatColor.DARK_PURPLE.getRGB();
        }
        return rgb;
    }

    public int wlrColor(double wlr) {
        int color;
        if (wlr < 5) {
            color = ChatColor.GRAY.getRGB();
        } else if (wlr < 10) {
            color = ChatColor.WHITE.getRGB();
        } else if (wlr < 20) {
            color = ChatColor.GOLD.getRGB();
        } else if (wlr < 25) {
            color = ChatColor.DARK_GREEN.getRGB();
        } else if (wlr < 30) {
            color = ChatColor.RED.getRGB();
        } else if (wlr < 50) {
            color = ChatColor.DARK_RED.getRGB();
        } else if (wlr < 100) {
            color = ChatColor.LIGHT_PURPLE.getRGB();
        } else {
            color = ChatColor.DARK_PURPLE.getRGB();
        }
        return color;
    }

    public int bestWSColor(int bestws) {
        int color;
        if (bestws < 50) {
            color = ChatColor.GRAY.getRGB();
        } else if (bestws < 75) {
            color = ChatColor.WHITE.getRGB();
        } else if (bestws < 100) {
            color = ChatColor.GOLD.getRGB();
        } else if (bestws < 150) {
            color = ChatColor.DARK_GREEN.getRGB();
        } else if (bestws < 200) {
            color = ChatColor.RED.getRGB();
        } else if (bestws < 250) {
            color = ChatColor.DARK_RED.getRGB();
        } else if (bestws < 500) {
            color = ChatColor.LIGHT_PURPLE.getRGB();
        } else {
            color = ChatColor.DARK_PURPLE.getRGB();
        }
        return color;
    }

    public int currentWSColor(int currws) {
        int color;
        if (currws < 50) {
            color = ChatColor.GRAY.getRGB();
        } else if (currws < 75) {
            color = ChatColor.WHITE.getRGB();
        } else if (currws < 100) {
            color = ChatColor.GOLD.getRGB();
        } else if (currws < 150) {
            color = ChatColor.DARK_GREEN.getRGB();
        } else if (currws < 200) {
            color = ChatColor.RED.getRGB();
        } else if (currws < 250) {
            color = ChatColor.DARK_RED.getRGB();
        } else if (currws < 500) {
            color = ChatColor.LIGHT_PURPLE.getRGB();
        } else {
            color = ChatColor.DARK_PURPLE.getRGB();
        }
        return color;
    }

    public int mAimColorDouble(double maim) {
        int color;
        if (maim < 0.25) {
            color = ChatColor.GRAY.getRGB();
        } else if (maim < 0.30) {
            color = ChatColor.WHITE.getRGB();
        } else if (maim < 0.35) {
            color = ChatColor.GOLD.getRGB();
        } else if (maim < 0.40) {
            color = ChatColor.DARK_GREEN.getRGB();
        } else if (maim < 0.50) {
            color = ChatColor.RED.getRGB();
        } else if (maim < 0.75) {
            color = ChatColor.DARK_RED.getRGB();
        } else if (maim < 0.85) {
            color = ChatColor.LIGHT_PURPLE.getRGB();
        } else {
            color = ChatColor.DARK_PURPLE.getRGB();
        }
        return color;
    }

}
