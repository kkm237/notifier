package io.github.kkm237.notifier.core.exceptions;

/**
 * Base Exception for all the errors linked at notifications
 */
public class NotifierException extends RuntimeException {

    public NotifierException(String message) {
        super(message);
    }

    public NotifierException(String message, Throwable cause) {
        super(message, cause);
    }
}