package io.github.kkm237.notifier.sms;

import io.github.kkm237.notifier.core.exceptions.NotifierException;
import io.github.kkm237.notifier.core.utils.StringUtils;

import java.util.Objects;

public final class SmsConfig {

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
            this.fromPhone = fromPhone;
            return this;
        }

        public SmsConfig build() {
            if (StringUtils.isNullOrEmpty(accountSid)) throw new NotifierException("accountSid is null or empty");
            if (StringUtils.isNullOrEmpty(authToken)) throw new NotifierException("authToken is null or empty");
            if (StringUtils.isNullOrEmpty(fromPhone)) throw new NotifierException("fromPhone is null or empty");
            return new SmsConfig(this);
        }

    }


}