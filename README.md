# Notifier 📧

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kkm237/notifier-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:io.github.kkm237%20AND%20a:notifier-*)
[![Build Status](https://github.com/kkm237/notifier/workflows/Build/badge.svg)](https://github.com/kkm237/notifier/actions)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/Java-17%2B-orange)](https://openjdk.java.net/)

**Notifier** est une librairie Java moderne et modulaire pour l'envoi de notifications multi-canaux (Email, SMS, WhatsApp). Conçue pour être **framework-agnostic**, elle fonctionne aussi bien avec Spring Boot qu'avec des applications Java SE.

---

## Préréquis
- Java 17+
- Maven 3+

## ✨ Fonctionnalités

- 📧 **Email** - Envoi d'emails avec pièces jointes multiples (HTML/texte)
- 💬 **SMS** - Envoi de SMS via Twilio
- 📱 **WhatsApp** - Envoi de messages WhatsApp via Twilio
- 🎯 **API unifiée** - Interface simple et cohérente pour tous les canaux
- 🚀 **Spring Boot Ready** - Auto-configuration avec starter
- 🔧 **Framework-agnostic** - Utilisable dans n'importe quel projet Java
- 📎 **Pièces jointes** - Support complet (File, byte[], InputStream)
- 🌐 **Multi-destinataires** - Envoi à plusieurs destinataires
- 🎨 **HTML & UTF-8** - Support complet des emails HTML et caractères spéciaux

---

## 🚀 Quick Start

### 1. Installation

#### Avec Spring Boot (recommandé)

```xml
<dependency>
    <groupId>io.github.kkm237</groupId>
    <artifactId>notifier-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
```

#### Sans Spring Boot (Java SE)

```xml
<dependency>
    <groupId>io.github.kkm237</groupId>
    <artifactId>notifier-email</artifactId>
    <version>1.0.1</version>
</dependency>
```

```xml
<dependency>
    <groupId>io.github.kkm237</groupId>
    <artifactId>notifier-sms</artifactId>
    <version>1.0.1</version>
</dependency>
```

```xml
<dependency>
    <groupId>io.github.kkm237</groupId>
    <artifactId>notifier-core</artifactId>
    <version>1.0.1</version>
</dependency>
```

### 2. Configuration (Spring Boot)

`application.yml` :
```yaml
notifier:
  email:
    enabled: true
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-app-password
    from: noreply@yourcompany.com
  
  sms:
    enabled: true
    account-sid: ACxxxxxxxxxxxx
    auth-token: your-twilio-token
    from-phone: +15551234567
```

### 3. Utilisation

#### Avec Spring Boot

```java
@Service
public class NotificationService {

    private final NotifierService notifierService;

    public NotificationService(NotifierService notifierService) {
        this.notifierService = notifierService;
    }
    
    // sms or email or whatsapp
    public void sendNotification(NotifierPayload payload) {
        notifierService.send(payload);
    }
}
```

#### Sans Spring Boot

```java
public class Main {
    public static void main(String[] args) {
        // Configuration
        EmailConfig config = EmailConfig.builder()
                .host("smtp.gmail.com")
                .port(587)
                .username("your-email@gmail.com")
                .password("your-app-password")
                .fromEmail("noreply@example.com")
                .startTlsEnabled(true)
                .authEnabled(true)
                .build();

        EmailNotifierImpl emailNotifierImpl = new EmailNotifierImpl(config);

        SmsConfig config = SmsConfig.builder()
                .accountSid("your_account_sid")
                .authToken("your_auth_token")
                .fromPhone("your_phone")
                .build();

        SmsNotifierImpl smsNotifierImpl = new SmsNotifierImpl(config);
        
        // Envoi email
        NotifierPayload emailPayload = NotifierPayload.builder()
                .channel(NotificationChannel.EMAIL)
                .recipient("user@example.com")
                .subject("Hello")
                .body("This is a test email")
                .build();

        emailNotifierImpl.send(emailPayload);

        //  Envoi sms
        NotifierPayload smsPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.SMS)
                .addRecipient("+2376xxxxxxxx")
                .subject("Welcoming notification")
                .body("sending sms with twilio app")
                .build();

        smsNotifierImpl.send(smsRequest);
    }
}
```

---

## 📦 Modules

| Module                          | Description | Maven Central                                                                                                                                                                                 |
|---------------------------------|-------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **notifier-core**                | API et interfaces communes | [![Maven Central](https://img.shields.io/maven-central/v/io.github.kkm237/notifier-core.svg)](https://search.maven.org/artifact/io.github.kkm237/notifier-core)                               |
| **notifier-email**               | Implémentation email (Jakarta Mail) | [![Maven Central](https://img.shields.io/maven-central/v/io.github.kkm237/notifier-email.svg)](https://search.maven.org/artifact/io.github.kkm237/notifier-email)                             |
| **notifier-sms**                 | Implémentation SMS (Twilio) | [![Maven Central](https://img.shields.io/maven-central/v/io.github.kkm237/notifier-sms.svg)](https://search.maven.org/artifact/io.github.kkm237/notifier-sms)                                 |
| **notifier-whatsapp**            | Implémentation WhatsApp (Twilio) | [![Maven Central](https://img.shields.io/maven-central/v/io.github.kkm237/notifier-whatsapp.svg)](https://search.maven.org/artifact/io.github.kkm237/notifier-whatsapp)                       |
| **notifier-spring-boot-starter** | Auto-configuration Spring Boot | [![Maven Central](https://img.shields.io/maven-central/v/io.github.kkm237/notifier-spring-boot-starter.svg)](https://search.maven.org/artifact/io.github.kkm237/notifier-spring-boot-starter) |

---

## 📧 Exemples d'utilisation

### Email avec pièces jointes

```java

public class Main {
    public static void main(String[] args) {
        // Configuration
        EmailConfig config = EmailConfig.builder()
                .host("smtp.gmail.com")
                .port(587)
                .username("your-email@gmail.com")
                .password("your-app-password")
                .fromEmail("noreply@example.com")
                .startTlsEnabled(true)
                .authEnabled(true)
                .build();

        EmailNotifierImpl emailNotifierImpl = new EmailNotifierImpl(config);
        
        // Envoi email
        byte[] invoice = "Invoice content here".getBytes(StandardCharsets.UTF_8);
        AttachmentPayload pdf = AttachmentPayload.builder()
                .content(invoice)
                .filename("invoice.pdf")
                .contentType("application/pdf")
                .build();

        NotifierPayload payload = NotifierPayload.builder()
                .channel(NotificationChannel.EMAIL)
                .addRecipient("client@example.com")
                .subject("Invoice")
                .body("Please find your invoice attached")
                .addAttachment(pdf)
                .build();

        emailNotifierImpl.send(emailPayload);
    }
}


```

### Email HTML

```java
public class Main {
    public static void main(String[] args) {
        // Configuration
        EmailConfig config = EmailConfig.builder()
                .host("smtp.gmail.com")
                .port(587)
                .username("your-email@gmail.com")
                .password("your-app-password")
                .fromEmail("noreply@example.com")
                .startTlsEnabled(true)
                .authEnabled(true)
                .build();

        EmailNotifierImpl emailNotifierImpl = new EmailNotifierImpl(config);

        String htmlBody = """
                <html>
                <body>
                    <h1>Welcome!</h1>
                    <p>Thank you for joining us.</p>
                </body>
                </html>
                """;
        
        NotifierPayload emailPayload = NotifierPayload.builder()
                .channel(NotificationChannel.EMAIL)
                .recipient("user@example.com")
                .subject("Welcome")
                .htmlContent(htmlBody)
                .build();

        emailNotifierImpl.send(emailPayload);

        
    }
}

```

### SMS de vérification

```java

public class Main {
    public static void main(String[] args) {
        // Configuration
        SmsConfig config = SmsConfig.builder()
                .accountSid("your_account_sid")
                .authToken("your_auth_token")
                .fromPhone("your_phone")
                .build();

        SmsNotifierImpl smsNotifierImpl = new SmsNotifierImpl(config);
        
        //  Envoi sms
        NotifierPayload smsPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.SMS)
                .addRecipient("+2376xxxxxxxx")
                .subject("verification code")
                .body("Your verification code is: 123456")
                .build();

        smsNotifierImpl.send(smsPayload);
    }
}

```

---

## 🛠️ Configuration avancée

### Plusieurs pièces jointes

```java

public class Main {
    public static void main(String[] args) {
        // Configuration
        EmailConfig config = EmailConfig.builder()
                .host("smtp.gmail.com")
                .port(587)
                .username("your-email@gmail.com")
                .password("your-app-password")
                .fromEmail("noreply@example.com")
                .startTlsEnabled(true)
                .authEnabled(true)
                .build();

        EmailNotifierImpl emailNotifierImpl = new EmailNotifierImpl(config);

        byte[] invoice = "Invoice content here".getBytes(StandardCharsets.UTF_8);
        AttachmentPayload pdf = AttachmentPayload.builder()
                .content(invoice)
                .filename("invoice.pdf")
                .contentType("application/pdf")
                .build();

        AttachmentPayload csv = AttachmentPayload.builder()
                .content( "Name,Email\nJohn,john@test.com".getBytes(StandardCharsets.UTF_8))
                .filename("data.csv")
                .contentType("text/csv")
                .build();

        NotifierPayload payload = NotifierPayload.builder()
                .channel(NotificationChannel.EMAIL)
                .addRecipient("client@example.com")
                .subject("Invoice")
                .body("Please find your invoice attached")
                .addAttachments(List.of(pdf, csv))
                .build();

        emailNotifierImpl.send(payload);

    }
}

```

---

## 🏗️ Architecture

Notifier est construit autour d'une architecture modulaire :

```
notifier-core (API commune)
    ↑
    ├── notifier-email (Jakarta Mail)
    ├── notifier-sms (Twilio)
    └── notifier-whatsapp (Twilio)
    
notifier-spring-boot-starter (Auto-configuration)
    → Utilise tous les modules ci-dessus
```

**Avantages** :
- ✅ Modules indépendants
- ✅ Pas de dépendances lourdes dans notifier-core
- ✅ Utilisable avec ou sans Spring Boot
- ✅ Extensible facilement

---

## 📚 Documentation

- [Getting Started](docs/getting-started.md) - Guide de démarrage complet
- [Configuration](docs/configuration.md) - Toutes les options de configuration
- [Examples](docs/examples.md) - Exemples avancés
- [Deployment](docs/deployment.md) - Déploiement en production
- [Troubleshooting](docs/troubleshooting.md) - Résolution de problèmes

---

## 🧪 Tests

```bash
# Tests unitaires
mvn test

```

---

## 🤝 Contribution

Les contributions sont les bienvenues ! Consultez [CONTRIBUTING.md](CONTRIBUTING.md) pour les guidelines.

### Workflow de contribution

1. Fork le projet
2. Créer une branche (`git checkout -b feature/amazing-feature`)
3. Commit les changements (`git commit -m 'feat: add amazing feature'`)
4. Push vers la branche (`git push origin feature/amazing-feature`)
5. Ouvrir une Pull Request

---

## 📄 Licence

Ce projet est sous licence Apache 2.0 - voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

## 🙏 Remerciements

- [Jakarta Mail](https://eclipse-ee4j.github.io/mail/) pour l'API email
- [Twilio](https://www.twilio.com/) pour SMS et WhatsApp
- [Spring Boot](https://spring.io/projects/spring-boot) pour l'écosystème

---

## 📞 Support

- 📧 Email : maximiliendenver@gmail.com
- 💬 Discord : [Notifier Community](https://discord.gg/notifier)
- 🐛 Issues : [GitHub Issues](https://github.com/kkm237/notifier/issues)
- 📖 Documentation : ...

---

## 🗺️ Roadmap

- [ ] Support Slack notifications
- [ ] Support Microsoft Teams
- [ ] Support Firebase Cloud Messaging (FCM)
- [ ] Support Push notifications
- [ ] Templates système
- [ ] Dashboard de monitoring
- [ ] Rate limiting intégré

---

## ⭐ Star History

[![Star History Chart](https://api.star-history.com/svg?repos=kkm237/notifier&type=Date)](https://star-history.com/#kkm237/notifier&Date)

---

**Made with ❤️ by [Maximilien KENGNE KONGNE](https://github.com/kkm237)**