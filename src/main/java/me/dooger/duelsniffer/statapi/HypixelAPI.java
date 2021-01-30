package me.dooger.duelsniffer.statapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.dooger.duelsniffer.config.ModConfig;
import me.dooger.duelsniffer.statapi.exception.ApiRequestException;
import me.dooger.duelsniffer.statapi.exception.InvalidKeyException;
import me.dooger.duelsniffer.statapi.exception.PlayerNullException;
import me.dooger.duelsniffer.utils.ChatColor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HypixelAPI {

    private final String key = ModConfig.getInstance().getApiKey();
    protected int rankColor;

    public JsonObject setGameData(String uuid, String gameType) throws InvalidKeyException, PlayerNullException, ApiRequestException {
        JsonObject obj = new JsonObject();
        if (key == null) {
            throw new InvalidKeyException();
        } else {
            String requestURL = String.format("https://api.hypixel.net/player?key=%s&uuid=%s", key, uuid.replace("-", ""));
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(requestURL);
                JsonParser parser = new JsonParser();

                obj = parser.parse(new InputStreamReader(client.execute(request).getEntity().getContent(), StandardCharsets.UTF_8)).getAsJsonObject();
                System.out.println("[DUELSNIFFER] Stat Checking: " + uuid);

                if (obj.get("player") == null) {
                    if (obj.get("cause").getAsString().equals("Invalid API key!")) throw new InvalidKeyException();
                }
                else if (obj.get("player").toString().equalsIgnoreCase("null"))
                    throw new PlayerNullException();
                else if (obj.get("success").getAsString().equals("false"))
                    throw new ApiRequestException();

            } catch (IOException ex) {
                System.err.println("[DUELSNIFFER] setGameData: " + ex.getStackTrace().toString());
            }

            JsonObject player = obj.get("player").getAsJsonObject();
            rankColor = getRankColor(player);
            JsonObject stats = player.get("stats").getAsJsonObject();
            obj = stats.get(gameType).getAsJsonObject();

            return obj;
        }
    }

    private int getRankColor(JsonObject player) {
        String staff, rank = "", mvpPlusPlus;
        int color = ChatColor.DARK_GRAY.getRGB();
        try {
            staff = player.get("rank").getAsString();
        } catch (NullPointerException ignored) {
            staff = "NOT STAFF";
        }
        try {
            mvpPlusPlus = player.get("monthlyPackageRank").getAsString();
        } catch (Exception e) {
            mvpPlusPlus = "NEVER BROUGHT";
        }
        try {
            rank = player.get("newPackageRank").getAsString();
        } catch (NullPointerException e) {
            color = ChatColor.GRAY.getRGB();
        }
        if (mvpPlusPlus.equalsIgnoreCase("SUPERSTAR")) {
            color = ChatColor.GOLD.getRGB();
        } else if (!mvpPlusPlus.equalsIgnoreCase("SUPERSTAR")) {
            if (rank.equalsIgnoreCase("MVP_PLUS")) {
                color = ChatColor.AQUA.getRGB();
            } else if (rank.equalsIgnoreCase("MVP")) {
                color = ChatColor.AQUA.getRGB();
            } else if (rank.equalsIgnoreCase("VIP_PLUS")) {
                color = ChatColor.GREEN.getRGB();
            } else if (rank.equalsIgnoreCase("VIP")) {
                color = ChatColor.GREEN.getRGB();
            }
        }
        try {
            if (staff.equalsIgnoreCase("HELPER")) {
                color = ChatColor.BLUE.getRGB();
            } else if (staff.equalsIgnoreCase("MODERATOR")) {
                color = ChatColor.DARK_GREEN.getRGB();
            } else if (staff.equalsIgnoreCase("ADMIN")) {
                color = ChatColor.RED.getRGB();
            } else if (staff.equalsIgnoreCase("YOUTUBER")) {
                color = ChatColor.RED.getRGB();
            }
        } catch (Exception ignored) {
        }
        return color;
    }

}
