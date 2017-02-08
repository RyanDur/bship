package com.bship.contracts.utils;

import java.util.regex.Pattern;

public class PatternUtils {
    public static Pattern anything() {
        return Pattern.compile(".*");
    }
}
