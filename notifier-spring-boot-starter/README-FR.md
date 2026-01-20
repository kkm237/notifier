# Notifier Spring Boot Starter

Module d'auto-configuration Spring Boot pour Notifier. Intègre automatiquement tous les canaux de notification dans votre application Spring Boot.

## 📦 Installation

```xml
<dependency>
    <groupId>io.github.kkm237</groupId>
    <artifactId>notifier-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
```

## 📌 Prérequis

- **Java** : 17+
- **Spring Boot** : 4.0.0+
- **Maven** : 3.8.1+

## ✨ Fonctionnalités

- ✅ Auto-configuration de tous les canaux (Email, SMS, WhatsApp)
- ✅ Configuration via `application.yml` ou `application.properties`
- ✅ Activation/désactivation par canal
- ✅ Facade unifiée `NotifierService`
- ✅ Beans Spring automatiques
- ✅ Support des profiles Spring
- ✅ Validation de la configuration

## 🚀 Quick Start

### 1. Ajouter la dépendance

```xml
<dependency>
    <groupId>io.github.kkm237</groupId>
    <artifactId>notifier-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
```

### 2. Configuration minimale

`application.yml` :
```yaml
notifier:
  email:
    enabled: true
    host: smtp.gmail.com
    port: 587
    username: ${EMAIL_USERNAME}
    password: ${EMAIL_PASSWORD}
    from: noreply@yourcompany.com
```

### 3. Utiliser dans votre code

```java
@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotifierService notifier;
    
    public void sendWelcomeEmail(User user) {
        notifier.sendEmail(
            user.getEmail(),
            "Bienvenue !",
            "Merci de vous être inscrit."
        );
    }
}
```

C'est tout ! 🎉

## ⚙️ Configuration complète

### Configuration YAML

```yaml
notifier:
  # Email (optionnel)
  email:
    enabled: true
    host: smtp.gmail.com
    port: 587
    username: ${EMAIL_USERNAME}
    password: ${EMAIL_PASSWORD}
    from: noreply@yourcompany.com
    start-tls-enabled: true
    auth-enabled: true
  
  # SMS (optionnel)
  sms:
    enabled: true
    account-sid: ${TWILIO_ACCOUNT_SID}
    auth-token: ${TWILIO_AUTH_TOKEN}
    from-phone: ${TWILIO_FROM_PHONE}
  
  # WhatsApp (optionnel)
  whatsapp:
    enabled: true
    account-sid: ${TWILIO_ACCOUNT_SID}
    auth-token: ${TWILIO_AUTH_TOKEN}
    from-phone: ${TWILIO_WHATSAPP_FROM}
```

### Configuration Properties

`application.properties` :
```properties
# Email
notifier.email.enabled=true
notifier.email.host=smtp.gmail.com
notifier.email.port=587
notifier.email.username=${EMAIL_USERNAME}
notifier.email.password=${EMAIL_PASSWORD}
notifier.email.from=noreply@yourcompany.com
notifier.email.start-tls-enabled=true
notifier.email.auth-enabled=true

# SMS
notifier.sms.enabled=true
notifier.sms.account-sid=${TWILIO_ACCOUNT_SID}
notifier.sms.auth-token=${TWILIO_AUTH_TOKEN}
notifier.sms.from-phone=${TWILIO_FROM_PHONE}

# WhatsApp
notifier.whatsapp.enabled=true
notifier.whatsapp.account-sid=${TWILIO_ACCOUNT_SID}
notifier.whatsapp.auth-token=${TWILIO_AUTH_TOKEN}
notifier.whatsapp.from-phone=${TWILIO_WHATSAPP_FROM}
```

## 🎯 Utilisation de la Facade

### Méthodes disponibles

```java
@Service
@RequiredArgsConstructor
public class MyService {
    
    private final NotifierService notifier;
    
    // Email simple
    public void sendSimpleEmail() {
        notifier.sendEmail(
            "user@example.com",
            "Sujet",
            "Corps du message"
        );
    }
    
    // Email avec pièce jointe
    public void sendEmailWithAttachment() {
        NotificationAttachment attachment = NotificationAttachment.fromFile(
            new File("invoice.pdf"),
            "application/pdf"
        );
        
        notifier.sendEmailWithAttachment(
            "client@example.com",
            "Votre facture",
            "Veuillez trouver votre facture en pièce jointe",
            attachment
        );
    }
    
    // SMS
    public void sendSms() {
        notifier.sendSms(
            "+237612345678",
            "Votre code: 123456"
        );
    }
    
    // WhatsApp
    public void sendWhatsApp() {
        notifier.sendWhatsApp(
            "+237612345678",
            "Bonjour depuis Notifier !"
        );
    }
    
    // Envoi personnalisé
    public void sendCustom() {
        NotificationRequest request = NotificationRequest.builder()
            .channel(NotificationChannel.EMAIL)
            .recipient("user@example.com")
            .subject("Sujet")
            .body("Corps")
            .priority(NotificationPriority.HIGH)
            .build();
        
        notifier.send(request);
    }
    
    // Vérifier les canaux disponibles
    public void checkChannels() {
        List<NotificationChannel> channels = notifier.getAvailableChannels();
        System.out.println("Canaux disponibles: " + channels);
        
        if (notifier.isChannelAvailable(NotificationChannel.SMS)) {
            // SMS est configuré
        }
    }
}
```

## 🔧 Configuration par environnement

### Profil Development

`application-dev.yml` :
```yaml
notifier:
  email:
    enabled: true
    host: smtp.mailtrap.io  # Service de test
    port: 2525
    username: test-user
    password: test-password
    from: dev@example.com
  
  sms:
    enabled: false  # Désactivé en dev
  
  whatsapp:
    enabled: false  # Désactivé en dev
```

### Profil Production

`application-prod.yml` :
```yaml
notifier:
  email:
    enabled: true
    host: smtp.gmail.com
    port: 587
    username: ${EMAIL_USERNAME}
    password: ${EMAIL_PASSWORD}
    from: noreply@yourcompany.com
  
  sms:
    enabled: true
    account-sid: ${TWILIO_ACCOUNT_SID}
    auth-token: ${TWILIO_AUTH_TOKEN}
    from-phone: ${TWILIO_FROM_PHONE}
  
  whatsapp:
    enabled: true
    account-sid: ${TWILIO_ACCOUNT_SID}
    auth-token: ${TWILIO_AUTH_TOKEN}
    from-phone: ${TWILIO_WHATSAPP_FROM}
```

## 💉 Injection des services

### Option 1 : Utiliser la Facade (recommandé)

```java
@Service
@RequiredArgsConstructor
public class MyService {
    private final NotifierService notifier;
}
```

### Option 2 : Injecter les services individuels

```java
@Service
@RequiredArgsConstructor
public class MyService {
    private final EmailNotificationService emailService;
    private final SmsNotificationService smsService;  // Peut être null si désactivé
    private final WhatsAppNotificationService whatsappService;  // Peut être null
}
```

### Option 3 : Injection conditionnelle

```java
@Service
public class MyService {
    
    @Autowired(required = false)
    private EmailNotificationService emailService;
    
    @Autowired(required = false)
    private SmsNotificationService smsService;
    
    public void sendNotification() {
        if (emailService != null) {
            // Email est configuré
        }
        
        if (smsService != null) {
            // SMS est configuré
        }
    }
}
```

## 🎨 Exemples avancés

### Service de notification multi-canal

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class UserNotificationService {
    
    private final NotifierService notifier;
    
    public void notifyUserRegistration(User user) {
        // Email de bienvenue
        notifier.sendEmail(
            user.getEmail(),
            "Bienvenue sur notre plateforme !",
            createWelcomeEmailBody(user)
        );
        
        // SMS de confirmation (si numéro fourni)
        if (user.getPhone() != null) {
            notifier.sendSms(
                user.getPhone(),
                "Bienvenue " + user.getName() + " ! Votre compte est actif."
            );
        }
    }
    
    public void sendOrderConfirmation(Order order) {
        User user = order.getUser();
        
        // Email avec facture PDF
        byte[] invoicePdf = generateInvoicePdf(order);
        NotificationAttachment invoice = NotificationAttachment.fromBytes(
            "facture-" + order.getId() + ".pdf",
            "application/pdf",
            invoicePdf
        );
        
        notifier.sendEmailWithAttachment(
            user.getEmail(),
            "Confirmation de commande #" + order.getId(),
            createOrderConfirmationBody(order),
            invoice
        );
        
        // WhatsApp avec suivi
        if (user.hasWhatsAppOptIn()) {
            notifier.sendWhatsApp(
                user.getPhone(),
                "✅ Commande #" + order.getId() + " confirmée !\n" +
                "Suivez votre colis: " + order.getTrackingUrl()
            );
        }
    }
    
    public void sendPasswordReset(User user, String resetToken) {
        String resetUrl = "https://example.com/reset?token=" + resetToken;
        
        // Email principal
        notifier.sendEmail(
            user.getEmail(),
            "Réinitialisation de mot de passe",
            "Cliquez sur ce lien pour réinitialiser: " + resetUrl
        );
        
        // SMS avec code de sécurité
        String securityCode = resetToken.substring(0, 6);
        notifier.sendSms(
            user.getPhone(),
            "Code de sécurité: " + securityCode
        );
    }
}
```

### Configuration conditionnelle

```java
@Configuration
public class NotificationConfig {
    
    @Bean
    @ConditionalOnProperty(prefix = "notifier.email", name = "enabled", havingValue = "true")
    public EmailTemplateService emailTemplateService() {
        return new EmailTemplateService();
    }
    
    @Bean
    @Profile("!test")
    public NotificationLogger notificationLogger(NotifierService notifier) {
        return new NotificationLogger(notifier);
    }
}
```

### Listener d'événements

```java
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {
    
    private final NotifierService notifier;
    
    @EventListener
    public void onUserRegistered(UserRegisteredEvent event) {
        User user = event.getUser();
        
        notifier.sendEmail(
            user.getEmail(),
            "Bienvenue !",
            "Merci de vous être inscrit"
        );
    }
    
    @Async
    @EventListener
    public void onOrderPlaced(OrderPlacedEvent event) {
        Order order = event.getOrder();
        
        // Envoi asynchrone
        notifier.sendEmail(
            order.getCustomer().getEmail(),
            "Commande confirmée",
            "Votre commande #" + order.getId()
        );
    }
}
```

## 🧪 Tests

### Tester avec des mocks

```java
@SpringBootTest
@TestPropertySource(properties = {
    "notifier.email.enabled=false",
    "notifier.sms.enabled=false",
    "notifier.whatsapp.enabled=false"
})
class MyServiceTest {
    
    @MockBean
    private NotifierService notifier;
    
    @Autowired
    private MyService myService;
    
    @Test
    void shouldSendEmail() {
        // Given
        when(notifier.sendEmail(anyString(), anyString(), anyString()))
            .thenReturn(NotificationResponse.success("123", "Sent"));
        
        // When
        myService.sendNotification();
        
        // Then
        verify(notifier, times(1))
            .sendEmail("user@example.com", "Subject", "Body");
    }
}
```

### Tests d'intégration

```java
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class NotifierIntegrationTest {
    
    @Autowired
    private NotifierService notifier;
    
    @Test
    void contextLoads() {
        assertNotNull(notifier);
    }
    
    @Test
    void shouldHaveEmailConfigured() {
        assertTrue(notifier.isChannelAvailable(NotificationChannel.EMAIL));
    }
}
```

## 📚 Propriétés de configuration

### Email

| Propriété                          | Type | Défaut | Description |
|------------------------------------|------|--------|-------------|
| `notifier.email.enabled`           | boolean | false | Active le canal email |
| `notifier.email.host`              | String | - | Hôte SMTP |
| `notifier.email.port`              | int | 587 | Port SMTP |
| `notifier.email.username`          | String | - | Username SMTP |
| `notifier.email.password`          | String | - | Password SMTP |
| `notifier.email.from`              | String | - | Adresse d'envoi |
| `notifier.email.start-tls-enabled` | boolean | true | Active STARTTLS |
| `notifier.email.auth-enabled`      | boolean | true | Active l'auth SMTP |

### SMS

| Propriété                  | Type | Défaut | Description |
|----------------------------|------|--------|-------------|
| `notifier.sms.enabled`     | boolean | false | Active le canal SMS |
| `notifier.sms.account-sid` | String | - | Twilio Account SID |
| `notifier.sms.auth-token`  | String | - | Twilio Auth Token |
| `notifier.sms.from-phone`  | String | - | Numéro Twilio |

### WhatsApp

| Propriété                       | Type | Défaut | Description |
|---------------------------------|------|--------|-------------|
| `notifier.whatsapp.enabled`     | boolean | false | Active le canal WhatsApp |
| `notifier.whatsapp.account-sid` | String | - | Twilio Account SID |
| `notifier.whatsapp.auth-token`  | String | - | Twilio Auth Token |
| `notifier.whatsapp.from-phone`  | String | - | Numéro WhatsApp Twilio |

## 🔍 Troubleshooting

### Beans non créés

**Problème** : `NotifierService` ne peut pas être injecté

**Solution** :
```yaml
# Vérifier que au moins un canal est activé
notifier:
  email:
    enabled: true  # Au moins ça
```

### Configuration invalide

**Problème** : Application ne démarre pas

**Solution** :
```bash
# Activer les logs de debug
logging.level.io.github.kkm237.notifier=DEBUG
```

### Email ne s'envoie pas

**Solution** :
1. Vérifier les credentials
2. Activer le debug SMTP
3. Vérifier les règles firewall

## 📚 Voir aussi

- [notifier-core](../notifier-core/README-FR.md)
- [notifier-email](../notifier-email/README-FR.md)
- [notifier-sms](../notifier-sms/README-FR.md)
- [notifier-whatsapp](../notifier-whatsapp/README-FR.md)