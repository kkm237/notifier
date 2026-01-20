package io.github.kkm237.notifier.core.model;

import io.github.kkm237.notifier.core.exceptions.NotifierException;
import io.github.kkm237.notifier.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class NotifierPayload {
    private final Channel channel;
    /**
     * subject of notification (mainly for email)
     */
    private final String subject;
    /**
     * body of message
     */
    private final String body;
    /**
     * recipient of notification
     * email address or phoneNumber
     */
    private final List<String> recipients;
    private final List<String> cc;
    private final List<String> bcc;
    private final String organizationName;
    private final String htmlContent;
    private final Priority priority;
    private final String replyTo;
    private final List<AttachmentPayload> attachmentPayloads;

    public Channel getChannel() {
        return channel;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public List<String> getCc() {
        return cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public List<AttachmentPayload> getCourierAttachments() {
        return attachmentPayloads;
    }


    private NotifierPayload(Builder builder) {
        this.channel = builder.channel;
        this.recipients = List.copyOf(builder.recipients);
        this.cc = List.copyOf(builder.cc);
        this.bcc = List.copyOf(builder.bcc);
        this.subject = builder.subject;
        this.body = builder.body;
        this.organizationName = builder.organizationName;
        this.htmlContent = builder.htmlContent;
        this.priority = builder.priority;
        this.replyTo = builder.replyTo;
        this.attachmentPayloads = List.copyOf(builder.attachmentPayloads);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Channel channel;
        private List<String> recipients = new ArrayList<>();
        private List<String> cc = new ArrayList<>();
        private List<String> bcc = new ArrayList<>();
        private String subject;
        private String body;
        private String organizationName;
        private String htmlContent;
        private Priority priority = Priority.NORMAL;
        private String replyTo;
        private List<AttachmentPayload> attachmentPayloads = new ArrayList<>();

        private Builder() {}

        public Builder channel(Channel channel) {
            this.channel = Objects.requireNonNull(channel, "channel cannot be null");
            return this;
        }

        public Builder addRecipient(String recipient) {
            if (StringUtils.isNullOrEmpty(recipient)) throw new NotifierException("Recipient cannot be null or empty");
            if (!StringUtils.isValidEmail(recipient)) throw new NotifierException("Recipient is not valid");
            this.recipients.add(recipient);
            return this;
        }

        public Builder addRecipients(List<String> recipients) {
            recipients.forEach(this::addRecipient);
            return this;
        }

        public Builder addCc(String ccRecipient) {
            if (StringUtils.isNullOrEmpty(ccRecipient)) throw new NotifierException("CC recipient cannot be null or empty");
            if (!StringUtils.isValidEmail(ccRecipient)) throw new NotifierException("CC recipient is not valid");
            this.cc.add(ccRecipient);
            return this;
        }

        public Builder addCcs(List<String> ccRecipients) {
            ccRecipients.forEach(this::addCc);
            return this;
        }

        public Builder addBcc(String bccRecipient) {
            if (StringUtils.isNullOrEmpty(bccRecipient)) throw new NotifierException("BCC recipient cannot be null or empty");
            if (StringUtils.isValidEmail(bccRecipient)) throw new NotifierException("BCC recipient is not valid");
            this.bcc.add(bccRecipient);
            return this;
        }

        public Builder addBccs(List<String> bccRecipients) {
            bccRecipients.forEach(this::addBcc);
            return this;
        }

        public Builder subject(String subject) {
            if (StringUtils.isNullOrEmpty(subject)) throw new NotifierException("Subject cannot be null or empty");
            this.subject = subject;
            return this;
        }

        public Builder body(String body) {
            if (StringUtils.isNullOrEmpty(body)) throw new NotifierException("Body cannot be null or empty");
            this.body = body;
            return this;
        }

        public Builder organizationName(String organizationName) {
            if (StringUtils.isNullOrEmpty(organizationName)) throw new NotifierException("Organization name cannot be null or empty");
            this.organizationName = organizationName;
            return this;
        }

        public Builder htmlContent(String htmlContent) {
            if (StringUtils.isNullOrEmpty(htmlContent)) throw new NotifierException("HTML content cannot be null or empty");
            this.htmlContent = htmlContent;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = Objects.requireNonNull(priority, "Priority cannot be null");
            return this;
        }

        public Builder replyTo(String replyTo) {
            if (StringUtils.isNullOrEmpty(replyTo)) throw new NotifierException("Reply To cannot be null or empty");
            if (!StringUtils.isValidEmail(replyTo)) throw new NotifierException("Reply To is not valid");
            this.replyTo = replyTo;
            return this;
        }

        public Builder addAttachment(AttachmentPayload attachmentPayload) {
            this.attachmentPayloads.add(Objects.requireNonNull(attachmentPayload, "courierAttachment cannot be null"));
            return this;
        }

        public Builder addAttachments(List<AttachmentPayload> attachmentPayloads) {
            attachmentPayloads.forEach(this::addAttachment);
            return this;
        }

        public NotifierPayload build() {

            if (channel == null) {
                throw new NotifierException("channel is required");
            }

            if (recipients.isEmpty()) {
                throw new NotifierException("At least one recipient is required");
            }

            if (subject.isBlank()) {
                throw new NotifierException("Subject is required");
            }

            if (body == null && htmlContent == null) {
                throw new NotifierException("Cannot empty both body and htmlContent");
            }

            if (body != null && htmlContent != null) {
                throw new NotifierException("Cannot specify both body and htmlContent");
            }

            return new NotifierPayload(this);
        }
    }

    public enum Channel{
        EMAIL,
        SMS,
        WHATSAPP
    }

    public enum Priority {
        LOW(5),
        NORMAL(3),
        HIGH(1);

        private final int value;

        Priority(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}