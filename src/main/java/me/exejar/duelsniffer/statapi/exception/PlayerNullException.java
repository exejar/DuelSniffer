package me.exejar.duelsniffer.statapi.exception;

public class PlayerNullException extends Exception {

    public PlayerNullException() {
        System.err.println("[DUELSNIFFER] Player returned as null");;
    }

}
