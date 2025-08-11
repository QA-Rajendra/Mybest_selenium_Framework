package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


import static org.example.drivers.DriverManager.getDriver;

/**
 * WaitHelpers - Utility class for managing waits in Selenium WebDriver.
 * Supports:
 *  - Thread.sleep() (use sparingly)
 *  - Implicit waits
 *  - Explicit waits (WebDriverWait)
 *  - Fluent waits
 *
 * Can be reused in any project by importing and calling static methods.
 */
public class WaitHelpers {

    // Default Wait Time (can be overridden in methods)
    private static final int DEFAULT_WAIT_SECONDS = 10;

    /** JVM Wait - Hard wait (Use only for debugging) */
    public static void waitJVM(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread sleep interrupted", e);
        }
    }

    /** Implicit Wait */
    public static void setImplicitWait(WebDriver driver, int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /** Explicit Wait - Wait for element to be visible (custom time) */
    public static void waitForVisibility(WebDriver driver, By locator, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Explicit Wait - Wait for element to be visible (default time) */
    public static void waitForVisibility(By locator) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_WAIT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Wait for specific text to be present in element */
    public static void waitForTextToBePresent(WebDriver driver, WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    /** Fluent Wait - Custom polling and timeout */
    public static WebElement waitForElementWithFluentWait(WebDriver driver, By locator, int timeoutSeconds, int pollingSeconds) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofSeconds(pollingSeconds))
                .ignoring(NoSuchElementException.class);

        return wait.until(driver1 -> driver1.findElement(locator));
    }

    /** Wait for presence of element in DOM */
    public static WebElement waitForPresence(By locator) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_WAIT_SECONDS))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /** Wait for visibility of element (WebElement) */
    public static WebElement waitForVisibility(WebElement element) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_WAIT_SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /** Get WebElement without wait (Direct Access) */
    public static WebElement getElement(By locator) {
        return getDriver().findElement(locator);
    }
}
