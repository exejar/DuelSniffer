package me.exejar.duelsniffer.statapi.duels;

import com.google.gson.JsonObject;
import me.exejar.duelsniffer.statapi.exception.ApiRequestException;
import me.exejar.duelsniffer.statapi.exception.InvalidKeyException;
import me.exejar.duelsniffer.statapi.exception.PlayerNullException;
import me.exejar.duelsniffer.utils.ChatColor;
import me.exejar.duelsniffer.utils.ChatUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Duels extends DuelsUtils {
    private JsonObject duelsJson;
    private int gamesPlayed = 0, kills = 0, deaths = 0, wins = 0, losses = 0, meleeSwings = 0, meleeHits = 0, currWs = 0, bestWs = 0;
    private DuelsModes mode;

    public Duels(String playerName, String playerUUID, DuelsModes mode) {
        super(playerName, playerUUID, "Duels");
        this.mode = mode;
        setData();
        if (!this.isNicked) setStats(hasPlayed);
    }

    public void setData() {
        this.isNicked = false;
        this.hasPlayed = false;
        JsonObject obj = null;
        boolean isFunctional = false;
        try {
            obj = setGameData(getPlayerUUID(), "Duels");
            isFunctional = true;
        } catch (ApiRequestException ignored) {
        } catch (PlayerNullException ex) {
            this.isNicked = true;
        } catch (InvalidKeyException ex) {
            ChatUtils.sendMessage("Invalid API Key!");
        }

        try {
            if (!this.isNicked && isFunctional) {
                this.hasPlayed = true;
                this.duelsJson = obj;
            }
        } catch (NullPointerException ex) {
            if (!this.isNicked) {
                System.err.println("[DUELSNIFFER] Maybe they have never played Duels before");
            }

            System.err.println("[DUELSNIFFER] " + ex.getStackTrace().toString());
        }
    }

    public void setStats(boolean hasPlayed) {
        if (hasPlayed) {
            setGamesPlayed();
            setKills(mode);
            setDeaths(mode);
            setWins(mode);
            setLosses(mode);
            setMeleeSwings(mode);
            setMeleeHits(mode);
            setCurrentWinstreak(mode);
            setBestWinstreak(mode);
        }
    }

    private void setGamesPlayed() {
        this.gamesPlayed = Integer.parseInt(duelsJson.get("games_played_duels").toString());
    }

    private void setKills(DuelsModes mode) {
        try {
            this.kills = mode == DuelsModes.ALL ? Integer.parseInt(duelsJson.get("kills").toString()) : Integer.parseInt(duelsJson.get(mode + "duel_kills").toString());
        } catch (Exception ex) {
            this.kills = 0;
        }
    }

    private void setDeaths(DuelsModes mode) {
        try {
            this.deaths = mode == DuelsModes.ALL ? Integer.parseInt(duelsJson.get("deaths").toString()) : Integer.parseInt(duelsJson.get(mode + "duel_deaths").toString());
        } catch (Exception ex) {
            this.deaths = 0;
        }
    }

    private void setWins(DuelsModes mode) {
        try {
            this.wins = mode == DuelsModes.ALL ? Integer.parseInt(duelsJson.get("wins").toString()) : Integer.parseInt(duelsJson.get(mode + "duel_wins").toString());
        } catch (Exception ex) {
            this.wins = 0;
        }
    }

    private void setLosses(DuelsModes mode) {
        try {
            this.losses = mode == DuelsModes.ALL ? Integer.parseInt(duelsJson.get("losses").toString()) : Integer.parseInt(duelsJson.get(mode + "duel_losses").toString());
        } catch (Exception ex) {
            this.losses = 0;
        }
    }

    private void setMeleeSwings(DuelsModes mode) {
        try {
            this.meleeSwings = mode == DuelsModes.ALL ? Integer.parseInt(duelsJson.get("melee_swings").toString()) : Integer.parseInt(duelsJson.get(mode + "duel_melee_swings").toString());
        } catch (Exception ex) {
            this.meleeSwings = 0;
        }
    }

    private void setMeleeHits(DuelsModes mode) {
        try {
            this.meleeHits = mode == DuelsModes.ALL ? Integer.parseInt(duelsJson.get("melee_hits").toString()) : Integer.parseInt(duelsJson.get(mode + "duel_melee_hits").toString());
        } catch (Exception ex) {
            this.meleeHits = 0;
        }
    }

    private void setCurrentWinstreak(DuelsModes mode) {
        try {
            this.currWs = mode == DuelsModes.ALL ? Integer.parseInt(duelsJson.get("current_winstreak").toString()) : Integer.parseInt(duelsJson.get(String.format("current_%swinstreak", mode)).toString());
        } catch (Exception ex) {
            this.currWs = 0;
        }
    }

    private void setBestWinstreak(DuelsModes mode) {
        try {
            this.bestWs = mode == DuelsModes.ALL ? Integer.parseInt(duelsJson.get("best_overall_winstreak").toString()) : Integer.parseInt(duelsJson.get(String.format("best_%swinstreak", mode)).toString());
        } catch (Exception ex) {
            this.bestWs = 0;
        }
    }


    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public int getKills() {
        return this.kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public int getWins() {
        return this.wins;
    }

    public int getLosses() {
        return this.losses;
    }

    public int getMeleeSwings() {
        return this.meleeSwings;
    }

    public int getMeleeHits() {
        return this.meleeHits;
    }

    public int getCurrentWs() {
        return this.currWs;
    }

    public int getBestWs() {
        return this.bestWs;
    }

    @Override
    public String getNameStats() {
        return String.format("[%s] %s", getBestWs(), getPlayerName());
    }

    @Override
    public String getFormattedStatsString() {

        if (!this.isNicked) {
            int current = getCurrentWs();
            int best = getBestWs();
            double wlr = getWLR(this);
            double kdr = getKDR(this);
            double aim = getMeleeAim(this);

            String formatString = "%s%s - CWS: %s, BWS: %s, WLR: %s, KDR: %s, AIM: %s";

            if (this.rank.getRankColor() != ChatColor.GRAY) {
                formatString = "%s %s - CWS: %s, BWS: %s, WLR: %s, KDR: %s, AIM: %s";
            }

            return String.format(formatString,
                    this.rank.getFormattedRank(),
                    getPlayerName() + ChatColor.RESET,
                    currentWSColor(current) + Integer.toString(current) + ChatColor.RESET,
                    bestWSColor(best) + Integer.toString(best) + ChatColor.RESET,
                    wlrColor(wlr) + Double.toString(wlr) + ChatColor.RESET,
                    kdrColor(kdr) + Double.toString(kdr) + ChatColor.RESET,
                    meleeAimColor(aim) + Double.toString(aim));
        } else {
            return ChatColor.RED + "[NICKED] " + getPlayerName();
        }
    }

    @Override
    public HashMap<String,Integer> getFormattedStatColorMap() {
        LinkedHashMap<String,Integer> list = new LinkedHashMap<>();
        if (!this.isNicked) {
            int current = getCurrentWs();
            int best = getBestWs();
            double wlr = getWLR(this);
            double kdr = getKDR(this);
            double aim = getMeleeAim(this);

            list.put(Integer.toString(current), currentWSColor(current).getRGB());
            list.put(Integer.toString(best), bestWSColor(best).getRGB());
            list.put(Double.toString(wlr), wlrColor(wlr).getRGB());
            list.put(Double.toString(kdr), kdrColor(kdr).getRGB());
            list.put(Double.toString(aim), meleeAimColor(aim).getRGB());
        } else {
            list.put("NICKED", ChatColor.RED.getRGB());
            list.put("[NICKED]", ChatColor.RED.getRGB());
        }

        return list;
    }

}
