package me.dooger.duelsniffer.statapi.duels;

public enum DuelsModes {

    ALL(""), SKYWARS("sw_", "skywars"), SKYWARS_DOUBLES("sw_doubles_", "skywars_doubles"),
    SKYWARS_TOURNAMENT("sw_tournament_", "skywars_tournament"), BOW("bow_", "bow"),
    OP("op_", "op"), OP_DOUBLES("op_doubles_", "op doubles"), UHC("uhc_", "uhc"),
    UHC_DOUBLES("uhc_doubles_", "uhc_doubles"), NO_DEBUFF("no_debuff_", "no_debuff"),
    COMBO("combo_", "combo"), BLITZ("blitz_", "blitz"), SUMO("sumo_", "sumo"),
    SUMO_TOURNAMENT("sumo_tournament_", "sumo_tournament"), MEGAWALLS("mw_", "megawalls"),
    BRIDGE("bridge_", "bridge"), CLASSIC("classic_", "classic");

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
