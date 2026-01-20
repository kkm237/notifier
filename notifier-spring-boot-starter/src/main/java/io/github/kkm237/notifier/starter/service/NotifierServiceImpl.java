package io.github.kkm237.notifier.starter.service;

import io.github.kkm237.notifier.core.exceptions.ChannelNotSupportedException;
import io.github.kkm237.notifier.core.model.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;



/**
 * main class which centralize the access of different services of de notification
 */
public class NotifierServiceImpl implements NotifierService {

    private static final Logger log = Logger.getLogger(NotifierServiceImpl.class.getName());
    private final Map<NotifierPayload.Channel, Notifier> channelNotifierMap;

    public NotifierServiceImpl(List<Notifier> notifiers) {
         // call getChannel() on each instance and associate the key at the matching instance
        this.channelNotifierMap = notifiers.stream().collect(Collectors.toMap(Notifier::getChannel, Function.identity()));
        log.info("Notifier initialized with channels: {}" + channelNotifierMap.keySet());
    }


    @Override
    public void send(NotifierPayload payload) {
        Notifier notifier = channelNotifierMap.get(payload.getChannel());

        if (notifier == null) {
            throw new ChannelNotSupportedException(payload.getChannel());
        }

        notifier.send(payload);
    }

    /**
     * Verify if a channel is available
     */
    @Override
    public boolean isChannelAvailable(NotifierPayload.Channel channel) {
        return channelNotifierMap.containsKey(channel);
    }

    /**
     * Return the list of available channels
     */
    @Override
    public List<NotifierPayload.Channel> getAvailableChannels() {
        return List.copyOf(channelNotifierMap.keySet());
    }
}