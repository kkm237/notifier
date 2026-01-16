package io.github.kkm237.notifier.sms;

import io.github.kkm237.notifier.core.exceptions.NotifierException;

import java.util.Objects;

public class SmsConfig {

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

    private SmsConfig(Builder builder) {
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
            this.accountSid = Objects.requireNonNull(accountSid, "accountSid cannot be null");
            return this;
        }

        public Builder authToken(String authToken) {
            this.authToken = Objects.requireNonNull(authToken, "authToken cannot be null");
            return this;
        }

        public Builder fromPhone(String fromPhone) {
            this.fromPhone = Objects.requireNonNull(fromPhone, "fromPhone cannot be null");
            return this;
        }

        public SmsConfig build() {

            if (accountSid.isEmpty()) {
                throw new NotifierException("accountSid is required");
            }

            if (authToken.isEmpty()) {
                throw new NotifierException("authToken is required");
            }

            if (fromPhone.isEmpty()) {
                throw new NotifierException("fromPhone is required");
            }

            return new SmsConfig(this);
        }

    }


}