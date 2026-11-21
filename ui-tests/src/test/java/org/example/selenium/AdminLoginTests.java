package org.example.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminLoginTests extends BaseSeleniumTest {

    @Test
    void loginWithWrongCredentials_accessIsDenied() {
        driver.get(BASE_URL + "/login");

        driver.findElement(By.id("username")).sendKeys("wrongUser");
        driver.findElement(By.id("password")).sendKeys("wrongPassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Возвращаемся на страницу входа — ждём форму заново
        wait.until(ExpectedConditions.urlContains("/login"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        // При ошибке в URL появляется параметр error (типовое поведение Spring Security)
        assertTrue(driver.getCurrentUrl().contains("error"),
                "При неверных учётных данных должен произойти возврат на страницу входа с ошибкой");
    }
}

