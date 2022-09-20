package com.kkr.optional;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionalTest {
    @ParameterizedTest
    @MethodSource("provideStringsForIfNullThenReturn")
    public void ifNullThenReturn(String initialString, String expectedResult) {
        var actualResult = Optional.ofNullable(initialString).map(str -> str.toLowerCase(Locale.ROOT)).orElse("null");
        assertEquals(expectedResult, actualResult);
    }
    static Stream<Arguments> provideStringsForIfNullThenReturn() {
        return Stream.of(
                Arguments.of("String", "string"),
                Arguments.of(null, "null"));
    }
}

