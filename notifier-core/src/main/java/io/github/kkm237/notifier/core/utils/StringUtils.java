package io.github.kkm237.notifier.core.utils;

public class StringUtils {
    private StringUtils() {}

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
