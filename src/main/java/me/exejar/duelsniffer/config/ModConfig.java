package me.exejar.duelsniffer.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.exejar.duelsniffer.utils.ChatUtils;
import me.exejar.duelsniffer.utils.Handler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.HashMap;

public class ModConfig {

    private String apiKey, hudMode, hudX, hudY;
    private boolean inGame;
    private static ModConfig instance;

    public static ModConfig getInstance() {
        if (instance == null) instance = new ModConfig();
        return instance;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String key) {
        apiKey = key;
    }

    public String getHudMode() { return hudMode; }

    public void setHudMode(String mode) { hudMode = mode; }

    public boolean getInGame() { return inGame; }

    public void setInGame(boolean inGame) { this.inGame = inGame; }

    public String getHudX() { return hudX; }

    public void setHudX(int x) { hudX = Integer.toString(x); }

    public String getHudY() { return hudY;}

    public void setHudY(int y) { hudY = Integer.toString(y); }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void makeFile() {
        try {
            if (!getFile().exists()) {
                getFile().getParentFile().mkdir();
                getFile().createNewFile();
                try (FileWriter writer = new FileWriter(getFile())) {
                    writer.write("{}");
                    writer.flush();
                    save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfigFromFile() {
        if (!getFile().exists()) makeFile();
        apiKey = getString(ModConfigNames.APIKEY);
        hudMode = getString(ModConfigNames.HUDMODE);
        hudX = getString(ModConfigNames.HUDX);
        hudY = getString(ModConfigNames.HUDY);
        inGame = getBool(ModConfigNames.INGAME);
    }

    public File getFile() {
        String here = Paths.get("").toAbsolutePath().toString();
        String file = here + File.separator + "DuelSniffer" + File.separator + "config.json";
        return new File(file);
    }

    public void init() {
        loadConfigFromFile();
    }

    public void save() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(ModConfigNames.APIKEY.toString(), getApiKey());
        map.put(ModConfigNames.HUDMODE.toString(), getHudMode());
        map.put(ModConfigNames.HUDX.toString(), getHudX());
        map.put(ModConfigNames.HUDY.toString(), getHudY());
        map.put(ModConfigNames.INGAME.toString(), getInGame());
        try (Writer writer = new FileWriter(getFile())) {
            Handler.getGson().toJson(map, writer);
        } catch (Exception e) {
            e.printStackTrace();
            ChatUtils.sendMessage("&cUNABLE TO SAVE CONFIG!");
        }
    }

    public String getString(ModConfigNames key) {
        JsonParser parser = new JsonParser();
        String s = "";
        try {
            JsonObject object = parser.parse(new FileReader(getFile())).getAsJsonObject();
            s = object.get(key.toString()).getAsString();
        } catch (NullPointerException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public boolean getBool(ModConfigNames key) {
        JsonParser parser = new JsonParser();
        boolean b = false;
        try {
            JsonObject object = parser.parse(new FileReader(getFile())).getAsJsonObject();
            b = object.get(key.toString()).getAsBoolean();
        } catch (NullPointerException ignored) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

}
