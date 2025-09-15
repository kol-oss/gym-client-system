package com.epam.gym.utils;

import java.util.Random;

public final class PasswordUtils {
    private static final int ASCII_PRINTABLE_MIN = 33;
    private static final int ASCII_PRINTABLE_MAX = 126;
    private static final int ASCII_PRINTABLE_RANGE = ASCII_PRINTABLE_MAX - ASCII_PRINTABLE_MIN + 1;

    public static String generate(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(ASCII_PRINTABLE_RANGE) + ASCII_PRINTABLE_MIN);
            sb.append(c);
        }

        return sb.toString();
    }
}
