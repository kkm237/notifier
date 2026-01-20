# Notifier Email

Module d'envoi d'emails utilisant Jakarta Mail API. **Framework-agnostic** - fonctionne avec ou sans Framework Java.

## 📦 Installation

```xml
<dependency>
    <groupId>io.github.kkm237</groupId>
    <artifactId>notifier-email</artifactId>
    <version>1.0.2</version>
</dependency>
```

## ✨ Fonctionnalités

- ✅ Email texte et HTML
- ✅ Pièces jointes multiples (File, byte[], InputStream)
- ✅ Multi-destinataires
- ✅ Support UTF-8 complet
- ✅ Configuration SMTP flexible
- ✅ Indépendant d'un Framework Java

## 🚀 Utilisation

### Configuration

```java
import io.github.kkm237.notifier.email.EmailConfig;

EmailConfig config = EmailConfig.builder()
        .host("smtp.gmail.com")
        .port(587)
        .username("your-email@gmail.com")
        .password("your-app-password")
        .fromEmail("noreply@yourcompany.com")
        .startTlsEnabled(true)
        .authEnabled(true)
        .build();


```

### Envoi simple

```java
import io.github.kkm237.notifier.core.model.NotifierPayload;
import io.github.kkm237.notifier.email.EmailNotifierImpl;

EmailNotifierImpl emailNotifier = new EmailNotifierImpl(config);
NotifierPayload payload = NotifierPayload.builder()
    .channel(NotifierPayload.Channel.EMAIL)
    .addRecipient("user@example.com")
    .subject("Hello")
    .body("This is a test email")
    .build();

emailNotifier.send(payload);
```

### Email HTML

```java
String htmlBody = "<html><body><h1>Hello</h1></body></html>";

NotifierPayload payload = NotifierPayload.builder()
    .channel(NotificationChannel.EMAIL)
    .addRecipient("user@example.com")
    .subject("HTML Email")
    .htmlContent(htmlBody)
    .build();


emailNotifier.send(payload);
```

### Avec pièces jointes

```java

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

emailNotifier.send(payload);
```

## ⚙️ Configuration SMTP

### Gmail

```java
EmailConfig config = EmailConfig.builder()
    .host("smtp.gmail.com")
    .port(587)
    .username("your-email@gmail.com")
    .password("app-password")  // Mot de passe d'application
    .fromEmail("noreply@yourcompany.com")
    .startTlsEnabled(true)
    .authEnabled(true)
    .build();
```

### Outlook

```java
EmailConfig config = EmailConfig.builder()
    .host("smtp.office365.com")
    .port(587)
    .username("your-email@outlook.com")
    .password("your-password")
    .fromEmail("noreply@yourcompany.com")
    .startTlsEnabled(true)
    .authEnabled(true)
    .build();
```

### SMTP personnalisé

```java
EmailConfig config = EmailConfig.builder()
    .host("smtp.yourcompany.com")
    .port(465)
    .username("smtp-user")
    .password("smtp-password")
    .fromEmail("noreply@yourcompany.com")
    .startTlsEnabled(false)
    .sslEnabled(true)
    .authEnabled(true)
    .build();
```

## 🔧 Options avancées

### Mode debug

```java
EmailConfig config = EmailConfig.builder()
    // ... autres configs
    .debug(true)  // Active les logs JavaMail
    .build();
```

### Multi-destinataires

```java
NotifierPayload payload = NotifierPayload.builder()
    .addRecipients(List.of("user1@example.com", "user2@example.com", "user3@example.com"))
    .subject("Team Notification")
    .body("Message for everyone")
    .build();
```

## 📎 Types MIME supportés

| Extension | Type MIME |
|-----------|-----------|
| `.pdf` | `application/pdf` |
| `.docx` | `application/vnd.openxmlformats-officedocument.wordprocessingml.document` |
| `.xlsx` | `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet` |
| `.txt` | `text/plain` |
| `.csv` | `text/csv` |
| `.json` | `application/json` |
| `.png` | `image/png` |
| `.jpg` | `image/jpeg` |


## 📚 Voir aussi

- [notifier-core](../notifier-core/README-FR.md) - API de base
- [notifier-spring-boot-starter](../notifier-spring-boot-starter/README.md) - Intégration Spring Boot