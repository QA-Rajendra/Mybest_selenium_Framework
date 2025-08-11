package org.example.base;

import org.example.utils.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.example.drivers.DriverManager.getDriver;

public class CommonToAllPage {

    // Constructor: runs before every page object class call
    public CommonToAllPage() {
        // Optional: add logs, pre-setup, DB connection, etc.
    }

    /**
     * Open URL from properties file
     * @param key Key in properties file (e.g., "url", "prodUrl")
     */
    public void openUrl(String key) {
        String url = PropertiesReader.readKey(key);
        getDriver().get(url);
    }

    /** Click element using By locator */
    public void clickElement(By by) {
        getDriver().findElement(by).click();
    }

    /** Click element using WebElement */
    public void clickElement(WebElement element) {
        element.click();
    }

    /** Enter text using By locator */
    public void enterInput(By by, String value) {
        getDriver().findElement(by).sendKeys(value);
    }

    /** Enter text using WebElement */
    public void enterInput(WebElement element, String value) {
        element.sendKeys(value);
    }

    /** Get text using By locator */
    public String getText(By by) {
        return getDriver().findElement(by).getText();
    }

    /** Get text using WebElement */
    public String getText(WebElement element) {
        return element.getText();
    }

    /** Select multiple options from a list/dropdown */
    public void selectMultipleOptions(WebElement dropdown, String xpathPattern, List<String> options) {
        clickElement(dropdown); // open dropdown
        for (String option : options) {
            WebElement element = getDriver().findElement(By.xpath(String.format(xpathPattern, option)));
            clickElement(element);
        }
    }

    /** Check if element is displayed */
    public boolean isElementDisplayed(By by) {
        try {
            return getDriver().findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Wait for element (basic explicit wait) */
    public WebElement waitForElement(By by, int seconds) {
        return new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), java.time.Duration.ofSeconds(seconds))
                .until(driver -> driver.findElement(by));
    }
}
