package com.skryl.edu.parametrize;

import com.skryl.edu.SiteService;
import com.skryl.edu.extensions.SiteServiceInjector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

/**
 * This test demonstrates data injections throw test instance field
 *
 * @author Skryl D.V. on 2022-05-27
 */
@ExtendWith(SiteServiceInjector.class)
class SiteServiceTest {

    @Inject
    SiteService service;

    @Test
    void sayTest() {
        Assertions.assertEquals("This is a test", service.say());
    }

    @Test
    void sayMockTest() {
        Assertions.assertEquals("This is a mock", service.say());
    }

}