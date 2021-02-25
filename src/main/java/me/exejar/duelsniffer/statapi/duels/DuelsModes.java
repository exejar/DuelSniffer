package me.exejar.duelsniffer.statapi.duels;

public enum DuelsModes {

    ALL("", "Overall"), SKYWARS("sw_", "Skywars"), SKYWARS_DOUBLES("sw_doubles_", "Skywars 2v2"),
    SKYWARS_TOURNAMENT("sw_tournament_", "Skywars Tournament"), BOW("bow_", "Bow"),
    OP("op_", "OP"), OP_DOUBLES("op_doubles_", "OP 2v2"), UHC("uhc_", "UHC"),
    UHC_DOUBLES("uhc_doubles_", "UHC 2v2"), NO_DEBUFF("no_debuff_", "No Debuff"),
    COMBO("combo_", "Combo"), BLITZ("blitz_", "Blitz"), SUMO("sumo_", "Sumo"),
    SUMO_TOURNAMENT("sumo_tournament_", "Sumo Tournament"), MEGAWALLS("mw_", "Mega Walls"),
    BRIDGE("bridge_", "Bridge"), CLASSIC("classic_", "Classic");

    final String mode, gameModeName;

    DuelsModes(String mode, String gameModeName) {
        this.mode = mode;
        this.gameModeName = gameModeName;
    }

    DuelsModes(String mode) {
        this.mode = mode;
        this.gameModeName = "";
    }

    @Override
    public String toString() {
        return mode;
    }

    public String toMode() {
        return mode;
    }

    public String toGameString() {
        return gameModeName;
    }


}
