package me.dooger.duelsniffer.utils;

import java.util.regex.Pattern;

public interface References {
    Pattern UUID = Pattern.compile("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}");
    Pattern UUID_NO_DASHES = Pattern.compile("\\w{32}");

    String MODNAME = "Duel Sniffer";
    String MODID = "duelsniffer";
    String VERSION = "1.0-DEV";

}
