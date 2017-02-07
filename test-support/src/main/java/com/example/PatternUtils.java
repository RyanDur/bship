package com.example;

import java.util.regex.Pattern;

public class PatternUtils {
    public static Pattern anything() {
        return Pattern.compile(".*");
    }
}
