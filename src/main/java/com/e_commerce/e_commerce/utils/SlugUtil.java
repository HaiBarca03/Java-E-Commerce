package com.e_commerce.e_commerce.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugUtil {

    private SlugUtil() {
    }

    public static String toSlug(String input) {
        if (input == null) return null;

        input = input.replaceAll("Đ", "D").replaceAll("đ", "d");

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        String withoutDiacritics = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return withoutDiacritics.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "") // allow hyphens in case user already typed
                .replaceAll("\\s+", "-");
    }
}
