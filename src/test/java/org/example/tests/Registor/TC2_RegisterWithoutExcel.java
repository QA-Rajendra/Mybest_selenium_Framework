package org.example.tests.Registor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.CommonToAllTest;
import org.example.pages.RegisterPage;
import org.example.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;



import java.util.Arrays;

import static org.example.drivers.DriverManager.getDriver;


public class TC2_RegisterWithoutExcel extends CommonToAllTest {
    private static final Logger logger = LogManager.getLogger(TC2_RegisterWithoutExcel.class);
    @Test
    public void testRegisterForm() {
        logger.info("🚀 Starting Main");

        WebDriver driver = getDriver();

        // Step 1: Navigate
        driver.get(PropertiesReader.readKey("url"));

        // Create RegisterPage instance using the inherited WebDriver from CommonToAllTest
        RegisterPage registerPage = new RegisterPage(getDriver());



        // Fill out the form
        registerPage.fillForm(
                "John",                      // First Name
                "Doe",                       // Last Name
                "123 Main St",               // Address
                "john.doe@example.com",      // Email
                "9876543210",                 // Phone
                "Male",                       // Gender
                Arrays.asList("Cricket", "Movies"), // Hobbies
                Arrays.asList("English", "Spanish"), // Languages
                "Java",                       // Skills
                "India",                      // Country
                "1990",                       // DOB Year
                "July",                       // DOB Month
                "25",                         // DOB Day
                "Test@1234",                  // Password
                "D:\\APIlearnProject\\MyBestSeleniumFramework\\images"      // File Upload
        );
    }
}


