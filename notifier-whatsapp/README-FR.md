# Notifier WhatsApp

Module d'envoi de notifications WhatsApp utilisant l'API Twilio. **Framework-agnostic** - fonctionne avec ou sans Spring Boot.

## 📦 Installation

```xml
<dependency>
    <groupId>io.github.kkm237</groupId>
    <artifactId>notifier-whatsapp</artifactId>
    <version>1.0.2</version>
</dependency>
```

## 📌 Prérequis

- **Java** : 17+
- **Compte Twilio** : https://www.twilio.com/
- **Account SID** et **Auth Token** depuis votre console Twilio
- **Numéro Twilio** actif

## ✨ Fonctionnalités

- ✅ Envoi de notification WhatsApp via Twilio
- ✅ Support international (tous les pays)
- ✅ Gestion des caractères spéciaux et UTF-8
- ✅ Configuration simple
- ✅ Indépendant de Spring Boot

## 🚀 Utilisation

### Configuration 

```java
import io.github.kkm237.notifier.whatsapp.WhatsAppConfig;

WhatsAppConfig config = WhatsAppConfig.builder()
              .accountSid("ACxxxxxxxxxxxxxxxxxxxxxxxxxx")
              .authToken("your_auth_token")
              .fromPhone("+15551234567")
              .build();
```

## 📱 Configuration Twilio

### Étape 1 : Créer un compte

1. Aller sur : https://www.twilio.com/try-twilio
2. S'inscrire (essai gratuit disponible)
3. Vérifier votre email et téléphone

### Étape 2 : Obtenir les credentials

1. Se connecter au dashboard : https://console.twilio.com/
2. Trouver :
   - **Account SID** : `ACxxxxxxxxxxxxxxxxxxxxxxxxxx`
   - **Auth Token** : Cliquer sur "Show" pour le révéler

### Étape 3 : Obtenir un numéro

1. Dans la console → Phone Numbers → Manage → Buy a number
2. Choisir un pays (États-Unis recommandé pour débuter)
3. Acheter un numéro avec capacité WhatsApp


### Compte d'essai

Avec un compte d'essai gratuit :
- ✅ 15$ de crédit offert
- ⚠️ Peut envoyer uniquement vers des numéros vérifiés
- ⚠️ Messages préfixés par "Sent from your Twilio trial account"

Pour production : passer en compte payant

## 🌍 Formats de numéros internationaux

### Format E.164 (requis)

Tous les numéros doivent être au format **E.164** :
- Commencer par `+`
- Code pays
- Numéro sans espaces ni tirets

**Exemples valides** :
```
+237612345678  # Cameroun
+33612345678   # France
+15551234567   # États-Unis
+447911123456  # Royaume-Uni
```

**Exemples invalides** :
```
612345678  # Manque le + et le code pays
+237 6 12 34 56 78  # Espaces
237612345678   # Manque le +
```

## 💡 Cas d'usage

### Code de vérification + Gestion des erreurs

```java
import io.github.kkm237.notifier.core.exceptions.NotifierSendFailedException;
import io.github.kkm237.notifier.core.model.Notifier;
import io.github.kkm237.notifier.core.model.NotifierPayload;
import io.github.kkm237.notifier.whatsapp.WhatsAppConfig;
import io.github.kkm237.notifier.whatsapp.WhatsAppNotifierImpl;

public class WhatsAppSample {
   static void main(String[] args) {
      WhatsAppConfig config = WhatsAppConfig.builder()
              .accountSid("ACxxxxxxxxxxxxxxxxxxxxxxxxxx")
              .authToken("your_auth_token")
              .fromPhone("+15551234567")
              .build();

      Notifier notifier = new WhatsAppNotifierImpl(config);

      String code = generateVerificationCode();

      NotifierPayload payload = NotifierPayload.builder()
              .channel(NotifierPayload.Channel.WHATSAPP)
              .recipient("+237612345678")
              .body("Votre code de vérification est: " + code +
                      ". Valide pendant 5 minutes.")
              .build();
      try {
         notifier.send(payload);
      } catch (NotifierSendFailedException e) {
         log.error("Erreur d'envoi ", e);
         // Gérer l'erreur (retry, alerter admin, etc.)
      }
   }
}

```

## 📚 Voir aussi

- [notifier-core](../notifier-core/README.md) - API de base
- [notifier-sms](../notifier-sms/README-FR.md) - Messages simples
- [notifier-spring-boot-starter](../notifier-spring-boot-starter/README.md) - Intégration Spring Boot
