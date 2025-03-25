package org.example.ngevaticketmanagerspring.utils.exceptions;

public class ExceptionMessageShortener {
    public static String extractBetweenQuotes(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        int firstQuoteIndex = input.indexOf('\'');
        int secondQuoteIndex = input.indexOf('\'', firstQuoteIndex + 1);
        if (firstQuoteIndex == -1 || secondQuoteIndex == -1) {
            return "";
        }

        return input.substring(firstQuoteIndex + 1, secondQuoteIndex);
    }
}
