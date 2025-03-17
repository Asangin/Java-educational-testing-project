package com.skryl.edu;

import com.microsoft.playwright.*;
import com.skryl.edu.pages.LoginPage;
import com.skryl.edu.pages.MainPage;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

/**
 * @author Skryl D.V. on 2022-05-17
 */
public class PlaywrightIT {
    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false).setSlowMo(150));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void trySelectors() {
        new LoginPage(page)
                .goToLoginPage()
                .enterUserName("test")
                .enterPassword("test")
                .clickLogin()
                .addNewBook()
                .enterBookName("Book1")
                .enterISBNnumber("11111-11111")
                .chooseCategory(MainPage.BookCategory.Magazine)
                .chooseFormat(MainPage.BookFormat.eBook)
                .createBook();
    }

    @Test
    void playwriteTrace() {
        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium()
                     .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50))
        ) {
            BrowserContext context = browser.newContext();

            // Start tracing before creating / navigating a page.
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true));

            Page page = context.newPage();
            page.navigate("https://playwright.dev");

            // Stop tracing and export it into a zip archive.
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("target/trace.zip")));
        }
    }
}
