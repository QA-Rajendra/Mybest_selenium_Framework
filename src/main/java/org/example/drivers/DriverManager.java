package org.example.drivers;

import org.example.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverManager {

    // ThreadLocal for parallel execution support
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Get current driver instance
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Set driver instance
    private static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    // Initialize WebDriver
    public static void init() {
        if (getDriver() == null) {
            String browser = PropertiesReader.readKey("browser");
            String url = PropertiesReader.readKey("url");
            int timeout = Integer.parseInt(PropertiesReader.readKey("timeout"));

            if (browser == null || browser.isEmpty()) {
                browser = "chrome"; // Default browser
            }
            browser = browser.toLowerCase();

            WebDriver newDriver;

            switch (browser) {
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized", "--guest");
                    newDriver = new EdgeDriver(edgeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    newDriver = new FirefoxDriver(firefoxOptions);
                    break;

                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    newDriver = new ChromeDriver(chromeOptions);
                    break;
            }

            newDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
            newDriver.get(url);
            setDriver(newDriver);
        }
    }

    // Close only current tab
    public static void down() {
        if (getDriver() != null) {
            getDriver().close();
        }
    }

    // Quit browser completely
    public static void quit() {
        if (getDriver() != null) {
//            getDriver().quit();
//            driver.remove();
        }
    }
}
