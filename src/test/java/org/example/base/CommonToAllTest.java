package org.example.base;




import org.example.drivers.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;





public class CommonToAllTest {
    @BeforeMethod
    public void Setup() {
        DriverManager.init();
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quit();
    }
}
