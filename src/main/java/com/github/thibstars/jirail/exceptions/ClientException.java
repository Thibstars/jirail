package com.github.thibstars.jirail.exceptions;

/**
 * @author Thibault Helsmoortel
 */
public class ClientException extends RuntimeException {

    public ClientException(Exception exception) {
        super(exception);
    }
}
