package com.ahirajustice.configserver.common.utils;

import com.ahirajustice.configserver.common.enums.TimeFactor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    public static boolean containsSpecialCharactersAndNumbers(String str) {
        String specialCharactersAndNumbers = "!@#$%&*()'+,-./:;<=>?[]^`{|}0123456789";

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (specialCharactersAndNumbers.contains(Character.toString(ch))) {
                return true;
            }
        }

        return false;
    }

    public static int convertToMillis(int time, TimeFactor timeFactor) {
        int result = 0;

        switch (timeFactor) {
            case SECOND:
                result = time * 1000;
                break;
            case MINUTE:
                result = time * 60000;
                break;
            case HOUR:
                result = time * 3600000;
                break;
            default:
                break;
        }

        return result;
    }

    public static String generateRandomDigits(String prefix, int length) {
        return String.format("%s_%s", prefix, generateRandomDigits(length));
    }

    public static String generateRandomDigits(int length) {
        String alphabet = "0123456789";
        return generateRandomString(length, alphabet);
    }

    public static String generateRandomAlphaNumeric(String prefix, int length) {
        return String.format("%s_%s", prefix, generateRandomAlphaNumeric(length));
    }

    public static String generateRandomAlphaNumeric(int length) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return generateRandomString(length, alphabet);
    }

    public static String generateRandomHex(String prefix, int length) {
        return String.format("%s_%s", prefix, generateRandomHex(length));
    }

    public static String generateRandomHex(int length) {
        String alphabet = "0123456789abcdef";
        return generateRandomString(length, alphabet);
    }

    public static String generateRandomString(int length, String alphabet) {
        Random random = new SecureRandom();
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return new String(returnValue);
    }

}
