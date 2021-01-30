package me.dooger.duelsniffer.statapi.duels;

import com.google.gson.JsonObject;
import me.dooger.duelsniffer.statapi.exception.ApiRequestException;
import me.dooger.duelsniffer.statapi.exception.InvalidKeyException;
import me.dooger.duelsniffer.statapi.exception.PlayerNullException;
import me.dooger.duelsniffer.utils.ChatUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Duels extends DuelsUtils {
    private boolean isNicked;
    private JsonObject duelsJson;
    private int gamesPlayed, kills, deaths, wins, losses, meleeSwings, meleeHits, currWs, bestWs;
    private DuelsModes mode;

    public Duels(String playerName, String playerUUID, DuelsModes mode) {
        super(playerName, playerUUID, "Duels");
        this.mode = mode;
        setData();
        setStats();
    }

    public void setData() {
        isNicked = false;
        JsonObject obj = null;
        boolean isFunctional = false;
        try {
            obj = setGameData(getPlayerUUID(), "Duels");
            isFunctional = true;
        } catch (ApiRequestException ignored) {
        } catch (PlayerNullException ex) {
            isNicked = true;
        } catch (InvalidKeyException ex) {
            ChatUtils.sendMessage("Invalid API Key!");
        }

        try {
            if (!isNicked && isFunctional) {
                this.duelsJson = obj;
            }
        } catch (NullPointerException ex) {
            if (!isNicked) {
                System.err.println("[DUELSNIFFER] Maybe they have never played Duels before");
            }

            System.err.println("[DUELSNIFFER] " + ex.getStackTrace().toString());
        }
    }

    public void setStats() {
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
        return String.format("[%s] %s - BWS: %s, WLR: %s, KDR: %s, Aim: %s", getCurrentWs(), getPlayerName(), getBestWs(), getWLR(this), getKDR(this), getMeleeAim(this));
    }

    @Override
    public HashMap<String,Integer> getFormattedStatColorMap() {
        LinkedHashMap<String,Integer> list = new LinkedHashMap<>();
        int current = getCurrentWs();
        int best = getBestWs();
        double wlr = getWLR(this);
        double kdr = getKDR(this);
        double aim = getMeleeAim(this);

        list.put(Integer.toString(current), currentWSColor(current));
        list.put(Integer.toString(best), bestWSColor(best));
        list.put(Double.toString(wlr), wlrColor(wlr));
        list.put(Double.toString(kdr), kdrColor(kdr));
        list.put(Double.toString(aim), mAimColorDouble(aim));

        return list;
    }

}
