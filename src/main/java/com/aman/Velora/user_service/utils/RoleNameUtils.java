package com.aman.Velora.user_service.utils;

import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@NoArgsConstructor
public class RoleNameUtils {

    public static String normalizeRoleName(String input) {
        return input
                .trim()
                .replaceAll("\\s+", "_")
                .toUpperCase(Locale.ROOT);
    }

    public static String generateDisplayName(String input) {
        return Arrays.stream(input.trim().split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase(Locale.ROOT)
                        + word.substring(1).toLowerCase(Locale.ROOT))
                .collect(Collectors.joining(" "));
    }
}
