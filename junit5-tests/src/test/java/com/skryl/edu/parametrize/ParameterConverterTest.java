package com.skryl.edu.parametrize;

import com.skryl.edu.User;
import com.skryl.edu.extensions.TestCase;
import com.skryl.edu.extensions.UserParameterConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author Skryl D.V. on 2022-05-27
 */
public class ParameterConverterTest {

    static Stream<Arguments> advSource() {
        return Stream.of(
                Arguments.of(User.State.DISABLE, "User account is disabled"),
                Arguments.of(User.State.ENABLE, "User account is enabled")
        );
    }

    @TestCase(id = "20")
    @EnumSource(User.State.class)
    @ParameterizedTest
    void userEnumSourceConverter(@ConvertWith(UserParameterConverter.class) User user) {
        System.out.println(user);
    }

    @TestCase(id = "20")
    @MethodSource("advSource")
    @ParameterizedTest
    void userMethodSourceConverter(
            @ConvertWith(UserParameterConverter.class) User user,
            String expectedMessage
    ) {
        System.out.println(user + " " + expectedMessage);
    }
}
