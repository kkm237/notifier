package io.github.kkm237.notifier.starter.config;

import io.github.kkm237.notifier.core.model.Notifier;
import io.github.kkm237.notifier.email.EmailConfig;
import io.github.kkm237.notifier.email.EmailNotifierImpl;
import io.github.kkm237.notifier.sms.SmsConfig;
import io.github.kkm237.notifier.sms.SmsNotifierImpl;
import io.github.kkm237.notifier.starter.service.NotifierService;
import io.github.kkm237.notifier.starter.service.NotifierServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;



@Configuration
@AutoConfiguration
@EnableConfigurationProperties(NotifierProperties.class)
public class NotifierAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "notifier.email", name = "enabled", havingValue = "true")
    public Notifier emailNotifierImpl(NotifierProperties properties) {
        NotifierProperties.Email props = properties.getEmail();

        EmailConfig config = EmailConfig.builder()
                .protocol(props.getProtocol())
                .host(props.getHost())
                .port(props.getPort())
                .username(props.getUsername())
                .password(props.getPassword())
                .fromEmail(props.getFrom())
                .startTlsEnabled(props.isStartTlsEnabled())
                .sslEnabled(props.getSslEnabled())
                .authEnabled(props.isAuthEnabled())
                .debug(props.getDebug())
                .build();

        return new EmailNotifierImpl(config);
    }

    @Bean
    @ConditionalOnProperty(prefix = "notifier.sms", name = "enabled", havingValue = "true")
    public Notifier smsNotifierImpl(NotifierProperties properties) {
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
        return new NotifierServiceImpl(notifiers);
    }
}