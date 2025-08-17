package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public static String generateRandomEmail() {
        return RandomStringUtils.randomAlphabetic(10) + "@example.com";
    }

    public static String generateRandomPassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String generateRandomName() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}