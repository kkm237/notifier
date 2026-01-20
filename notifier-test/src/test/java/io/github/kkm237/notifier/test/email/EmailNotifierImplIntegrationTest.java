package io.github.kkm237.notifier.test.email;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "notifier.email.enabled=false",
        "notifier.sms.enabled=false",
        "notifier.whatsapp.enabled=false"
})
@DisplayName("integration test for EmailNotifierImpl")
 class EmailNotifierImplIntegrationTest {

    @Test
    void shouldSendEmail() {
        // Given

        // When

        // Then

    }
}
