package me.exejar.duelsniffer.config;

public enum ModConfigNames {

    APIKEY("ApiKey"), HUDMODE("HudMode"), HUDX("HudX"), HUDY("HudY"), INGAME("InGame");

    private final String name;

    ModConfigNames(String name) {
        this.name = name;
    }

    @Override
    public String toString() { return name; }

}
