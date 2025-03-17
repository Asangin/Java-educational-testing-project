package com.skryl.edu.rerun;

import com.skryl.exception.MyException;
import io.github.artsok.RepeatedIfExceptionsTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import java.io.IOException;

public class ReRunTest {

    /**
     * Repeated three times if test failed.
     * By default Exception.class will be handled in test
     */
    @RepeatedIfExceptionsTest(repeats = 3)
    void reRunTest() throws IOException {
        throw new IOException("Error in Test");
    }

    @RetryingTest(maxAttempts = 3, onExceptions = MyException.class)
    void reRunExceptionTest() throws IOException {
        System.out.println("Run test");
        throw new IOException("Error in Test");
    }

    @RetryingTest(maxAttempts = 3, onExceptions = MyException.class)
    void reRunMyExceptionTest() throws MyException {
        System.out.println("Run test");
        throw new MyException();
    }

    @Test
    void reRunMyExceptionGloballyTest() throws MyException {
        System.out.println("Run test");
        throw new MyException();
    }
}
