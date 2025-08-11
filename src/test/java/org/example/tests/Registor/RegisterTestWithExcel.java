package org.example.tests.Registor;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.UtilsExcel.ExcelUtil;
import org.example.base.CommonToAllTest;
import org.example.pages.RegisterPage;
import org.example.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.example.drivers.DriverManager.getDriver;

public class RegisterTestWithExcel  extends CommonToAllTest {

        private static final Logger logger = LogManager.getLogger(RegisterTestWithExcel.class);

    @DataProvider(name = "registerData")
    public Object[][] registerDataProvider() throws IOException {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/RegisterDataTestData.xlsx";
        return ExcelUtil.getTestData(filePath, "RegisterData");
    }


    @Test(dataProvider = "registerData")
    public void testRegisterForm(String firstName, String lastName, String address, String email, String phone, String gender, String hobbies, String languages, String skills, String country, String dobYear, String dobMonth, String dobDay, String password, String filePath) {
        WebDriver driver = getDriver();
        driver.get(PropertiesReader.readKey("url"));

        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.fillForm(
                firstName,
                lastName,
                address,
                email,
                phone,
                gender,
                Arrays.asList(hobbies.split(",")),
                Arrays.asList(languages.split(",")),
                skills,
                country,
                dobYear,
                dobMonth,
                dobDay,
                password,
                filePath
        );
    }

}

