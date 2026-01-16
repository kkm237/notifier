package io.github.kkm237.notifier.starter.service;

import io.github.kkm237.notifier.core.model.NotifierPayload;

import java.util.List;

public interface NotifierService {
    void send(NotifierPayload request);

    boolean isChannelAvailable(NotifierPayload.Channel channel);

    List<NotifierPayload.Channel> getAvailableChannels();
}
