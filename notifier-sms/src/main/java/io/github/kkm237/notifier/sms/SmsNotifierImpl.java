package io.github.kkm237.notifier.sms;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.kkm237.notifier.core.exceptions.NotifierSendFailedException;
import io.github.kkm237.notifier.core.model.NotifierPayload;
import io.github.kkm237.notifier.core.model.Notifier;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SmsNotifierImpl implements Notifier {
    Logger log = Logger.getLogger(SmsNotifierImpl.class.getName());
    private final String fromPhoneNumber;

    public SmsNotifierImpl(SmsConfig config) {
        Twilio.init(config.getAccountSid(), config.getAuthToken());
        this.fromPhoneNumber = config.getFromPhone();
    }

    @Override
    public void send(NotifierPayload payload) {
        try {

            if (!payload.getRecipients().isEmpty() && payload.getRecipients() != null) {
                for (String recipient: payload.getRecipients()) {
                    Message message = Message.creator(new PhoneNumber(recipient), new PhoneNumber(fromPhoneNumber), payload.getBody()).create();
                    log.info("SMS sent successfully. Message SID: " +message.getSid() +" Message Status: " +message.getStatus().toString());
                }
            }else {
                throw new NotifierSendFailedException( "Failed to send SMS: missing or invalid recipient");
            }

        } catch (Exception e) {
            throw new NotifierSendFailedException( "Failed to send SMS: " + e.getMessage(),e );
        }
    }

    @Override
    public boolean supports(NotifierPayload.Channel channel) {
        return NotifierPayload.Channel.SMS.equals(channel);
    }

    @Override
    public NotifierPayload.Channel getChannel() {
        return NotifierPayload.Channel.SMS;
    }
}
