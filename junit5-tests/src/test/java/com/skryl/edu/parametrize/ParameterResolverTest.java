package com.skryl.edu.parametrize;

import com.skryl.edu.User;
import com.skryl.edu.extensions.TestCase;
import com.skryl.edu.extensions.UserParameterResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

/**
 * This test demonstrates data injections to test method
 *
 * @author Skryl D.V. on 2022-05-27
 */
@ExtendWith({UserParameterResolver.class})
public class ParameterResolverTest {

    @Test
    @Inject
    @TestCase(id = "20")
    void testWithUserData(User user) {
        Assertions.assertEquals(user.getName(), "Skryl");
    }
}
