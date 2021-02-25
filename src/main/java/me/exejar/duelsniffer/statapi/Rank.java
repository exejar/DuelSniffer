package me.exejar.duelsniffer.statapi;

import me.exejar.duelsniffer.utils.ChatColor;

public class Rank {
    private String formattedRank;
    private ChatColor rankColor;
    private ChatColor plusColor;

    public Rank(String formattedRank, ChatColor rankColor, ChatColor plusColor) {
        this.formattedRank = formattedRank;
        this.rankColor = rankColor;
        this.plusColor = plusColor;
    }

    public String getFormattedRank() { return this.formattedRank; }

    public ChatColor getRankColor() { return this.rankColor; }

    public ChatColor getPlusColor() { return this.plusColor; }

}
