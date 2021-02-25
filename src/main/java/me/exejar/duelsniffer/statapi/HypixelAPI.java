package me.exejar.duelsniffer.statapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.exejar.duelsniffer.config.ModConfig;
import me.exejar.duelsniffer.statapi.exception.ApiRequestException;
import me.exejar.duelsniffer.statapi.exception.InvalidKeyException;
import me.exejar.duelsniffer.statapi.exception.PlayerNullException;
import me.exejar.duelsniffer.utils.ChatColor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HypixelAPI {

    private final String key = ModConfig.getInstance().getApiKey();
    protected Rank rank;

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
            setRankColor(player);
            JsonObject stats = player.get("stats").getAsJsonObject();
            obj = stats.get(gameType).getAsJsonObject();

            return obj;
        }
    }

    public static String getUUID(String name) {
        String uuid = "";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(String.format("https://api.mojang.com/users/profiles/minecraft/%s", name));
            try (InputStream is = client.execute(request).getEntity().getContent()) {
                JsonParser jsonParser = new JsonParser();
                JsonObject object = jsonParser.parse(new InputStreamReader(is, StandardCharsets.UTF_8)).getAsJsonObject();
                uuid = object.get("id").getAsString();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return uuid;
    }

    private void setRankColor(JsonObject player) {
        String staff, rank = "", mvpPlusPlus, plus;
        ChatColor rankColor = ChatColor.GRAY;
        ChatColor plusColor;
        String formattedRank = ChatColor.GRAY + "";

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
            rankColor = ChatColor.GRAY;
        }
        try {
            plus = player.get("rankPlusColor").getAsString();
        } catch (NullPointerException ex) {
            plus = "RED";
        }
        plusColor = ChatColor.valueOf(plus);

        if (mvpPlusPlus.equalsIgnoreCase("SUPERSTAR")) {
            rankColor = ChatColor.GOLD;
            formattedRank = String.format("%s[MVP%s++%s]", rankColor, plusColor, rankColor);
        } else if (!mvpPlusPlus.equalsIgnoreCase("SUPERSTAR")) {
            if (rank.equalsIgnoreCase("MVP_PLUS")) {
                rankColor = ChatColor.AQUA;
                formattedRank = String.format("%s[MVP%s+%s]", rankColor, plusColor, rankColor);
            } else if (rank.equalsIgnoreCase("MVP")) {
                rankColor = ChatColor.AQUA;
                formattedRank = String.format("%s[MVP]", rankColor);
            } else if (rank.equalsIgnoreCase("VIP_PLUS")) {
                rankColor = ChatColor.GREEN;
                plusColor = ChatColor.GOLD;
                formattedRank = String.format("%s[VIP%s+%s]", rankColor, plusColor, rankColor);
            } else if (rank.equalsIgnoreCase("VIP")) {
                rankColor = ChatColor.GREEN;
                formattedRank = String.format("%s[VIP]", rankColor);
            }
        }
        try {
            if (staff.equalsIgnoreCase("HELPER")) {
                rankColor = ChatColor.BLUE;
                formattedRank = String.format("%s[HELPER]", rankColor);
            } else if (staff.equalsIgnoreCase("MODERATOR")) {
                rankColor = ChatColor.DARK_GREEN;
                formattedRank = String.format("%s[MODERATOR]", rankColor);
            } else if (staff.equalsIgnoreCase("ADMIN")) {
                rankColor = ChatColor.RED;
                formattedRank = String.format("%s[ADMIN]", rankColor);
            } else if (staff.equalsIgnoreCase("YOUTUBER")) {
                rankColor = ChatColor.RED;
                formattedRank = String.format("%s[%sYOUTUBE%s]", rankColor, ChatColor.WHITE, rankColor);
            }
        } catch (Exception ignored) {
        }

        this.rank = new Rank(formattedRank, rankColor, plusColor);
    }

}
