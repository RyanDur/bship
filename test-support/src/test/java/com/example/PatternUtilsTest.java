package com.example;

import org.junit.Test;

import java.util.regex.Matcher;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PatternUtilsTest {


    @Test
    public void anything_shouldReturnTrueForEmptyString() {
        Matcher matcher = PatternUtils.anything().matcher("");
        assertThat(matcher.matches(), is(true));
    }

    @Test
    public void anything_shouldReturnTrueForACharacter() {
        Matcher matcher = PatternUtils.anything().matcher("A");
        assertThat(matcher.matches(), is(true));
    }

    @Test
    public void anything_shouldReturnTrueForADigit() {
        Matcher matcher = PatternUtils.anything().matcher("9");
        assertThat(matcher.matches(), is(true));
    }

    @Test
    public void anything_shouldReturnTrueForAWhitespace() {
        Matcher matcher = PatternUtils.anything().matcher(" ");
        assertThat(matcher.matches(), is(true));
    }

    @Test
    public void anything_shouldReturnTrueForAnyMixtureOfAlphanumericsAndWhitespace() {
        Matcher matcher = PatternUtils.anything().matcher("Abc   ssdf  99  83q3a3ae ");
        assertThat(matcher.matches(), is(true));
    }
}