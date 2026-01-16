package io.github.kkm237.notifier.email;

import io.github.kkm237.notifier.core.exceptions.NotifierException;

import java.util.Objects;

public class EmailConfig {

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

    @Override
    public String toString() {
        return "EmailConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", fromEmail='" + fromEmail + '\'' +
                ", startTlsEnabled=" + startTlsEnabled +
                ", authEnabled=" + authEnabled +
                ", sslEnabled=" + sslEnabled +
                ", debug=" + debug +
                '}';
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
            this.host = Objects.requireNonNull(host, "host cannot be null");
            return this;
        }

        public Builder port(Integer port) {
            this.port = Objects.requireNonNull(port, "port cannot be null");
            return this;
        }

        public Builder username(String username) {
            this.username = Objects.requireNonNull(username, "username cannot be null");
            return this;
        }

        public Builder password(String password) {
            this.password = Objects.requireNonNull(password, "password cannot be null");
            return this;
        }

        public Builder fromEmail(String fromEmail) {
            this.fromEmail = Objects.requireNonNull(fromEmail, "fromEmail cannot be null");
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

            if (host.isEmpty()) {
                throw new NotifierException("host is required");
            }

            if (port == null) {
                throw new NotifierException("port is required");
            }

            if (username.isEmpty()) {
                throw new NotifierException("username is required");
            }

            if (password.isEmpty()) {
                throw new NotifierException("password is required");
            }

            if (fromEmail.isEmpty()) {
                throw new NotifierException("fromEmail is required");
            }

            if (startTlsEnabled == null) {
                throw new NotifierException("startTlsEnabled is required");
            }

            if (authEnabled == null) {
                throw new NotifierException("authEnabled is required");
            }

            if (sslEnabled == null) {
                throw new NotifierException("sslEnabled is required");
            }
            if (debug == null) {
                throw new NotifierException("debug is required");
            }

            return new EmailConfig(this);
        }

    }


}