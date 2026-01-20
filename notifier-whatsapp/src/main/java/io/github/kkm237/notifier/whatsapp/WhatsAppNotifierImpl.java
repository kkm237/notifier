package io.github.kkm237.notifier.whatsapp;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.kkm237.notifier.core.exceptions.NotifierSendFailedException;
import io.github.kkm237.notifier.core.model.Notifier;
import io.github.kkm237.notifier.core.model.NotifierPayload;


import java.util.logging.Logger;

/**
 * @implNote Send WhatsApp notification {@link  io.github.kkm237.notifier.whatsapp.WhatsAppConfig}
 * @author Maximilien kengne kongne
 * @since 20.01.2026
 * @version 1.0.0
 * @see io.github.kkm237.notifier.core.model.NotifierPayload
 */
public class WhatsAppNotifierImpl implements Notifier {
    Logger log = Logger.getLogger(WhatsAppNotifierImpl.class.getName());
    private final String fromPhoneNumber;

    public WhatsAppNotifierImpl(WhatsAppConfig config) {
        Twilio.init(config.getAccountSid(), config.getAuthToken());
        this.fromPhoneNumber = config.getFromPhone();
    }

    @Override
    public void send(NotifierPayload payload) {
        try {

            if (!payload.getRecipients().isEmpty()) {
                for (String recipient: payload.getRecipients()) {
                    Message message = Message.creator(new PhoneNumber(recipient), new PhoneNumber(fromPhoneNumber), payload.getBody()).create();
                    log.info("WhatsApp notification sent successfully. Message SID: " +message.getSid() +" Message Status: " +message.getStatus().toString());
                }
            }else {
                throw new NotifierSendFailedException( "Failed to send WhatsApp notification: missing or invalid recipient");
            }

        } catch (Exception e) {
            throw new NotifierSendFailedException( "Failed to send WhatsApp notification: " + e.getMessage(),e );
        }
    }

    @Override
    public boolean supports(NotifierPayload.Channel channel) {
        return NotifierPayload.Channel.WHATSAPP.equals(channel);
    }

    @Override
    public NotifierPayload.Channel getChannel() {
        return NotifierPayload.Channel.WHATSAPP;
    }
}
