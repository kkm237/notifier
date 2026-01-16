package io.github.kkm237.notifier.email;


import io.github.kkm237.notifier.core.exceptions.NotifierSendFailedException;
import io.github.kkm237.notifier.core.model.*;
import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EmailNotifierImpl implements Notifier {

    private static final Logger log = Logger.getLogger(EmailNotifierImpl.class.getName());
    private final Session session;
    private final String fromEmail;

    /**
     * Constructor with personalized configuration
     */
    public EmailNotifierImpl(EmailConfig config) {
        this.fromEmail = config.getFromEmail();
        this.session = createSession(config);
        log.info("Email notification service initialized for :" +fromEmail);
    }


    @Override
    public void send(NotifierPayload payload) {
        log.info("Sending email notification to: " + payload.getRecipients().toString() + " with " + payload.getCourierAttachments().size() + " attachment(s)" + payload.getCourierAttachments().toString());
        Calendar calendar = Calendar.getInstance();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, payload.getOrganizationName()));
            message.setSubject(payload.getSubject());
            message.setSentDate(calendar.getTime());

            if (payload.getReplyTo()!= null) {
                message.setReplyTo(InternetAddress.parse(payload.getReplyTo()));
            }

            if (payload.getPriority() != null) {
                message.setHeader("X-Priority", String.valueOf(payload.getPriority().getValue()));
            }

            if (payload.getCc() != null) {
                for (String cc : payload.getCc()) {
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
                }
            }

            if (payload.getRecipients() != null) {
                for (String recipient : payload.getRecipients()) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                }
            }

            if (payload.getBcc() != null) {
                for (String bcc : payload.getBcc()) {
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
                }
            }

            // Create content of message (body + attachment)
            if (payload.getCourierAttachments() != null && !payload.getCourierAttachments().isEmpty()) {
                message.setContent(createMultipartContent(payload));
            } else {
                // simple message without attachment
                if (payload.getHtmlContent() != null) {
                    message.setContent(payload.getHtmlContent(), "text/html; charset=utf-8");
                } else {
                    message.setText(payload.getBody());
                }
            }

            Transport.send(message);
            String notificationId = UUID.randomUUID().toString();
            log.info("Email sent successfully. Notification ID: " + notificationId);

        } catch (SendFailedException e) {
            log.log(Level.WARNING, "Email partially sent. Invalid addresses: " + Arrays.toString(e.getInvalidAddresses()));
            throw new NotifierSendFailedException("Partial failure: " + e.getMessage(), e);
        } catch (MessagingException e) {
            log.log(Level.SEVERE, "Fail sending email", e);
            throw new NotifierSendFailedException("Messaging error: " + e.getMessage(), e);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Unexpected error while sending email", e);
            throw new NotifierSendFailedException("Unexpected error: " + e.getMessage(), e);
        }
    }

        private Multipart createMultipartContent(NotifierPayload request) throws MessagingException, IOException {

            Multipart multipart = new MimeMultipart("mixed");

            // Corps du message
            MimeBodyPart bodyPart = new MimeBodyPart();
            if (request.getHtmlContent() != null) {
                bodyPart.setContent(request.getHtmlContent(), "text/html; charset=UTF-8");
            } else {
                bodyPart.setContent(request.getBody(), "text/plain; charset=UTF-8");
            }
            multipart.addBodyPart(bodyPart);

            for (AttachmentPayload attachment : request.getCourierAttachments()) {
                MimeBodyPart attachmentPart = createAttachmentPart(attachment);
                multipart.addBodyPart(attachmentPart);
            }
            return multipart;
        }


        private MimeBodyPart createAttachmentPart(AttachmentPayload attachment) throws MessagingException, IOException {
            log.info("start build courier attachment : ");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            if (attachment.getContent() != null) {
                ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment.getContent(), attachment.getContentType() );
                attachmentPart.setDataHandler(new jakarta.activation.DataHandler(dataSource));

            } else if (attachment.getFile() != null) {
                FileDataSource dataSource = new FileDataSource(attachment.getFile());
                attachmentPart.setDataHandler(new DataHandler(dataSource));

            } else if (attachment.getInputStream() != null) {
                ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment.getInputStream(), attachment.getContentType());
                attachmentPart.setDataHandler(new jakarta.activation.DataHandler(dataSource));
            }

            attachmentPart.setDisposition(Part.ATTACHMENT);
            attachmentPart.setFileName(attachment.getFilename());
            attachmentPart.setDescription(attachment.getDescription(), "UTF-8");

           // log.info("Added attachment, filename: " +attachment.getFilename() +" contentType : " +attachment.getContentType());
            return attachmentPart;
        }

        /**
         * Create Jakarta Mail session
         */
        private Session createSession(EmailConfig config) {

            Properties props = new Properties();

            props.put("mail.smtp.host", config.getHost());
            props.put("mail.smtp.port", String.valueOf(config.getPort()));
            props.put("mail.smtp.auth", String.valueOf(config.isAuthEnabled()));
            props.put("mail.smtp.user", config.getUsername());
            props.put("mail.debug", String.valueOf(config.isDebug()));
            // Propriétés additionnelles pour plus de compatibilité
            props.put("mail.mime.charset", "UTF-8");
            props.put("mail.smtp.timeout", "10000");
            props.put("mail.smtp.connectiontimeout", "10000");

            /* Choix EXCLUSIF SSL vs STARTTLS */
            if (config.isSslEnabled()) {
                // Port 465
                props.put("mail.smtp.ssl.enable", "true");
            } else if (config.isStartTlsEnabled()) {
                // Port 587
                props.put("mail.smtp.starttls.enable", "true");
            }

            if (config.isAuthEnabled()) {
                return Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication( config.getUsername(), config.getPassword() );
                    }
                });
            }

            return Session.getInstance(props);
        }

        @Override
        public boolean supports(NotifierPayload.Channel channel) {
            return NotifierPayload.Channel.EMAIL.equals(channel);
        }

        @Override
        public NotifierPayload.Channel getChannel() {
            return NotifierPayload.Channel.EMAIL;
        }


    }