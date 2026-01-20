package io.github.kkm237.notifier.email;

import io.github.kkm237.notifier.core.exceptions.NotifierException;
import io.github.kkm237.notifier.core.utils.StringUtils;

import java.util.Objects;

/**
 * @implNote Email configuration
 * @author Maximilien kengne kongne
 * @since 16.01.2026
 * @version 1.0.0
 */
public final class EmailConfig {

    private final String host;
    private final Integer port;
    private final String username;
    private final String password;
    private final String fromEmail;
    private final Boolean startTlsEnabled;
    private final Boolean authEnabled;
    private final Boolean sslEnabled;
    private final Boolean debug;

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public Boolean isStartTlsEnabled() {
        return startTlsEnabled;
    }

    public Boolean isAuthEnabled() {
        return authEnabled;
    }

    public Boolean isSslEnabled() {
        return sslEnabled;
    }

    public Boolean isDebug() {
        return debug;
    }

    private EmailConfig(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.fromEmail = builder.fromEmail;
        this.startTlsEnabled = builder.startTlsEnabled;
        this.authEnabled = builder.authEnabled;
        this.sslEnabled = builder.sslEnabled;
        this.debug = builder.debug;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String host = "smtp.gmail.com";
        private Integer port = 587;
        private String username;
        private String password;
        private String fromEmail;
        private Boolean startTlsEnabled = true;
        private Boolean authEnabled = true;
        private Boolean sslEnabled = false;
        private Boolean debug = false;


        private Builder() {}

        public Builder host(String host) {
            if (StringUtils.isNullOrEmpty(host)) throw new NotifierException("host cannot be null or empty");
            this.host = host;
            return this;
        }

        public Builder port(Integer port) {
            this.port = Objects.requireNonNull(port, "port cannot be null");
            return this;
        }

        public Builder username(String username) {
            if (StringUtils.isNullOrEmpty(username)) throw new NotifierException("username cannot be null or empty");
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            if (StringUtils.isNullOrEmpty(password)) throw new NotifierException("password cannot be null or empty");
            this.password = password;
            return this;
        }

        public Builder fromEmail(String fromEmail) {
            if (StringUtils.isNullOrEmpty(fromEmail)) throw new NotifierException("fromEmail cannot be null or empty");
            if (!StringUtils.isValidEmail(fromEmail)) throw new NotifierException("fromEmail is not valid");
            this.fromEmail = fromEmail;
            return this;
        }

        public Builder startTlsEnabled(Boolean startTlsEnabled) {
            this.startTlsEnabled = Objects.requireNonNull(startTlsEnabled, "startTlsEnabled cannot be null");
            return this;
        }

        public Builder authEnabled(Boolean authEnabled) {
            this.authEnabled = Objects.requireNonNull(authEnabled, "authEnabled cannot be null");
            return this;
        }

        public Builder sslEnabled(Boolean sslEnabled) {
            this.sslEnabled = Objects.requireNonNull(sslEnabled, "sslEnabled cannot be null");
            return this;
        }

        public Builder debug(Boolean debug) {
            this.debug = Objects.requireNonNull(debug, "debug cannot be null");
            return this;
        }

        public EmailConfig build() {

            if (StringUtils.isNullOrEmpty(host)) throw new NotifierException("host cannot be null or empty");
            if (port == null) throw new NotifierException("port cannot be null");
            if (StringUtils.isNullOrEmpty(username)) throw new NotifierException("username cannot be null or empty");
            if (StringUtils.isNullOrEmpty(password)) throw new NotifierException("password cannot be null or empty");
            if (StringUtils.isNullOrEmpty(fromEmail)) throw new NotifierException("fromEmail cannot be null or empty");
            if (!StringUtils.isValidEmail(fromEmail)) throw new NotifierException("fromEmail is not valid");
            if (startTlsEnabled == null) throw new NotifierException("startTlsEnabled cannot be null");
            if (authEnabled == null) throw new NotifierException("authEnabled cannot be null");
            if (sslEnabled == null) throw new NotifierException("sslEnabled cannot be null");
            if (debug == null) throw new NotifierException("debug cannot be null");

            return new EmailConfig(this);
        }

    }


}