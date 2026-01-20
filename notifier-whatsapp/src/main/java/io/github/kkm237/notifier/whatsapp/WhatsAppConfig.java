package io.github.kkm237.notifier.whatsapp;

import io.github.kkm237.notifier.core.exceptions.NotifierException;
import io.github.kkm237.notifier.core.utils.StringUtils;

/**
 * @implNote WhatsApp configuration
 * @author Maximilien kengne kongne
 * @since 16.01.2026
 * @version 1.0.0
 */
public final class WhatsAppConfig {

    private final String accountSid;
    private final String authToken;
    private final String fromPhone;

    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getFromPhone() {
        return fromPhone;
    }

    private WhatsAppConfig(Builder builder) {
        this.accountSid = builder.accountSid;
        this.authToken = builder.authToken;
        this.fromPhone = builder.fromPhone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String accountSid;
        private String authToken;
        private String fromPhone;


        private Builder() {}

        public Builder accountSid(String accountSid) {
            if (StringUtils.isNullOrEmpty(accountSid)) throw new NotifierException("accountSid is null or empty");
            this.accountSid = accountSid;
            return this;
        }

        public Builder authToken(String authToken) {
            if (StringUtils.isNullOrEmpty(authToken)) throw new NotifierException("authToken is null or empty");
            this.authToken = authToken;
            return this;
        }

        public Builder fromPhone(String fromPhone) {
            if (StringUtils.isNullOrEmpty(fromPhone)) throw new NotifierException("fromPhone is null or empty");
            if (StringUtils.isValidPhoneNumber(fromPhone)) throw  new NotifierException("fromPhone is not valid");
            this.fromPhone = fromPhone;
            return this;
        }

        public WhatsAppConfig build() {
            if (StringUtils.isNullOrEmpty(accountSid)) throw new NotifierException("accountSid is null or empty");
            if (StringUtils.isNullOrEmpty(authToken)) throw new NotifierException("authToken is null or empty");
            if (StringUtils.isNullOrEmpty(fromPhone)) throw new NotifierException("fromPhone is null or empty");
            if (StringUtils.isValidPhoneNumber(fromPhone)) throw  new NotifierException("fromPhone is not valid");
            return new WhatsAppConfig(this);
        }

    }


}