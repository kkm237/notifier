package io.github.kkm237.notifier.test.email;



import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import io.github.kkm237.notifier.core.model.AttachmentPayload;
import io.github.kkm237.notifier.core.model.NotifierPayload;
import io.github.kkm237.notifier.email.EmailConfig;
import io.github.kkm237.notifier.email.EmailNotifierImpl;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DisplayName("EmailNotifierImplTest")
class EmailNotifierImplTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("test@localhost", "password"))
            .withPerMethodLifecycle(true);

    private EmailNotifierImpl emailService;

    @BeforeEach
    void setUp() {
        EmailConfig config = EmailConfig.builder()
                .host(greenMail.getSmtp().getBindTo())
                .port(greenMail.getSmtp().getPort())
                .username("test@localhost")
                .password("password")
                .fromEmail("sender@example.com")
                .startTlsEnabled(true)
                .authEnabled(true)
                .build();

        emailService = new EmailNotifierImpl(config);
    }

    @Test
    @DisplayName("send() should send simple email successfully")
    void sendShouldSendSimpleEmail() throws Exception {
        // Given - Créer un CourierRequest
        NotifierPayload notifierPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.EMAIL)
                .addRecipient("recipient@example.com")
                .subject("Test Email")
                .body("This is a test email body")
                .build();

        // When - Appeler send()
        emailService.send(notifierPayload);

        // Then - Vérifier que l'email a été envoyé
        assertThat(greenMail.getReceivedMessages()).hasSize(1);

        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getSubject()).isEqualTo("Test Email");
        assertThat(receivedMessage.getContent().toString()).contains("This is a test email body");
        assertThat(receivedMessage.getRecipients(Message.RecipientType.TO)[0].toString())
                .isEqualTo("recipient@example.com");
    }

    @Test
    @DisplayName("send() should send email with single attachment")
    void sendShouldSendEmailWithSingleAttachment() throws Exception {
        // Given
        byte[] attachmentContent = "Invoice content here".getBytes(StandardCharsets.UTF_8);
        AttachmentPayload attachment = AttachmentPayload.builder()
                .content(attachmentContent)
                .description("attachmentContent")
                .filename("invoice.pdf")
                .contentType("application/pdf")
                .build();

        NotifierPayload notifierPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.EMAIL)
                .addRecipient("client@example.com")
                .subject("Your Invoice")
                .body("Please find your invoice attached.")
                .addAttachments(List.of(attachment))
                .build();

        // When
        emailService.send(notifierPayload);

        // Then
        assertThat(greenMail.getReceivedMessages()).hasSize(1);

        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getContent()).isInstanceOf(MimeMultipart.class);

        MimeMultipart multipart = (MimeMultipart) receivedMessage.getContent();
        assertThat(multipart.getCount()).isEqualTo(2); // Body + 1 attachment
    }

    @Test
    @DisplayName("send() should send email with multiple attachments")
    void sendShouldSendEmailWithMultipleAttachments() throws Exception {
        // Given
        AttachmentPayload pdf = AttachmentPayload.builder()
                .content( "PDF Report Content".getBytes(StandardCharsets.UTF_8))
                .description("attachmentContent")
                .filename("invoice.pdf")
                .contentType("application/pdf")
                .build();

        AttachmentPayload csv = AttachmentPayload.builder()
                .content(  "Name,Email\nJohn,john@test.com".getBytes(StandardCharsets.UTF_8))
                .description("attachmentContent")
                .filename("data.csv")
                .contentType("text/csv")
                .build();

        AttachmentPayload excel = AttachmentPayload.builder()
                .content("Excel Content".getBytes(StandardCharsets.UTF_8))
                .description("attachmentContent")
                .filename("spreadsheet.xlsx")
                .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .build();

        NotifierPayload notifierPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.EMAIL)
                .addRecipient("manager@example.com")
                .subject("Monthly Report Package")
                .body("Please find all monthly reports attached.")
                .addAttachments(Arrays.asList(pdf, csv, excel))
                .build();

        // When
        emailService.send(notifierPayload);

        // Then
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        MimeMultipart multipart = (MimeMultipart) receivedMessage.getContent();
        assertThat(multipart.getCount()).isEqualTo(4); // Body + 3 attachments
    }

    @Test
    @DisplayName("send() should send HTML email")
    void sendShouldSendHtmlEmail() throws Exception {
        // Given
        String htmlBody = """
                <html>
                <body>
                    <h1>Welcome!</h1>
                    <p>Thank you for joining us.</p>
                </body>
                </html>
                """;

        NotifierPayload notifierPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.EMAIL)
                .addRecipient("newuser@example.com")
                .subject("Welcome to Herald")
                .htmlContent(htmlBody)
                .build();

        //courierRequest.getMetadata().put("html", true);

        // When
        emailService.send(notifierPayload);

        // Then
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getContentType()).contains("text/html");
    }

    @Test
    @DisplayName("send() should send HTML email with attachments")
    void sendShouldSendHtmlEmailWithAttachments() throws Exception {
        // Given
        String htmlBody = """
                <html>
                <body>
                    <h1>Invoice Ready</h1>
                    <p>Your invoice is attached below.</p>
                </body>
                </html>
                """;
        AttachmentPayload invoice = AttachmentPayload.builder()
                .content( "Invoice PDF content".getBytes(StandardCharsets.UTF_8))
                .description("attachmentContent")
                .filename("invoice-2025.pdf")
                .contentType("application/pdf")
                .build();


        NotifierPayload notifierPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.EMAIL)
                .addRecipient("customer@example.com")
                .subject("Invoice #2025-001")
                .htmlContent(htmlBody)
                .addAttachments(List.of(invoice))
                .build();

        //courierRequest.getMetadata().put("html", true);

        // When
        emailService.send(notifierPayload);

        // Then
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        MimeMultipart multipart = (MimeMultipart) receivedMessage.getContent();
        assertThat(multipart.getCount()).isEqualTo(2); // HTML body + attachment
    }

    @Test
    @DisplayName("send() should support EMAIL channel")
    void sendShouldSupportEmailChannel() {
        // When & Then
        assertThat(emailService.supports(NotifierPayload.Channel.EMAIL)).isTrue();
        assertThat(emailService.supports(NotifierPayload.Channel.SMS)).isFalse();
        assertThat(emailService.supports(NotifierPayload.Channel.WHATSAPP)).isFalse();
    }

    @Test
    @DisplayName("send() should handle UTF-8 characters correctly")
    void sendShouldHandleUtf8Characters() throws Exception {
        // Given
        NotifierPayload notifierPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.EMAIL)
                .addRecipient("recipient@example.com")
                .subject("Test avec caractères spéciaux: é, è, ê, ç, à, ù")
                .body("Corps du message avec émojis: 🎉 🚀 ✅ 📧")
                .build();

        // When
        emailService.send(notifierPayload);

        // Then
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getSubject()).contains("caractères spéciaux");
    }

    @Test
    @DisplayName("send() should send email to multiple recipients")
    void sendShouldSendEmailToMultipleRecipients() throws Exception {
        // Given
        NotifierPayload notifierPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.EMAIL)
                .addRecipients(List.of("user1@example.com", "user2@example.com", "user3@example.com"))
                .subject("Team Notification")
                .body("This message is for the entire team.")
                .build();

        // When
        emailService.send(notifierPayload);

        // Then
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getRecipients(Message.RecipientType.TO)).hasSize(3);
    }

    @Test
    @DisplayName("send() should handle empty body gracefully")
    void sendShouldHandleEmptyBody() throws Exception {
        // Given
        NotifierPayload notifierPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.EMAIL)
                .addRecipient("user@example.com")
                .subject("Empty Body Test")
                .body("")
                .build();

        // When
        emailService.send(notifierPayload);

        // Then
        assertThat(greenMail.getReceivedMessages()).hasSize(1);
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getSubject()).isEqualTo("Empty Body Test");
    }

    @Test
    @DisplayName("send() should preserve attachment descriptions")
    void sendShouldPreserveAttachmentDescriptions() throws Exception {
        // Given
        AttachmentPayload attachment = AttachmentPayload.builder()
                .filename("contract.pdf")
                .contentType("application/pdf")
                .content("Contract content".getBytes(StandardCharsets.UTF_8))
                .description("Annual service contract - Please review and sign")
                .build();

        NotifierPayload notifierPayload = NotifierPayload.builder()
                .channel(NotifierPayload.Channel.EMAIL)
                .addRecipient("partner@example.com")
                .subject("Contract for Review")
                .body("Please find the contract attached.")
                .addAttachments(List.of(attachment))
                .build();

        // When
        emailService.send(notifierPayload);

        // Then
        assertThat(greenMail.getReceivedMessages()).hasSize(1);
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getContent()).isInstanceOf(MimeMultipart.class);
    }
}


