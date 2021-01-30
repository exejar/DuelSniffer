package me.dooger.duelsniffer.statapi.exception;

public class InvalidKeyException extends Exception {

    public InvalidKeyException() {
        System.err.println("[DUELSNIFFER] Invalid API Key");
    }

}
