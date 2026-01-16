package io.github.kkm237.notifier.starter.config;


import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "notifier")
public class NotifierProperties {

    private Email email = new Email();
    private Sms sms = new Sms();
    private WhatsApp whatsapp = new WhatsApp();

    private NotifierProperties() {
    }

    public NotifierProperties(Email email, Sms sms, WhatsApp whatsapp) {
        this.email = email;
        this.sms = sms;
        this.whatsapp = whatsapp;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public WhatsApp getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(WhatsApp whatsapp) {
        this.whatsapp = whatsapp;
    }

    public static class Email {
        private boolean enabled = false;
        private String host;
        private int port = 587;
        private String username;
        private String password;
        private String from;
        private boolean startTlsEnabled = true;
        private boolean authEnabled = true;

        public Email() {
        }

        public Email(boolean enabled, String host, int port, String username, String password, String from, boolean startTlsEnabled, boolean authEnabled) {
            this.enabled = enabled;
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
            this.from = from;
            this.startTlsEnabled = startTlsEnabled;
            this.authEnabled = authEnabled;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public boolean isStartTlsEnabled() {
            return startTlsEnabled;
        }

        public void setStartTlsEnabled(boolean startTlsEnabled) {
            this.startTlsEnabled = startTlsEnabled;
        }

        public boolean isAuthEnabled() {
            return authEnabled;
        }

        public void setAuthEnabled(boolean authEnabled) {
            this.authEnabled = authEnabled;
        }
    }


    public static class Sms {
        private boolean enabled = false;
        private String accountSid;
        private String authToken;
        private String fromPhone;

        public Sms() {
        }

        public Sms(boolean enabled, String accountSid, String authToken, String fromPhone) {
            this.enabled = enabled;
            this.accountSid = accountSid;
            this.authToken = authToken;
            this.fromPhone = fromPhone;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getAccountSid() {
            return accountSid;
        }

        public void setAccountSid(String accountSid) {
            this.accountSid = accountSid;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getFromPhone() {
            return fromPhone;
        }

        public void setFromPhone(String fromPhone) {
            this.fromPhone = fromPhone;
        }
    }


    public static class WhatsApp {
        private boolean enabled = false;
        private String accountSid;
        private String authToken;
        private String fromPhone;

        public WhatsApp() {
        }

        public WhatsApp(boolean enabled, String accountSid, String authToken, String fromPhone) {
            this.enabled = enabled;
            this.accountSid = accountSid;
            this.authToken = authToken;
            this.fromPhone = fromPhone;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getAccountSid() {
            return accountSid;
        }

        public void setAccountSid(String accountSid) {
            this.accountSid = accountSid;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getFromPhone() {
            return fromPhone;
        }

        public void setFromPhone(String fromPhone) {
            this.fromPhone = fromPhone;
        }
    }
}