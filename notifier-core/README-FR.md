# Notifier Core

Module de base contenant les API et interfaces communes pour tous les canaux de notification Notifier.

## 📦 Installation

```xml
<dependency>
    <groupId>io.github.kkm237</groupId>
    <artifactId>notifier-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 📋 Description

`notifier-core` fournit :
- ✅ Interfaces communes (`Notifier`)
- ✅ Modèles de données (DTOs)
- ✅ Enums (`NotifierPayload.Channel`, `NotifierPayload.Priority`)
- ✅ Exceptions personnalisées
- ✅ **AUCUNE dépendance externe** 

## 🏗️ Structure

```
notifier-core/
├── src/main/java/
│   └── io/github/kkm237/notifier/core/
│       ├── model/
│       │   ├── NotifierPayload.java
│       │   ├── AttachmentPayload.java
│       └── exceptions/
│           ├── NotifierException.java
│           ├── ChannelNotSupportedException.java
│           └── NotifierSendFailedException.java
└── pom.xml
```

## 🎯 API Principale

### Notifier

Interface que tous les canaux doivent implémenter :

```java
public interface Notifier {
    void send(NotifierPayload payload);
    boolean supports(NotifierPayload.Channel channel);
    NotifierPayload.Channel getChannel();
}
```

### NotifierPayload

```java
NotifierPayload payload = NotifierPayload.builder()
    .channel(NotifierPayload.Channel.EMAIL)
    .addRecipient("user@example.com")
    .subject("Hello")
    .body("Message body")
    .priority(NotifierPayload.Priority.HIGH)
    .addAttachments(List.of(attachment))
    .build();
```

### NotifierPayload.Channel

```java
public enum Channel {
    EMAIL,
    SMS,
    WHATSAPP
}
```

### NotifierPayload.Priority

```java
public enum Priority {
    LOW(5),
    NORMAL(3),
    HIGH(1);
}
```

## 🔌 Extension

Pour créer un nouveau canal (ex: Slack) :

```java
public class SlackNotifierImpl implements Notifier {
    
    @Override
    public void send(NotifierPayload payload) {
        // Implémentation Slack
    }
    
    @Override
    public boolean supports(NotifierPayload.Channel channel) {
        return NotifierPayload.Channel.SLACK.equals(channel);
    }
    
    @Override
    public NotifierPayload.Channel getChannel() {
        return NotifierPayload.Channel.SLACK;
    }
}
```

## 📝 Exceptions

```java
// Exception de base
throw new NotifierException("Error message");

// Canal non supporté
throw new ChannelNotSupportedException(NotifierPayload.Channel.SMS);

// Échec d'envoi
throw new NotifierSendFailedException("Failed to send", cause);
```

## 🧪 Tests

```bash
cd notifier-core
mvn test
```

## 📚 Voir aussi

- [notifier-email](../notifier-email/README-FR.md) - Implémentation email
- [notifier-sms](../notifier-sms/README-FR.md) - Implémentation SMS
