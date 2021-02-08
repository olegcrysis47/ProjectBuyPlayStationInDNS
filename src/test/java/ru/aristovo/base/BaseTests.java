package ru.aristovo.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseTests {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void beforeEach() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 50, 1000);

        // 1. открыть dns-shop
        String baseUrl = "https://www.dns-shop.ru/";
        driver.get(baseUrl);

    }

    @AfterEach
    void afterEach() {
        // закрываем драйвер
        driver.quit();
    }
}
