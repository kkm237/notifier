package io.github.kkm237.notifier.core.exceptions;



import io.github.kkm237.notifier.core.model.NotifierPayload;

/**
 * Throw this Exception when one channel is not supported
 */
public class ChannelNotSupportedException extends NotifierException {

    public ChannelNotSupportedException(NotifierPayload.Channel channel) {
        super("Channel not supported: " + channel);
    }
}
