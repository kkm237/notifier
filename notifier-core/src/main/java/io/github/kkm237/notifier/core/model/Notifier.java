package io.github.kkm237.notifier.core.model;


/**
 * main interface for send notifications
 */
public interface Notifier {

    /**
     * send notification
     *
     * @param payload the payload of notification
     */
    void send(NotifierPayload payload);

    /**
     * check if this service support the specified channel
     *
     * @param channel channel to check
     * @return true channel is supported
     */
    boolean supports(NotifierPayload.Channel channel);

    /**
     * return the channel supported by this service
     *
     * @return the channel of notification
     */
    NotifierPayload.Channel getChannel();
}