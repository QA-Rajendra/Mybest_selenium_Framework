package org.example.pages;


import org.example.base.CommonToAllPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.example.drivers.DriverManager.getDriver;


public class RegisterPage extends CommonToAllPage {

    // Locators (already fine as you had)
    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastName;

    @FindBy(xpath = "//textarea[@ng-model='Adress']")
    private WebElement address;

    @FindBy(xpath = "//input[@type='email']")
    private WebElement email;

    @FindBy(xpath = "//input[@type='tel']")
    private WebElement phone;

    @FindBy(id = "msdd")
    private WebElement languageDropdown;

    @FindBy(id = "Skills")
    private WebElement skillsDropdown;

    @FindBy(xpath = "//span[@role='combobox']")
    private WebElement selectCountry;

    @FindBy(id = "yearbox")
    private WebElement yearBox;

    @FindBy(xpath = "//select[@placeholder='Month']")
    private WebElement monthDropdown;

    @FindBy(id = "daybox")
    private WebElement dayBox;

    @FindBy(id = "firstpassword")
    private WebElement firstPassword;

    @FindBy(id = "secondpassword")
    private WebElement secondPassword;

    @FindBy(id = "imagesrc")
    private WebElement fileUploadInput;

    @FindBy(id = "submitbtn")
    private WebElement submitBtn;

    public RegisterPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // ✅ Select multiple options dynamically
    public void selectLanguages(List<String> languages) {
        selectMultipleOptions(languageDropdown, "//a[text()='%s']", languages);
    }

    // ✅ Upload file
    public void uploadFile(String filePath) {
        fileUploadInput.sendKeys(filePath);
    }

    // ✅ Generic dropdown selection by visible text
    private void selectDropdownByText(WebElement element, String visibleText) {
        new Select(element).selectByVisibleText(visibleText);
    }

    // ✅ Fill form method
    public void fillForm(String fName, String lName, String addr, String emailId, String phoneNo,
                         String gender, List<String> hobbies, List<String> languages,
                         String skills, String country, String dobYear, String dobMonth,
                         String dobDay, String password, String filePath) {

        enterInput(firstName, fName);
        enterInput(lastName, lName);
        enterInput(address, addr);
        enterInput(email, emailId);
        enterInput(phone, phoneNo);

        // Gender
        clickElement(getDriver().findElement(By.xpath("//input[@value='" + gender + "']")));

        // Hobbies dynamically
        for (String hobby : hobbies) {
            clickElement(getDriver().findElement(By.xpath("//input[@value='" + hobby + "']")));
        }

        // Languages
        selectLanguages(languages);

        // Skills
        selectDropdownByText(skillsDropdown, skills);

        // Country
        clickElement(selectCountry);
        clickElement(getDriver().findElement(By.xpath("//li[text()='" + country + "']")));

        // DOB
        selectDropdownByText(yearBox, dobYear);
        selectDropdownByText(monthDropdown, dobMonth);
        selectDropdownByText(dayBox, dobDay);

        // Passwords
        enterInput(firstPassword, password);
        enterInput(secondPassword, password);

        // File upload (optional)
        if (filePath != null && !filePath.isEmpty()) {
            uploadFile(filePath);
        }

        // Submit
        clickElement(submitBtn);
    }
}
