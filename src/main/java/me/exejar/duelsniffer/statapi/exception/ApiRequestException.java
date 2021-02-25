package me.exejar.duelsniffer.statapi.exception;

public class ApiRequestException extends Exception {

    public ApiRequestException() {
        System.err.println("[DUELSNIFFER] Api Request UnSuccessful");
    }

}
