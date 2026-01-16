package io.github.kkm237.notifier.starter.config;

import io.github.kkm237.notifier.core.model.Notifier;
import io.github.kkm237.notifier.email.EmailConfig;
import io.github.kkm237.notifier.email.EmailNotifierImpl;
import io.github.kkm237.notifier.sms.SmsConfig;
import io.github.kkm237.notifier.sms.SmsNotifierImpl;
import io.github.kkm237.notifier.starter.service.NotifierService;
import io.github.kkm237.notifier.starter.service.NotifierServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;



@AutoConfiguration
@EnableConfigurationProperties(NotifierProperties.class)
public class NotifierAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(NotifierAutoConfiguration.class);

    @Bean
    @ConditionalOnProperty(prefix = "notifier.email", name = "enabled", havingValue = "true")
    public Notifier emailNotifierImpl(NotifierProperties properties) {
        log.info("Initializing EmailNotifierImpl");
        NotifierProperties.Email emailProps = properties.getEmail();

        EmailConfig config = EmailConfig.builder()
                .host(emailProps.getHost())
                .port(emailProps.getPort())
                .username(emailProps.getUsername())
                .password(emailProps.getPassword())
                .fromEmail(emailProps.getFrom())
                .startTlsEnabled(emailProps.isStartTlsEnabled())
                .authEnabled(emailProps.isAuthEnabled())
                .build();

        return new EmailNotifierImpl(config);
    }

    @Bean
    @ConditionalOnProperty(prefix = "notifier.sms", name = "enabled", havingValue = "true")
    public Notifier smsNotifierImpl(NotifierProperties properties) {
        log.info("Initializing SmsNotifierImpl");
        NotifierProperties.Sms sms = properties.getSms();

        SmsConfig config = SmsConfig.builder()
                .accountSid(sms.getAccountSid())
                .authToken(sms.getAuthToken())
                .fromPhone(sms.getFromPhone())
                .build();

        return new SmsNotifierImpl(config);
    }

    @Bean
    public NotifierService notifierService(List<Notifier> notifiers) {
        log.info("Initializing NotifierServiceImpl with {} services ", notifiers.size());
        return new NotifierServiceImpl(notifiers);
    }
}