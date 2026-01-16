package io.github.kkm237.notifier.core.exceptions;



/**
 * Throw this Exception when sending notification fail
 */
public class NotifierSendFailedException extends NotifierException {

    public NotifierSendFailedException(String message) {
        super(message);
    }

    public NotifierSendFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
