package io.github.kkm237.notifier.core.utils;

public class StringUtils {
    private StringUtils() {}

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\+[1-9]\\d{1,14}$");
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
