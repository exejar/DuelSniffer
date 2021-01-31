package me.dooger.duelsniffer.config;

public enum ModConfigNames {

    APIKEY("ApiKey"), HUDMODE("HudMode"), HUDX("HudX"), HUDY("HudY");

    private final String name;

    ModConfigNames(String name) {
        this.name = name;
    }

    @Override
    public String toString() { return name; }

}
